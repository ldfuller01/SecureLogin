package me.pookeythekid.securelogin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import me.pookeythekid.securelogin.ConfigManager.MyConfig;
import me.pookeythekid.securelogin.ConfigManager.MyConfigManager;
import me.pookeythekid.securelogin.executors.Changers;
import me.pookeythekid.securelogin.executors.Getters;
import me.pookeythekid.securelogin.executors.HelpCmds;
import me.pookeythekid.securelogin.executors.LoginCmds;
import me.pookeythekid.securelogin.listeners.BlockListener;
import me.pookeythekid.securelogin.listeners.ChatListener;
import me.pookeythekid.securelogin.listeners.EntityDamageListener;
import me.pookeythekid.securelogin.listeners.ItemListener;
import me.pookeythekid.securelogin.listeners.LoginListener;
import me.pookeythekid.securelogin.listeners.MoveListener;
import me.pookeythekid.securelogin.passwords.Passwords;
import me.pookeythekid.securelogin.passwords.SecurityAnswers;
import me.pookeythekid.securelogin.passwords.SecurityQuestions;
import me.pookeythekid.securelogin.permissions.Permissions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public final Logger logger = Logger.getLogger("Minecraft");

	public MyConfigManager configManager;

	public MyConfig playerFile;

	public MyConfig loginSpawn;


	public final Permissions Permissions = new Permissions();
	
	// TODO Create deleting players and maybe adding new players. Add auto-updater, possibly plugin metrics too.


	// isFrozen stores players who are frozen.
	public ArrayList<Player> isFrozen = new ArrayList<Player>();


	// idFetch allows for getting a player's UUID by using their name.
	// This map is filled with all OfflinePlayers' names and IDs when the plugin enables.
	// All Strings that enter this map are lower case.
	public HashMap<String, UUID> idFetch = new HashMap<String, UUID>();


	// strIdFetch contains the string IDs of all players in the server.
	public ArrayList<String> strIds = new ArrayList<String>();


	// nameFetch allows for getting a player's name using their UUID.
	// This map is filled with all OfflinePlayers' IDs and names when the plugin enables.
	// All strings that enter this map are lower case.
	public HashMap<UUID, String> nameFetch = new HashMap<UUID, String>();


	// This stores players' locations when they log in so that they can be teleported
	// back from the Login Spawn to their previous location after logging in; this is
	// if it's set to do so in the config file. Locations are stored in the form of
	// strings because p.getLocation() always represents a player's current location,
	// instead of being stored as a previous location.
	public HashMap<Player, String> loginLocations = new HashMap<Player, String>();


	// changePassMap hold players' names and new passwords for their changepassword
	// commands so they can be asked their security questions or have enter their
	// old password, and after that have their new passwords be made official by 
	// the ChatListener. The ChatListener will pick up their answers to enact their
	// password change.
	public HashMap<Player, String> changePassMap = new HashMap<Player, String>();


	// changeSQMap will store the player's arguments from SecurityQuestions and make them
	// accessible to ChatListener. ChatListener will use these arugments to change the player's
	// Security Question by setting them into the config in proper order.
	// See ChatListener to see changeSQMap's function in code.
	// See SecurityQuestions to see changeSQMap's memory assignment in code.
	public HashMap<String, String> changeSQMap = new HashMap<String, String>();


	// changeSAMap stores the player's arguments from SecurityAnswers and make them
	// accessible to ChatListener. ChatListener will use these arguments to change the player's
	// Security Answer by setting them to the config in proper order. See ChatListener and
	// SecurityQuestions.
	public HashMap<String, String> changeSAMap = new HashMap<String, String>();


	// changeSQ stores players who are going through the process of
	// changing their Security Question.
	// See SecurityQuestions and ChatListener classes to see its function.
	public ArrayList<Player> changeSQ = new ArrayList<Player>();


	// changeSA stores players who are going through the process of
	// changing their Security Answer.
	// See SecurityAnswers and ChatListener.
	public ArrayList<Player> changeSA = new ArrayList<Player>();


	// changePass stores players who are going through the process
	// of changing their password. See Passwords and ChatListener.
	public ArrayList<Player> changePass = new ArrayList<Player>();


	// If changeQWithQuestion is set to true in the config, this will
	// store players who are changing their Security Question, and starts
	// their direction down the path of using their old Security Question
	// to change their Security Question.
	public ArrayList<Player> verifyQuesWithQ = new ArrayList<Player>();


	// verifyQuesWithQuesAnswer stores players who are asked to enter their
	// Security Answer to change their Security Question.
	public ArrayList<Player> verifyQuesWithQAnswer = new ArrayList<Player>();


	// If changeSAWithPassword is set to true in the config, this will
	// store players who are changing their Security Question, and starts
	// their direction down the path of using their password to change
	// their Security Question.
	public ArrayList<Player> verifyQuesWithP = new ArrayList<Player>();


	// verifyQuesWithPAnswer stores players who are asked to enter their
	// login Password to change their Security Question.
	public ArrayList<Player> verifyQuesWithPAnswer = new ArrayList<Player>();


	// If changeSAWithQuestion is set to true in the config, this will
	// store players who are changing their security question, and starts
	// their direction down the path of using their Security Question to
	// change their Security Answer.
	public ArrayList<Player> verifySAWithQ = new ArrayList<Player>();


	// verifySAWithQAnswer stores players who are asked to enter their old
	// Security Answer to change their Security Answer.
	public ArrayList<Player> verifySAWithQAnswer = new ArrayList<Player>();


	// If changeSAWithPassword is set to true in the config, this will
	// store players who are changing their security question, and starts
	// their direction down the path of using their password to change
	// their Security Answer.
	public ArrayList<Player> verifySAWithPass = new ArrayList<Player>();


	// verifySAWithPassAnswer stores players who are asked to entier their
	// password to change their Security Answer.
	public ArrayList<Player> verifySAWithPassAnswer = new ArrayList<Player>();


	// If changePWithQuestion is set to true in the config, this will
	// store players who are changing their password, and starts their
	// direction down the path of using their Security Question to change
	// their password.
	public ArrayList<Player> verifyPassWithQues = new ArrayList<Player>();


	// verifyPassWithQuesAnswer stores players who are asked to enter their
	// Security Question to change their password.
	public ArrayList<Player> verifyPassWithQuesAnswer = new ArrayList<Player>();


	// If changePWithPassword is set to true in the config, this will
	// store players who are changing their password, and starts their
	// direction down the path of using their old password to change
	// it to their new password.
	public ArrayList<Player> verifyPassWithPass = new ArrayList<Player>();


	// verifyPassWithPassAnswer stores players who are asked to enter their old
	// password in order to change it to their new one.
	public ArrayList<Player> verifyPassWithPassAnswer = new ArrayList<Player>();


	// createSA keeps track of players who have just created their
	// Security Question, and who are allowed to then use /CreateSA
	public ArrayList<Player> createSA = new ArrayList<Player>();


	public String colorCode(String string) {

		string = string.replaceAll("&1", ChatColor.DARK_BLUE + "");
		string = string.replaceAll("&2", ChatColor.DARK_GREEN + "");
		string = string.replaceAll("&3", ChatColor.DARK_AQUA + "");
		string = string.replaceAll("&4", ChatColor.DARK_RED + "");
		string = string.replaceAll("&5", ChatColor.DARK_PURPLE + "");
		string = string.replaceAll("&6", ChatColor.GOLD + "");
		string = string.replaceAll("&7", ChatColor.GRAY + "");
		string = string.replaceAll("&8", ChatColor.DARK_GRAY + "");
		string = string.replaceAll("&9", ChatColor.BLUE + "");
		string = string.replaceAll("&0", ChatColor.BLACK + "");

		string = string.replaceAll("&a", ChatColor.GREEN + "");
		string = string.replaceAll("&b", ChatColor.AQUA + "");
		string = string.replaceAll("&c", ChatColor.RED + "");
		string = string.replaceAll("&d", ChatColor.LIGHT_PURPLE + "");
		string = string.replaceAll("&e", ChatColor.YELLOW + "");
		string = string.replaceAll("&f", ChatColor.WHITE + "");

		string = string.replaceAll("&l", ChatColor.BOLD + "");
		string = string.replaceAll("&o", ChatColor.ITALIC + "");
		string = string.replaceAll("&m", ChatColor.STRIKETHROUGH + "");
		string = string.replaceAll("&n", ChatColor.UNDERLINE + "");
		string = string.replaceAll("&k", ChatColor.MAGIC + "");

		return string;

	}

	public void putLoginLocation(Player p, Location loc) {

		Location location = p.getLocation();

		String x = String.valueOf(location.getX());

		String y = String.valueOf(location.getY());

		String z = String.valueOf(location.getZ());

		String pitch = String.valueOf(location.getPitch());

		String yaw = String.valueOf(location.getYaw());

		String world = location.getWorld().getName();

		StringBuffer strLoc = new StringBuffer();

		for (int i = 1; i <= 6; i++) {

			switch (i) {

			case 1:

				strLoc.append(x + " - ");

				break;

			case 2:

				strLoc.append(y + " - ");

				break;

			case 3:

				strLoc.append(z + " - ");

				break;

			case 4:

				strLoc.append(pitch + " - ");

				break;

			case 5:

				strLoc.append(yaw + " - ");

				break;

			case 6:

				strLoc.append(world);

				break;

			}

		}

		this.loginLocations.put(p, strLoc.toString());

	}

	public void returnToLoginLoc(Player p) {

		Location loc = p.getLocation();

		String[] coords = this.loginLocations.get(p).split(" - ");

		for (int i = 0; i < coords.length; i++) {

			switch (i) {

			case 0:

				loc.setX(Double.valueOf(coords[i]));

				break;

			case 1:

				loc.setY(Double.valueOf(coords[i]));

				break;

			case 2:

				loc.setZ(Double.valueOf(coords[i]));

				break;

			case 3:

				loc.setPitch(Float.valueOf(coords[i]));

				break;

			case 4:

				loc.setYaw(Float.valueOf(coords[i]));

				break;

			case 5:

				loc.setWorld(Bukkit.getWorld(coords[i]));

				break;

			}

		}

		p.teleport(loc);

	}

	public void stopChange(Player p) {

		changeSQ.remove(p);

		changeSA.remove(p);

		verifyQuesWithP.remove(p);

		verifyQuesWithPAnswer.remove(p);

		verifyQuesWithQ.remove(p);

		verifyQuesWithQAnswer.remove(p);

		verifySAWithQ.remove(p);

		verifySAWithQAnswer.remove(p);

		verifySAWithPass.remove(p);

		verifySAWithPassAnswer.remove(p);

		changePass.remove(p);

		verifyPassWithQues.remove(p);

		verifyPassWithQuesAnswer.remove(p);

		verifyPassWithPass.remove(p);

		verifyPassWithPassAnswer.remove(p);

		changePassMap.remove(p);

		createSA.remove(p);

		int i = 1;

		while (i < 101) {

			changeSQMap.remove(p.getName() + "-" + i);

			i++;

		}

		i = 1;

		while (i < 101) {

			changeSAMap.remove(p.getName() + "-" + i);

			i++;

		}

	}

	public boolean playerIsChanging(Player p) {

		return (

				changeSQ.contains(p)

				|| changeSA.contains(p)

				|| verifyQuesWithP.contains(p)

				|| verifyQuesWithPAnswer.contains(p)

				|| verifyQuesWithQ.contains(p)

				|| verifyQuesWithQAnswer.contains(p)

				|| verifySAWithQ.contains(p)

				|| verifySAWithQAnswer.contains(p)

				|| verifySAWithPass.contains(p)

				|| verifySAWithPassAnswer.contains(p)

				|| changePass.contains(p)

				|| verifyPassWithQues.contains(p)

				|| verifyPassWithQuesAnswer.contains(p)

				|| verifyPassWithPass.contains(p)

				|| verifyPassWithPassAnswer.contains(p)

				|| createSA.contains(p)

				|| changeSQMap.containsKey(p.getName() + "-1")

				|| changeSAMap.containsKey(p.getName() + "-1")

				|| changePassMap.containsKey(p)

				);

	}

	@Override
	public void onDisable() {

		PluginDescriptionFile pdfFile = this.getDescription();

		this.logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " is now disabled.");

		PluginManager pm = this.getServer().getPluginManager();

		Permissions.removePerms(pm);

	}

	@Override
	public void onEnable() {

		PluginDescriptionFile pdfFile = this.getDescription();

		this.logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " is now enabled.");


		for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {

			this.idFetch.put(p.getName().toLowerCase(), p.getUniqueId());

			this.strIds.add(p.getUniqueId().toString());

			this.nameFetch.put(p.getUniqueId(), p.getName().toLowerCase());

		}


		PluginManager pm = this.getServer().getPluginManager();

		pm.registerEvents(new LoginListener(this), this);

		pm.registerEvents(new ChatListener(this), this);

		pm.registerEvents(new BlockListener(this), this);

		pm.registerEvents(new EntityDamageListener(this), this);

		pm.registerEvents(new ItemListener(this), this);

		pm.registerEvents(new MoveListener(this), this);

		Permissions.registerPerms(pm);

		try {


			saveDefaultConfig();

			configManager = new MyConfigManager(this);

			playerFile = configManager.getNewConfig("players.yml");

			loginSpawn = configManager.getNewConfig("loginspawn.yml");


			Set<String> keys = playerFile.getConfigurationSection("Players").getKeys(false);

			// StringBuffer sb = new StringBuffer();

			boolean makeNewPlayerFile = false;

			for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {

				if (keys.contains(p.getName())) {

					makeNewPlayerFile = true;

					break;

				}

			}

			if (makeNewPlayerFile) {

				logger.info("Making new player file...");

				File newFile = new File(this.getDataFolder(), "newplayerfile.yml");

				if (!newFile.exists())

					newFile.createNewFile();

				MyConfig newPlayerFile = configManager.getNewConfig("newplayerfile.yml");

				for (String s : keys) {

					logger.info("Going through for loop");

					String name = s;

					logger.info(name);

					String password = playerFile.getString("Players." + s + ".Password");

					logger.info(password);

					String secques = playerFile.getString("Players." + s + ".SecurityQuestion");

					logger.info(secques);

					String secanswer = playerFile.getString("Players." + s + ".SecurityAnswer");

					logger.info(secanswer);

					String id = this.idFetch.get(s.toLowerCase()).toString();

					logger.info(id);

					logger.info("Start name " + name);

					newPlayerFile.set("Players." + id + ".Name", name);

					newPlayerFile.saveConfig();

					logger.info("End name " + name);

					logger.info("Start password " + name);

					newPlayerFile.set("Players." + id + ".Password", password);

					newPlayerFile.saveConfig();

					logger.info("End password " + name);

					logger.info("Start secques " + name);

					newPlayerFile.set("Players." + id + ".SecurityQuestion", secques);

					newPlayerFile.saveConfig();

					logger.info("End secques " + name);

					logger.info("Start secanswer " + name);

					newPlayerFile.set("Players." + id + ".SecurityAnswer", secanswer);

					newPlayerFile.saveConfig();

					logger.info("End secanswer " + name);

				}

				newPlayerFile.reloadConfig();

				StringBuffer content = new StringBuffer();

				File oldFile = new File(this.getDataFolder(), "players.yml");

				BufferedReader reader = new BufferedReader(new FileReader(newFile));

				while (true) {

					String line = reader.readLine();

					if (line == null)

						break;

					if (line.startsWith(newFile.getName()))

						content.append(line.replace(newFile.getName(), "") + "\n");
					
					else
						
						content.append(line + "\n");

				}

				reader.close();

				logger.info(content.toString());

				FileWriter writer = new FileWriter(oldFile);

				writer.write(content.toString());

				writer.close();
				
				newFile.delete();

				playerFile.reloadConfig();

			}

			/*

			if (makeNewPlayerFile) {

				File file = new File(this.getDataFolder(), "players.yml");

				BufferedReader reader = new BufferedReader(new FileReader(file));

				while (true) {

					String line = reader.readLine();

					if (line == null)

						break;

					sb.append(line + "\n");

				}

				for (String s : keys) {

					UUID id = this.idFetch.get(s.toLowerCase());

					String name = this.nameFetch.get(id);

					// For some odd reason, the name string is outputted with a colon on the end of it.
					// The second for loop that you see below is a reiteration over the file's text to remove
					// that second colon from the names.

					String str = sb.toString().replace(s, id.toString() + ":\n    Name: " + name);

					sb.setLength(0);

					sb.append(str);

				}

				FileWriter writer = new FileWriter(file);

				writer.write(sb.toString());

				writer.close();

				reader.close();

				FileWriter writer2 = new FileWriter(file);

				StringBuffer sb2 = new StringBuffer();

				HashMap<Integer, String> lineMap = new HashMap<Integer, String>();

				String[] sbLines = sb.toString().split("\n");

				int lines = 1;

				String id = "";

				boolean readingName = false;

				for (String s : sbLines) {

					String line = s;

					if (readingName) {

						String name = nameFetch.get(UUID.fromString(id));

						String newLine = "    Name: " + name + "\n";

						lineMap.put(lines, newLine);

						readingName = false;

					} else

						lineMap.put(lines, line + "\n");

					String uuid;

					uuid = line.replaceFirst("  ", "");

					uuid = uuid.replace(":", "");

					if (this.strIds.contains(uuid)) {

						id = uuid;

						readingName = true;

					}

					lines++;

				}

				for (int i = 1; i <= lines; i++) {

					sb2.append(lineMap.get(i));

				}

				String content = sb2.toString().replace("null", "");

				writer2.write(content);

				writer2.close();

			}

			 */


		} catch (Exception e) { e.printStackTrace(); }


		getCommand("login").setExecutor(new LoginCmds(this));

		getCommand("register").setExecutor(new LoginCmds(this));

		getCommand("setloginspawn").setExecutor(new LoginCmds(this));

		getCommand("loginspawn").setExecutor(new LoginCmds(this));


		// getCommand("setmessage").setExecutor(new MessageCmds(this));

		// getCommand("setmessagehelp").setExecutor(new MessageCmds(this));


		getCommand("securelogin").setExecutor(new HelpCmds(this));


		getCommand("createsecurityquestion").setExecutor(new SecurityQuestions(this));

		getCommand("changesecurityquestion").setExecutor(new SecurityQuestions(this));

		getCommand("getsecurityquestion").setExecutor(new Getters(this));

		getCommand("changesqfor").setExecutor(new Changers(this));


		getCommand("createsecurityanswer").setExecutor(new SecurityAnswers(this));

		getCommand("changesecurityanswer").setExecutor(new SecurityAnswers(this));

		getCommand("getsecurityanswer").setExecutor(new Getters(this));

		getCommand("changesafor").setExecutor(new Changers(this));


		getCommand("changepassword").setExecutor(new Passwords(this));

		getCommand("getpassword").setExecutor(new Getters(this));

		getCommand("changepasswordfor").setExecutor(new Changers(this));


	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if (commandLabel.equalsIgnoreCase("slreload")) {

			if (sender.hasPermission(new Permissions().reload)) {

				reloadConfig();

				playerFile.reloadConfig();

				loginSpawn.reloadConfig();

				for (Player p : Bukkit.getServer().getOnlinePlayers()) {

					if (playerIsChanging(p)) {

						stopChange(p);

						p.sendMessage(ChatColor.RED + "You must restart your changing process because "
								+ "an administrator has reloaded SecureLogin.");

					}

				}

				sender.sendMessage(ChatColor.GREEN + "Configs reloaded.");

			} else if (!(sender.hasPermission(new Permissions().reload)) && !(sender.isOp())) {

				sender.sendMessage(ChatColor.DARK_RED
						+ "You don't have permission to perform this command.");

			}

		}

		return true;

	}

}
