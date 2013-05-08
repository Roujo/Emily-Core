package roujo.emily.commands;

import roujo.emily.MessageContext;

public class HatsCommand extends Command {

	protected HatsCommand() {
		super("hats", ".*hats.*", "Who knows?", "hats", false);
	}

	@Override
	public boolean execute(MessageContext context) {
		sendMessageBack(context, "no u");
		return true;
	}
	
	
}
