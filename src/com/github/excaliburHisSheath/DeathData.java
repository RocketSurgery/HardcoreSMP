package com.github.excaliburHisSheath;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class DeathData {
	
	public static boolean containsKey(Player p, File file) {
		Properties properties = new Properties();
		String player = p.getName();
		try {
			FileInputStream input = new FileInputStream(file);
			properties.load(input);
			if (properties.containsKey(player))
				return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void write(Player player, Location loc, File file) {
		Properties properties = new Properties();
		String dest = loc.getX() + "," + loc.getY() + "," + loc.getZ();
		try {
			FileInputStream input = new FileInputStream(file);
			properties.load(input);
			properties.setProperty(player.getName(), dest);
			properties.store(new FileOutputStream(file), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static double[] retrieveDest(Player p, File file) {
		Properties properties = new Properties();
		String player = p.getName();
		Scanner scan = null;
		try {
			FileInputStream input = new FileInputStream(file);
			properties.load(input);
			String string = properties.getProperty(player);
			properties.remove(player);
			scan = new Scanner(string);
			scan.useDelimiter(",");
			double[] result = {Double.parseDouble(scan.next()), Double.parseDouble(scan.next()), Double.parseDouble(scan.next()) };
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (scan != null)
				scan.close();
		}
		return null;
	}
}
