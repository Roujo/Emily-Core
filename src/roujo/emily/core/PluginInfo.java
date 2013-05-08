package roujo.emily.core;

import java.util.List;

import roujo.emily.core.capabilities.Capability;

public class PluginInfo {
	private final Plugin plugin;
	private final List<Capability> capabilities;
	private final String pluginName;
	
	public PluginInfo(String pluginName, Plugin plugin) {
		this.plugin = plugin;
		this.capabilities = plugin.getCapabilities();
		this.pluginName = pluginName;
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
