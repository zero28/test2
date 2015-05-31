package com.gmail.zhou1992228.rpgsuit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.item.ItemStone;

public class CommandGetRpgStone   implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if (arg0 instanceof Player && arg0.isOp()) {
			try {
				String name = arg3[0];
				String material = arg3[1];
				int level = Integer.parseInt(arg3[2]);
				int hp = Integer.parseInt(arg3[3]);
				int str = Integer.parseInt(arg3[4]);
				int def = Integer.parseInt(arg3[5]);
				int dh = Integer.parseInt(arg3[6]);
				((Player) arg0).getInventory().addItem(
						ItemStone.getItemStoneStack(name, material, level, hp, str, def, dh));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
