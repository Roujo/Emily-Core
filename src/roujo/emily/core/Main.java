package roujo.emily.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.pircbotx.PircBotX;

import roujo.emily.core.extensibility.PluginManager;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Load all plugins
		for (File file : new File("plugins/").listFiles())
			if(file.getName().endsWith(".jar"))
				PluginManager.getInstance().loadPlugin(file);

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		Emily emily;
		if (args.length == 0) {
			String name, password;
			try {
				System.out
						.println("Emily: How would you like to name me? [Emily]");
				name = reader.readLine();
				if (name.equals(""))
					name = "Emily";
				password = reader.readLine();
			} catch (IOException e) {
				logError(e);
				System.out
						.println("Emily: I'll just call myself Emily, mmkay? =)");
				name = "Emily";
				password = "";
			}
			emily = new Emily(name, password);
		} else {
			emily = new Emily(args[0], args[1]);
		}

		PircBotX bot = emily.getBot();
		bot.setVerbose(true);

		if (args.length < 3) {
			System.out.println("Emily: Where should I connect? =)");
			String address;
			try {
				address = reader.readLine();
				System.out.println("Emily: Alright! =D");
				bot.connect(address);
			} catch (Exception e) {
				logError(e);
			}
		} else {
			System.out.println("Emily: Connecting to " + args[2]);
			try {
				bot.connect(args[2]);
			} catch (Exception e) {
				logError(e);
			}
			if (bot.isConnected() && args.length > 3) {
				String[] channels = Arrays.copyOfRange(args, 3, args.length);
				for (String channel : channels)
					bot.joinChannel(channel);
			}
		}

		// Console Messages have to be reworked
		/*
		 * while (emily.isConnected()) { try {
		 * emily.onConsoleMessage(reader.readLine()); } catch (IOException e) {
		 * logError(e); } }
		 */
	}

	private static void logError(Exception e) {
		System.out.println("Emily: Ouch! =(");
		e.printStackTrace();
	}

}
