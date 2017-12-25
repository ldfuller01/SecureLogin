package me.pookeythekid.securelogin.executors;

import java.util.HashMap;

import me.pookeythekid.securelogin.Main;
import me.pookeythekid.securelogin.permissions.Permissions;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class MessageExe implements CommandExecutor {

	public Main plugin;

	public MessageExe(Main instance) {
		plugin = instance;
	}

	public static String main = ChatColor.DARK_GREEN + "[" + ChatColor.AQUA
			+ "SecureLogin" + ChatColor.DARK_GREEN + "]";
	public static String main2 = ChatColor.GREEN + "--==" + main
			+ ChatColor.GREEN + "==--";
	public HashMap<String, String> hash = new HashMap<String, String>();
	
	private int argLength;
	private int argCount;

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		if (commandLabel.equalsIgnoreCase("securelogin")
				|| commandLabel.equalsIgnoreCase("sl")) {
			sender.sendMessage(ChatColor.GREEN + main2);
			sender.sendMessage(ChatColor.GOLD + "/SecureLogin" + ChatColor.GRAY
					+ " : This Help Page.");
			sender.sendMessage(ChatColor.GOLD + "/Login" + ChatColor.GRAY
					+ " : Log Into The Server To Play.");
			sender.sendMessage(ChatColor.GOLD + "/Register" + ChatColor.GRAY
					+ " : Register Your Password To Be Able To Play.");

			if (sender.isOp()
					|| sender.hasPermission(new Permissions().setLoginSpawn)) {
				sender.sendMessage(ChatColor.GOLD
						+ "/SetLoginSpawn"
						+ ChatColor.GRAY
						+ " : Set The Spot Where Players Are Teleported To Log In.");
			}
			
			if (sender.isOp()
					|| sender.hasPermission(new Permissions().loginSpawn)) {
				sender.sendMessage(ChatColor.GOLD
						+ "/LoginSpawn"
						+ ChatColor.GRAY
						+ " : Teleport To The Spot Where Players Go To Log In.");
			}

			/*if (sender.isOp()
					|| sender.hasPermission(new Permissions().setMessage)) {
				sender.sendMessage(ChatColor.GOLD
						+ "/SetMessage"
						+ ChatColor.GRAY
						+ " : Set The Message That Players Receive On Certain Occasions");
				sender.sendMessage(ChatColor.DARK_AQUA
						+ "(Use /SetMessageHelp To See More Help On Message Occasions.)");
				sender.sendMessage(ChatColor.DARK_AQUA
						+ "Using the Console for this command is recommended,"
						+ " as the Console allows much more chat space.");
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.DARK_RED
							+ "Because you are using the Console, with unlimited typing space,"
							+ " the maximum arguments for this command is 48, not including spaces!");
					sender.sendMessage(ChatColor.DARK_RED
							+ "(As a player it doesn't matter, since the chat bar is limited.)");
				}
			}*/

			if (sender.isOp()
					|| sender.hasPermission(new Permissions().setLoginSpawn)) {
				sender.sendMessage(ChatColor.GOLD + "/SlReload"
						+ ChatColor.GRAY + " : Reload The Configuration.");
			}
		} /*else if (commandLabel.equalsIgnoreCase("setmessagehelp")) {
			if (sender.hasPermission(new Permissions().setMessageHelp)
					|| sender.isOp()) {
				sender.sendMessage(main2);
				sender.sendMessage(ChatColor.GOLD
						+ "Options for message occasions are below. They are case-insensitive, but you must use them word-for-word to change messages ingame.");
				sender.sendMessage(ChatColor.GOLD
						+ "Options: " + ChatColor.RED + "Must-Login-Message, Unfreeze-Message, You-Have-Registered-Message, Remember-Your-Password-Message");
				sender.sendMessage(ChatColor.DARK_AQUA
						+ "Command Usage: /SetMessage <Message Name> <Message>. Color-Code Compatible.");
			} else if (!(sender.isOp())
					&& !(sender.hasPermission(new Permissions().setMessageHelp))) {
				sender.sendMessage(ChatColor.DARK_RED
						+ "You do not have permission to perform this command.");
			}
		} else if (commandLabel.equalsIgnoreCase("setmessage")) {
			if (sender.hasPermission(new Permissions().setMessage) || sender.isOp()) {
				if (args.length < 2) {
					sender.sendMessage(ChatColor.RED
							+ "Too few arguments. Usage: /SetMessage <Message Name> <Message>");
				} else if (args.length >= 2) {
					if (args[0].equalsIgnoreCase("Must-Login-Message")) {
						hash.put("MustLogin", args[0]);
						hash.put(args[0], "Must-Login-Message");
					} else if (args[0].equalsIgnoreCase("Unfreeze-Message")) {
						hash.put("Unfreeze", args[0]);
						hash.put(args[0], "Unfreeze-Message");
					} else if (args[0]
							.equalsIgnoreCase("You-Have-Registered-Message")) {
						hash.put("HaveRegistered", args[0]);
						hash.put(args[0], "You-Have-Registered-Message");
					} else if (args[0]
							.equalsIgnoreCase("Remember-Your-Password-Message")) {
						hash.put("Remember", args[0]);
						hash.put(args[0], "Remember-Your-Password-Message");
					}
					if (args[0].equalsIgnoreCase(hash.get("MustLogin"))
							|| args[0].equalsIgnoreCase(hash.get("Unfreeze"))
							|| args[0].equalsIgnoreCase(hash
									.get("HaveRegistered"))
							|| args[0].equalsIgnoreCase(hash.get("Remember"))) {
						
						// argLength counts the actual argument length, as if it were "args.length == 2"
						argLength = 2;
						// argCount counts as if it were "args[0].toLowerCase", making it one behind argLength
						argCount = 1;
						
						do {
							plugin.getConfig().set(hash.get(args[0]), 
									plugin.getConfig().getString(hash.get(args[0])) + " " + args[argCount]);
							plugin.saveConfig();
							if (argLength == args.length) {
								plugin.reloadConfig();
							}
							argLength++;
							argCount++;
						} while (argLength <= args.length);
						
					} else if (!args[0].equalsIgnoreCase(hash.get("MustLogin"))
							&& !args[0].equalsIgnoreCase(hash.get("Unfreeze"))
							&& !args[0].equalsIgnoreCase(hash.get("HaveRegistered"))
							&& !args[0].equalsIgnoreCase(hash.get("Remember"))) {
						sender.sendMessage(ChatColor.RED + "'" + args[0] + "' is not a message!");
					}
				}
			} else if (!(sender.hasPermission(new Permissions().setMessage)) && !(sender.isOp())) {
				sender.sendMessage(ChatColor.DARK_RED + "You don't have permission to perform this command.");
			}
		}*/

		return false;
	}
}
