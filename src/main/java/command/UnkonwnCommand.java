package command;

import ftp.FTPClientSession;
import utils.FTPServerReply;

/** The unknown command is an implemented command sent bu the user 
 * 
 * @author laghmich
 * @author idtaleb
 *
 */
public class UnkonwnCommand implements Command {

	@Override
	public void execute(FTPClientSession server) {
		server.sendResponse(FTPServerReply.FTP_502_NOT_IMPLEMENTED);
	}

}
