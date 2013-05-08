package roujo.emily.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import roujo.emily.core.MessageContext;

public class StringHelper {
	private static final String defaultPrefixPattern = "(?:%s(?::|,) |%%)(.*)";
	private static Map<String, Pattern> patterns = new TreeMap<String, Pattern>();
	
	public static Pattern getPatternFromNick(String nick) {
		if(!patterns.containsKey(nick)) {
			Pattern pattern = Pattern.compile(String.format(defaultPrefixPattern, nick));
			patterns.put(nick, pattern);
			return pattern;
		} else {
			return patterns.get(nick);
		}
			
	}
	
	public static boolean isChannel(String string) {
		return string.matches("#[#A-z0-9]+");
	}
	
	public static String[] keepChannels(String[] strings) {
		List<String> channels = new ArrayList<String>();
		for(String string : strings) {
			if(isChannel(string))
				channels.add(string);
		}
		String[] channelArray = new String[channels.size()];
		return channels.toArray(channelArray);
	}
	
	public static String removeMessageFlags(MessageContext context, String message) {
		String botNick = context.getBot().getNick();
		if (message.startsWith("%")) {
			// Emily was called by prefix
			message = message.substring(1);
		} else if (message.startsWith(botNick)) {
			// Emily was reffered to by name
			int nickLength = botNick.length();
			// NickLength + 2, so that messages like "Emily, q" or "Emily: d"
			// work as intended
			if (message.length() > nickLength + 2) {
				if (message.charAt(nickLength) == ','
						|| message.charAt(nickLength) == ':') {
					message = message.substring(nickLength + 2);
				}
			}
		}
		return message;
	}
}
