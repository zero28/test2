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
				p.sendMessage("��ʯ�ϳ�ʱ�뽫ͬ����ͬ�ȼ��ı�ʯ���ڵ�һ��ڶ���������");
				return true;
			}
			p.getInventory().setItem(1, null);
			if (random.nextDouble() < combineFailProp) {
				p.sendMessage("��׼����Ƕ��ʱ��С�Ľ�һ����ʯ���������ˣ�Ȼ����Ҳ�Ҳ�����");
				return true;
			}
			p.getInventory().setItem(0, is);
			p.sendMessage("�ϳɳɹ���");
		}
		return true;
	}
}
