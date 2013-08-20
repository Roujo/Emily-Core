package roujo.emily.core;

public class State {
	public static final State INSTANCE = new State();
	
	private boolean shouldQuit;
	private String password;
	
	private State() {
		shouldQuit = false;
		password = "";
	}

	public boolean shouldQuit() {
		return shouldQuit;
	}

	public void setShouldQuit(boolean shouldQuit) {
		this.shouldQuit = shouldQuit;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;		
	}
}
