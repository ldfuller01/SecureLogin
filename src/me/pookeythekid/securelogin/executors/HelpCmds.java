package me.pookeythekid.securelogin.executors;

import java.util.HashMap;

import me.pookeythekid.securelogin.Main;
import me.pookeythekid.securelogin.permissions.Permissions;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCmds implements CommandExecutor {

	public Main M;

	public HelpCmds(Main instance) {

		M = instance;

	}

	public static String tag = ChatColor.DARK_GREEN + "[" + ChatColor.GREEN
			+ "SecureLogin" + ChatColor.DARK_GREEN + "]";

	public static String title = ChatColor.AQUA + "--==" + tag
			+ ChatColor.AQUA + "==--";

	public HashMap<String, String> map = new HashMap<String, String>();


	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if (commandLabel.equalsIgnoreCase("securelogin") || commandLabel.equalsIgnoreCase("sl")) {

			if (sender.hasPermission(new Permissions().help)) {

				if (args.length < 1) {

					sender.sendMessage(ChatColor.GREEN + title + ChatColor.YELLOW + " Page 1 of 2.");

					if (sender.hasPermission(new Permissions().help))

						sender.sendMessage(ChatColor.GOLD + "/SecureLogin (/Sl)" + ChatColor.GRAY
								+ " : This help page.");

					if (sender.hasPermission(new Permissions().login))

						sender.sendMessage(ChatColor.GOLD + "/Login" + ChatColor.GRAY
								+ " : Log into the server to play.");

					sender.sendMessage(ChatColor.GOLD + "/Register" + ChatColor.GRAY
							+ " : Register your password to be able to play.");

					if (sender.hasPermission(new Permissions().setLoginSpawn))

						sender.sendMessage(ChatColor.GOLD + "/SetLoginSpawn"
								+ ChatColor.GRAY + " : Set the spot where players are teleported to log in.");

					if (sender.hasPermission(new Permissions().loginSpawn))

						sender.sendMessage(ChatColor.GOLD + "/LoginSpawn [Player]"
								+ ChatColor.GRAY + " : teleport yourself or another player to the spot "
										+ "where players go to log in.");

					if (sender.hasPermission(new Permissions().reload))

						sender.sendMessage(ChatColor.GOLD + "/SlReload"
								+ ChatColor.GRAY + " : Reload the configuration.");

					if (sender.hasPermission(new Permissions().changePassword))

						sender.sendMessage(ChatColor.GOLD + "/ChangePassword"
								+ ChatColor.GRAY + " : Change your password.");

				}

				else if (args.length > 0) {

					switch (Integer.valueOf(args[0])) {
					
					case 1:

						sender.sendMessage(ChatColor.GREEN + title + ChatColor.YELLOW + " Page 1 of 2.");

						if (sender.hasPermission(new Permissions().help))

							sender.sendMessage(ChatColor.GOLD + "/SecureLogin (/Sl) <Page #>" + ChatColor.GRAY
									+ " : This help page.");

						if (sender.hasPermission(new Permissions().login))

							sender.sendMessage(ChatColor.GOLD + "/Login <Password>" + ChatColor.GRAY
									+ " : Log into the server to play.");

						if (sender.hasPermission(new Permissions().register))
						
						sender.sendMessage(ChatColor.GOLD + "/Register <New Password>" + ChatColor.GRAY
								+ " : Register your password to be able to play.");

						if (sender.hasPermission(new Permissions().setLoginSpawn))

							sender.sendMessage(ChatColor.GOLD + "/SetLoginSpawn"
									+ ChatColor.GRAY + " : Set the spot where players are teleported to log in.");

						if (sender.hasPermission(new Permissions().loginSpawn))

							sender.sendMessage(ChatColor.GOLD + "/LoginSpawn [Player]"
									+ ChatColor.GRAY + " : teleport yourself or another player to the spot "
											+ "where players go to log in.");

						/*if (sender.hasPermission(new Permissions().setMessage))

				sender.sendMessage(ChatColor.GOLD
						+ "/SetMessage"
						+ ChatColor.GRAY
						+ " : Set The Message That Players Receive On Certain Occasions");

				sender.sendMessage(ChatColor.DARK_AQUA
						+ "(Use /SetMessageHelp To See More Help On Message Occasions.)");

				sender.sendMessage(ChatColor.DARK_AQUA
						+ "Using the Console for this command is recommended,"
						+ " as the Console allows much more chat space.");*/

						if (sender.hasPermission(new Permissions().reload))

							sender.sendMessage(ChatColor.GOLD + "/SlReload"
									+ ChatColor.GRAY + " : Reload the configuration.");

						if (sender.hasPermission(new Permissions().changePassword))

							sender.sendMessage(ChatColor.GOLD + "/ChangePassword"
									+ ChatColor.GRAY + " : Change your password.");
						
						break;

					case 2:

						sender.sendMessage(ChatColor.GREEN + title + ChatColor.YELLOW + " Page 2 of 2.");

						if (sender.hasPermission(new Permissions().createSQ))

							sender.sendMessage(ChatColor.GOLD + "/CreateSecurityQuestion (/CreateSQ) <Question>"
									+ ChatColor.GRAY + " : Create your Security Question.");

						if (sender.hasPermission(new Permissions().changeSQ))

							sender.sendMessage(ChatColor.GOLD + "/ChangeSecurityQuestion (/ChangeSQ) <New Question>"
									+ ChatColor.GRAY + " : Change your Security Question.");

						if (sender.hasPermission(new Permissions().createSA))

							sender.sendMessage(ChatColor.GOLD + "/CreateSecurityAnswer (/CreateSA) <Answer>"
									+ ChatColor.GRAY + " : Create the answer to your Security Question.");

						if (sender.hasPermission(new Permissions().changeSA))

							sender.sendMessage(ChatColor.GOLD + "/ChangeSecurityAnswer (/ChangeSA) <New Answer>"
									+ ChatColor.GRAY + " : Change the answer to your Security Question.");
						
						if (sender.hasPermission(new Permissions().getPassword))
							
							sender.sendMessage(ChatColor.GOLD + "/GetPassword <Player>"
									+ ChatColor.GRAY + " : Get the password of a player.");
						
						if (sender.hasPermission(new Permissions().getSQ))
							
							sender.sendMessage(ChatColor.GOLD + "/GetSecurityQuestion (/GetSQ) <Player>"
									+ ChatColor.GRAY + " : Get the Security Question of a player.");
						
						if (sender.hasPermission(new Permissions().getSA))
							
							sender.sendMessage(ChatColor.GOLD + "/GetSecurityAnswer (/GetSA) <Player"
									+ ChatColor.GRAY + " : Get the Security Answer of a player.");
						
						if (sender.hasPermission(new Permissions().changePasswordOther))
							
							sender.sendMessage(ChatColor.GOLD + "/ChangePasswordFor <Player> <New Password>"
									+ ChatColor.GRAY + " : Change the password of another player.");
						
						break;
						
					default:
					case 3:
						
						if (sender.hasPermission(new Permissions().changeSQOther))
							
							sender.sendMessage(ChatColor.GOLD + "/ChangeSQFor <Player> <New Question>"
									+ ChatColor.GRAY + " : Change the Security Question of another player.");
						
						if (sender.hasPermission(new Permissions().changeSAOther))
							
							sender.sendMessage(ChatColor.GOLD + "/ChangeSAFor <Player> <New Answer>"
									+ ChatColor.GRAY + " : Change the Security Answer of another player.");

					}

				}

			}

		}

		/*else if (commandLabel.equalsIgnoreCase("setmessagehelp")) {

			if (sender.hasPermission(new Permissions().setMessageHelp)) {

				sender.sendMessage(title);

				sender.sendMessage(ChatColor.GOLD + "Options for message occasions are below. "
						+ "They are case-insensitive, but you must use them word-for-word to change messages in-game.");

				sender.sendMessage(ChatColor.GOLD
						+ "Options: " + ChatColor.RED + "Must-Login-Message, Unfreeze-Message, "
						+ "You-Have-Registered-Message");

				sender.sendMessage(ChatColor.DARK_AQUA + "Command Usage: /SetMessage <Message Name> <Message>. Color-Code Compatible.");

			} else if (!sender.hasPermission(new Permissions().setMessageHelp)) {

				sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to perform this command.");

			}

		}

		else if (commandLabel.equalsIgnoreCase("setmessage")) {

			if (sender.hasPermission(new Permissions().setMessage)) {

				if (args.length < 2) {

					sender.sendMessage(ChatColor.RED + "Too few arguments. Usage: /SetMessage <Message Name> <Message>");

				} else if (args.length >= 2) {

					String messageName = null;

					switch (args[0].toLowerCase()) {

					case "must-login-message":

						map.put("MustLogin", args[0].toLowerCase());
						map.put(args[0], "Must-Login-Message");

						messageName = args[0].toLowerCase();

						break;

					case "unfreeze-message":

						map.put("Unfreeze", args[0].toLowerCase());
						map.put(args[0], "Unfreeze-Message");

						messageName = args[0].toLowerCase();

						break;

					case "you-have-registered-message":

						map.put("HaveRegistered", args[0].toLowerCase());
						map.put(args[0], "You-Have-Registered-Message");

						messageName = args[0].toLowerCase();

						break;

					default:

						sender.sendMessage(ChatColor.RED + args[0] + " is not a message.");

					}

					if (args[0].equalsIgnoreCase(map.get("MustLogin"))
							|| args[0].equalsIgnoreCase(map.get("Unfreeze"))
							|| args[0].equalsIgnoreCase(map.get("HaveRegistered"))
							|| args[0].equalsIgnoreCase(map.get("Remember"))) {

						// argLength counts the actual argument length, as if it were "args.length == 2"
						int argLength = 2;

						// argCount counts as if it were "args[0].toLowerCase", making it one behind argLength
						int argNum = 1;

						while (argLength <= args.length) {

							M.getConfig().set(map.get(messageName), 
									M.getConfig().getString(map.get(args[0].toLowerCase()))
																+ " " + args[argNum]);

							M.saveConfig();

							if (argLength == args.length) {

								M.reloadConfig();

							}

							argLength++;
							argNum++;

						}

					} else if (!args[0].equalsIgnoreCase(map.get("MustLogin"))
							&& !args[0].equalsIgnoreCase(map.get("Unfreeze"))
							&& !args[0].equalsIgnoreCase(map.get("HaveRegistered"))
							&& !args[0].equalsIgnoreCase(map.get("Remember"))) {

						sender.sendMessage(ChatColor.RED + "'" + args[0] + "' is not a message!");

					}

				}

			} else if (!(sender.hasPermission(new Permissions().setMessage)) && !(sender.isOp())) {

				sender.sendMessage(ChatColor.DARK_RED + "You don't have permission to perform this command.");

			}

		}*/

		return true;
	}

}
