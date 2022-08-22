package command;

import java.io.File;

import ftp.FTPClientSession;
import utils.FTPServerReply;

/** Creates a new directory
 * 
 * @author laghmich
 * @author idtaleb
 *
 */
public class MKDCommand implements Command {

	@Override
	public void execute(FTPClientSession server) {
		if (server.getCommandParser().containsArgs()) {
			String name = server.getCommandParser().getArgs()[0];
		    File directory = new File(server.getCurrentDirectory() + FTPClientSession.getFileSeperator() + name );
			boolean isCreated = directory.mkdir();
			if (isCreated) 
				server.sendResponse(FTPServerReply.FTP_257_PATH_SUCCESS.customize(name + " created."));
			else 
				server.sendResponse(FTPServerReply.FTP_550_DIRECTORY_NOT_CREATED);
		}
		else 
			server.sendResponse(FTPServerReply.	FTP_501_ERROR_SYNTX);
	}

}
