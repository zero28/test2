package com.gmail.zhou1992228.rpgsuit.command;

import java.util.Random;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.item.ItemStone;

public class CommandInlay implements CommandExecutor{
	
	public double inlayFailProp;
	public Random random = new Random();

	public CommandInlay(RPGSuit p) {
		inlayFailProp = p.getConfig().getDouble("inlayFailProp", 0.05);
		p.getConfig().set("inlayFailProp", inlayFailProp);
	}
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if (arg0 instanceof Player) {
			Player p = (Player) arg0;
			ItemStack is = ItemStone.tryAddStone(p, p.getInventory().getItem(0), p.getInventory().getItem(1));
			if (is == null) {
				return true;
			}
			p.getInventory().setItem(1, null);
			if (random.nextDouble() < inlayFailProp) {
				p.sendMessage("��׼����Ƕ��ʱ��С�Ľ���ʯ���������ˣ�Ȼ����Ҳ�Ҳ�����");
				return true;
			}
			p.getInventory().setItem(0, is);
			p.sendMessage("��Ƕ�ɹ���");
		}
		return true;
	}
}
