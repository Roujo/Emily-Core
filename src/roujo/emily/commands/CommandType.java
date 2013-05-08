package roujo.emily.commands;

public enum CommandType {
	Join(new JoinCommand()),
	Part(new PartCommand()),
	Quit(new QuitCommand()),
	Tell(new TellCommand()),
	
	Time(new TimeCommand()),
	Roll(new RollCommand()),
	Hats(new HatsCommand()),
	Echo(new EchoCommand()),
	
	Voice(new VoiceCommand()),
	
	Lion(new LionCommand()),
	Slap(new SlapCommand());
	
	private final Command command;
	
	private CommandType(Command command) {
		this.command = command;
	}
	
	public Command getCommand() {
		return command;
	}
}
