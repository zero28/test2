package com.gmail.zhou1992228.rpgsuit.command;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;

public class CommandRpgMob implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg0 instanceof Player && arg0.isOp()) {
			Player p = (Player) arg0;
			World w = p.getWorld();
			if (EntityType.valueOf(arg3[0]) != null) {
				Entity e = w.spawnEntity(p.getLocation(), EntityType.valueOf(arg3[0]));
				if (e instanceof Monster) {
					RPGSuit.mobGenerator.apply(e, Integer.parseInt(arg3[1]));
				} else {
					e.remove();
				}
			}
		}
		return true;
	}
}
