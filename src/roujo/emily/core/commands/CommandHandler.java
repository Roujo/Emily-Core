package roujo.emily.core.commands;

import java.util.List;
import java.util.regex.Matcher;

import roujo.emily.core.MessageContext;
import roujo.emily.core.extensibility.PluginManager;
import roujo.emily.core.extensibility.capabilities.CommandManager;
import roujo.emily.core.util.StringHelper;

public class CommandHandler {
	public static boolean processMessage(MessageContext context) {
		Matcher matcher = StringHelper.getPatternFromNick(context.getBot().getNick()).matcher(context.getMessage());
		
		String message;
		if(matcher.matches()) {
			message = matcher.group(1);
			context.setProcessedMessage(message);
		} else {
			message = context.getMessage();
		}
		
		List<CommandManager> commandManagers = PluginManager.getInstance().getCommandManagers();
		for (CommandManager manager : commandManagers) {
			if(manager.matchesMessage(context)) {
				manager.processMessage(context);
				return true;
			}
		}
		return false;
	}
}
