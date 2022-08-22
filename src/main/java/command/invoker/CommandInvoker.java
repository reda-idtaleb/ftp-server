package command.invoker;

import command.Command;
import utils.FTPCommands;

/**
 * Invoke the right FTP {@link Command}
 * @author idtaleb
 * @author laghmich
 */
public class CommandInvoker {
	
	/**
	 * Create an instance of {@link Command} from a given {@link FTPCommands}
	 * @param command The FTP client command
	 * @return return the concrete {@link Command} corresponding 
	 * 		   to the given command.
	 */
	public static Command invoke(FTPCommands command) {
		return command.getValue();
	}

}
