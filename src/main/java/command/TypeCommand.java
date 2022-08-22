package command;

import ftp.FTPClientSession;
import utils.FTPServerReply;
import utils.FTPTransferMode;

/** The TYPE command is issued to inform the server of the type 
 * of data that is being transferred by the client.
 * 
 * @author laghmich
 * @author idtaleb
 *
 */
public class TypeCommand implements Command {

	@Override
	public void execute(FTPClientSession server) {
		if (server.getCommandParser().containsArgs()) {
			String[] param = server.getCommandParser().getArgs();
			if (param.length == 1) {
				try {
					FTPTransferMode mode = FTPTransferMode.getValueFrom(param[0]);
					server.setTransferMode(mode);
					FTPServerReply reply = FTPServerReply.FTP_200_OK;
					reply.setReply("Switching to " + mode.getValue() + " mode.");
					server.sendResponse(FTPServerReply.FTP_200_OK);
				} catch(IllegalArgumentException e) {
					server.sendResponse(FTPServerReply.FTP_501_ERROR_SYNTX);
				}
			}
				
		}
		else
			server.sendResponse(FTPServerReply.FTP_550_ERROR_FILE);
	}

}
