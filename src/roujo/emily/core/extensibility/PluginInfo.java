package roujo.emily.core.extensibility;

import java.io.File;

public class PluginInfo {
	private final Plugin plugin;
	private final File pluginFile;
	
	public PluginInfo(File pluginFile, Plugin plugin) {
		this.plugin = plugin;
		this.pluginFile = pluginFile;
	}

	public Plugin getPlugin() {
		return plugin;
	}
	
	public File getPluginFile() {
		return pluginFile;
	}

	public String getPluginName() {
		return plugin.getName();
	}
}
