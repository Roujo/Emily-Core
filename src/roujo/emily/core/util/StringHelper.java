package roujo.emily.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

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
		return string.matches("#[#A-z0-9_]*");
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
}
