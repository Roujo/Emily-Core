package roujo.emily.core.extensibility.capabilities;

import roujo.emily.core.MessageContext;

public interface CommandManager extends CapabilityManager {
	boolean processMessage(MessageContext context);
}
