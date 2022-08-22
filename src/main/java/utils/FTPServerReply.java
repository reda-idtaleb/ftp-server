package utils;


/**
 * Enumeration of all the possible FTP server reply
 * @author idtaleb
 * @author laghmich
 */
public enum FTPServerReply {
	FTP_150_LISTING("150 Here comes the directory listing."),	
	
	FTP_200_OK("200 OK"),
	FTP_220_WELCOME("220 FTP server (vsftpd)"),
	FTP_221_QUIT_SUCCESS("221 Quit server"),
	FTP_226_FILES_SENT("226 Directory send OK."),
	FTP_227_PASV_MODE("227"),
	FTP_230_LOGIN_OK("230 Login successful."),
	FTP_250("250 Directory successfully changed."),
	FTP_257_PATH_SUCCESS("257"),
	
	FTP_331_PASS_NEED("331 Please specify password"),
	FTP_350("350 File or directory exists, ready for destination name."),
	
	FTP_421_UNAVAILABLE_SERVICE("421 Unavailable service"),
	FTP_426_TRANSFER_ABORTED("426 Connection closed, transfer aborted."),
	FTP_451_FILE_NOT_FOUND("451 File not found"),
	
	FTP_501_ERROR_SYNTX("501 Syntax error in the command"),
	FTP_502_NOT_IMPLEMENTED("502 Command not implemented"),
	FTP_530_UNOPENED_SESSION("530 Please login with USER and PASS"),
	FTP_550_ERROR_FILE("550 File service not processed. File not accessible."),
	FTP_550_DIRECTORY_NOT_CREATED("550 Failed to create directory."),
	FTP_550_DIRECTORY_NOT_DELETED("550 Failed to delete directory."),
	FTP_553("553 Requested action not taken. File name not allowed.");	
	
	/**
	 * The message of server reply
	 */
	private String reply;
	
	/**
	 * Private constructor, initialize each reply of each enumeration.
	 * @param reply The message of a FTP server reply
	 */
	private FTPServerReply(String reply) {
		this.reply = reply;
	}
	
	/**
	 * Customize a server reply and return a new instance of the receiver.
	 * @param msg The customized message 
	 * @return an instance of {@link FTPServerReply} with the new customized reply.
	 */
	public FTPServerReply customize(String msg) {
		this.setReply(msg);
		return this;
	}
	
	/**
	 * @param reply The reply to set
	 */
	public void setReply(String reply) {
		String[] split = this.reply.split("\\s+");
		this.reply = split[0]+" "+reply;
	}
	
	/**
	 * 
	 * @return The message of the server's reply
	 */
	public String getReply() {
		return reply;
	}

}
