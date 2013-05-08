package roujo.emily.commands;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;

import roujo.emily.MessageContext;

public class PartCommand extends Command {

	protected PartCommand() {
		super("part", "part (?<args>.*)", "Parts with the given channel", "part #channel reason", true);
	}

	@Override
	public boolean execute(MessageContext context) {
		String arguments = getArguments(context);
		if(arguments.equals("")) {
			sendUsageBack(context);
			return false;
		}
		
		String[] args = arguments.split(" ");
		PircBotX bot = context.getBot();
		for(Channel channel : bot.getChannels()) {
			if(channel.getName().equals(args[0])) {
				sendMessageBack(context, "Alright!");
				bot.sendMessage(args[0], "See you later!");
				if(args.length > 1)
					bot.partChannel(channel, args[1]);
				else
					bot.partChannel(channel, "Off and away...");
				return true;
			}
		}
		sendMessageBack(context, "I'm not in " + args[0] + " right now.");
		return false;
	}

	
}
