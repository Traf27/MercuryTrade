package com.mercury.platform.client.handlers;

import com.mercury.platform.client.bus.UpdaterClientEventBus;
import com.mercury.platform.client.bus.event.UpdateReceivedEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Frost on 14.01.2017.
 */
public class ClientHandler extends SimpleChannelInboundHandler<Object> {


    private static final Logger LOGGER = LogManager.getLogger(ClientHandler.class);

    public ClientHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        LOGGER.debug("Server says \"{}\"", object);
        UpdateReceivedEvent event = new UpdateReceivedEvent(object.toString().getBytes());
        UpdaterClientEventBus.getInstance().post(event);
    }


    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {
        LOGGER.debug("Sending echo message to server");
        context.channel().writeAndFlush("echo");
    }


    @Override
    public void channelInactive(ChannelHandlerContext context) throws Exception {
        LOGGER.info("Channel {} is inactive", context.channel().id());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) throws Exception {
        LOGGER.error(cause);
    }

}