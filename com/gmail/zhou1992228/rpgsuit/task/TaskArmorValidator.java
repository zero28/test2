package com.gmail.zhou1992228.rpgsuit.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;

public class TaskArmorValidator implements Runnable {
	public static final String AUTHORIZED_TEXT = "RPG装备";

	public static boolean isAuthorizedArmor(ItemStack it) {
		try {
			return it.getItemMeta().getLore().contains(AUTHORIZED_TEXT);
		} catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (RPGSuit.mobEnableWorld.contains(p.getWorld().getName())) {
				PlayerInventory inv = p.getInventory();
				int removedCount = 0;
				if ((inv.getHelmet() != null)
						&& (inv.getHelmet().getTypeId() != 0)) {
					if (!isAuthorizedArmor(inv.getHelmet())) {
						inv.setHelmet(null);
						removedCount++;
					}
				}
				if ((inv.getChestplate() != null)
						&& (inv.getChestplate().getTypeId() != 0)) {
					if (!isAuthorizedArmor(inv.getChestplate())) {
						inv.setChestplate(null);
						removedCount++;
					}
				}
				if ((inv.getLeggings() != null)
						&& (inv.getLeggings().getTypeId() != 0)) {
					if (!isAuthorizedArmor(inv.getLeggings())) {
						inv.setLeggings(null);
						removedCount++;
					}
				}
				if ((inv.getBoots() != null)
						&& (inv.getBoots().getTypeId() != 0)) {
					if (!isAuthorizedArmor(inv.getBoots())) {
						inv.setBoots(null);
						removedCount++;
					}
				}
				if (removedCount > 0) {
					p.sendMessage(removedCount + " 件未被授权的装备被移除了");
				}
			}
		}
	}

}
