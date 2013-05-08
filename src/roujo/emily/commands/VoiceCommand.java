package roujo.emily.commands;

import roujo.emily.MessageContext;

public class VoiceCommand extends Command {

	protected VoiceCommand() {
		super("voice", "voice (?<args>.*)", "Voices a user",
				"voice user | voice #channel user", false);
	}

	@Override
	public boolean execute(MessageContext context) {
		String arguments = getArguments(context);
		if (context.isPrivateMessage() && arguments.matches("#[^ ,]+ [^# ,]+"))
			context.getBot().sendMessage("ChanServ", "voice " + arguments);
		else if (!context.isPrivateMessage() && arguments.matches("[^# ,]+"))
			context.getBot().sendMessage("ChanServ",
					"voice " + context.getChannel() + " " + arguments);
		else
			return false;
		return true;
	}

}
