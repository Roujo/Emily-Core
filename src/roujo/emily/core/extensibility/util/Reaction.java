package roujo.emily.core.extensibility.util;

import roujo.emily.core.contexts.MessageContext;

/**
 * Created by Jonathan on 14-02-23.
 */
public abstract class Reaction extends Trigger {
    protected Reaction(String name, String pattern, String description) {
        super(name, pattern, description);
    }

    public abstract boolean react(MessageContext context);
}
