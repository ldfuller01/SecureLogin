package me.pookeythekid.securelogin.listeners;

import me.pookeythekid.securelogin.Main;
import me.pookeythekid.securelogin.permissions.Permissions;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

	public Main plugin;

	public MoveListener(Main instance) {

		plugin = instance;

	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerMove(PlayerMoveEvent event) {

		try {

			Player player = event.getPlayer();

			if (plugin.isFrozen.contains(player)) {

				if (!player.hasPermission(new Permissions().antifreeze)) {

					player.teleport(player);

					if (plugin.getConfig().getString("Must-Login-Message") != null
							&& !plugin.getConfig().getString("Must-Login-Message").isEmpty()) {

						player.sendMessage(plugin.colorCode(plugin.getConfig().getString("Must-Login-Message")));

					}

				}

			}

		} catch (Exception e) {}

	}

}
