package command;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import ftp.FTPClientSession;
import utils.FTPServerReply;

/** Transfer command is a command to send or receive data to/from the client
 * 
 * @author laghmich
 * @author idtaleb
 *
 */
public abstract class TransferCommand implements Command {
	
	@Override
	public void execute(FTPClientSession server) throws IOException {
		if (!server.getCommandParser().containsArgs()) {
			FTPServerReply reply = FTPServerReply.FTP_501_ERROR_SYNTX;
			reply.setReply("No filename given");
			server.sendResponse(FTPServerReply.FTP_501_ERROR_SYNTX);
		} else {
			String path = server.getCommandParser().getArgs()[0];
			if (!path.contains(server.getCurrentDirectory()))
				path = server.getCurrentDirectory() + FTPClientSession.getFileSeperator() + path;
			File file = new File(path);	
			// Data structure for binary mode
			BufferedOutputStream output = createBinaryOutput(server, file);
			BufferedInputStream input = createBinaryInput(server, file);;	
			
			// Data structure for ASCII mode
			BufferedReader in = createAsciiInput(server, file);;
	        PrintWriter out = createAsciiOutput(server, file);;
			try {
				// Binary mode			
				if (server.getTransferMode().getValue().equalsIgnoreCase("binary")) 			
					this.transferBinaryMode(server, file, output, input);
				// Data structure for ASCII mode
				else if (server.getTransferMode().getValue().equalsIgnoreCase("ascii") || 
						 server.getTransferMode().getValue().equalsIgnoreCase("EBCDIC")) 
					this.transferAsciiMode(server, file, out, in);			
			} catch (FileNotFoundException e) {
				server.sendResponse(FTPServerReply.FTP_550_ERROR_FILE);
			} catch (IOException e) {
				server.sendResponse(FTPServerReply.FTP_426_TRANSFER_ABORTED);
				server.getDataConnection().disconnect();
			} finally {
                try {
                	if (output != null)
                		output.close();
                	if (input != null)
                		input.close();
                } catch (Exception e) {
                	server.sendResponse(FTPServerReply.FTP_421_UNAVAILABLE_SERVICE);
                }
	        }	
			server.sendResponse(FTPServerReply.FTP_226_FILES_SENT);
		}
		server.getDataConnection().disconnect();
	}
	
	/**
	 * Transfer data file received from the input stream to output stream as ASCII transfer mode.
	 * @param server The server to which we will transfer the files
	 * @param file The file to transfer
	 * @param out The output stream corresponding to the file
	 * @param in The input stream of the data connection, to which we want transfer the file
	 * @throws FileNotFoundException When the file is not present
	 * @throws IOException When an error during the creation of output/input buffers
	 */
	private void transferAsciiMode(FTPClientSession server, File file, PrintWriter output, BufferedReader input) throws FileNotFoundException, IOException {
		// opening binary data mode connection
		server.sendResponse(FTPServerReply.FTP_150_LISTING.customize("Opening data connection for " + file.getName()));
		
		String ligne = null;
		while ((ligne = input.readLine()) != null) 
			output.println(ligne);
		
		output.close();
		input.close();
	}
	
	/**
	 * Transfer data file received from the input stream to output stream as binary transfer mode.
	 * @param server The server to which we will transfer the files
	 * @param file The file to transfer
	 * @param output The output stream corresponding to the file
	 * @param input The input stream of the data connection, to which we want transfer the file
	 * @throws FileNotFoundException When the file is not present
	 * @throws IOException When an error during the creation of output/input buffers
	 */
	private void transferBinaryMode(FTPClientSession server, File file, BufferedOutputStream output, BufferedInputStream input) throws FileNotFoundException, IOException {	
		// opening binary data mode connection
		FTPServerReply reply = FTPServerReply.FTP_150_LISTING;
		reply.setReply("Opening data connection for " + file.getName());
		server.sendResponse(reply);
				
		int bufSize = 1024;
		byte[] buffer = new byte[bufSize];
		int ligne = 0;
		while((ligne = input.read(buffer, 0, bufSize)) != -1) 
				output.write(buffer, 0, ligne);
		// close streams
		output.close();
		input.close();
	}
	
	protected abstract PrintWriter createAsciiOutput(FTPClientSession server, File file) throws IOException;

	protected abstract BufferedReader createAsciiInput(FTPClientSession server, File file) throws FileNotFoundException, IOException;

	protected abstract BufferedInputStream createBinaryInput(FTPClientSession server, File file) throws FileNotFoundException, IOException;

	protected abstract BufferedOutputStream createBinaryOutput(FTPClientSession server, File file) throws IOException;
}