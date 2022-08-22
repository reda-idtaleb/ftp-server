package command;

import java.io.File;

import ftp.FTPClientSession;
import utils.FTPCommandParser;
import utils.FTPServerReply;

/** Changes to Parent Directory
 * 
 * @author laghmich
 * @author idtaleb
 *
 */
public class CdupCommand implements Command {

	@Override
	public void execute(FTPClientSession server) {
		File parent = new File(server.getCurrentDirectory()).getParentFile();
		if (parent != null) {
			server.setCurrentDirectory(new File(server.getCurrentDirectory()).getParent());
			server.sendResponse(FTPServerReply.FTP_250);
		}
		else {
			FTPServerReply reply = FTPServerReply.FTP_550_ERROR_FILE;
			reply.setReply("Failed to change directory.");
			server.sendResponse(reply);
		}
	}
		

}

