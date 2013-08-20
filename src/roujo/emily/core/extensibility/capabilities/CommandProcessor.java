package roujo.emily.core.extensibility.capabilities;

import roujo.emily.core.MessageContext;

public interface CommandProcessor extends CapabilityProcessor {
	boolean processCommand(MessageContext context);
}
