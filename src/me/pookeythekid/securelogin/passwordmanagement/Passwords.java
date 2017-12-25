package me.pookeythekid.securelogin.passwordmanagement;

import me.pookeythekid.securelogin.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Passwords implements CommandExecutor {
	
	public Main M;
	
	public Passwords(Main instance) {
		M = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (commandLabel.equalsIgnoreCase("changepassword")) {
			
		}
		
		return true;
	}

}
