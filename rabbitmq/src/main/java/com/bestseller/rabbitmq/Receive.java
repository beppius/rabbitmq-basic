package com.bestseller.rabbitmq; //"Path"

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receive {

//    public class MyInt(){
//        private int t;
//        public MyInt(int c){this.t=c;}
//
//        int getAttr(){return this.t;}
//        void setAttr(int c){this.t =c};
//    }
    private final static String QUEUE_NAME = "hello";

    final private static String ACT1 = "Off";
    public static void main(String[]args) throws Exception {

        System.out.println("Hello World");
        ConnectionFactory factory = new ConnectionFactory(); // Una classe viene chiamata factory se serve ad instaziare un oggetto di un'altra classe
        factory.setHost("localhost");
        Connection connection = factory.newConnection(); // Overload dei metodi: Dato un output in ingresso + input
        Channel channel = connection.createChannel();  //Le eccezioni vanno sempre gestite

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + parsePayload(message) + "'");
            if(shouldClose(message)){
                gratefullyClose(connection, channel);
            }
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

    }

    private static void gratefullyClose(Connection connection, Channel channel) {
        System.out.println("Gratefully closing connection");
        try {
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        System.out.println("Bye bye!");
        return;
    }


    private static boolean shouldClose(String message) {
        if (message.equals(ACT1))
            return true;
        return false;
    }

    public static String parsePayload(String p){
        if(p.equals("Off"))
            return "Shutting down";
        return "Message";
    }
}
