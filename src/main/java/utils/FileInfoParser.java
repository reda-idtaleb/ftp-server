package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;
import java.sql.Date;
import java.text.SimpleDateFormat;

/** Parse the file's informations such as name, permissions...
 * 
 * @author laghmich
 * @author idtaleb
 *
 */
public class FileInfoParser {
	
	/**
	 * The current file
	 */
	private static String currentFile;

	/** returns the file informations for listing
	 * 
	 * @param p the path's name
	 * @param file the file 
	 * @return a string containing the file's informations
	 * @throws IOException throws an exception when an I/O exception occurs
	 */
	public static String getFileInfo(Path p, File file) throws IOException {
		PosixFileAttributes attrs = Files.readAttributes(p, PosixFileAttributes.class);
		
		String permissions = PosixFilePermissions.toString(attrs.permissions());
		String user = attrs.owner().getName();
		String group = attrs.group().getName();
		
		Date date = new Date(file.lastModified());
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy MMM dd");
		String datelastModified = dFormat.format(date);
		
		String name = file.getName();
		
		String fileInfo = permissions + "\t" + user + "\t" + group + "\t" + datelastModified + "\t" + name + "\n";
		
		return fileInfo;
	}

	/**
	 * @return the currentFile
	 */
	public static String getCurrentFile() {
		return currentFile;
	}

	/**
	 * @param currentFile the currentFile to set
	 */
	public static void setCurrentFile(String currentFile) {
		FileInfoParser.currentFile = currentFile;
	}
}
