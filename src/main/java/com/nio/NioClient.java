package com.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class NioClient {

    private final int port = 9090;
    private final String host = "localhost";
    private ByteBuffer buffer = ByteBuffer.allocate(16);

    private void run() throws IOException {
        Selector selector = Selector.open();

        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_CONNECT);
        channel.connect(new InetSocketAddress(host, port));

        BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if ("q".equalsIgnoreCase(line)) {
                    System.exit(0);
                }
                try {
                    queue.put(line);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                SelectionKey key =  channel.keyFor(selector);
                key.interestOps(SelectionKey.OP_WRITE);
                selector.wakeup();
            }
        }).start();

        while (true) {
            selector.select();
            for (SelectionKey key : selector.selectedKeys()) {
                if (key.isConnectable()) {
                    channel.finishConnect();
                    key.interestOps(SelectionKey.OP_WRITE);
                } else if (key.isReadable()) {
                    buffer.clear();
                    channel.read(buffer);
                    System.out.println("Received = " + new String(buffer.array()));
                } else if (key.isWritable()) {
                    String line = queue.poll();
                    if (line != null) {
                        channel.write(ByteBuffer.wrap(line.getBytes()));
                    }
                    key.interestOps(SelectionKey.OP_READ);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new NioClient().run();
    }
}
