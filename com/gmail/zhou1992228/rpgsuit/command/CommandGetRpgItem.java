package com.gmail.zhou1992228.rpgsuit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.item.ItemUtil;

public class CommandGetRpgItem  implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if (arg0 instanceof Player && arg0.isOp()) {
			if (arg3.length < 8) {
				arg0.sendMessage("String name, Material m, int hp, int str, int def, int dura, int level, String special");
			}
			try {
				String name = arg3[0];
				String m = arg3[1];
				int hp = Integer.parseInt(arg3[2]);
				int str = Integer.parseInt(arg3[3]);
				int def = Integer.parseInt(arg3[4]);
				int broken = Integer.parseInt(arg3[5]);
				int level = Integer.parseInt(arg3[6]);
				String special = arg3[7];
				((Player) arg0).getInventory().addItem(ItemUtil.makeItem(name, m, hp, str, def, broken, broken, level, 0, 0, special, 0));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
