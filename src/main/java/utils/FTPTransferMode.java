package utils;

/**
 * Enumeration of the supported transfer mode
 * @author idtaleb
 * @author laghmich
 */
public enum FTPTransferMode {
	/** ASCII transfer mode */
	A("ASCII"),
	/** EBCDIC transfer mode */
	EBCDIC("EBCDIC"),
	/** Image transfer mode */
	I("BINARY");
	
	private String value;
	
	private FTPTransferMode(String value) {
		this.value = value;
	}

	/**
	 * Get the FTP transfer mode corresponding to the given mode as the argument.
	 * @param mode The file transfer mode
	 * @return return the FTP transfer mode
	 * @throws IllegalArgumentException if the mode is not supported bu the FTP server
	 */
	public static FTPTransferMode getValueFrom(String mode) throws IllegalArgumentException{
		return FTPTransferMode.valueOf(mode.toUpperCase());
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
