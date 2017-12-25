package me.pookeythekid.securelogin.executors;

import me.pookeythekid.securelogin.Main;
import me.pookeythekid.securelogin.permissions.Permissions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoginExe implements CommandExecutor {

	public Main plugin;

	public LoginExe(Main instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		if (commandLabel.equalsIgnoreCase("register")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED
						+ "You must be a player to perform this command.");
			} else if (sender instanceof Player) {
				Player player = (Player) sender;
				if (args.length < 1) {
					player.sendMessage(ChatColor.RED
							+ "Too few arguments. Usage: /register <password>");
				} else if (args.length == 1) {

					if (plugin.getConfig().getString(
							"Players." + player.getName() + ".Password") != null) {
						player.sendMessage(ChatColor.RED
								+ "You already have a password. Use /login <password> to login.");
					} else {
						plugin.getConfig().set(
								"Players." + player.getName()
										+ ".Password", args[0]);
						plugin.saveConfig();

						if (plugin.getConfig().getString(
								"You-Have-Registered-Message") != null) {
							player.sendMessage(plugin.colorCode(
									plugin.getConfig().getString("You-Have-Registered-Message")));
						}

						if (plugin.getConfig().getString(
								"Remember-Your-Password-Message") != null) {
							player.sendMessage(plugin.colorCode(
									plugin.getConfig().getString("You-Have-Registered-Message")));
						}
					}
				}
			}
		} else if (commandLabel.equalsIgnoreCase("login")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED
						+ "You must be a player to perform this command.");
			} else if (sender instanceof Player) {
				Player player = (Player) sender;
				if (args.length < 1) {
					player.sendMessage(ChatColor.RED
							+ "Too few arguments. Usage: /login <password>");
				} else if (args.length == 1) {
					if (plugin.getConfig().getString(
							"Players." + player.getName() + ".Password") != null) {

						if (args[0].equals(plugin.getConfig().getString(
								"Players." + player.getName()
										+ ".Password"))) {
							plugin.isFrozen.remove(player.getName());
							if (plugin.getConfig()
									.getString("Unfreeze-Message") != null) {
								player.sendMessage(plugin.colorCode(
										plugin.getConfig().getString("Unfreeze-Message")));
							}
						} else {
							player.sendMessage(ChatColor.RED
									+ "Password Incorrect. Try Again.");
						}
					} else if (!(player.hasPlayedBefore())) {
						player.sendMessage(ChatColor.DARK_RED
								+ "You do not have a password to log in with. Use /register <password> to make a password.");
					} else if (player.hasPlayedBefore()) {
						player.sendMessage(ChatColor.DARK_RED
								+ "You may need to register a new password, due to an error recalling your original one.");
					}
				}
			}
		} else if (commandLabel.equalsIgnoreCase("setloginspawn")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED
						+ "You must be a player to perform this command.");
			} else if (sender instanceof Player) {
				Player player = (Player) sender;
				Location location = player.getLocation();
			if (player.hasPermission(new Permissions().setLoginSpawn)
					|| player.isOp()) {
				plugin.getConfig().set("LoginSpawn.x", location.getX());
				plugin.getConfig().set("LoginSpawn.y", location.getY());
				plugin.getConfig().set("LoginSpawn.z", location.getZ());
				plugin.getConfig().set("LoginSpawn.Pitch", location.getPitch());
				plugin.getConfig().set("LoginSpawn.Yaw", location.getYaw());
				plugin.getConfig().set("LoginSpawn.World",
						location.getWorld().getName());
				plugin.saveConfig();
				player.sendMessage(ChatColor.GREEN + "Login Spawn Set.");
			} else if (!(player.hasPermission(new Permissions().setLoginSpawn))
					&& !(player.isOp())) {
				player.sendMessage(ChatColor.DARK_RED
						+ "You don't have permission to perform this command.");
				}
			}
		} else if (commandLabel.equalsIgnoreCase("loginspawn")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED
						+ "You must be a player to perform this command.");
			} else if (sender instanceof Player) {
				Player player = (Player) sender;
				Location location = player.getLocation();
				if (player.hasPermission(new Permissions().loginSpawn) || player.isOp()) {
					location.setX(plugin.getConfig().getDouble("LoginSpawn.x"));
					location.setY(plugin.getConfig().getDouble("LoginSpawn.y"));
					location.setZ(plugin.getConfig().getDouble("LoginSpawn.z"));
					World world = Bukkit.getServer().getWorld(
							plugin.getConfig().getString("LoginSpawn.World"));
					location.setWorld(world);
					location.setPitch(Float.valueOf(plugin.getConfig().getLong("LoginSpawn.Pitch")));
					location.setYaw(Float.valueOf(plugin.getConfig().getLong("LoginSpawn.Yaw")));
				} else if (!(player.hasPermission(new Permissions().loginSpawn) && !(player.isOp()))) {
					player.sendMessage(ChatColor.DARK_RED + "You don't have permission to perfrom this command.");
				}
			}
		}

		return false;
	}
}
