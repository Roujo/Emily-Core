package roujo.emily.core.extensibility.capabilities;

public enum Capability {
	ProcessCommands(CommandProcessor.class);
	
	private final Class<? extends CapabilityProcessor> capabilityProcessor;
	
	private Capability(Class<? extends CapabilityProcessor> capabilityProcessor) {
		this.capabilityProcessor = capabilityProcessor;
	}

	public Class<?> getCapabilityManager() {
		return capabilityProcessor;
	}
}
