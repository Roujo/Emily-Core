package roujo.emily.core.extensibility.capabilities;

public enum Capability {
	ManageCommands(CommandManager.class);
	
	private final Class<? extends CapabilityManager> capabilityManager;
	
	private Capability(Class<? extends CapabilityManager> capabilityManager) {
		this.capabilityManager = capabilityManager;
	}

	public Class<?> getCapabilityManager() {
		return capabilityManager;
	}
}
