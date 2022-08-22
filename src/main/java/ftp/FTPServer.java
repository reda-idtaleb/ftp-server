package ftp;

import java.io.IOException;
import java.net.Socket;

import tcp.TCPConnection;
import utils.FTPServerReply;


/**
 * The FTP server launcher that launches a server session.
 * @author idtaleb
 * @author laghmich
 */
public class FTPServer {
	/**
	 * The default control port
	 */
	private static final int DEFAULT_CONTROL_PORT = 2121;
	/**
	 * The TCP control connection
	 */
	private TCPConnection controlConnection;
	/**
	 * indicates if a server is still running or not
	 */
	private static boolean isLaunching = true;
	/**
	 * A server session
	 */
	private FTPClientSession session;
	/**
	 * The FTP server listening port
	 */
	private int port;
	
	public FTPServer() {
		this(DEFAULT_CONTROL_PORT);
	}
	
	public FTPServer(int port) {
		this.port = port;
		controlConnection = new TCPConnection(port);
	}
		
	/**
	 * Launch the FTP server with the multi-threading feature
	 */
	public void launch() {	
		int idClient = 0;
		System.out.println("Server starts on port: " + controlConnection.getLocalPort());
		while(isLaunching) {
			try {				
				Socket client = controlConnection.accept();
				session = new FTPClientSession(client, controlConnection, idClient);			
				session.start();
				System.out.println("Client "+idClient);
				idClient++;
			} catch (IOException e) {
				System.out.println("Connection not established");
			}
		}
		try {
			controlConnection.getServerSocket().close();
			System.out.println("Server finished!");
		} catch (IOException e) {
			System.out.println("Error when terminating the server!");
			System.out.println("Cannot close the server socket!");
			System.exit(0);
		}
		
	}

	/**
	 * @return the controlConnection
	 */
	public TCPConnection getControlConnection() {
		return controlConnection;
	}

	/**
	 * @return the FTP session 
	 */
	public FTPClientSession getSession() {
		return session;
	}

}
