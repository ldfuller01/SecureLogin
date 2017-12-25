package me.pookeythekid.securelogin.listeners;

import me.pookeythekid.securelogin.Main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
	
	public Main M;
	
	public EntityDamageListener(Main instance) {
		
		M = instance;
	
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		
		if (e.getEntity() instanceof Player) {
			
			Player p = (Player) e.getEntity();
			
			if (M.isFrozen.contains(p)
				&& !M.getConfig().getBoolean("allowGetDamaged")) {
				
				e.setCancelled(true);
				
				if (M.getConfig().getString("Must-Login-Message") != null
					&& !M.getConfig().getString("Must-Login-Message").isEmpty()) {
					
					p.sendMessage(M.colorCode(M.getConfig().getString("Must-Login-Message")));
				
				}
			
			}
		
		}
	
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		
		if (e.getDamager() instanceof Player) {
			
			Player p = (Player) e.getDamager();
			
			if (M.isFrozen.contains(p)
				&& !M.getConfig().getBoolean("allowDamaging")) {
				
				e.setCancelled(true);
				
				if (M.getConfig().getString("Must-Login-Message") != null
					&& !M.getConfig().getString("Must-Login-Message").isEmpty()) {
					
					p.sendMessage(M.colorCode(M.getConfig().getString("Must-Login-Message")));
				
				}
			
			}
		
		}
	
	}

}
