package me.pookeythekid.securelogin.listeners;

import java.util.List;

import me.pookeythekid.securelogin.Main;
import me.pookeythekid.securelogin.ConfigManager.MyConfig;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

@SuppressWarnings("deprecation")
public class ChatListener implements Listener {

	public Main M;

	public ChatListener(Main instance) {
		M = instance;
	}

	@EventHandler
	public void onCmd(PlayerCommandPreprocessEvent e) {

		Player p = e.getPlayer();

		if (M.getConfig().getBoolean("freezeChat")
				&& M.isFrozen.contains(p)) {

			List<String> allowedCommands = M.getConfig().getStringList("allowedCommands");
			
			String cmdName = e.getMessage().split(" ")[0];

			if (!allowedCommands.contains(cmdName.toLowerCase())) {

				e.setCancelled(true);

				if (M.getConfig().getString("Must-Login-Message") != null
						&& !M.getConfig().getString("Must-Login-Message").isEmpty()) {

					p.sendMessage(M.colorCode(M.getConfig().getString(
							"Must-Login-Message")));

				}

			}

		}

	}


	@EventHandler
	public void onPlayerChat(PlayerChatEvent e) {

		Player p = e.getPlayer();
		
		String id = p.getUniqueId().toString();

		MyConfig playerFile = M.playerFile;

		if (M.changeSQ.contains(p)
				&& e.getMessage().equalsIgnoreCase("yes")) {

			e.setCancelled(true);

			p.sendMessage(ChatColor.GOLD + "You must now verify that you are a permitted user of this account to "
					+ "finish changing your Security Question.");

			if (M.getConfig().getBoolean("changeQWithQuestion")
					&& M.verifyQuesWithQ.contains(p)) {

				p.sendMessage(ChatColor.AQUA + playerFile.getString("Players." + id
						+ ".SecurityQuestion"));

				p.sendMessage(ChatColor.GREEN + "Chat the answer to this question to continue.");

				M.verifyQuesWithQ.remove(p);

				M.verifyQuesWithQAnswer.add(p);

			} else if (M.getConfig().getBoolean("changeQWithPassword")
					&& M.verifyQuesWithP.contains(p)) {

				p.sendMessage(ChatColor.AQUA + "To comfirm that you are a permitted user of this account, chat your "
						+ "login password.");

				p.sendMessage(ChatColor.GREEN + "Password is case-sensitive. You must restart your change of Security Question if you "
						+ "enter your password incorrectly.");

				M.verifyQuesWithP.remove(p);

				M.verifyQuesWithPAnswer.add(p);

			}

		}

		else if (M.changeSQ.contains(p)
				&& M.verifyQuesWithPAnswer.contains(p)
				&& e.getMessage().equals(playerFile.getString("Players." + id + ".Password"))) {

			e.setCancelled(true);

			int size = 1;

			while (M.changeSQMap.get(p.getName() + "-" + size) != null) {

				size++;

			}

			size--;

			int mapCount = 1;

			StringBuffer sb = new StringBuffer();

			while (mapCount <= size) {

				if (mapCount != size)
					sb.append(M.changeSQMap.get(p.getName() + "-" + mapCount) + " ");

				else if (mapCount == size)
					sb.append(M.changeSQMap.get(p.getName() + "-" + mapCount));

				mapCount++;

			}

			playerFile.set("Players." + id + ".SecurityQuestion", sb.toString());

			playerFile.saveConfig();

			playerFile.reloadConfig();

			p.sendMessage(ChatColor.GREEN + "Security Question Created: "
					+ playerFile.getString("Players." + id + ".SecurityQuestion"));

			M.verifyQuesWithPAnswer.remove(p);

			M.changeSQ.remove(p);

			p.sendMessage(ChatColor.GOLD + "You must now create your Security Answer. Use "
					+ ChatColor.AQUA + "/CreateSecurityAnswer" + ChatColor.GOLD + " or "
					+ ChatColor.AQUA + "/CreateSA" + ChatColor.GOLD + " to do this.");
			
			p.sendMessage(ChatColor.GOLD + "If your old answer is correct with your new Security Question, "
					+ "there is no need to do this.");

			M.createSA.add(p);

		}

		else if (M.changeSQ.contains(p)
				&& M.verifyQuesWithQAnswer.contains(p)
				&& e.getMessage().equalsIgnoreCase(playerFile.getString("Players." + id + ".SecurityAnswer"))) {

			e.setCancelled(true);

			int size = 1;

			while (M.changeSQMap.get(p.getName() + "-" + size) != null) {

				size++;

			}

			size--;

			int mapCount = 1;

			StringBuffer sb = new StringBuffer();

			while (mapCount <= size) {

				if (mapCount != size)
					sb.append(M.changeSQMap.get(p.getName() + "-" + mapCount) + " ");

				else if (mapCount == size)
					sb.append(M.changeSQMap.get(p.getName() + "-" + mapCount));

				mapCount++;

			}

			playerFile.set("Players." + id + ".SecurityQuestion", sb.toString());

			playerFile.saveConfig();

			playerFile.reloadConfig();

			p.sendMessage(ChatColor.GREEN + "Security Question Created: "
					+ playerFile.getString("Players." + id + ".SecurityQuestion"));

			M.verifyQuesWithPAnswer.remove(p);

			M.changeSQ.remove(p);

			p.sendMessage(ChatColor.GOLD + "You must now create your Security Answer. Use "
					+ ChatColor.AQUA + "/CreateSecurityAnswer" + ChatColor.GOLD + " or "
					+ ChatColor.AQUA + "/CreateSA" + ChatColor.GOLD + " to do this.");
			
			p.sendMessage(ChatColor.GOLD + "If your old answer is correct with your new Security Question, "
					+ "there is no need to do this.");

			M.createSA.add(p);

		}

		else if (M.changeSQ.contains(p)
				&& M.verifyQuesWithPAnswer.contains(p)
				&& !e.getMessage().equals(playerFile.getString("Players." + id + ".Password"))) {

			e.setCancelled(true);

			p.sendMessage(ChatColor.RED + "Password incorrect. "
					+ "You must restart the process of changing your Security Question.");

			M.stopChange(p);

		}

		else if (M.changeSQ.contains(p)
				&& M.verifyQuesWithQAnswer.contains(p)
				&& !e.getMessage().equalsIgnoreCase(playerFile.getString("Players." + id + ".SecurityAnswer"))) {

			e.setCancelled(true);

			p.sendMessage(ChatColor.RED + "Security Answer incorrect. "
					+ "You must restart the process of changing your Security Question.");

			M.stopChange(p);

		}

		else if (M.changeSA.contains(p)
				&& M.verifySAWithPassAnswer.contains(p)
				&& e.getMessage().equals(playerFile.getString("Players." + id + ".Password"))) {

			e.setCancelled(true);

			int size = 1;

			while (M.changeSAMap.get(p.getName() + "-" + size) != null) {

				size++;

			}

			size--;

			int mapCount = 1;

			StringBuffer sb = new StringBuffer();

			while (mapCount <= size) {

				if (mapCount != size)
					sb.append(M.changeSAMap.get(p.getName() + "-" + mapCount) + " ");

				else if (mapCount == size)
					sb.append(M.changeSAMap.get(p.getName() + "-" + mapCount));

				mapCount++;

			}

			playerFile.set("Players." + id + ".SecurityAnswer", sb.toString());

			playerFile.saveConfig();

			playerFile.reloadConfig();

			p.sendMessage(ChatColor.GREEN + "Security Answer Created: "
					+ playerFile.getString("Players." + id + ".SecurityAnswer"));

			M.stopChange(p);

		}

		else if (M.changeSA.contains(p)
				&& M.verifySAWithQAnswer.contains(p)
				&& e.getMessage().equalsIgnoreCase(playerFile.getString("Players." + id + ".SecurityAnswer"))) {

			e.setCancelled(true);

			playerFile.set("Players." + id + ".SecurityAnswer", "");

			playerFile.saveConfig();

			int size = 1;

			while (M.changeSAMap.get(p.getName() + "-" + size) != null) {

				size++;

			}

			size--;

			int mapCount = 1;

			StringBuffer sb = new StringBuffer();

			while (mapCount <= size) {

				if (mapCount != size)
					sb.append(M.changeSAMap.get(p.getName() + "-" + mapCount) + " ");

				else if (mapCount == size)
					sb.append(M.changeSAMap.get(p.getName() + "-" + mapCount));

				mapCount++;

			}

			playerFile.set("Players." + id + ".SecurityAnswer", sb.toString());

			playerFile.saveConfig();

			playerFile.reloadConfig();

			p.sendMessage(ChatColor.GREEN + "Security Answer Created: "
					+ playerFile.getString("Players." + id + ".SecurityAnswer"));

			M.stopChange(p);

		}

		else if (M.changeSA.contains(p)
				&& M.verifySAWithPassAnswer.contains(p)
				&& !e.getMessage().equals(playerFile.getString("Players." + id + ".Password"))) {

			e.setCancelled(true);

			p.sendMessage(ChatColor.RED + "Password incorrect. "
					+ "You must restart the process of changing your Security Answer.");

			M.stopChange(p);

		}

		else if (M.changeSA.contains(p)
				&& M.verifySAWithQAnswer.contains(p)
				&& !e.getMessage().equalsIgnoreCase(playerFile.getString("Players." + id + ".SecurityAnswer"))) {

			e.setCancelled(true);

			p.sendMessage(ChatColor.RED + "Security Answer incorrect. "
					+ "You must restart the process of changing your Security Answer.");

			M.stopChange(p);

		}

		else if (M.changePass.contains(p)
				&& M.verifyPassWithQuesAnswer.contains(p)
				&& e.getMessage().equalsIgnoreCase(playerFile.getString("Players." + id + ".SecurityAnswer"))) {

			e.setCancelled(true);

			playerFile.set("Players." + id + ".Password",
					M.changePassMap.get(p));

			playerFile.saveConfig();

			playerFile.reloadConfig();

			p.sendMessage(ChatColor.GREEN + "Password changed: "
					+ playerFile.getString("Players." + id + ".Password"));

			M.stopChange(p);

		}

		else if (M.changePass.contains(p)
				&& M.verifyPassWithPassAnswer.contains(p)
				&& e.getMessage().equals(playerFile.getString("Players." + id + ".Password"))) {

			e.setCancelled(true);

			playerFile.set("Players." + id + ".Password",
					M.changePassMap.get(p));

			playerFile.saveConfig();

			playerFile.reloadConfig();

			p.sendMessage(ChatColor.GREEN + "Password changed: "
					+ playerFile.getString("Players." + id + ".Password"));

			M.stopChange(p);

		}

		else if (M.changePass.contains(p)
				&& M.verifyPassWithQuesAnswer.contains(p)
				&& !e.getMessage().equalsIgnoreCase(playerFile.getString("Players." + id + ".SecurityAnswer"))) {

			e.setCancelled(true);

			p.sendMessage(ChatColor.RED + "Security Answer incorrect. "
					+ "You must restart the process of changing your password.");

			M.stopChange(p);

		}

		else if (M.changePass.contains(p)
				&& M.verifyPassWithPassAnswer.contains(p)
				&& !e.getMessage().equals(playerFile.getString("Players." + id + ".Password"))) {

			e.setCancelled(true);

			p.sendMessage(ChatColor.RED + "Password incorrect. "
					+ "You must restart the process of changing your password.");

			M.stopChange(p);

		}

		else if (M.playerIsChanging(p)) {

			if (e.getMessage().equalsIgnoreCase("cancel")) {

				e.setCancelled(true);

				M.stopChange(p);

				p.sendMessage(ChatColor.GREEN + "Cancelled. You may resume chatting.");

			} else if (!e.getMessage().equalsIgnoreCase("cancel")) {

				e.setCancelled(true);

				p.sendMessage(ChatColor.AQUA
						+ "Chat the word 'cancel' if you wish to stop and resume chatting.");

			}

		}

	}

}
