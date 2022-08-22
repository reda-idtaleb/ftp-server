package command;

import java.io.IOException;
import java.net.InetAddress;

import ftp.FTPClientSession;
import tcp.TCPConnection;
import utils.FTPServerReply;

/** establishes the data connection with the passive mode
 * 
 * @author laghmich
 * @author idtaleb
 *
 */
public class PasvCommand implements Command{

	@Override
	public void execute(FTPClientSession server) {
		try {
			establishDataConnection(server);
		} catch (IOException e) {
			server.sendResponse(FTPServerReply.FTP_421_UNAVAILABLE_SERVICE);
		}
	}
	
	/**
	 * Establish the passive data connection
	 * @param server The server session in which we will 
	 * @throws IOException When an I/O occurs if the server cannot establish a data conncetion 
	 */
	public void establishDataConnection(FTPClientSession server) throws IOException {
		server.setDataConnection(new TCPConnection(0));
		int port = server.getDataConnection().getLocalPort();
		String adress = InetAddress.getLocalHost().getHostAddress();
		
	    int port1 = port / 256;
	    int port2 = port % 256;
	    
	    String[] adressSplitted = adress.split("\\.");
	    String reply = "Entering Passive Mode (" + adressSplitted[0] + "," + adressSplitted[1] + "," + adressSplitted[2] + ","
	            + adressSplitted[3] + "," + port1 + "," + port2 + ")";
		server.sendResponse(FTPServerReply.FTP_227_PASV_MODE.customize(reply));
		server.getDataConnection().accept();
	}

}
