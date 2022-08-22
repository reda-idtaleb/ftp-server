package main;

import ftp.FTPServer;

/**
 * 
 * @author laghmich
 * @author idtaleb
 *
 */
public class FTPServerLauncher {
	
	/** launches the FTP Server
	 * 
	 * @param args main arguments
	 */
	public static void main(String[] args) {
		new FTPServer().launch();		
	}

}
