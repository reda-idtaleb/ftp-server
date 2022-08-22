package command;

import java.io.File;

import ftp.FTPClientSession;
import utils.FTPCommandParser;
import utils.FTPServerReply;

/** Changes the current working directory
 * 
 * @author laghmich
 * @author idtaleb
 *
 */
public class CwdCommand implements Command {

	@Override
	public void execute(FTPClientSession server) {
		FTPCommandParser parser = server.getCommandParser();
		if (!parser.containsArgs()) 
			server.sendResponse(FTPServerReply.FTP_501_ERROR_SYNTX);
		else {
			String path = parser.getArgs()[0];
			System.out.println("CWD currentDir: "+server.getCurrentDirectory());
			/**if (!server.getCurrentDirectory().contains(path))
				path = server.getCurrentDirectory() + path;	*/
			System.out.println("CWD path: "+path);
			File target = new File(path);
			if (target.exists() && target.isDirectory()) {
				server.setCurrentDirectory(path);				
				server.sendResponse(FTPServerReply.FTP_250);
			}
			else {
				FTPServerReply reply = FTPServerReply.FTP_550_ERROR_FILE;
				reply.setReply("Failed to change directory.");
				server.sendResponse(reply);
			}
		}
	}
}

