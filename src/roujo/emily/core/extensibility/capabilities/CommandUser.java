package roujo.emily.core.extensibility.capabilities;

import roujo.emily.core.MessageContext;

public class CommandUser implements CapabilityUser<CommandManager> {
	private MessageContext context;
	
	public CommandUser(MessageContext context) {
		this.context = context;
	}
	
	@Override
	public Capability getRequestedCapability() {
		return Capability.ManageCommands;
	}

	@Override
	public boolean use(CommandManager commandManager) {
		return commandManager.processMessage(context);
	}	
}
