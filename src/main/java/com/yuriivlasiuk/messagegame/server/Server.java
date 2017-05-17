package com.yuriivlasiuk.messagegame.server;

import com.yuriivlasiuk.messagegame.service.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Server socket
 */
public class Server {

    /**
     * List for accessing connections in different threads
     */
    private List<Connection> connections =
            Collections.synchronizedList(new ArrayList<Connection>());
    private ServerSocket server;

    /**
     * The constructor creates the server.
     * Then, for each connection, a Connection object is created and adds it to the connection list.
     * @param port Port of create server
     */
    public Server(int port) {
        try {
            server = new ServerSocket(port);

            while (true) {
                Socket socket = server.accept();

                Connection connection = new Connection(socket, connections.size());
                connections.add(connection);

                connection.start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    /**
     * Closes all threads of all connections as well as the server socket
     */
    private void closeAll() {
        try {
            server.close();

            synchronized(connections) {
                connections.stream().forEach((s) -> s.close());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Class for working with data of a specific connectionClass for working with data of a specific connection
     */
    private class Connection extends Thread {
        private ObjectInputStream in;
        private ObjectOutputStream out;
        private Socket socket;

        private int connectionId;

        /**
         * Initializes the fields of the object.
         * @param socket Socket get in server.accept()
         * @param connectionId id of connection. To not send to itself the same data
         */
        public Connection(Socket socket, int connectionId) {
            this.socket = socket;
            this.connectionId = connectionId;

            try {
                out = new ObjectOutputStream(
                        socket.getOutputStream());
                in = new ObjectInputStream(
                        socket.getInputStream());


            } catch (IOException e) {
                e.printStackTrace();
                close();
            }
        }

        /**
         * Get message from connection and send to other connections
         */
        @Override
        public void run() {
            Scanner sc = new Scanner(System.in);
            try {
                while (true) {
                    Message message = (Message)in.readObject();
                    connections.stream().filter((s) -> s.getConnectionId() != getConnectionId()).forEach((s) -> s.write(message));

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

        /**
         * Recording for a specific connectionRecording for a specific connection
         * @param message Message to be written
         */
        public void write(Message message) {
            try {
                out.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * When all operations complete close connections
         */
        public void close() {
            try {
                in.close();
                out.close();
                socket.close();


                connections.remove(this);
                if (connections.size() == 0) {
                    Server.this.closeAll();
                    System.exit(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**Used in the constructor for sending messages
         * @return id of connections
         */
        public int getConnectionId() {
            return connectionId;
        }
    }
}