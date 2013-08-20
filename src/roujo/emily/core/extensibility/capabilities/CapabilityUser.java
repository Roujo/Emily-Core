package roujo.emily.core.extensibility.capabilities;

public interface CapabilityUser<T extends CapabilityProcessor> {
	public Capability getRequestedCapability();
	public boolean use(T capabilityManager) throws CapabilityUseException;
}
