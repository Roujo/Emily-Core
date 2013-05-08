package roujo.emily.core.extensibility;

import java.util.ArrayList;
import java.util.List;

import roujo.emily.core.extensibility.capabilities.Capability;

public class PluginInfo {
	private final Plugin plugin;
	private final List<Capability> capabilities;
	private final String pluginName;
	
	public PluginInfo(String pluginName, Plugin plugin) {
		this.plugin = plugin;
		this.pluginName = pluginName;
		
		this.capabilities = new ArrayList<Capability>();
		for(Capability cap : Capability.values())
			if(cap.getCapabilityManager().isInstance(plugin))
				this.capabilities.add(cap);
	}

	public Plugin getPlugin() {
		return plugin;
	}

	public List<Capability> getCapabilities() {
		return capabilities;
	}

	public String getPluginName() {
		return pluginName;
	}
}
