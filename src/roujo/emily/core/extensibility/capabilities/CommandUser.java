package roujo.emily.core.extensibility.capabilities;

import roujo.emily.core.MessageContext;

public class CommandUser implements CapabilityUser<CommandProcessor> {
	private MessageContext context;
	
	public CommandUser(MessageContext context) {
		this.context = context;
	}
	
	@Override
	public Capability getRequestedCapability() {
		return Capability.ProcessCommands;
	}

	@Override
	public boolean use(CommandProcessor commandManager) {
		return commandManager.processCommand(context);
	}	
}
