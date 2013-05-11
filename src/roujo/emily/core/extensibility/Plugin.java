package roujo.emily.core.extensibility;


public interface Plugin {
	String getName();
	boolean load();
	boolean unload();
}
