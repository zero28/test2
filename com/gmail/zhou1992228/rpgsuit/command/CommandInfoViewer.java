package com.gmail.zhou1992228.rpgsuit.command;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.gmail.zhou1992228.rpgsuit.util.InventoryInfoViewer;

public class CommandInfoViewer implements CommandExecutor {

	public Random random = new Random();
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if (arg0 instanceof Player) {
			Player p = (Player) arg0;
			Inventory iv = Bukkit.createInventory(new InventoryInfoViewer((Player) arg0), 54, "Íæ¼ÒÐÅÏ¢");
			InventoryInfoViewer.addInfosForPlayer(iv, p);
			p.openInventory(iv);
		}
		return true;
	}
}
