package me.pookeythekid.securelogin.passwords;

import me.pookeythekid.securelogin.Main;
import me.pookeythekid.securelogin.ConfigManager.MyConfig;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SecurityAnswers implements CommandExecutor {

	public Main M;

	public SecurityAnswers(Main instance) {
		M = instance;
	}


	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		MyConfig playerFile = M.playerFile;

		if (commandLabel.equalsIgnoreCase("createsecurityanswer")
				|| commandLabel.equalsIgnoreCase("createsa")) {

			if (!(sender instanceof Player)) {

				sender.sendMessage(ChatColor.RED + "You must be a player to perform this command.");

			} else if (sender instanceof Player) {

				if (args.length < 1) {

					sender.sendMessage(ChatColor.RED + "Too few arguments. Usage: /CreateSecurityAnswer <Answer>");

				} else if (args.length >= 1) {

					Player p = (Player) sender;
					
					String id = p.getUniqueId().toString();

					if (M.createSA.contains(p)) {

						// argLength counts the actual argument length, as if it were "args.length == 1"
						int argLength = 1;
						// argCount counts as if it were "args[0]", making it one behind argLength
						int argNum = 0;

						StringBuffer sb = new StringBuffer();

						while (argLength <= args.length) {

							if (argLength != args.length)
								sb.append(args[argNum] + " ");

							else if (argLength == args.length)
								sb.append(args[argNum]);

							argLength++;
							argNum++;

						}

						playerFile.set("Players." + id + ".SecurityAnswer", sb.toString());

						playerFile.saveConfig();

						playerFile.reloadConfig();

						playerFile.reloadConfig();

						p.sendMessage(ChatColor.GREEN + "Created Security Answer: "
								+ playerFile.getString("Players." + id + ".SecurityAnswer"));

						M.createSA.remove(p);

						M.stopChange(p);

					} else if (!M.createSA.contains(p)) {

						p.sendMessage(ChatColor.RED + "You have no need to create a Security Answer, since you have not changed your "
								+ "Security Question.");
						
						p.sendMessage(ChatColor.RED + "If you need to change your Security Answer, use "
								+ ChatColor.AQUA + "/ChangeSecurityAnswer" + ChatColor.RED + " or "
								+ ChatColor.AQUA + "/ChangeSA" + ChatColor.RED + ".");

					}

				}

			}

		}

		else if (commandLabel.equalsIgnoreCase("changesecurityanswer")
				|| commandLabel.equalsIgnoreCase("changesa")) {

			if (!(sender instanceof Player))

				sender.sendMessage(ChatColor.RED + "You must be a player to perform this command.");

			else if (sender instanceof Player) {

				if (args.length < 1)

					sender.sendMessage(ChatColor.RED + "Too few arguments. Usage: /ChangeSecurityAnswer <New Answer>");

				else if (args.length > 0) {

					Player p = (Player) sender;
					
					String id = p.getUniqueId().toString();

					int mapCount = 1;

					int mapArgs = 0;

					while (mapCount <= args.length) {

						M.changeSAMap.put(p.getName() + "-" + mapCount, args[mapArgs]);

						mapCount++;

						mapArgs++;

					}

					M.changeSA.add(p);


					if (M.getConfig().getBoolean("changeSAWithQuestion"))

						M.verifySAWithQ.add(p);

					else if (M.getConfig().getBoolean("changeSAWithPassword"))

						M.verifySAWithPass.add(p);


					p.sendMessage(ChatColor.GOLD + "You must now verify that you are a permitted user of this account to "
							+ "finish changing your Security Answer.");


					if (M.getConfig().getBoolean("changeSAWithQuestion")
							&& M.verifySAWithQ.contains(p)) {

						p.sendMessage(ChatColor.AQUA + playerFile.getString("Players." + id
								+ ".SecurityQuestion"));

						p.sendMessage(ChatColor.GREEN + "Chat the answer to this question to change your Security Answer. "
								+ "Security Answer is case in-sensitive.");
						
						p.sendMessage(ChatColor.GREEN + "If you enter this incorrectly, you must restart "
								+ "the process of changing your Security Answer.");

						M.verifySAWithQAnswer.add(p);

					} else if (M.getConfig().getBoolean("changeSAWithPassword")
							&& M.verifySAWithPass.contains(p)) {

						p.sendMessage(ChatColor.AQUA + "To comfirm that you are a permitted user of this account, chat your "
								+ "login password.");

						p.sendMessage(ChatColor.GREEN + "Password is case-sensitive. You must restart your change of Security Question if you "
								+ "enter your password incorrectly.");

						M.verifySAWithPassAnswer.add(p);

					}


				}

			}

		}


		return true;

	}

}
