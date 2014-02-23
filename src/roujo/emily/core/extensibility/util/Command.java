package roujo.emily.core.extensibility.util;

import java.util.regex.Matcher;

import roujo.emily.core.contexts.CommandContext;
import roujo.emily.core.util.InternalUser;

public abstract class Command extends Trigger {
	private final String usage;
	private final boolean isSuperUserOnly;

	protected Command(String name, String pattern, String description,
			String usage, boolean isSuperUserOnly) {
        super(name, pattern, description);
		this.usage = usage;
		this.isSuperUserOnly = isSuperUserOnly;
	}

	public String getUsage() {
		return usage;
	}

	public boolean isSuperUserOnly() {
		return isSuperUserOnly;
	}

	public abstract boolean execute(CommandContext context);

	protected String getArguments(CommandContext context) {
		Matcher matcher = getPattern().matcher(context.getCommandString());
		if (matcher.matches())
			return matcher.group("args");
		else
			return "";
	}

	protected boolean isValidSender(CommandContext context) {
		InternalUser internalUser = context.getInternalUser();
		if (isSuperUserOnly) {
			return internalUser != null && internalUser.isSuper();
		} else {
			return internalUser == null || !internalUser.isBlackListed();
		}
	}

	protected void sendUsageBack(CommandContext context) {
		sendMessageBack(context, usage);
	}
}
