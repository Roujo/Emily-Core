package roujo.emily.core;

import java.util.List;

import roujo.emily.core.capabilities.Capability;

public interface Plugin {
	List<Capability> getCapabilities();
	boolean load();
	boolean unload();
}
