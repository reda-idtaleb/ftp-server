package command;

import ftp.FTPClientSession;
import ftp.FTPServer;
import utils.FTPCommandParser;
import utils.FTPServerReply;

/** Sends the password of the user
 * 
 * @author laghmich
 * @author idtaleb
 *
 */
public class PassCommand implements Command {

	@Override
	public void execute(FTPClientSession server) {
		String password = "";
		FTPCommandParser parser = server.getCommandParser();
		if (parser.containsArgs()) 
			password = parser.getArgs()[0].toLowerCase();
		else
			password = "anonymous";
		if (password.contentEquals(server.getDefaultPassword())) {
			server.sendResponse(FTPServerReply.FTP_230_LOGIN_OK);
		}
		else {
			server.sendResponse(FTPServerReply.FTP_530_UNOPENED_SESSION);
		}
	}

}
