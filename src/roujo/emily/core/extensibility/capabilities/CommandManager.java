package roujo.emily.core.extensibility.capabilities;

import roujo.emily.core.MessageContext;
import roujo.emily.core.extensibility.Plugin;

public interface CommandManager extends Plugin {
	boolean matchesMessage(MessageContext context);
	boolean parseMessage(MessageContext context);
}
