package com.github.excaliburHisSheath;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HardcoreSMP extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		new File(mainDirectory).mkdir();
		if (!data.exists()) {
			try {
				data.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		PluginDescriptionFile pdFile = getDescription();

		// load the config file, create one if there is none
		getConfig().options().copyDefaults(true);
		saveConfig();

		logger.info(pdFile.getName() + " version " + pdFile.getVersion()
				+ " is now running.");
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile pdFile = getDescription();
		logger.info(pdFile.getName() + " version " + pdFile.getVersion()
				+ " is now disabled.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("hsmp")) {
			if (args.length == 0) {
				String[] messages = {
						"/hsmp set [min:max] <value> - sets the minimum or maximum respawn distance",
						"/hsmp [min:max] - view the currently set minimum or maximum respawn distance" };
				sender.sendMessage(messages);
			} else if (args[0].equalsIgnoreCase("set")) {
				if (args.length < 3) {
					sender.sendMessage(ChatColor.RED + "not enough arguments!");
				} else if (args[1].equalsIgnoreCase("min")
						|| args[1].equalsIgnoreCase("minimum")) {
					try {
						int value = Integer.parseInt(args[2]);
						if (value < getConfig().getInt("maximum")) {
							getConfig().set("minimum", value);
							saveConfig();
							sender.sendMessage(ChatColor.GREEN
									+ "minimum distance successfully set to "
									+ value);
						} else {
							sender.sendMessage(ChatColor.RED
									+ "The minimum distance must be less than the maximum distance!");
						}
					} catch (NumberFormatException e) {
						sender.sendMessage(ChatColor.RED
								+ "That is not a valid minimum distance!");
					}
				} else if (args[1].equalsIgnoreCase("max")
						|| args[1].equalsIgnoreCase("maximum")) {
					try {
						int value = Integer.parseInt(args[2]);
						if (value > getConfig().getInt("minimum")) {
							getConfig().set("maximum", value);
							saveConfig();
							sender.sendMessage(ChatColor.GREEN
									+ "Maximum distance successfully set to "
									+ value);
						} else {
							sender.sendMessage(ChatColor.RED
									+ "The maximum must be more than than the minimum distance!");
						}
					} catch (NumberFormatException e) {
						sender.sendMessage(ChatColor.RED
								+ "That is not a valid maximum distance!");
					}
				} else {
					sender.sendMessage("That is not a valid variable to be set");
				}
			} else if (args[0].equalsIgnoreCase("min")
					|| args[0].equalsIgnoreCase("minimum")) {
				sender.sendMessage("Minimum respawn distance: "
						+ getConfig().getInt("minimum"));
			} else if (args[0].equalsIgnoreCase("max")
					|| args[0].equalsIgnoreCase("maximum")) {
				sender.sendMessage("Maximum respawn distance: "
						+ getConfig().getInt("maximum"));
			} else {
				sender.sendMessage(ChatColor.RED + "That is not a valid argument for the hsmp command!");
			}
			return true;
		} // If this has happened the function will break and return true. if
			// this hasn't happened a value of false will be returned.
		return false;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = (Player) event.getEntity();
		logger.info(player.getName() + " died");
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = (Player) event.getPlayer();
		World world = player.getWorld();
		logger.info(player.getName() + " respawned");

		// load from config the minimum and maximum respawn range
		int min = getConfig().getInt("minimum");
		int max = getConfig().getInt("maximum");

		// pick a random distance between 1000 and 2000
		// pick a random angle value
		int dist = (new Random()).nextInt(max - min) + min;
		double angle = (new Random()).nextDouble() * 2 * Math.PI;

		int xDest = (int) (player.getLocation().getX() + dist * Math.cos(angle));
		int zDest = (int) (player.getLocation().getZ() + dist * Math.sin(angle));

		// the biome at the new coordinates is checked
		// a new spot is chosen until the destination is not in the ocean
		while (world.getBiome(xDest, zDest) == Biome.OCEAN) {
			dist = (new Random()).nextInt(max - min) + min;
			angle = (new Random()).nextDouble() * 2 * Math.PI;

			xDest = (int) (player.getLocation().getX() + dist * Math.cos(angle));
			zDest = (int) (player.getLocation().getZ() + dist * Math.sin(angle));
		}
		world.loadChunk(xDest, zDest);
		int yDest = world.getHighestBlockYAt(xDest, zDest);
		
		Location dest = new Location(world, xDest, yDest, zDest);
		event.setRespawnLocation(dest);
	}

	public static final String mainDirectory = "plugins/HardcoreSMP/";
	public static final File data = new File(mainDirectory + "data.dat");
	public static final Logger logger = Logger.getLogger("Minecraft");
}