package roujo.emily.core.extensibility;

import roujo.emily.core.contexts.CommandContext;
import roujo.emily.core.contexts.MessageContext;

public abstract class Plugin {
	private String name;
	
	public Plugin(String name) {
		this.name = name;
	}
	
	public final String getName() {
		return name;
	}
	
	public abstract boolean load();
	public abstract boolean unload();
	
	public void onMessage(MessageContext context) {
	}
	
	public void onCommand(CommandContext context) {
	}
}
