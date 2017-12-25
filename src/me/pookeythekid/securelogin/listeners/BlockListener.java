package me.pookeythekid.securelogin.listeners;

import me.pookeythekid.securelogin.Main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockListener implements Listener {

	public Main M;

	public BlockListener(Main instance) {
		
		M = instance;
	
	}
	
	
	public void sendLoginMsg(Player player) {

		String message = M.colorCode(M.getConfig().getString("Must-Login-Message"));

		if (M.getConfig().getString("Must-Login-Message") != null
				&& !M.getConfig().getString("Must-Login-Message").isEmpty()) {

			player.sendMessage(message);

		}

	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		
		if (!M.getConfig().getBoolean("allowBlockBreak")
				&& M.isFrozen.contains(e.getPlayer())) {
			
			e.setCancelled(true);
			
			sendLoginMsg(e.getPlayer());
		
		}
	
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		
		if (!M.getConfig().getBoolean("allowBlockPlace")
				&& M.isFrozen.contains(e.getPlayer())) {
			
			e.setCancelled(true);
			
			sendLoginMsg(e.getPlayer());
		
		}
	
	}

	public void cancelEvent(PlayerInteractEvent event, Player p) {

		event.setCancelled(true);

		sendLoginMsg(p);

	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		if (!M.getConfig().getBoolean("allowInteract")
				&& M.isFrozen.contains(event.getPlayer())) {

			Player p = event.getPlayer();
			
			cancelEvent(event, p);

			/*if (event.getClickedBlock().getType() == Material.STONE_BUTTON) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.WOOD_BUTTON) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.TRIPWIRE) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.BURNING_FURNACE) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.FURNACE) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.TRAPPED_CHEST) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.HOPPER) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.ANVIL) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.ITEM_FRAME) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.NOTE_BLOCK) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.STONE_PLATE) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.WOOD_PLATE) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.GOLD_PLATE) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.IRON_PLATE) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.REDSTONE_COMPARATOR) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.DIODE) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.MINECART) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.BOAT) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.STORAGE_MINECART) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.POWERED_MINECART) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.HOPPER_MINECART) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.WORKBENCH) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.LEVER) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.DISPENSER) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.DROPPER) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.BEACON) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.BREWING_STAND) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.TRAPPED_CHEST) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.WOOD_DOOR) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.TRAP_DOOR) {

				cancelEvent(event, p);

			} else if (event.getClickedBlock().getType() == Material.FENCE_GATE) {

				cancelEvent(event, p);

			}*/

		}

	}

}
