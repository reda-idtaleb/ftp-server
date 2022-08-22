package command;

import java.io.File;

import ftp.FTPClientSession;
import utils.FTPServerReply;
import utils.FileInfoParser;

/**
 * Ask to rename a file 
 * @author idtaleb
 * @author laghmich
 */
public class RNFRCommand implements Command{

	@Override
	public void execute(FTPClientSession server) {
		if (server.getCommandParser().containsArgs()) {
			String name = server.getCommandParser().getArgs()[0];
			String path = server.getCurrentDirectory() + FTPClientSession.getFileSeperator() + name;
			FileInfoParser.setCurrentFile(path);
			File f = new File(path);
			if (f != null && f.exists()) {
				server.sendResponse(FTPServerReply.FTP_350);
			}
			else {
				server.sendResponse(FTPServerReply.FTP_550_ERROR_FILE);
			}
		}
		else {
			server.sendResponse(FTPServerReply.FTP_501_ERROR_SYNTX);
		}
	}

}
