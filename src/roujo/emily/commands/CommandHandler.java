package roujo.emily.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import roujo.emily.MessageContext;
import roujo.emily.util.StringHelper;

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
		
		for (CommandType type : CommandType.values()) {
			Command command = type.getCommand();
			Pattern pattern = command.getPattern(); 
			if (pattern.matcher(message).matches()) {
				if(command.isValidSender(context)) {
					return command.execute(context);
				} else {
					return false;
				}
			}
		}
		return false;
	}
}
