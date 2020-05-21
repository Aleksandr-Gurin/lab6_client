package ru.ifmo.se.client;

import java.io.IOException;

public class ClientLauncher {
    public static void main(String[] args)  {
        try {
            Client client = new Client(5002);
            client.start();
        } catch (InterruptedException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
