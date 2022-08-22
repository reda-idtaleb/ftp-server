package ftpServer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.*;

import ftp.FTPServer;
import utils.FTPServerReply;

public class FTPServerTest {
	private static final String SERVER_ADDRESS = "localhost";
	private FTPClientMock fakeFTPClient;
	private FTPServerMock fakeFTPServer;
	private int serverPort;
	public Socket socket;
	
	@BeforeEach
	public void init() throws UnknownHostException, IOException {	
		fakeFTPServer = new FTPServerMock(0);
		serverPort = fakeFTPServer.getControlConnection().getLocalPort();
		socket = new Socket(SERVER_ADDRESS, serverPort);
		fakeFTPClient = new FTPClientMock(serverPort, SERVER_ADDRESS);
	}
	 
	@Test 
	public void testClientSocketGetsCreated() throws IOException { 
		fakeFTPServer.getControlConnection().accept();
		assertNotNull(fakeFTPServer.getControlConnection().getServerSocket());
	}
	
	@Test
	public void testWriteOnControlSocket() throws IOException {
		fakeFTPServer.getControlConnection().accept();
		fakeFTPServer.getControlConnection().sendResponse(FTPServerReply.FTP_220_WELCOME.getReply());
		assertNotNull(fakeFTPServer.getControlConnection().getWriter());
	}
	
	/** ------ Mock creations ------ */
	private class FTPServerMock extends FTPServer{
		public FTPServerMock(int port) {
			super(port);
		}
	}
	
	private class FTPClientMock {
		public int serverPort;
		public String serverAddress;
		public BufferedReader reader;
		public PrintWriter writer;
		public Socket clientSocket;
		
		public FTPClientMock(int serverPort, String serverAddress) {
			this.serverPort = serverPort;
			this.serverAddress = serverAddress;
			try {
				SocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(SERVER_ADDRESS), serverPort);  
				clientSocket = new Socket();
				clientSocket.connect(socketAddress);
				init();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void init() throws IOException {
			InputStream in = clientSocket.getInputStream();
			InputStreamReader isr = new InputStreamReader(in);
			this.reader = new BufferedReader(isr);
			
			OutputStream out = clientSocket.getOutputStream();
			this.writer = new PrintWriter(out, true);
		}
		
		public void disconnect() throws IOException {
			clientSocket.close();
		}
		
		/**
		 * @return the socket
		 */
		public Socket getSocket() {
			return clientSocket;
		}

		/**
		 * @return the writer
		 */
		public PrintWriter getWriter() {
			return writer;
		}

		/**
		 * @return the reader
		 */
		public BufferedReader getReader() {
			return reader;
		}
	}

	
}
