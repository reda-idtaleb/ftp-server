package command;

import java.io.File;

import ftp.FTPClientSession;
import utils.FTPServerReply;

/** This command causes the directory specified in the pathname
 * to be removed as a directory (if the pathname is absolute)
 * or as a subdirectory of the current working directory (if
 * the pathname is relative).
 * 
 * @author laghmich
 * @author idtaleb
 *
 */
public class RMDCommand implements Command{

	@Override
	public void execute(FTPClientSession server) {
		if (server.getCommandParser().containsArgs()) {
			String name = server.getCommandParser().getArgs()[0];
			File directory = new File(server.getCurrentDirectory() + FTPClientSession.getFileSeperator() + name );
			boolean isDeleted = false;
		    if (directory.exists() && directory.isDirectory()) {
		    	isDeleted = directory.delete();
				if (isDeleted) {
					FTPServerReply reply = FTPServerReply.FTP_250;
					reply.setReply(name + " deleted.");
					server.sendResponse(reply);
				}
				else {
					server.sendResponse(FTPServerReply.FTP_550_DIRECTORY_NOT_DELETED);
				
				}
			}
			else {
				//not found or not a directory
				server.sendResponse(FTPServerReply.	FTP_550_ERROR_FILE);
			}
		}
		else {
			server.sendResponse(FTPServerReply.FTP_501_ERROR_SYNTX);
		}
	}

}
