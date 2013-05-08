package roujo.emily.core.extensibility.capabilities;

import roujo.emily.core.extensibility.Plugin;

public enum Capability {
	ManageCommands(CommandManager.class);
	
	private final Class<? extends Plugin> capabilityManager;
	
	private Capability(Class<? extends Plugin> capabilityManager) {
		this.capabilityManager = capabilityManager;
	}

	public Class<?> getCapabilityManager() {
		return capabilityManager;
	}
}
