package com.example.vapp.backend.netty;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.vapp.backend.entity.ChatMessage;
import com.example.vapp.backend.enums.MsgActionEnum;
import com.example.vapp.backend.service.MessageService;
import com.example.vapp.backend.util.SpringUtil;
import com.example.vapp.backend.util.UUIDUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.Utf8FrameValidator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.List;

public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    //用于管理和记录客户端的channel
    public static ChannelGroup users=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        //获取客户端传输的信息
        String content =textWebSocketFrame.text();
        System.out.println("接收到的数据："+content);
        DataContent dataContent = JSONObject.parseObject(content, DataContent.class);
        Channel channel = channelHandlerContext.channel();
        Integer action=dataContent.getAction();



        if(action == MsgActionEnum.CONNECT.type){
           // System.out.println("初始化channel，把用的channel 和 userid 关联起来");
            String senderId=dataContent.getChatMessage().getFromId();
            UserChannelRel.put(senderId,channel);


        }
        else if(action == MsgActionEnum.CHAT.type){
            ChatMessage chatMessage=dataContent.getChatMessage();
            MessageService messageService = (MessageService) SpringUtil.getBean("messageService");
            String msgId = messageService.saveMsg(chatMessage);
            //测试
            //String msgId= UUIDUtil.getOneUUID();
            DataContent result=new DataContent();
            result.setChatMessage(chatMessage);


            //发送消息
            Channel receiverChannel = UserChannelRel.get(chatMessage.getToId());

            if(receiverChannel == null){
                //离线用户
            }else{
                Channel findChannel = users.find(receiverChannel.id());
                if(findChannel != null){
                    //用户在线
                    receiverChannel.writeAndFlush(
                            new TextWebSocketFrame(
                                    JSONObject.toJSONString(dataContent)
                            )
                    );
                }else{
                    //离线用户
                }
            }
        }
        else if(action == MsgActionEnum.SIGNED.type){
            MessageService messageService = (MessageService) SpringUtil.getBean("messageService");
            String msgIdsStr = dataContent.getExtand();
            messageService.updateMsgSigned(msgIdsStr);
        }else if(action == MsgActionEnum.KEEPALIVE.type){
            System.out.println("收到来自channel 为【"+channel+"】的心跳包");
        }
        else if(action == MsgActionEnum.PULL_HISTORY.type){
            MessageService messageService = (MessageService) SpringUtil.getBean("messageService");
            String[] array=dataContent.getExtand().split(",");
            List<ChatMessage> list=messageService.getHistoryMst(array[0],array[1]);
            JSONArray arr=new JSONArray();
            arr.addAll(list);
            JSONObject result=new JSONObject();
            result.put("action",5);
            result.put("arr",JSONArray.toJSONString(arr));
            channel.writeAndFlush(new TextWebSocketFrame(
                JSONObject.toJSONString(result)
            ));
        }


    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端加入");
        users.add(ctx.channel());

    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
       users.remove(ctx.channel());
       System.out.println("客户端断开,channel对应的长id为："+ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //发生了异常后关闭连接，同时从channelgroup移除
        ctx.channel().close();
        users.remove(ctx.channel());
    }
}
