package command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import ftp.FTPClientSession;
import utils.FTPServerReply;
import utils.FileInfoParser;

/**
 * Rename a file to a given name
 * @author idtaleb
 * @author laghmich
 */
public class RNTOCommand implements Command {

	@Override
	public void execute(FTPClientSession server) {
		if (server.getCommandParser().containsArgs()) {
			String newName = server.getCommandParser().getArgs()[0];
			if (newName.matches("^[a-zA-Z0-9]+$") ) {
				Path source = Paths.get(FileInfoParser.getCurrentFile());
				try {
					Files.move(source, source.resolveSibling(newName), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					server.sendResponse(FTPServerReply.FTP_553);
				}
				FTPServerReply reply = FTPServerReply.FTP_250;
				reply.setReply("File renamed successfuly.");
				server.sendResponse(reply);
			}
			else {
				server.sendResponse(FTPServerReply.FTP_553);
			}
		}
		else {
			server.sendResponse(FTPServerReply.FTP_501_ERROR_SYNTX);
		}
			
	}

}
