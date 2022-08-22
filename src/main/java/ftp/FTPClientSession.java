package ftp;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import command.Command;
import command.invoker.CommandInvoker;
import tcp.TCPConnection;
import utils.FTPCommands;
import utils.FTPServerReply;
import utils.FTPTransferMode;
import utils.FTPCommandParser;

/**
 * A class that represent a client session, and it's features.
 * @author idtaleb
 * @author laghmich
 */
public class FTPClientSession extends Thread{
	/**
	 * The string representing the end-of-line code.
	 */
	private final static String FILE_SEPERATOR = System.getProperty("file.separator");
	/**
	 * The Default server folder path to test
	 */
	private final static String DEFAULT_SERVER_PATH = new File(System.getProperty("user.dir")).getAbsolutePath() + FILE_SEPERATOR + "server_data" ;
	/**
	 * The correct username
	 */
	private static final String USERNAME = "anonymous";
	/**
	 * The correct password
	 */
	private static final String PASSWORD = "anonymous";
	/**
	 * The FTP control connection
	 */
	private TCPConnection controlConnection;
	/**
	 * The FTP  data connection 
	 */
	private TCPConnection dataConnection;
	/**
	 * The control socket 
	 */
	private Socket controlSocket;
	/**
	 * The parser of client commands
	 */
	private FTPCommandParser commandParser;
	/**
	 * The listening server port
	 */
	private int port;
	/**
	 * Tell if the client is connected
	 */
	private boolean isFinished;
	/**
	 * The root directory of the server
	 */
	private String root;
	/**
	 * The current directory
	 */
	private String currentDirectory;
	/**
	 * The FTP transfer mode
	 */
	private FTPTransferMode transferMode;
	/**
	 * The id of a client session
	 */
	private int clientID;
	
	/**
	 * Default constructor, connect to the 2121 default port. 
	 * @param client The client socket
	 * @param controlConnection The TCP control connection 
	 * @param id The id of the client session
	 */
	public FTPClientSession(Socket client, TCPConnection controlConnection, int id) {
		_initialize_();
		this.controlConnection = controlConnection;
		controlSocket = client;
		clientID = id;
	}

	/**
	 * Initialize all the parameters of the FTP connection.
	 */
	private void _initialize_() {	
		isFinished = false;
		root = DEFAULT_SERVER_PATH;
		currentDirectory = root;
		transferMode = FTPTransferMode.A; // AScii mode by default
		dataConnection = null;
	}
	
	/** 
	 * Sends a response to the client.
	 * @param command The reply to send. 
	 */
	public void sendResponse(FTPServerReply command) {
		controlConnection.sendResponse(command.getReply());
	}
	
	/** receive the client's request
	 * @return the client's request
	 * @throws IOException if an I/O error occurs
	 * 
	 */
	public String receiveRequest() throws IOException {
		return controlConnection.getClientRequest();
	}
	
	/**
	 * Get the appropriate {@link FTPCommands} depending on the command received
	 * from the client
	 * @return an instance of FTPCommands representing the command received from the 
	 * 		   client
	 * @throws IOException When an error occurred during reading the input control 
	 * 		   socket stream.
	 */
	public FTPCommands getClientCommand() throws IOException {
		commandParser = new FTPCommandParser();
		FTPCommands command = commandParser.parse(receiveRequest());
		System.out.println(commandParser);
		return command;
	}

	/**
	 * Disconnect from the client and initialize all the FTPServer parameters.
	 * @throws IOException 
	 */
	public void disconnect() throws IOException {
		controlConnection.disconnect();		
		this._initialize_();
	}
	
	/**
	 * Run a client session, Read it's input stream and execute the right command.
	 * Once The connection is finished, the input/output streams and the control socket
	 * are closed. The current thread is interrupted.
	 */
	@Override
	public void run() {
		try {
			sendResponse(FTPServerReply.FTP_220_WELCOME);
			while (!isFinished) {
				FTPCommands command = this.getClientCommand();			
				Command c = CommandInvoker.invoke(command);
				c.execute(this);
			}		
		} catch (IOException e) {			
			System.out.println("Closing client Sockets!");
		} finally {
			try {
				this.controlConnection.getReader().close();
				this.controlConnection.getWriter().close();
				controlSocket.close();
				System.out.println("Closing sockets. The client "+ clientID +" stopped");
				Thread.currentThread().interrupt();
			} catch (IOException e) {
				System.out.println("Error when closing sockets!");
			}	
		}
	}
   
   /**
    * 
    * @return The listening control port of the server
    */
   public int getControlPort() {
       return this.controlSocket.getLocalPort();
   }
	
	/**
	 * @return The listening local port used by the socket server
	 */
	public int getLocalPort() {
		return this.controlConnection.getLocalPort();
	}
	
	public Socket getControlSocket() {
		return controlSocket;
	}

	public void setControlSocket(Socket controlSocket) {
		this.controlSocket = controlSocket;
	}
	
	/**
	 * @return the commandParser
	 */
	public FTPCommandParser getCommandParser() {
		return commandParser;
	}
	
	/**
	 * @return the username
	 */
	public String getDefaultUsername() {
		return USERNAME;
	}
	
	/**
	 * @return the password
	 */
	public String getDefaultPassword() {
		return PASSWORD;
	}

	/**
	 * @return the fileSeperator
	 */
	public static String getFileSeperator() {
		return FILE_SEPERATOR;
	}

	/**
	 * @return the currentDirectory
	 */
	public String getCurrentDirectory() {
		return currentDirectory;
	}

	/**
	 * @param currentDirectory the currentDirectory to set
	 */
	public void setCurrentDirectory(String currentDirectory) {
		this.currentDirectory = currentDirectory;
	}

	/**
	 * @return the transferMode
	 */
	public FTPTransferMode getTransferMode() {
		return transferMode;
	}

	/**
	 * @param transferMode the transferMode to set
	 */
	public void setTransferMode(FTPTransferMode transferMode) {
		this.transferMode = transferMode;
	}
	
	/**
	 * @return the dataConnection
	 */
	public TCPConnection getDataConnection() {
		return dataConnection;
	}
	
	public void setDataConnection(TCPConnection dataConnection) {
		this.dataConnection = dataConnection;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @return the controlConnection
	 */
	public TCPConnection getControlConnection() {
		return controlConnection;
	}

	/**
	 * @return the clientID
	 */
	public int getClientID() {
		return clientID;
	}

	/**
	 * @return the isFinished
	 */
	public boolean isFinished() {
		return isFinished;
	}

	/**
	 * @param isFinished the isFinished to set
	 */
	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
	
}
