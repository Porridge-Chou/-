package com.example.vapp.backend.netty;

import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    public void userEventTriggered(ChannelHandlerContext ctx,Object evt)throws Exception{
        IdleStateEvent event=(IdleStateEvent) evt;
        if(event.state()== IdleState.READER_IDLE){
            System.out.println("进入读空闲");
        }else if(event.state() == IdleState.WRITER_IDLE){
            System.out.println("进入写空闲");
        }else if(event.state() == IdleState.ALL_IDLE){
            System.out.println("channel 关闭之前:users 的数量为:"+ChatHandler.users.size());
            Channel channel = ctx.channel();
            //资源释放
            channel.close();
            System.out.println("channel关闭之后：users的数量为"+ChatHandler.users.size());

        }
    }
}
