package com.yuriivlasiuk.messagegame;

import com.yuriivlasiuk.messagegame.client.Client;
import com.yuriivlasiuk.messagegame.server.Server;

import java.util.Arrays;


public class App
{
    private static String ERROR_ARGUMENT_MESSAGE = "1 argument: appMode (server or client), 2 argument: using port ";

    /**
     * Entry point to the program, verify entry arguments and run program in the right mode
     * @param args The first argument is the server or client, the second argument is the port
     */
    public static void main( String[] args )
    {
        int port;
        if (args.length == 0) {
            System.out.println("You must enter arguments: " + ERROR_ARGUMENT_MESSAGE);
            System.exit(0);
        }

        String appMode = args[0];
        String stringPort = args[1];

        if (stringPort.matches("[-+]?\\d+") && appMode.matches("server|client")) {
            port = Integer.valueOf(stringPort);
            System.out.println("Use " + appMode + " mode\n" +
                    "On port:" + port);
            if (appMode.equals("server")) {
                new Server(port);
            } else {
                new Client(port);
            }

        } else {
            System.out.println(
                    "Invalid enter argument(" + Arrays.toString(args) + ") " + ERROR_ARGUMENT_MESSAGE);
        }



    }
}
