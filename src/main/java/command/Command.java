package command;

import java.io.IOException;

import ftp.FTPClientSession;

/**
 * An interface to handle the commands supported by the FTP server.
 * This is an implementation of the Command design pattern(https://en.wikipedia.org/wiki/Command_pattern)
 * @author idtaleb
 * @author laghmich
 */
public interface Command {
	/**
	 * execute the corresponding client command
	 * @param server The client connected to the FTP server
	 * @throws IOException 
	 */
	public void execute(FTPClientSession server) throws IOException;
}
