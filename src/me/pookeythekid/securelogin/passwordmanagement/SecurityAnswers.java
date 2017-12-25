package me.pookeythekid.securelogin.passwordmanagement;

import me.pookeythekid.securelogin.Main;

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
	
	private int argCount;
	private int argLength;
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (commandLabel.equalsIgnoreCase("createsecurityanswer")
			|| commandLabel.equalsIgnoreCase("createSA")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to perform this command.");
			} else if (sender instanceof Player) {
				if (args.length < 1) {
					sender.sendMessage(ChatColor.RED + "Too few arguments. Usage: /CreateSecurityAnswer <Answer>");
				
				} else if (args.length >= 1) {
					Player p = (Player) sender;
					
					if (M.createSA.contains(p)) {
						
						// argLength counts the actual argument length, as if it were "args.length == 1"
						argLength = 1;
						// argCount counts as if it were "args[0]", making it one behind argLength
						argCount = 0;
						
							do {
								M.getConfig().set("Players." + p.getName() + ".SecurityAnswer",
									M.getConfig().getString("Players." + p.getName() + ".SecurityAnswer") + " " + args[argCount]);
								M.saveConfig();
								if (argLength == args.length) {
									M.reloadConfig();
								}
								argLength++;
								argCount++;
							} while (argLength <= args.length);
							p.sendMessage(ChatColor.GREEN + "Created Security Answer: "
									+ M.getConfig().getString("Players." + p.getName() + ".SecurityAnswer"));
							M.createSA.remove(p);
					
					} else if (!M.createSA.contains(p)) {
						p.sendMessage(ChatColor.RED + "You have no need to create a Security Answer, as you have not changed your "
								+ "Security Question.");
					}
				}
			}
		}

		return true;
	}

}
