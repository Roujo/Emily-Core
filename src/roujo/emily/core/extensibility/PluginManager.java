package roujo.emily.core.extensibility;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import roujo.emily.core.extensibility.capabilities.Capability;
import roujo.emily.core.extensibility.capabilities.CapabilityManager;
import roujo.emily.core.extensibility.capabilities.CapabilityUser;
import roujo.emily.core.extensibility.capabilities.CommandManager;
import roujo.emily.core.extensibility.capabilities.CommandUser;

public class PluginManager {
	private static PluginManager INSTANCE = new PluginManager();
	
	public static PluginManager getInstance() {
		return INSTANCE;
	}
	
	private Map<String, PluginInfo> loadedPlugins;
	
	// Plugin types
	private Map<Capability, List<CapabilityManager>> capabilityManagers;
	
	private PluginManager() {
		loadedPlugins = new LinkedHashMap<String, PluginInfo>();
		capabilityManagers = new TreeMap<Capability, List<CapabilityManager>>();
		capabilityManagers.put(Capability.ManageCommands, new ArrayList<CapabilityManager>());
	}
	
	public boolean reloadPlugin(String pluginName, File pluginFile) {
		unloadPlugin(pluginName);
		return loadPlugin(pluginName, pluginFile);
	}
	
	public boolean loadPlugin(String pluginName, File pluginFile) {
		// TODO: Determine the plugin name according to package name instead of file name
		// For now, assume Plugin Name == Package Name
		if(loadedPlugins.containsKey(pluginName)) {
			System.out.println(pluginName + " was already loaded.");
			return false;
		}
		
		PluginInfo pluginInfo = null;
		
		try {
			URL pluginURL = new URL("file:" + pluginFile.getAbsolutePath());
			URLClassLoader loader = URLClassLoader.newInstance(new URL[]{ pluginURL }, getClass().getClassLoader());
			Class<? extends Plugin> pluginClass = Class.forName(pluginName + ".PluginController", true, loader).asSubclass(Plugin.class);
			Constructor<? extends Plugin> constructor = pluginClass.getConstructor();
			Plugin plugin = constructor.newInstance();
			plugin.load();
			pluginInfo = new PluginInfo(pluginName, plugin);
			loadedPlugins.put(pluginName, pluginInfo);
			loader.close();
			
			processLoadedPlugin(pluginInfo);
		} catch(ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
		
		System.out.println(pluginName + " has been loaded successfully.");
		return true;
	}

	public boolean unloadPlugin(String pluginName) {
		if(!loadedPlugins.containsKey(pluginName)) {
			System.out.println(pluginName + " isn't loaded.");
			return false;
		}
		
		PluginInfo pluginInfo = loadedPlugins.get(pluginName);
		pluginInfo.getPlugin().unload();
		processUnloadedPlugin(pluginInfo);
		loadedPlugins.remove(pluginName);
		System.out.println(pluginName + " has been unloaded successfully.");
		return true;
	}
	
	public boolean useCapability(CapabilityUser<? extends CommandManager> capabilityUser) {
		Capability cap = capabilityUser.getRequestedCapability();
		switch(cap) {
		case ManageCommands:
			for(CapabilityManager manager : capabilityManagers.get(cap)) {
				if(((CommandUser) capabilityUser).use((CommandManager) manager))
					return true;
			}
			return false;
		default:
			System.out.println("Unsupported capability!");
			return false;
		}
	}
	
	private void processLoadedPlugin(PluginInfo pluginInfo) {
		List<Capability> capabilities = pluginInfo.getCapabilities();
		for(Capability cap : capabilities) {
			if(capabilityManagers.containsKey(cap)) {
				capabilityManagers.get(cap).add((CapabilityManager) pluginInfo.getPlugin());
			}
		}
	}
	
	private void processUnloadedPlugin(PluginInfo pluginInfo) {
		List<Capability> capabilities = pluginInfo.getCapabilities();
		for(Capability cap : capabilities) {
			if(capabilityManagers.containsKey(cap)) {
				capabilityManagers.get(cap).remove(pluginInfo.getPlugin());
			}
		}
	}
}
