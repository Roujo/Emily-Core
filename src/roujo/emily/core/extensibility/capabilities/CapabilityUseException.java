package roujo.emily.core.extensibility.capabilities;

public class CapabilityUseException extends Exception {
	private static final long serialVersionUID = 8503097664212213329L;

	public CapabilityUseException(String message) {
		super(message);
	}
	
	public CapabilityUseException(String message, Throwable t) {
		super(message, t);
	}
}
