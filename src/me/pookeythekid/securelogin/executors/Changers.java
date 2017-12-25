package me.pookeythekid.securelogin.executors;

import me.pookeythekid.securelogin.Main;
import me.pookeythekid.securelogin.permissions.Permissions;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Changers implements CommandExecutor {

	public Main M;

	public Changers(Main plugin) {

		M = plugin;

	}


	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


		if (label.equalsIgnoreCase("changepasswordfor")) {

			if (sender.hasPermission(new Permissions().changePasswordOther)) {

				if (args.length < 2) {

					sender.sendMessage(ChatColor.RED + "Too few arguments. Usage: /changepasswordfor <player> <new password>");

					return false;

				}

				if (M.idFetch.containsKey(args[0].toLowerCase())) {

					String id = M.idFetch.get(args[0].toLowerCase()).toString();

					String name = M.playerFile.getString("Players." + id + ".Name");

					M.playerFile.set("Players." + id + ".Password", args[1]);

					M.playerFile.saveConfig();

					sender.sendMessage(ChatColor.GREEN + "The new password of " + ChatColor.AQUA + name
							+ ChatColor.GREEN + " is " + ChatColor.AQUA + args[1]);

				} else

					sender.sendMessage(ChatColor.RED + "That player does not exist on this server.");

			} else

				sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");

		}

		else if (label.equalsIgnoreCase("changesqfor")) {

			if (sender.hasPermission(new Permissions().changeSQOther)) {

				if (args.length < 2) {

					sender.sendMessage(ChatColor.RED + "Too few arguments. Usage: /changesqfor <player> <new security question");

					return false;

				}

				if (M.idFetch.containsKey(args[0].toLowerCase())) {

					String id = M.idFetch.get(args[0].toLowerCase()).toString();

					String name = M.playerFile.getString("Players." + id + ".Name");

					StringBuffer newSQ = new StringBuffer();

					int argCount = 1;

					for (String s : args) {

						if (!s.equalsIgnoreCase(name)) {

							if (argCount != args.length)

								newSQ.append(s + " ");

							else

								newSQ.append(s);

						}

						argCount++;

					}

					M.playerFile.set("Players." + id + ".SecurityQuestion", newSQ.toString());

					M.playerFile.saveConfig();

					sender.sendMessage(ChatColor.GREEN + "The new Security Question of " + ChatColor.AQUA + name
							+ ChatColor.GREEN + " is " + ChatColor.AQUA +  newSQ.toString());

				} else

					sender.sendMessage(ChatColor.RED + "That player does not exist on this server.");

			} else

				sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");

		}

		else if (label.equalsIgnoreCase("changesafor")) {

			if (sender.hasPermission(new Permissions().changeSAOther)) {

				if (args.length < 2) {

					sender.sendMessage(ChatColor.RED + "Too few arguments. Usage: /changesafor <player> <new security answer>");

					return false;

				}

				if (M.idFetch.containsKey(args[0].toLowerCase())) {

					String id = M.idFetch.get(args[0].toLowerCase()).toString();

					String name = M.playerFile.getString("Players." + id + ".Name");

					StringBuffer newSA = new StringBuffer();

					int argCount = 1;

					for (String s : args) {

						if (!s.equalsIgnoreCase(name)) {

							if (argCount != args.length)

								newSA.append(s + " ");

							else

								newSA.append(s);

						}

						argCount++;

					}

					M.playerFile.set("Players." + id + ".SecurityAnswer", newSA.toString());

					M.playerFile.saveConfig();

					sender.sendMessage(ChatColor.GREEN + "The new Security Answer of " + ChatColor.AQUA + name
							+ ChatColor.GREEN + " is " + ChatColor.AQUA + newSA.toString());

				} else

					sender.sendMessage(ChatColor.RED + "That player doesn't exist on this server.");

			} else

				sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");

		}


		return true;

	}

}
