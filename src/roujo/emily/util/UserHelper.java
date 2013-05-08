package roujo.emily.util;

import java.util.Map;
import java.util.TreeMap;

public class UserHelper {
	public static Map<String, InternalUser> NICK_MAP;

	public static InternalUser getUserByNick(String nick) {
		if(NICK_MAP == null)
			initializeNickMap();
		return NICK_MAP.get(nick);
	}

	public static boolean isSuper(String nick) {
		InternalUser user = getUserByNick(nick);
		return user != null && user.isSuper();
	}
	
	public static boolean isOwner(String nick) {
		InternalUser user = getUserByNick(nick);
		return user != null && user.isOwner();
	}
	
	public static boolean isBlackListed(String nick) {
		InternalUser user = getUserByNick(nick);
		return user != null && user.isBlackListed();
	}
	
	private static void initializeNickMap() {
		NICK_MAP = new TreeMap<String, InternalUser>();
		for(InternalUser user : InternalUser.values())
			for(String nick : user.getNicks())
				NICK_MAP.put(nick, user);
	}	
}
