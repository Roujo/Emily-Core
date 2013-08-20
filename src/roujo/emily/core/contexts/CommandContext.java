package roujo.emily.core.contexts;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

public class CommandContext extends MessageContext {
	private final String commandString;
	
	public CommandContext(MessageEvent<PircBotX> event, String commandString) {
		super(event);
		this.commandString = commandString;
	}

	public CommandContext(PrivateMessageEvent<PircBotX> event, String commandString) {
		super(event);
		this.commandString = commandString;
	}
	
	public String getCommandString() {
		return commandString;
	}
}
