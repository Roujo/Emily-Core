package roujo.emily.core.extensibility.util;

import roujo.emily.core.contexts.MessageContext;
import roujo.emily.core.util.InternalUser;

import java.util.regex.Pattern;

public abstract class Trigger {
    private final String name;
    private final Pattern pattern;
    private final String description;

    protected Trigger(String name, String pattern, String description) {
        this.name = name;
        this.pattern = Pattern.compile(pattern);
        this.description = description;
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

    protected void logError(MessageContext context, String message) {
        context.getBot().sendMessage(context.getSender(), message);
    }

    protected void sendMessageBack(MessageContext context, String message) {
        sendMessageBack(context, message, false);
    }

    protected void sendMessageBack(MessageContext context, String message,
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
}
