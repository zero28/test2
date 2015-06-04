package com.gmail.zhou1992228.rpgsuit.util;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;

public class StaticParticleEffect implements Runnable {
	
	public FileConfiguration config;
	public StaticParticleEffect() {
		config = RPGSuit.getConfigWithName("particle.yml");
	}

	@Override
	public void run() {
		for (String name : config.getConfigurationSection("particle").getKeys(false)) {
			World w = Bukkit.getServer().getWorld(config.getString("particle." + name + ".world", "world"));
			int x = config.getInt("particle." + name + ".x", 0);
			int y = config.getInt("particle." + name + ".y", 0);
			int z = config.getInt("particle." + name + ".z", 0);
			List<String> image = config.getStringList("particle." + name + ".image");
			String effect = config.getString("particle." + name + ".effect", "fire");
			double scale = config.getDouble("particle." + name + ".scale", 0.1);
			int face = config.getInt("particle." + name + ".face", 1);
			float ox = (float) config.getDouble("particle." + name + ".ox", 0);
			float oy = (float) config.getDouble("particle." + name + ".oy", 0);
			float oz = (float) config.getDouble("particle." + name + ".oz", 0);
			float speed = (float) config.getDouble("particle." + name + ".speed", 0);
			if (image != null) {
				if (face == 1) {
					for (int i = 0; i < image.size(); ++i) {
						String line = image.get(i);
						for (int j = 0; j < line.length(); ++j) {
							if (line.charAt(j) == 'x') {
								ParticleEffect.sendToLocation(
										ParticleEffect.valueOf(effect), 
										new Location(w, x, y - i * scale, z + j * scale), ox, oy, oz, speed, 1);
							}
						}
					}
				} else if (face == 2) {
					for (int i = 0; i < image.size(); ++i) {
						String line = image.get(i);
						for (int j = 0; j < line.length(); ++j) {
							if (line.charAt(j) == 'x') {
								ParticleEffect.sendToLocation(
										ParticleEffect.valueOf(effect), 
										new Location(w, x + j * scale, y - i * scale, z), ox, oy, oz, speed, 1);
							}
						}
					}
				} else if (face == 3) {
					for (int i = 0; i < image.size(); ++i) {
						String line = image.get(i);
						for (int j = 0; j < line.length(); ++j) {
							if (line.charAt(j) == 'x') {
								ParticleEffect.sendToLocation(
										ParticleEffect.valueOf(effect), 
										new Location(w, x + i * scale, y, z + j * scale), ox, oy, oz, speed, 1);
							}
						}
					}
				} else if (face == 4) {
					for (int i = 0; i < image.size(); ++i) {
						String line = image.get(i);
						int length = line.length();
						for (int j = 0; j < line.length(); ++j) {
							if (line.charAt(j) == 'x') {
								ParticleEffect.sendToLocation(
										ParticleEffect.valueOf(effect), 
										new Location(w, x, y - i * scale, z - (length - j) * scale), ox, oy, oz, speed, 1);
							}
						}
					}
				} else if (face == 5) {
					for (int i = 0; i < image.size(); ++i) {
						String line = image.get(i);
						int length = line.length();
						for (int j = 0; j < length; ++j) {
							if (line.charAt(j) == 'x') {
								ParticleEffect.sendToLocation(
										ParticleEffect.valueOf(effect), 
										new Location(w, x - (length - j) * scale, y - i * scale, z), ox, oy, oz, speed, 1);
							}
						}
					}
				} else if (face == 6) {
					for (int i = 0; i < image.size(); ++i) {
						String line = image.get(i);
						int length = line.length();
						for (int j = 0; j < line.length(); ++j) {
							if (line.charAt(j) == 'x') {
								ParticleEffect.sendToLocation(
										ParticleEffect.valueOf(effect), 
										new Location(w, x + i * scale, y, z - (length - j) * scale), ox, oy, oz, speed, 1);
							}
						}
					}
				}
			}
		}
	}
	
}
