package com.gmail.zhou1992228.rpgsuit.command;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.util.ParticleEffect;
import com.gmail.zhou1992228.rpgsuit.util.TargetUtils;

public class CommandForTest implements CommandExecutor {

	public Random random = new Random();
	
	public boolean is_sea_chunk(Chunk c) {
		int count = 0;
		boolean should_unload = false;
		if (!c.isLoaded()) {
			c.load();
			should_unload = true;
		}
		//Bukkit.getLogger().info(c.getBlock(0, 61, 0).getType().name());
		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				if (c.getBlock(i, 61, j).getType() == Material.STATIONARY_WATER ||
					c.getBlock(i, 61, j).getType() == Material.WATER) {
					++count;
				}
			}
		}
		if (should_unload) {
			c.unload();
		}
		return count > 128;
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if (arg0.isOp() && arg0 instanceof Player) {
			/*
			Player p = (Player) arg0;
			int count = Integer.parseInt(arg3[0]);
			int cx, cz;
			cx = p.getLocation().getChunk().getX();
			cz = p.getLocation().getChunk().getZ();
			for (int j = -count; j <= count; ++j) {
				String result = "";
				for (int i = -count; i <= count; ++i) {
					if (i == 0 && j == 0) {
						result += "X";
						continue;
					}
					if (is_sea_chunk(p.getWorld().getChunkAt(cx + i, cz + j))) {
						result += " ";
					} else {
						result += "#";
					}
				}
				p.sendMessage(result);
			}
			*/
			
			/*
			int int_v = 2, times = 10;
			double angle = 30;
			try {
				int_v = Integer.parseInt(arg3[0]);
				times = Integer.parseInt(arg3[1]);
				angle = Double.parseDouble(arg3[2]);
			} catch (Exception e) {
				
			}
			RPGSuit.ins.getServer().getScheduler().scheduleSyncDelayedTask(
					RPGSuit.ins, new TaskSweep(RPGSuit.ins, (Player) arg0, int_v, times, angle));
			*/
			
			ParticleEffect pe = ParticleEffect.valueOf(arg3[0]);
			//float x = 0, y = 0, z = 0;//, s = 5;
			int count = 10;
			try {
				/*
				x = Float.parseFloat(arg3[1]);
				y = Float.parseFloat(arg3[2]);
				z = Float.parseFloat(arg3[3]);
				*/
				count = Integer.parseInt(arg3[1]);
			} catch (Exception e) {
				
			}
			/*
			ParticleEffect.sendToLocation(pe,
					((Player) arg0).getLocation(), x, y, z, s, count);
			 */
			LivingEntity le = TargetUtils.getLivingTarget((Player) arg0, 30);
			if (le == null) le = (Player) arg0;
			ParticleEffect.CreateLine(pe, ((Player) arg0).getEyeLocation(), le.getEyeLocation(), count);
			
		}
		return true;
	}
}
