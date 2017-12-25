package me.pookeythekid.securelogin.passwordmanagement;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.pookeythekid.securelogin.Main;

public class SecurityQuestions implements CommandExecutor {
	
	public Main M;
	
	public SecurityQuestions(Main instance) {
		M = instance;
	}
	
	private int argLength;
	private int argCount;
	private int mapCount;
	private int mapArgs;
	
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("createsecurityquestion")
				|| commandLabel.equalsIgnoreCase("createsq")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to perform this command.");
			} else if (sender instanceof Player) {
				if (args.length < 1) {
					sender.sendMessage(ChatColor.RED + "Too few arguments. Usage: /CreateSecurityQuestion <Question>");
				} else if (args.length >= 1) {
				Player p = (Player) sender;
				
				if (M.getConfig().getString("Players." + p.getName() + ".SecurityQuestion") != null
					&& !M.getConfig().getString("Players." + p.getName() + ".SecurityQuestion").isEmpty()) {
					M.changeSQ.add(p);
					
					if (M.getConfig().getBoolean("changeQWithQuesiton") == true) {
						M.verifyQuesWithQ.add(p);
					} else if (M.getConfig().getBoolean("changeQWithPassword") == true) {
						M.verifyQuesWithQ.add(p);
					}
					
					p.sendMessage(ChatColor.GOLD + "You already have a Security Question. Would you like to overwrite it?");
					p.sendMessage(ChatColor.AQUA + "Chat " + ChatColor.GREEN + "'yes'" + ChatColor.AQUA + " to overwrite.");
					p.sendMessage(ChatColor.AQUA + "Chat " + ChatColor.RED + "'no'" + ChatColor.AQUA + " to cancel at any time.");
					
					mapCount = 1;
					mapArgs = 0;
					do {
						M.changeSQMap.put(p.getName() + "-" + mapCount, args[mapArgs]);
						mapCount++;
						mapArgs++;
					} while (mapCount < 101);
					
				} else if (M.getConfig().getString("Players." + p.getName() + ".SecurityQuestion") == null
						|| M.getConfig().getString("Players." + p.getName() + ".SecurityQuestion").isEmpty()) {
				// argLength counts the actual argument length, as if it were "args.length == 1"
				argLength = 1;
				// argCount counts as if it were "args[0]", making it one behind argLength
				argCount = 0;
				
					do {
						M.getConfig().set("Players." + p.getName() + ".SecurityQuestion",
							M.getConfig().getString("Players." + p.getName() + ".SecurityQuestion") + " " + args[argCount]);
						M.saveConfig();
						if (argLength == args.length) {
							M.reloadConfig();
						}
						argLength++;
						argCount++;
					} while (argLength <= args.length);
					p.sendMessage(ChatColor.GREEN + "Security Question Created: "
							+ M.getConfig().getString("Players." + p.getName() + ".SecurityQuestion"));
					p.sendMessage(ChatColor.GOLD + "You must now create your Security Answer. Use "
							+ ChatColor.AQUA + "/CreateSecurityAnswer" + ChatColor.GOLD + " or "
							+ ChatColor.AQUA + "/CreateSA" + ChatColor.GOLD + " to do this.");
					M.createSA.add(p);
					}
				}
			}
		
		} else if (commandLabel.equalsIgnoreCase("changesecurityquestion")
				|| commandLabel.equalsIgnoreCase("changesq")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to perform this command.");
			} else if (sender instanceof Player) {
				Player p = (Player) sender;
				
				mapCount = 1;
				mapArgs = 0;
				do {
					M.changeSQMap.put(p.getName() + "-" + mapCount, args[mapArgs]);
					mapCount++;
					mapArgs++;
				} while (mapCount < 101);
				
				M.changeSQ.add(p);
				
				p.sendMessage(ChatColor.GOLD + "You must verify that you are a permitted user of this account to "
						+ "finish changing your Security Question.");
				
				if (M.getConfig().getBoolean("changeQWithQuestion") == true
						&& M.verifyQuesWithQ.contains(p)) {
				p.sendMessage(ChatColor.AQUA + M.getConfig().getString("Players." + p.getName()
						+ ".SecurityQuestion"));
				p.sendMessage(ChatColor.GREEN + "Chat the answer to this question to change your Security Question.");
				M.verifyQuesWithQAnswer.add(p);
				
				} else if (M.getConfig().getBoolean("changeQWithPassword") == true
						&& M.verifyQuesWithP.contains(p)) {
					p.sendMessage(ChatColor.AQUA + "To comfirm that you are a permitted user of this account, chat your "
						+ "login password.");
					p.sendMessage(ChatColor.GREEN + "Password is case-sensitive. You must restart your change of Security Question if you "
						+ "enter your password incorrectly.");
					M.verifyQuesWithPAnswer.add(p);
				}	
			}
		}

		return true;
	}
}
