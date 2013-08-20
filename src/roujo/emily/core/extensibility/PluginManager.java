package roujo.emily.core.extensibility;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class PluginManager {
	public static final PluginManager INSTANCE = new PluginManager();
	
	private Map<String, PluginInfo> loadedPlugins;
	
	private PluginManager() {
		loadedPlugins = new LinkedHashMap<String, PluginInfo>();
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
		loadedPlugins.remove(pluginName);
		System.out.println(pluginName + " has been unloaded successfully.");
		return true;
	}
	
	protected Collection<PluginInfo> getPluginInfos() {
		return loadedPlugins.values();
	}
}
