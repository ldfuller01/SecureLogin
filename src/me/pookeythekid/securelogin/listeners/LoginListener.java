package me.pookeythekid.securelogin.listeners;

import me.pookeythekid.securelogin.Main;
import me.pookeythekid.securelogin.ConfigManager.MyConfig;
import me.pookeythekid.securelogin.permissions.Permissions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginListener implements Listener {

	public Main M;

	public LoginListener(Main instance) {

		M = instance;

	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent e) {

		MyConfig loginSpawn = M.loginSpawn;

		Player p = e.getPlayer();

		Location location = p.getLocation();

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
				|| loginSpawn.getString("LoginSpawn.World").isEmpty()) {

			if (M.getConfig().getBoolean("tpLoginSpawn")) {

				M.logger.warning("Warning: The login spawn is either empty or incomplete! "
						+ "Players will log in where they join the server instead!");

				if (p.hasPermission(new Permissions().seeSpawnWarning))

					p.sendMessage(ChatColor.RED + "Warning: There is no login spawn! "
							+ "Players will login where they join the server instead!");

			}

			if (!p.hasPermission(new Permissions().antifreeze)) {

				M.isFrozen.add(p);

				if (M.getConfig().getString("Must-Login-Message") != null
						&& !M.getConfig().getString("Must-Login-Message").isEmpty()) {

					p.sendMessage(M.colorCode(M.getConfig().getString("Must-Login-Message")));

					if (M.isFrozen.contains(p) && M.getConfig().getBoolean("tpLoginSpawn"))
						
						M.putLoginLocation(p, location);

				}

			}

		} else {

			if (!p.hasPermission(new Permissions().antifreeze)) {

				M.isFrozen.add(p);

				if (M.getConfig().getString("Must-Login-Message") != null
						&& !M.getConfig().getString("Must-Login-Message").isEmpty())

					p.sendMessage(M.colorCode(M.getConfig().getString("Must-Login-Message")));

				if (M.isFrozen.contains(p)
						&& M.getConfig().getBoolean("tpLoginSpawn")) {

					M.putLoginLocation(p, location);

					location.setX(loginSpawn.getDouble("LoginSpawn.x"));

					location.setY(loginSpawn.getDouble("LoginSpawn.y"));

					location.setZ(loginSpawn.getDouble("LoginSpawn.z"));

					location.setPitch(loginSpawn.getFloat("LoginSpawn.Pitch"));

					location.setYaw(loginSpawn.getFloat("LoginSpawn.Yaw"));

					World world = Bukkit.getServer().getWorld(loginSpawn.getString("LoginSpawn.World"));

					location.setWorld(world);

					p.teleport(location);

				}

			}

		}

	}

}