package com.bestseller.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

public class Sender {

    public static void main(String[] args) throws Exception {
        String QUEUE_NAME = "hello";
        Scanner scan = new Scanner(System.in);
        System.out.println("Messaggio: ");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        while(scan.hasNext()) {
            String payload = scan.next();
            if(payload.equals("STOP"))
                break;
            channel.basicPublish("", QUEUE_NAME, null, payload.getBytes());
            System.out.println(" [x] Sent '" + payload + "'");
        }

    }
}
