package roujo.emily.core.capabilities;

import roujo.emily.MessageContext;
import roujo.emily.core.Plugin;

public interface CommandManager extends Plugin {
	void parseMessage(MessageContext context);
}
