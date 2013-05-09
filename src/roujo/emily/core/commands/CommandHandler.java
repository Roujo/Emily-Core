package roujo.emily.core.commands;

import java.util.regex.Matcher;

import roujo.emily.core.MessageContext;
import roujo.emily.core.extensibility.PluginManager;
import roujo.emily.core.extensibility.capabilities.CommandUser;
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
		
		CommandUser user = new CommandUser(context);
		return PluginManager.getInstance().useCapability(user);
	}
}
