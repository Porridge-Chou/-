package com.example.vapp.backend.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.URI;


public class WSServerInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel channel) throws Exception {


        //获取管道
        ChannelPipeline channelPipeline=channel.pipeline();

        channelPipeline.addLast(new HttpServerCodec());



        channelPipeline.addLast(new ChunkedWriteHandler());
        channelPipeline.addLast(new HttpObjectAggregator(1024*64));

        channelPipeline.addLast(new IdleStateHandler(8,10,12));
        channelPipeline.addLast(new HeartBeatHandler());



        channelPipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        channelPipeline.addLast(new ChatHandler());







    }
}
