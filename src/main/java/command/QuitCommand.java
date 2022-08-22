package command;

import ftp.FTPClientSession;
import utils.FTPServerReply;

/**
 * This command terminates a USER and if file transfer is not
 * in progress, the server closes the control connection.
 * @author laghmich
 * @author idtaleb
 *
 */
public class QuitCommand implements Command {

	@Override
	public void execute(FTPClientSession server) {
		server.sendResponse(FTPServerReply.FTP_221_QUIT_SUCCESS);
		server.setFinished(true);
	}

}
