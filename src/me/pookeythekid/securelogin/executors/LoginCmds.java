package me.pookeythekid.securelogin.executors;

import me.pookeythekid.securelogin.Main;
import me.pookeythekid.securelogin.ConfigManager.MyConfig;
import me.pookeythekid.securelogin.permissions.Permissions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoginCmds implements CommandExecutor {

	public Main M;

	public LoginCmds(Main instance) {

		M = instance;

	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		MyConfig playerFile = M.playerFile;

		MyConfig loginSpawn = M.loginSpawn;

		if (label.equalsIgnoreCase("register")) {

			if (sender.hasPermission(new Permissions().register)) {

				if (!(sender instanceof Player)) {

					sender.sendMessage(ChatColor.RED + "You must be a player to perform this command.");

				} else if (sender instanceof Player) {

					Player p = (Player) sender;

					String id = p.getUniqueId().toString();

					String pName = p.getName();

					if (args.length < 1) {

						p.sendMessage(ChatColor.RED + "Too few arguments. Usage: /register <password>");

					} else if (args.length > 0) {

						if (playerFile.getString("Players." + id + ".Password") != null
								&& !playerFile.getString("Players." + id + ".Password").isEmpty()) {

							if (M.isFrozen.contains(p))

								p.sendMessage(ChatColor.RED + "You already have a password. Use /login <password> to log in.");

							else if (!M.isFrozen.contains(p))

								p.sendMessage(ChatColor.RED + "You already have a password.");

						} else if (playerFile.getString("Players." + id + ".Password") == null
								|| playerFile.getString("Players." + id + ".Password").isEmpty()) {

							playerFile.set("Players." + id + ".Name", pName);

							playerFile.set("Players." + id + ".Password", args[0]);

							playerFile.saveConfig();

							if (M.getConfig().getString("You-Have-Registered-Message") != null
									&& !M.getConfig().getString("You-Have-Registered-Message").isEmpty())

								p.sendMessage(M.colorCode(M.getConfig().getString("You-Have-Registered-Message")));

							if (playerFile.getString("Players." + id + ".SecurityQuestion") == null
									|| playerFile.getString("Players." + id + ".SecurityQuestion").isEmpty())

								p.sendMessage(ChatColor.GREEN + "You must now create a Security Question "
										+ "in case you'll need changes. Use "
										+ ChatColor.AQUA + "/CreateSecurityQuestion" + ChatColor.GREEN + " or "
										+ ChatColor.AQUA + "/CreateSQ" + ChatColor.GREEN + " to do so.");

						}

					}

				}

			} else if (!sender.hasPermission(new Permissions().register))

				sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");

		}

		else if (label.equalsIgnoreCase("login")) {

			if (sender.hasPermission(new Permissions().login)) {

				if (!(sender instanceof Player)) {

					sender.sendMessage(ChatColor.RED + "You must be a player to perform this command.");

				} else if (sender instanceof Player) {

					Player p = (Player) sender;

					String id = p.getUniqueId().toString();

					if (args.length < 1) {

						p.sendMessage(ChatColor.RED + "Too few arguments. Usage: /login <password>");

					} else if (args.length > 0) {


						if (playerFile.getString("Players." + id + ".Password") == null
								|| playerFile.getString("Players." + id + ".Password").isEmpty())

							p.sendMessage(ChatColor.RED + "You don't have a password to log in with. Use "
									+ ChatColor.AQUA + "/Register" + ChatColor.RED + " to create one.");

						else if (playerFile.getString("Players." + id + ".SecurityQuestion") == null
								|| playerFile.getString("Players." + id + ".SecurityQuestion").isEmpty())

							p.sendMessage(ChatColor.GREEN + "You must create a Security Question "
									+ "in case you'll need changes. Use "
									+ ChatColor.AQUA + "/CreateSecurityQuestion" + ChatColor.GREEN + " or "
									+ ChatColor.AQUA + "/CreateSQ" + ChatColor.GREEN + " to do so.");

						else if (playerFile.getString("Players." + id + ".SecurityAnswer") == null
								|| playerFile.getString("Players." + id + ".SecurityAnswer").isEmpty())

							p.sendMessage(ChatColor.GREEN + "You must create a Security Answer "
									+ "in case you'll need changes. Use "
									+ ChatColor.AQUA + "/CreateSecurityAnswer" + ChatColor.GREEN + " or "
									+ ChatColor.AQUA + "/CreateSA" + ChatColor.GREEN + " to do so.");

						else if (!p.hasPermission(new Permissions().antifreeze)
								&& !M.isFrozen.contains(p))

							p.sendMessage(ChatColor.AQUA + "You're already logged in.");

						else if (p.hasPermission(new Permissions().antifreeze)
								&& !M.isFrozen.contains(p))

							p.sendMessage(ChatColor.AQUA + "You have no need to log in.");




						else {

							if (playerFile.getString("Players." + id + ".Password") != null) {

								if (args[0].equals(playerFile.getString("Players." + id + ".Password"))) {

									M.isFrozen.remove(p);

									if (M.getConfig().getString("Unfreeze-Message") != null
											&& !M.getConfig().getString("Unfreeze-Message").isEmpty())

										p.sendMessage(M.colorCode(M.getConfig().getString("Unfreeze-Message")));

									if (M.getConfig().getBoolean("tpBack")) {

										M.returnToLoginLoc(p);

										M.loginLocations.remove(p);

									}

								} else

									p.sendMessage(ChatColor.RED + "Password Incorrect. Try Again.");


							} else if (!p.hasPlayedBefore())

								p.sendMessage(ChatColor.DARK_RED + "You do not have a password to log in with. "
										+ "Use /register <password> to make a password.");

							else if (p.hasPlayedBefore())

								p.sendMessage(ChatColor.DARK_RED + "You may need to register a new password "
										+ "due to an error recalling your original one.");

						}

					}

				}

			} else if (!sender.hasPermission(new Permissions().login))

				sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");

		}

		else if (label.equalsIgnoreCase("setloginspawn")) {

			if (sender.hasPermission(new Permissions().setLoginSpawn)) {

				if (!(sender instanceof Player)) {

					sender.sendMessage(ChatColor.RED + "You must be a player to perform this command.");

				} else if (sender instanceof Player) {

					Player p = (Player) sender;

					Location location = p.getLocation();

					loginSpawn.set("LoginSpawn.x", location.getX());

					loginSpawn.set("LoginSpawn.y", location.getY());

					loginSpawn.set("LoginSpawn.z", location.getZ());

					loginSpawn.set("LoginSpawn.Pitch", location.getPitch());

					loginSpawn.set("LoginSpawn.Yaw", location.getYaw());

					loginSpawn.set("LoginSpawn.World", location.getWorld().getName());

					loginSpawn.saveConfig();

					loginSpawn.reloadConfig();

					p.sendMessage(ChatColor.GREEN + "Login Spawn Set.");

				}

			} else if (!sender.hasPermission(new Permissions().setLoginSpawn))

				sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");

		}

		else if (label.equalsIgnoreCase("loginspawn")) {

			if (sender.hasPermission(new Permissions().loginSpawn)) {

				if (!(sender instanceof Player)) {

					sender.sendMessage(ChatColor.RED + "You must be a player to perform this command.");

				} else if (sender instanceof Player) {

					Player p = (Player) sender;

					if (loginSpawn.getString("LoginSpawn.x") == null
							|| loginSpawn.getString("LoginSpawn.x").isEmpty()
							|| loginSpawn.getString("LoginSpawn.y") == null
							|| loginSpawn.getString("LoginSpawn.y").isEmpty()
							|| loginSpawn.getString("LoginSpawn.z") == null
							|| loginSpawn.getString("LoginSpawn.z").isEmpty()
							|| loginSpawn.getString("LoginSpawn.Pitch") == null
							|| loginSpawn.getString("LoginSpawn.Pitch").isEmpty()
							|| loginSpawn.getString("LoginSpawn.Yaw") == null
							|| loginSpawn.getString("LoginSpawn.Yaw").isEmpty()
							|| loginSpawn.getString("LoginSpawn.World") == null
							|| loginSpawn.getString("LoginSpawn.World").isEmpty())

						p.sendMessage(ChatColor.RED + "There is no Login Spawn. "
								+ "You must set one with /SetLoginSpawn before using /LoginSpawn.");

					else {

						if (args.length == 0) {

							Location location = p.getLocation();

							location.setX(loginSpawn.getDouble("LoginSpawn.x"));

							location.setY(loginSpawn.getDouble("LoginSpawn.y"));

							location.setZ(loginSpawn.getDouble("LoginSpawn.z"));

							location.setPitch(loginSpawn.getFloat("LoginSpawn.Pitch"));

							location.setYaw(loginSpawn.getFloat("LoginSpawn.Yaw"));

							World world = Bukkit.getServer().getWorld(loginSpawn.getString("LoginSpawn.World"));

							location.setWorld(world);

							p.teleport(location);

						} else {
							
							if (sender.hasPermission(new Permissions().loginSpawnOther)) {
								
								if (Bukkit.getPlayer(args[0]) != null) {
									
									Player target = Bukkit.getPlayer(args[0]);
									
									Location loc = target.getLocation();
									
									loc.setX(loginSpawn.getDouble("LoginSpawn.x"));

									loc.setY(loginSpawn.getDouble("LoginSpawn.y"));

									loc.setZ(loginSpawn.getDouble("LoginSpawn.z"));

									loc.setPitch(loginSpawn.getFloat("LoginSpawn.Pitch"));

									loc.setYaw(loginSpawn.getFloat("LoginSpawn.Yaw"));

									World world = Bukkit.getServer().getWorld(loginSpawn.getString("LoginSpawn.World"));

									loc.setWorld(world);
									
									target.teleport(loc);
									
									sender.sendMessage(ChatColor.GREEN + "Teleported " + target.getName() + " to the Login Spawn.");
									
								} else
									
									sender.sendMessage(ChatColor.RED + "That player is not online.");
								
							} else
								
								sender.sendMessage(ChatColor.RED + "You don't have permission to teleport others to the Login Spawn.");
							
						}

					}

				}

			} else if (!sender.hasPermission(new Permissions().loginSpawn))

				sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");



		}

		return false;
	}

}
