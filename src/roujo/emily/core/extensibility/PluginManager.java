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
	
	public boolean reloadPlugin(String pluginName) {
		if(loadedPlugins.containsKey(pluginName)) {
			PluginInfo pluginInfo = loadedPlugins.get(pluginName);
			return unloadPlugin(pluginName) && loadPlugin(pluginInfo.getPluginFile()) != null;
		} else {
			System.out.println(pluginName + " isn't loaded.");
			return false;
		}
		
	}
	
	public String loadPlugin(File pluginFile) {		
		try {
			String pluginPackage = pluginFile.getName().substring(0, pluginFile.getName().length() - 4);
			URL pluginURL = new URL("file:" + pluginFile.getAbsolutePath());
			URLClassLoader loader = URLClassLoader.newInstance(new URL[]{ pluginURL }, getClass().getClassLoader());
			Class<? extends Plugin> pluginClass = Class.forName(pluginPackage + ".PluginController", true, loader).asSubclass(Plugin.class);
			Constructor<? extends Plugin> constructor = pluginClass.getConstructor();
			Plugin plugin = constructor.newInstance();
			
			plugin.load();
			PluginInfo pluginInfo = new PluginInfo(pluginFile, plugin);
			String pluginName = pluginInfo.getPluginName();
			loadedPlugins.put(pluginName, pluginInfo);
			loader.close();
			
			processLoadedPlugin(pluginInfo);
			System.out.println("Plugin " + pluginName + " has been loaded successfully.");
			return pluginName;
		} catch(ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
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
