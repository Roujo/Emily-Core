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

import roujo.emily.core.commands.CommandHandler;
import roujo.emily.core.extensibility.capabilities.Capability;

public class PluginManager {
	private static PluginManager INSTANCE = new PluginManager();
	
	public static PluginManager getInstance() {
		return INSTANCE;
	}
	
	private Map<String, PluginInfo> loadedPlugins;
	
	// Plugin types
	private List<CommandHandler> commandHandlers;
	
	private PluginManager() {
		loadedPlugins = new LinkedHashMap<String, PluginInfo>();
		commandHandlers = new ArrayList<CommandHandler>();
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
			Class<? extends Plugin> hatManagerClass = Class.forName(pluginName + ".Plugin", true, loader).asSubclass(Plugin.class);
			Constructor<? extends Plugin> constructor = hatManagerClass.getConstructor();
			Plugin hatManager = constructor.newInstance();
			hatManager.load();
			pluginInfo = new PluginInfo(pluginName, hatManager);
			loadedPlugins.put(pluginName, pluginInfo);
			loader.close();
			
			processPlugin(pluginInfo);
		} catch(ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public boolean unloadPlugin(String pluginName) {
		if(!loadedPlugins.containsKey(pluginName)) {
			System.out.println(pluginName + " isn't loaded.");
			return false;
		}
		
		PluginInfo pluginInfo = loadedPlugins.get(pluginName);
		pluginInfo.getPlugin().unload();
		loadedPlugins.remove(pluginName);
		return true;
	}
	
	public PluginInfo getPluginInfo(String pluginName) {
		return loadedPlugins.get(pluginName);
	}
	
	private void processPlugin(PluginInfo pluginInfo) {
		List<Capability> capabilities = pluginInfo.getCapabilities();
		for(Capability cap : capabilities) {
			switch(cap) {
			// Every cast here should not fail, as the capabilities
			// were discovered by using Class.isInstance(plugin)
			case ManageCommands:
				commandHandlers.add((CommandHandler) pluginInfo.getPlugin());
				break;
			default:
				System.out.println("Unsupported capability!");
				break;			
			}
		}
	}
}
