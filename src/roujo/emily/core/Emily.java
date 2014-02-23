package roujo.emily.core;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;

import roujo.emily.core.extensibility.EventHandler;

public class Emily extends ListenerAdapter<PircBotX> {
	private PircBotX bot;

	public Emily(String nickname, String password) {
		State.INSTANCE.setPassword(password);
		this.bot = new PircBotX();
		this.bot.getListenerManager().addListener(EventHandler.INSTANCE);
		this.bot.setCapEnabled(true);

		bot.setName(nickname);
		bot.setAutoNickChange(true);
		bot.setLogin("Emily");
		bot.setVersion("Emily v" + Info.Version + " | IRC Bot based on PircBotX 1.9");
	}

	// Has to be reworked, so removed for now
	/*
	 * public void onConsoleMessage(String message) { Context context = new
	 * Context(this, getNick(), message);
	 * CommandHandler.processMessage(context); }
	 */

	public PircBotX getBot() {
		return bot;
	}
}
