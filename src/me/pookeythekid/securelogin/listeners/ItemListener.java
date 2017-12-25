package me.pookeythekid.securelogin.listeners;

import me.pookeythekid.securelogin.Main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class ItemListener implements Listener {

	public Main M;

	public ItemListener(Main instance) {

		M = instance;

	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {

		if (!M.getConfig().getBoolean("allowItemDrop")
				&& M.isFrozen.contains(e.getPlayer())) {

			e.setCancelled(true);

			if (M.getConfig().getString("Must-Login-Message") != null
					&& !M.getConfig().getString("Must-Login-Message").isEmpty()) {

				e.getPlayer().sendMessage(M.colorCode(M.getConfig().getString("Must-Login-Message")));

			}

		}

	}

	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent e) {

		if (!M.getConfig().getBoolean("allowItemPickup")
				&& M.isFrozen.contains(e.getPlayer())) {

			e.setCancelled(true);
			
			if (M.getConfig().getString("Must-Login-Message") != null
					&& !M.getConfig().getString("Must-Login-Message").isEmpty()) {

				e.getPlayer().sendMessage(M.colorCode(M.getConfig().getString("Must-Login-Message")));

			}

		}

	}

}
