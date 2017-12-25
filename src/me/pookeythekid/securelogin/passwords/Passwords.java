package me.pookeythekid.securelogin.passwords;

import me.pookeythekid.securelogin.Main;
import me.pookeythekid.securelogin.ConfigManager.MyConfig;
import me.pookeythekid.securelogin.permissions.Permissions;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Passwords implements CommandExecutor {

	public Main M;

	public Passwords(Main instance) {

		M = instance;

	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		MyConfig playerFile = M.playerFile;

		if (commandLabel.equalsIgnoreCase("changepassword")) {

			if (!(sender instanceof Player))

				sender.sendMessage(ChatColor.RED + "You must be a player to perform this command.");

				else if (sender instanceof Player) {

					if (!sender.hasPermission(new Permissions().changePassword))

						sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");

					else if (sender.hasPermission(new Permissions().changePassword)) {

						if (args.length < 1)

							sender.sendMessage(ChatColor.RED + "Too few arguments. Usage: /ChangePassword <New Password>");

						else if (args.length > 0) {
							
							Player p = (Player) sender;
							
							String id = p.getUniqueId().toString();
							
							M.changePassMap.put(p, args[0]);
							
							M.changePass.add(p);
							
							if (M.getConfig().getBoolean("changePWithQuestion"))
								
								M.verifyPassWithQues.add(p);
							
							else if (M.getConfig().getBoolean("changePWithPassword"))
								
								M.verifyPassWithPass.add(p);
							
							p.sendMessage(ChatColor.GOLD + "You must now verify that you are a permitted user of this account to "
									+ "finish changing your password.");
							
							
							if (M.getConfig().getBoolean("changePWithQuestion")
									&& M.verifyPassWithQues.contains(p)) {
								
								p.sendMessage(ChatColor.AQUA + playerFile.getString("Players." + id
										+ ".SecurityQuestion"));

								p.sendMessage(ChatColor.GREEN + "Chat the answer to this question to continue.");
								
								M.verifyPassWithQues.remove(p);
								
								M.verifyPassWithQuesAnswer.add(p);
								
							} else if (M.getConfig().getBoolean("changePWithPassword")
									&& M.verifyPassWithPass.contains(p)) {
								
								p.sendMessage(ChatColor.AQUA + "To comfirm that you are a permitted user of this account, chat your "
										+ "login password.");

								p.sendMessage(ChatColor.GREEN + "Password is case-sensitive. You must restart your change of password if you "
										+ "enter your password incorrectly.");
								
								M.verifyPassWithPass.remove(p);
								
								M.verifyPassWithPassAnswer.add(p);
								
							}
							
						}

					} else if (!sender.hasPermission(new Permissions().changePassword))
						
						sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");

				}

		}

		return true;

	}

}
