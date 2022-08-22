package command;

import ftp.FTPClientSession;
import utils.FTPCommandParser;
import utils.FTPServerReply;

/** Sends the userName to the FTP Server
 * 
 * @author laghmich
 * @author idtaleb
 *
 */
public class UserCommand implements Command {
	
	@Override
	public void execute(FTPClientSession server) {
		String username = "";
		FTPCommandParser parser = server.getCommandParser();
		// init the user name
		if (parser.containsArgs()) 
			username = parser.getArgs()[0].toLowerCase();
		else
			username = "anonymous";
		// verification of the user name
		if(!username.contentEquals(server.getDefaultUsername())) 
			server.sendResponse(FTPServerReply.FTP_530_UNOPENED_SESSION.customize("This server is only anonymous."));
		else
			server.sendResponse(FTPServerReply.FTP_331_PASS_NEED);
	}

}
