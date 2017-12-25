package me.pookeythekid.securelogin.passwords;

import me.pookeythekid.securelogin.Main;
import me.pookeythekid.securelogin.ConfigManager.MyConfig;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SecurityQuestions implements CommandExecutor {

	public Main M;

	public SecurityQuestions(Main instance) {

		M = instance;

	}


	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		MyConfig playerFile = M.playerFile;

		if (commandLabel.equalsIgnoreCase("createsecurityquestion")
				|| commandLabel.equalsIgnoreCase("createsq")) {

			if (!(sender instanceof Player)) {

				sender.sendMessage(ChatColor.RED + "You must be a player to perform this command.");

			} else if (sender instanceof Player) {

				if (args.length < 1) {

					sender.sendMessage(ChatColor.RED + "Too few arguments. Usage: /CreateSecurityQuestion <Question>");

				} else if (args.length >= 1) {

					Player p = (Player) sender;
					
					String id = p.getUniqueId().toString();

					if (playerFile.getString("Players." + id + ".SecurityQuestion") != null
							&& !playerFile.getString("Players." + id + ".SecurityQuestion").isEmpty()) {

						M.changeSQ.add(p);


						if (M.getConfig().getBoolean("changeQWithQuestion"))

							M.verifyQuesWithQ.add(p);

						else if (M.getConfig().getBoolean("changeQWithPassword"))

							M.verifyQuesWithP.add(p);


						p.sendMessage(ChatColor.GOLD + "You already have a Security Question. Would you like to overwrite it?");

						p.sendMessage(ChatColor.AQUA + "Chat " + ChatColor.GREEN + "'yes'" + ChatColor.AQUA + " to overwrite.");

						p.sendMessage(ChatColor.AQUA + "Chat " + ChatColor.RED + "'cancel'" + ChatColor.AQUA + " to cancel at any time.");


						int mapCount = 1;

						int mapArgs = 0;

						while (mapCount <= args.length) {

							M.changeSQMap.put(p.getName() + "-" + mapCount, args[mapArgs]);

							mapCount++;

							mapArgs++;

						}

					} else if (playerFile.getString("Players." + id + ".SecurityQuestion") == null
							|| playerFile.getString("Players." + id + ".SecurityQuestion").isEmpty()) {

						// argLength counts the actual argument length, as if it were "args.length == 1"
						int argLength = 1;

						// argCount counts as if it were "args[0]", making it one behind argLength
						int argCount = 0;

						while (argLength <= args.length) {

							if (playerFile.getString("Players." + id + ".SecurityQuestion") == null
									|| playerFile.getString("Players." + id + ".SecurityQuestion").isEmpty())

								playerFile.set("Players." + id + ".SecurityQuestion", args[argCount]);

							else if (playerFile.getString("Players." + id + ".SecurityQuestion") != null
									&& !playerFile.getString("Players." + id + ".SecurityQuestion").isEmpty())

								playerFile.set("Players." + id + ".SecurityQuestion",
										playerFile.getString("Players." + id + ".SecurityQuestion") + " " + args[argCount]);

							playerFile.saveConfig();

							if (argLength == args.length) {

								playerFile.reloadConfig();

							}

							argLength++;

							argCount++;

						}

						p.sendMessage(ChatColor.GREEN + "Security Question Created: "
								+ playerFile.getString("Players." + id + ".SecurityQuestion"));

						p.sendMessage(ChatColor.GOLD + "You must now create your Security Answer. Use "
								+ ChatColor.AQUA + "/CreateSecurityAnswer" + ChatColor.GOLD + " or "
								+ ChatColor.AQUA + "/CreateSA" + ChatColor.GOLD + " to do this.");

						M.createSA.add(p);

					}

				}

			}

		}

		else if (commandLabel.equalsIgnoreCase("changesecurityquestion")
				|| commandLabel.equalsIgnoreCase("changesq")) {

			if (!(sender instanceof Player)) {

				sender.sendMessage(ChatColor.RED + "You must be a player to perform this command.");

			} else if (sender instanceof Player) {

				if (args.length < 1)

					sender.sendMessage(ChatColor.RED + "Too few arguments. Usage: /ChangeSecurityQuestion <New Security Question>");

				else if (args.length > 0) {

					Player p = (Player) sender;
					
					String id = p.getUniqueId().toString();

					int mapCount = 1;

					int mapArgs = 0;

					while (mapCount <= args.length) {

						M.changeSQMap.put(p.getName() + "-" + mapCount, args[mapArgs]);

						mapCount++;

						mapArgs++;

					}

					M.changeSQ.add(p);


					if (M.getConfig().getBoolean("changeQWithQuestion"))

						M.verifyQuesWithQ.add(p);

					else if (M.getConfig().getBoolean("changeQWithPassword"))

						M.verifyQuesWithP.add(p);


					p.sendMessage(ChatColor.GOLD + "You must now verify that you are a permitted user of this account to "
							+ "finish changing your Security Question.");


					if (M.getConfig().getBoolean("changeQWithQuestion")
							&& M.verifyQuesWithQ.contains(p)) {

						p.sendMessage(ChatColor.AQUA + playerFile.getString("Players." + id
								+ ".SecurityQuestion"));

						p.sendMessage(ChatColor.GREEN + "Chat the answer to this question to change your Security Question.");

						M.verifyQuesWithQAnswer.add(p);

					} else if (M.getConfig().getBoolean("changeQWithPassword")
							&& M.verifyQuesWithP.contains(p)) {

						p.sendMessage(ChatColor.AQUA + "To comfirm that you are a permitted user of this account, chat your "
								+ "login password.");

						p.sendMessage(ChatColor.GREEN + "Password is case-sensitive. You must restart your change of Security Question if you "
								+ "enter your password incorrectly.");

						M.verifyQuesWithPAnswer.add(p);

					}	

				}

			}

		}

		return true;
	}

}
