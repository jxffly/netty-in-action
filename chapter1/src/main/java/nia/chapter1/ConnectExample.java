package nia.chapter1;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by kerr.
 *
 * Listing 1.3 Asynchronous connect
 *
 * Listing 1.4 Callback in action
 */
public class ConnectExample {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    /**
     * Listing 1.3 Asynchronous connect
     *
     * Listing 1.4 Callback in action
     * */
    public static void connect() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        Channel channel = CHANNEL_FROM_SOMEWHERE; //reference form somewhere
        // Does not block
        ChannelFuture future = channel.connect(
                new InetSocketAddress("127.0.0.1", 9999));
        future.addListener((ChannelFutureListener) future1 -> {
            if (future1.isSuccess()) {
                ByteBuf buffer = Unpooled.copiedBuffer(
                        "Hello", Charset.defaultCharset());
                ChannelFuture wf = future1.channel()
                        .writeAndFlush(buffer);
                wf.addListener((ChannelFutureListener) future11 -> System.out.println("already send msg"));
                // ...
            } else {
                Throwable cause = future1.cause();
                cause.printStackTrace();
            }
        });
    }
}