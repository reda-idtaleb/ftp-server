package command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import ftp.FTPClientSession;
import utils.FTPServerReply;
import utils.FileInfoParser;

/**
 * Lists the files of the current directory
 * @author laghmich
 * @author idtaleb
 *
 */
public class ListCommand implements Command {
	
	@Override
	public void execute(FTPClientSession server) {
		String fileToList = server.getCurrentDirectory();
		try {
			if (server.getCommandParser().containsArgs())
				fileToList = fileToList + FTPClientSession.getFileSeperator() + server.getCommandParser().getArgs()[0];
			list(fileToList, server);
		} catch (IOException e) {
			server.sendResponse(FTPServerReply.FTP_451_FILE_NOT_FOUND);
		}

	}
	
	/** Lists the files of the current directory
	 * 
	 * @param path the path of the current directory
	 * @throws IOException if an I/O exception occurs
	 */
	public void list(String path, FTPClientSession server) throws IOException {
		server.sendResponse(FTPServerReply.FTP_150_LISTING);
		// on liste les répertoires du directory courant
		server.getDataConnection().sendResponse(getListingResponse(path, server));
		// on établit la connection via la DATA CHANNEL	
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		server.getDataConnection().disconnect();
		server.sendResponse(FTPServerReply.FTP_226_FILES_SENT);
	}
	
	/** Creates the listing response to send to the user
	 * 
	 * @param path the path of the directory or the file to list
	 * @return the listing response
	 * @throws IOException if an I/O exception occurs
	 */
	public String getListingResponse(String path, FTPClientSession server) throws IOException {
		String listingResponse = "";
		File f = new File(path);
		if (!f.isDirectory()) {
		    listingResponse += FileInfoParser.getFileInfo(Paths.get(path), f);
		}
		else {
			File subFiles[] = f.listFiles();
			for (File file : subFiles) {
				Path p = Paths.get(path + FTPClientSession.getFileSeperator() + file.getName());
				listingResponse += FileInfoParser.getFileInfo(p, file) + '\r' + '\n';
			}
		}
		return listingResponse;
	}

}
