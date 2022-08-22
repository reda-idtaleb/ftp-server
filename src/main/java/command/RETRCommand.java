package command;

import java.io.BufferedInputStream;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;

import java.io.PrintWriter;

import ftp.FTPClientSession;

/** This command causes the server-DTP to transfer a copy of the
 * file, specified in the pathname, to the server- or user-DTP
 * at the other end of the data connection.  The status and
 * contents of the file at the server site shall be unaffected.
 * 
 * @author laghmich
 * @author idtaleb
 *
 */
public class RETRCommand extends TransferCommand implements Command {
	
	@Override
	protected PrintWriter createAsciiOutput(FTPClientSession server, File file) throws IOException {
		return new PrintWriter(server.getDataConnection().getConnectionSocket().getOutputStream(), true);
	}

	@Override
	protected BufferedReader createAsciiInput(FTPClientSession server, File file) throws FileNotFoundException {
		return new BufferedReader(new FileReader(file));
	}

	@Override
	protected BufferedInputStream createBinaryInput(FTPClientSession server, File file) throws FileNotFoundException {
		return new BufferedInputStream(new FileInputStream(file));
	}

	@Override
	protected BufferedOutputStream createBinaryOutput(FTPClientSession server, File file) throws IOException {
		return new BufferedOutputStream(server.getDataConnection().getConnectionSocket().getOutputStream());
	}

}
