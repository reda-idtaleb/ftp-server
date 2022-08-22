package utils;

import java.util.Arrays;

/**
 * FTP command parser. Parse the received command from the client
 * and provide the corresponding {@link FTPCommands}.
 * @author idtaleb
 * @author laghmich
 */
public class FTPCommandParser {
	/**
	 * The command to parse
	 */
	private String command;
	/**
	 * The argument of a command 
	 */
    private String[] args;
    /**
     * True if the command contains arguments, false otherwise.
     */
    private boolean containsArgs;
    
    /**
     * Default constructor
     */
	public FTPCommandParser() {
		this.command = "";
		this.containsArgs = false;
	}
	
	/**
	 * Parse the command received from the client and return the corresponding
	 * {@link FTPCommands}.
	 * @param command the string representing a command
	 * @return The corresponding {@link FTPCommands}
	 */
	public FTPCommands parse(String command) {
		if (command == null) {
            this.command = "";
            this.containsArgs = false;
            return FTPCommands.UNKONWN;
	    }
		String[] split = command.split("\\s+");
        this.command = split[0];
        if (split.length > 1) {
            this.containsArgs = true;
            this.args = new String[split.length - 1];
            for (int i = 1; i < split.length; i++) 
                this.args[i - 1] = split[i];
        }
		return FTPCommands.getValueFrom(this.command.toUpperCase());
	}

	/**
	 * @return the command sent by the FTP client
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @return the arguments of the client command
	 */
	public String[] getArgs() {
		return args;
	}

	/**
	 * @return true if the command contains the arguments. False otherwise.
	 */
	public boolean containsArgs() {
		return containsArgs;
	}

	@Override
	public String toString() {
		return "command: " + command + ", args: " + Arrays.toString(args);
	}
	
	
	
}
