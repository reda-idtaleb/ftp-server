package utils;

import command.*;

/**
 * Class that represents the supported command by the FTP server
 * @author idtaleb
 * @author laghmich
 */
public enum FTPCommands {
	/** if a command is not supported **/
	UNKONWN(new UnkonwnCommand()),
	USER(new UserCommand()),
	PASS(new PassCommand()),
	PASV(new PasvCommand()),
	PORT(new PortCommand()),
	PWD(new PwdCommand()),
	TYPE(new TypeCommand()),
	STOR(new StorCommand()),
	LIST(new ListCommand()),
	CWD(new CwdCommand()),
	CDUP(new CdupCommand()),
	MKD(new MKDCommand()),
	RMD(new RMDCommand()),
	RNFR(new RNFRCommand()),
	QUIT(new QuitCommand()),
	RNTO(new RNTOCommand()),
	RETR(new RETRCommand());
	
	/**
	 * The {@link Command} instance associated to the client command 
	 */
	private Command value;
	
	/**
	 * Private constructor.
	 * @param value An instance of {@link Command}
	 */
	private FTPCommands(Command value) {
		this.value = value;
	}

	/**
	 * Check if the command is present in the enumeration.
	 * @param command The command to search.
	 * @return true if the command is present, otherwise false. 
	 */
	public static boolean checkIfPresent(String command) {
		try {
			FTPCommands.valueOf(command.toUpperCase());	
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Get a command from a given string
	 * @param command a string representing a command
	 * @return The FTPCommand corresponding to the given string.
	 */
	public static FTPCommands getValueFrom(String command) {
		if (FTPCommands.checkIfPresent(command.toUpperCase()))
			return FTPCommands.valueOf(command.toUpperCase());
		else 
			return FTPCommands.UNKONWN;
	}

	/**
	 * @return the value
	 */
	public Command getValue() {
		return value;
	}
}
