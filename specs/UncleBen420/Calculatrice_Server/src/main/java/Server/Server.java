package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This server response the answer to a calcul Inspired from streamingtimeserver
 *
 * @author Rémy Vuagniaux
 */

public class Server {

	static final Logger LOG = Logger.getLogger(Server.class.getName());

	private final int testDuration = 15000;

	private final int listenPort = 420;

	/**
	 * This method does the entire processing.
	 */
	public void start() {
		System.out.println("Starting server...");

		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		BufferedReader reader = null;
		PrintWriter writer = null;

		while (true) {

			try {
				LOG.log(Level.INFO,
						"Creating a server socket and binding it on any of the available network interfaces and on port {0}",
						new Object[] { Integer.toString(listenPort) });
				serverSocket = new ServerSocket(listenPort, 50, InetAddress.getLocalHost());
				logServerSocketAddress(serverSocket);

				LOG.log(Level.INFO, "Waiting (blocking) for a connection request on {0} : {1}",
						new Object[] { serverSocket.getInetAddress(), Integer.toString(serverSocket.getLocalPort()) });
				clientSocket = serverSocket.accept();

				LOG.log(Level.INFO, "A client has arrived. We now have a client socket with following attributes:");
				logSocketAddress(clientSocket);

				LOG.log(Level.INFO, "Getting a Reader and a Writer connected to the client socket...");
				reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				writer = new PrintWriter(clientSocket.getOutputStream());

				LOG.log(Level.INFO, "Starting my job... sending current time to the client for {0} ms", testDuration);

				int chara;
				String clientData = "";
				
				

				while ((chara = reader.read()) >= 0) {
					clientData += (char) chara;
				}
				
				
				writer.print(clientData);
				
				if(clientData.equals("exit\n")) {
					writer.println("fermeture du serveur");
					writer.flush();
					
					try {
						reader.close();
					} catch (IOException ex) {
						Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
					}
					writer.close();
					try {
						clientSocket.close();
					} catch (IOException ex) {
						Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
					}
					try {
						serverSocket.close();
					} catch (IOException ex) {
						Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
					}
					
					break;
					
				}

				double op1 = 0, op2 = 0;
				char operator = 0;
				String[] stringbuf = clientData.split(" ");
				

				try {

					op1 = Double.parseDouble(stringbuf[0]);
					op2 = Double.parseDouble(stringbuf[2]);
					operator = stringbuf[1].charAt(0);

				} catch (NumberFormatException e) {

					writer.println("Erreur pas un chiffre");

				}

				switch (operator) {

				case '+':

					writer.println("answer: " + (op1 + op2));
					break;

				case '-':

					writer.println("answer: " + (op1 - op2));
					break;

				case '*':

					writer.println("answer: " + (op1 * op2));
					break;

				case '/':

					writer.println("answer: " + (op1 / op2));
					break;
					
				case '^':

					writer.println("answer: " + Math.pow(op1, op2));
					break;

				default:

					writer.println("Error operation not accepted");
					break;
				}
				
				writer.flush();

			} catch (IOException ex) {
				LOG.log(Level.SEVERE, ex.getMessage());
			} finally {
				LOG.log(Level.INFO, "We are done. Cleaning up resources, closing streams and sockets...");
				try {
					reader.close();
				} catch (IOException ex) {
					Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
				}
				writer.close();
				try {
					clientSocket.close();
				} catch (IOException ex) {
					Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
				}
				try {
					serverSocket.close();
				} catch (IOException ex) {
					Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
				}
			}

		}

	}

	/**
	 * A utility method to print server socket information
	 *
	 * @param serverSocket the socket that we want to log
	 */
	private void logServerSocketAddress(ServerSocket serverSocket) {
		LOG.log(Level.INFO, "       Local IP address: {0}", new Object[] { serverSocket.getLocalSocketAddress() });
		LOG.log(Level.INFO, "             Local port: {0}",
				new Object[] { Integer.toString(serverSocket.getLocalPort()) });
		LOG.log(Level.INFO, "               is bound: {0}", new Object[] { serverSocket.isBound() });
	}

	/**
	 * A utility method to print socket information
	 *
	 * @param clientSocket the socket that we want to log
	 */
	private void logSocketAddress(Socket clientSocket) {
		LOG.log(Level.INFO, "       Local IP address: {0}", new Object[] { clientSocket.getLocalAddress() });
		LOG.log(Level.INFO, "             Local port: {0}",
				new Object[] { Integer.toString(clientSocket.getLocalPort()) });
		LOG.log(Level.INFO, "  Remote Socket address: {0}", new Object[] { clientSocket.getRemoteSocketAddress() });
		LOG.log(Level.INFO, "            Remote port: {0}", new Object[] { Integer.toString(clientSocket.getPort()) });
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");

		Server server = new Server();
		server.start();
	}

}
