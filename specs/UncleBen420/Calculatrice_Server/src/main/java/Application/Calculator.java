package Application;

import Client.Client;

public class Calculator {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Missing arguments : \n" +
                    "-s to lauch the server, -c to launch the client)");
        } else {
            if (args[0].equals("-s")) {

            } else if (args[0].equals("-c")) {

                if (args.length < 4) {
                    System.out.println("Need : serverIP serverPort Calculus");
                } else {
                    String serverIP = args[1];
                    int serverPort = Integer.parseInt(args[2]);
                    String calculus = args[3];

                    Client client = new Client();
                    client.sendCalculus(serverIP, serverPort, calculus);
                }
            } else {
                System.out.println("Wrong parameter : \n" +
                        "-s to lauch the server, -c to launch the client)");
            }
        }
    }
}
