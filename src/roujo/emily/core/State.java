package roujo.emily.core;

public class State {
	private boolean shouldQuit;
	
	public State() {
		shouldQuit = false;
	}

	public boolean shouldQuit() {
		return shouldQuit;
	}

	public void setShouldQuit(boolean shouldQuit) {
		this.shouldQuit = shouldQuit;
	}
}
