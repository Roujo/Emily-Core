package roujo.emily.core.commands;

import java.util.regex.Matcher;

import org.pircbotx.PircBotX;

import roujo.emily.core.MessageContext;
import roujo.emily.core.extensibility.PluginManager;
import roujo.emily.core.extensibility.capabilities.CapabilityUseException;
import roujo.emily.core.extensibility.capabilities.CommandUser;
import roujo.emily.core.util.StringHelper;

public class CommandHandler {
	public static boolean processMessage(MessageContext context) {
		Matcher matcher = StringHelper.getPatternFromNick(
				context.getBot().getNick()).matcher(context.getMessage());

		String message;
		if (matcher.matches()) {
			message = matcher.group(1);
			context.setProcessedMessage(message);
		} else {
			message = context.getMessage();
		}

		CommandUser user = new CommandUser(context);
		try {
			return PluginManager.getInstance().useCapability(user);
		} catch (CapabilityUseException e) {
			PircBotX bot = context.getBot();
			String errorMessage = "I'm sorry, but an error occured while processing your command.";
			if (context.isPrivateMessage()) {
				bot.sendMessage(context.getUser(), errorMessage);
			} else {
				bot.sendMessage(context.getChannel(), errorMessage);
			}
			return false;
		}
	}
}
