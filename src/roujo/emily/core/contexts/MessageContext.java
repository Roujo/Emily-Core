package roujo.emily.core.contexts;

import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

import roujo.emily.core.util.InternalUser;
import roujo.emily.core.util.UserHelper;

public class MessageContext {
	private final PircBotX bot;
	private final String message;
	private final String origin;
	private final boolean isPrivateMessage;
	private final User sender;
	private final InternalUser internalUser;

	public MessageContext(MessageEvent<PircBotX> event) {
		this(event.getBot(), event.getMessage(), event.getChannel().getName(),
				false, event.getUser(), UserHelper.getUserByNick(event
						.getUser().toString()));
	}

	public MessageContext(PrivateMessageEvent<PircBotX> event) {
		this(event.getBot(), event.getMessage(), event.getUser().getNick(),
				true, event.getUser(), UserHelper.getUserByNick(event.getUser()
						.toString()));
	}

	protected MessageContext(PircBotX bot, String message, String origin,
			boolean isPrivateMessage, User sender, InternalUser internalUser) {
		this.bot = bot;
		this.message = message;
		this.origin = origin;
		this.isPrivateMessage = isPrivateMessage;
		this.sender = sender;
		this.internalUser = internalUser;
	}

	public PircBotX getBot() {
		return bot;
	}

	public String getMessage() {
		return message;
	}

	public String getOrigin() {
		return origin;
	}

	public boolean isPrivateMessage() {
		return isPrivateMessage;
	}

	public User getSender() {
		return sender;
	}

	public InternalUser getInternalUser() {
		return internalUser;
	}
}
