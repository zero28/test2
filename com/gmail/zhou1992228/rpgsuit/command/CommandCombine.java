package com.gmail.zhou1992228.rpgsuit.command;

import java.util.Random;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.item.ItemStone;

public class CommandCombine implements CommandExecutor{
	
	public double combineFailProp;
	public Random random = new Random();

	public CommandCombine(RPGSuit p) {
		combineFailProp = p.getConfig().getDouble("combineFailProp", 0.05);
		p.getConfig().set("combineFailProp", combineFailProp);
	}
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if (arg0 instanceof Player) {
			Player p = (Player) arg0;
			ItemStack is = ItemStone.tryCombineStone(p.getInventory().getItem(0), p.getInventory().getItem(1));
			if (is == null) {
				p.sendMessage("宝石合成时请将同类型同等级的宝石放在第一与第二个格子里");
				return true;
			}
			p.getInventory().setItem(1, null);
			if (random.nextDouble() < combineFailProp) {
				p.sendMessage("你准备镶嵌的时候不小心将一个宝石掉到地上了，然后再也找不到了");
				return true;
			}
			p.getInventory().setItem(0, is);
			p.sendMessage("合成成功！");
		}
		return true;
	}
}
