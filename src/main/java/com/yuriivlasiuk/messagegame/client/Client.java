package com.yuriivlasiuk.messagegame.client;

import com.yuriivlasiuk.messagegame.service.Message;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client socket to connect to the server
 */
public class Client {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private boolean isReady = false;

    /**
     * Constructor, Connect to the server when initializing
     * @param port of the server
     */
    public Client(int port) {
        Scanner scanner = new Scanner(System.in);
        try {
            socket = new Socket("127.0.0.1", port);

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while (!isReady) {
                System.out.println("You are ready?(y)");
                if(scanner.nextLine().charAt(0) == 'y') {
                    isReady = true;
                }
            }

            int counter = 0;
            Message message = new Message();

            do {
                if(!message.currentIsEmpty()) {
                    System.out.println(message);
                }

                counter ++;
                message.setCounter(counter);
                message.setCurrentMessageToPrevious();
                System.out.println("Print your message:");
                message.setCurrentMessage(scanner.nextLine());


                out.writeObject(message);
                message = (Message)in.readObject();
            } while (counter < 10);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }


    /**
     * When all operations complete close connection
     */
    private void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


