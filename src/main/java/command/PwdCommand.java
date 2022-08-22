package command;

import ftp.FTPClientSession;
import utils.FTPServerReply;

/** Gets the current directory
 * 
 * @author laghmich
 * @author idtaleb
 *
 */
public class PwdCommand implements Command {

	@Override
	public void execute(FTPClientSession server) {
		String currentPath = "\""+server.getCurrentDirectory()+"\" is the current directory";
		FTPServerReply reply = FTPServerReply.FTP_257_PATH_SUCCESS;
		reply.setReply(currentPath);
		server.sendResponse(reply);
	}

}
