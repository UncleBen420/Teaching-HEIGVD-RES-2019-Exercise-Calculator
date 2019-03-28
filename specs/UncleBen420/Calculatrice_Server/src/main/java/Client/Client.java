package Client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    final static Logger LOG = Logger.getLogger(Client.class.getName());

    Socket clientSocket;

    final static int BUFFER_SIZE = 1024;

    public void sendCalculus(String serverIP, int serverPort, String calculus) {
        Socket clientSocket = null;
        OutputStream out = null;
        InputStream in = null;

        try {
            clientSocket = new Socket(InetAddress.getByName(serverIP), serverPort);
            out = clientSocket.getOutputStream();
            in = clientSocket.getInputStream();

            out.write(calculus.getBytes());

            ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();

            byte[] buffer = new byte[BUFFER_SIZE];
            int newBytes;

            while((newBytes = in.read(buffer)) != -1) {
                responseBuffer.write(buffer, 0, newBytes);
            }

            LOG.log(Level.INFO, "Response sent by the server: ");
            LOG.log(Level.INFO, responseBuffer.toString());
        } catch (IOException e) {
            LOG.log(Level.SEVERE, null, e);
        }
    }

}
