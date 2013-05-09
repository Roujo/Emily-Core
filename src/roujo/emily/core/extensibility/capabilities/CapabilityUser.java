package roujo.emily.core.extensibility.capabilities;

public interface CapabilityUser<T extends CapabilityManager> {
	public Capability getRequestedCapability();
	public boolean use(T capabilityManager);
}
