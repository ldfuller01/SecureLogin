package me.pookeythekid.securelogin.executors;

import me.pookeythekid.securelogin.Main;
import me.pookeythekid.securelogin.permissions.Permissions;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Getters implements CommandExecutor {

	public Main M;

	public Getters(Main plugin) {

		M = plugin;

	}


	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (label.equalsIgnoreCase("getpassword")) {

			if (sender.hasPermission(new Permissions().getPassword)) {

				if (args.length < 1)

					sender.sendMessage(ChatColor.RED + "Too few arguments. Usage: /getpassword <username>");

				else if (args.length > 0) {

					if (M.idFetch.containsKey(args[0].toLowerCase())) {

						String id = M.idFetch.get(args[0].toLowerCase()).toString();

						String password = M.playerFile.getString("Players." + id + ".Password");

						String name = M.playerFile.getString("Players." + id + ".Name");

						if (password == null || password.isEmpty()) {

							sender.sendMessage(ChatColor.GREEN + "The player " + ChatColor.AQUA + name
									+ ChatColor.GREEN + " does not have a password yet.");

							return false;

						}

						sender.sendMessage(ChatColor.AQUA + name + ChatColor.GREEN + "'s password is \"" + ChatColor.AQUA + password
								+ ChatColor.GREEN + "\".");

					} else {

						sender.sendMessage(ChatColor.RED + "That player does not exist on this server.");

					}

				}

			} else if (!sender.hasPermission(new Permissions().getPassword))

				sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");

		}

		else if (label.equalsIgnoreCase("getsecurityquestion") || label.equalsIgnoreCase("getsq")) {

			if (sender.hasPermission(new Permissions().getSQ)) {

				if (args.length < 1)

					sender.sendMessage(ChatColor.RED + "Too few arguments. Usage: /getsecurityquestion <username>");

				else if (args.length > 0) {

					if (M.idFetch.containsKey(args[0].toLowerCase())) {

						String id = M.idFetch.get(args[0].toLowerCase()).toString();

						String securityQuestion = M.playerFile.getString("Players." + id + ".SecurityQuestion");

						String name = M.playerFile.getString("Players." + id + ".Name");

						if (securityQuestion == null || securityQuestion.isEmpty()) {

							sender.sendMessage(ChatColor.GREEN + "The player " + ChatColor.AQUA + name
									+ ChatColor.GREEN + " does not have a Security Question yet.");

							return false;

						}

						sender.sendMessage(ChatColor.AQUA + name + ChatColor.GREEN + "'s Security Question is \""
								+ ChatColor.AQUA + securityQuestion + ChatColor.GREEN + "\".");

					} else {

						sender.sendMessage(ChatColor.RED + "That player does not exist on this server.");

					}

				}

			} else if (!sender.hasPermission(new Permissions().getPassword))

				sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");

		}
		
		else if (label.equalsIgnoreCase("getsecurityanswer") || label.equalsIgnoreCase("getsa")) {
			
			if (sender.hasPermission(new Permissions().getSA)) {

				if (args.length < 1)

					sender.sendMessage(ChatColor.RED + "Too few arguments. Usage: /getsecurityanswer <username>");

				else if (args.length > 0) {

					if (M.idFetch.containsKey(args[0].toLowerCase())) {

						String id = M.idFetch.get(args[0].toLowerCase()).toString();

						String securityAnswer = M.playerFile.getString("Players." + id + ".SecurityAnswer");

						String name = M.playerFile.getString("Players." + id + ".Name");

						if (securityAnswer == null || securityAnswer.isEmpty()) {

							sender.sendMessage(ChatColor.GREEN + "The player " + ChatColor.AQUA + name
									+ ChatColor.GREEN + " does not have a Security Answer yet.");

							return false;

						}

						sender.sendMessage(ChatColor.AQUA + name + ChatColor.GREEN + "'s Security Answer is \""
								+ ChatColor.AQUA + securityAnswer + ChatColor.GREEN + "\".");

					} else {

						sender.sendMessage(ChatColor.RED + "That player does not exist on this server.");

					}

				}

			} else if (!sender.hasPermission(new Permissions().getPassword))

				sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			
		}

		return true;

	}


}
