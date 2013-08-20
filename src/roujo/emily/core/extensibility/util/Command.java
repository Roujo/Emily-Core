package roujo.emily.core.extensibility.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import roujo.emily.core.contexts.CommandContext;
import roujo.emily.core.util.InternalUser;

public abstract class Command {
	private final String name;
	private final Pattern pattern;
	private final String description;
	private final String usage;
	private final boolean isSuperUserOnly;

	protected Command(String name, String pattern, String description,
			String usage, boolean isSuperUserOnly) {
		this.name = name;
		this.pattern = Pattern.compile(pattern);
		this.description = description;
		this.usage = usage;
		this.isSuperUserOnly = isSuperUserOnly;
	}

	public String getName() {
		return name;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public String getDescription() {
		return description;
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

	protected void logError(CommandContext context, String message) {
		context.getBot().sendMessage(context.getSender(), message);
	}

	protected void sendMessageBack(CommandContext context, String message) {
		sendMessageBack(context, message, false);
	}

	protected void sendMessageBack(CommandContext context, String message,
			boolean beautify) {
		// If requested...
		if (beautify) {
			// Add a little love
			InternalUser user = context.getInternalUser();
			if (user != null && user.isOwner())
				message += " <3";
		}

		// Add a little personalization
		if (!context.isPrivateMessage())
			message = context.getSender().getNick() + ": " + message;

		// Send it back ^^
		context.getBot().sendMessage(context.getOrigin(), message);
	}

	protected void sendUsageBack(CommandContext context) {
		sendMessageBack(context, usage);
	}
}
