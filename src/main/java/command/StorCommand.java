package command;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import ftp.FTPClientSession;
import utils.FTPServerReply;

/**
 * Implementation of the STOR FTP command, to upload files in the server.
 * Store receives a file from the client and saves it to the FTP server.
 * @author idtaleb
 * @author laghmich
 */
public class StorCommand extends TransferCommand implements Command {
	
	@Override
	protected PrintWriter createAsciiOutput(FTPClientSession server, File file) throws IOException {
		return new PrintWriter(new FileOutputStream(file), true);
	}

	@Override
	protected BufferedReader createAsciiInput(FTPClientSession server, File file) throws IOException {
		return new BufferedReader(new InputStreamReader(server.getDataConnection().getConnectionSocket().getInputStream()));
	}

	@Override
	protected BufferedInputStream createBinaryInput(FTPClientSession server, File file) throws IOException {
		return new BufferedInputStream(server.getDataConnection().getConnectionSocket().getInputStream());
	}

	@Override
	protected BufferedOutputStream createBinaryOutput(FTPClientSession server, File file) throws IOException {
		return new BufferedOutputStream(new FileOutputStream(file));
	}

}
