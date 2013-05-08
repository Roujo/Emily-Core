package roujo.emily.commands;

import java.util.Calendar;

import org.pircbotx.PircBotX;

import roujo.emily.MessageContext;

public class LionCommand extends Command {
	private Calendar nextLionAllowed;

	protected LionCommand() {
		super(">:3", ".*>:3.*", "...is that a lion?", ">:3]", false);
	}

	@Override
	public boolean execute(MessageContext context) {
		if (nextLionAllowed == null
				|| Calendar.getInstance().after(nextLionAllowed)) {
			String target = context.isPrivateMessage() ? context.getUser()
					.getNick() : context.getChannel().getName();
			PircBotX bot = context.getBot();
			bot.sendMessage(target, "OMG IT'S A LION GET IN THE CAR!");
			bot.sendAction(target, "gets in the car.");
			nextLionAllowed = Calendar.getInstance();
			nextLionAllowed.add(Calendar.MINUTE, 5);
			return true;
		} else {
			return false;
		}
	}
}
