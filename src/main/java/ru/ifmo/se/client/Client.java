package ru.ifmo.se.client;

import ru.ifmo.se.client.message.MessageReader;
import ru.ifmo.se.client.message.MessageWriter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class Client {
    private final InetAddress host;
    private final int port;
    private MessageWriter messageWriter;
    private MessageReader messageReader;
    private SocketAddress address;
    private DatagramChannel channel = DatagramChannel.open();
    private boolean connect = false;
    private Reader reader = new Reader();

    public Client(int port) throws IOException {
        this.host = InetAddress.getByName("localhost");
        this.port = port;
        address = new InetSocketAddress(host, port);
        channel.configureBlocking(false);
    }

    //Создает сокет, ридер райтер и запускает логику
    public void start() throws IOException, ClassNotFoundException, InterruptedException {
        this.messageWriter = new MessageWriter(channel, address);
        this.messageReader = new MessageReader(channel, address);
        String answer;
        while (true) {
            metka: while (true) {
                if (connect) {
                    messageWriter.writeRequest(reader.readCommand(new Scanner(System.in)));
                    messageReader.setAnswerWasRead(true);
                    long start = System.currentTimeMillis();
                    while (messageReader.isAnswerWasRead()) {
                        answer = messageReader.readAnswer();
                        if (answer != null) {
                            System.out.println(answer);
                            messageReader.setAnswerWasRead(false);
                        }
                        if (messageReader.isAnswerWasRead()&&System.currentTimeMillis()-start>3000L){
                            connect = false;
                            continue metka;
                        }
                    }
                } else {
                    System.out.println("Идет подключение к серверу");
                    messageWriter.writeRequest("connect");
                    Thread.sleep(1000);
                    String answer1 = messageReader.readAnswer();
                    if (answer1 != null && answer1.equals("connect")) {
                        System.out.println("Connect successful");
                        connect = true;
                    }
                }
            }
        }
    }
}
