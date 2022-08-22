package command;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import ftp.FTPClientSession;
import tcp.TCPConnection;
import utils.FTPCommandParser;
import utils.FTPServerReply;

/**
 * specifies an alternate data port
 * @author laghmich
 * @author idtaleb
 *
 */
public class PortCommand implements Command {
	
	@Override
	public void execute(FTPClientSession server) {
		FTPCommandParser command = server.getCommandParser();
		if (command.containsArgs()) {
			String[] args = command.getArgs()[0].split(",");
			if (args.length != 6)
				server.sendResponse(FTPServerReply.FTP_501_ERROR_SYNTX);
			else {
				if (isValidArgument(args)) {		
					String host = String.join(".", Arrays.copyOfRange(args, 0, 4));
					int port = Integer.parseInt(args[4]) * 256 + Integer.parseInt(args[5]);
					try {
						server.setDataConnection(new TCPConnection(0));
						server.getDataConnection().setConnectionSocket(new Socket(host, port));
					} catch (IOException e) {
						server.sendResponse(FTPServerReply.FTP_530_UNOPENED_SESSION.customize("Data active mode failed! Switch to passive mode."));
						return;
					}
				} 
				else 
					server.sendResponse(FTPServerReply.FTP_501_ERROR_SYNTX.customize("Some of the bytes are not valid! Only numbers accepted"));
			}		
		} else
			server.sendResponse(FTPServerReply.FTP_501_ERROR_SYNTX);
		server.sendResponse(FTPServerReply.FTP_200_OK.customize("PORT command successful."));
	}

	/**
	 * Check if the argument of the PORT command contains a bytes of numbers
	 * @param args The argument of the PORT command
	 * @return True if the argument is valid address + port. Otherwise false. 
	 */
	public boolean isValidArgument(String[] args) {
		for (String s : args) {
			try {
				Integer.parseInt(s);
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}

}
