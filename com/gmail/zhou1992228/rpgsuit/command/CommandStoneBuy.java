package com.gmail.zhou1992228.rpgsuit.command;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.item.StoneProperty;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class CommandStoneBuy implements CommandExecutor{

	private FileConfiguration config;
	static public CommandStoneBuy ins;
	public HashMap<String, StoneProperty> stoneInfos;
	public CommandStoneBuy(RPGSuit plugin) {
		ins = this;
		config = RPGSuit.getConfigWithName("stone.yml");
		Set<String> stones = config.getConfigurationSection("stones").getKeys(false);
		stoneInfos = new HashMap<String, StoneProperty>();
		for (String name : stones) {
			stoneInfos.put(name, 
					StoneProperty.getItemProperty(
							config.getString("stones." + name + ".name", "δ������Ʒ"),
							config.getString("stones." + name + ".material", "STICK"),
							config.getInt("stones." + name + ".hp", 0),
							config.getInt("stones." + name + ".str", 0),
							config.getInt("stones." + name + ".def", 0),
							config.getInt("stones." + name + ".cost", 50000),
							config.getInt("stones." + name + ".doublehit", 0),
							config.getInt("stones." + name + ".level", 150)
							));
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			if (!sender.hasPermission("rpgshop.buy")) {
				sender.sendMessage("��û�й���RPG��Ʒ��Ȩ��");
				return true;
			}
			if (args.length == 0) {
				sender.sendMessage("����������Ҫ���RPG��Ʒ");
				return true;
			}
			if (!stoneInfos.containsKey(args[0])) {
				sender.sendMessage("û�����RPG��Ʒ/��RPG��Ʒ�����ۣ�");
				return true;
			}
			int cost = stoneInfos.get(args[0]).cost;
			if (UserInfoUtils.haveRequire((Player) sender, "$" + cost)) {
				UserInfoUtils.takeItem((Player) sender, "$" + cost);
				((Player)sender).getInventory().addItem(
						StoneProperty.makeItem(stoneInfos.get(args[0])));
			} else {
				sender.sendMessage("��û���㹻����Ʒ/��Ǯ���������Ʒ");
			}
			return true;
		} else {
			sender.sendMessage("only player can do this");
			return true;
		}
	}
}
