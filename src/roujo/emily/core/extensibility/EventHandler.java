package roujo.emily.core.extensibility;

import java.util.regex.Matcher;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import roujo.emily.core.State;
import roujo.emily.core.contexts.CommandContext;
import roujo.emily.core.contexts.MessageContext;
import roujo.emily.core.util.StringHelper;

public class EventHandler extends ListenerAdapter<PircBotX> {
	public static final EventHandler INSTANCE = new EventHandler();

	private EventHandler() {
	}

	@Override
	public void onConnect(ConnectEvent<PircBotX> event) throws Exception {
		String password = State.INSTANCE.getPassword();
		if (!password.equals(""))
			event.getBot().identify(password);
	};

	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception {
		if (State.INSTANCE.shouldQuit())
			return;

		String command;
		if((command = extractCommandFromMessage(event)) != null) {
			CommandContext context = new CommandContext(event, command);
			for(PluginInfo pluginInfo : PluginManager.INSTANCE.getPluginInfos())
				pluginInfo.getPlugin().onCommand(context);
		} else {
			MessageContext context = new MessageContext(event);
			for(PluginInfo pluginInfo : PluginManager.INSTANCE.getPluginInfos())
				pluginInfo.getPlugin().onMessage(context);
		}
	}

	@Override
	public void onPrivateMessage(PrivateMessageEvent<PircBotX> event)
			throws Exception {
		if (State.INSTANCE.shouldQuit())
			return;

		String command;
		if((command = extractCommandFromMessage(event)) != null) {
			CommandContext context = new CommandContext(event, command);
			for(PluginInfo pluginInfo : PluginManager.INSTANCE.getPluginInfos())
				pluginInfo.getPlugin().onCommand(context);
		} else {
			MessageContext context = new MessageContext(event);
			for(PluginInfo pluginInfo : PluginManager.INSTANCE.getPluginInfos())
				pluginInfo.getPlugin().onMessage(context);
		}
	}
	
	public String extractCommandFromMessage(GenericMessageEvent<PircBotX> event) {
		Matcher matcher = StringHelper.getPatternFromNick(
				event.getBot().getNick()).matcher(event.getMessage().replaceAll("\\p{C}", ""));

		if (matcher.matches()) {
			return matcher.group(1);
		} else {
			return null;
		}
	}
}
