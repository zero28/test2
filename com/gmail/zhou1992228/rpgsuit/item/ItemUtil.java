package com.gmail.zhou1992228.rpgsuit.item;



import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.zhou1992228.rpgsuit.task.TaskArmorValidator;

public class ItemUtil {
	public static int strv, defv, doublev, hpv;
	
	@SuppressWarnings("deprecation")
	public static ItemStack getMaterialFromString(String s) {
		try {
			return new ItemStack(Material.valueOf(s));
		} catch (Exception e) {
			if (s.contains(":")) {
				try {
					return new ItemStack(Material.valueOf(s.split(":")[0]),
								1,
								(short) Math.min(Short.parseShort(s.split(":")[1]), 16));
				} catch (Exception ee) {
					return new ItemStack(Material.getMaterial(Integer.parseInt(s.split(":")[0])),
							1,
							(short) Math.min(Short.parseShort(s.split(":")[1]), 16));
				}
			} else {
				return new ItemStack(Material.getMaterial(Integer.parseInt(s)));
			}
		}
	}
	
	public static int getRepairCost(ItemStack it) {
		if (it == null) return 1000000000;
		double totalv = 0;
		ItemProperty ip = ItemProperty.getProperty(it);
		if (ip == null) return 1000000000;
		totalv = (strv * ip.str + defv * ip.def + doublev * ip.doublehit + hpv * ip.hp) / 1000.0 + 100;
		totalv *= Math.pow(1.2, ip.repairtime);
		return (int) totalv;
	}
	
	public static ItemStack RepairRPGItem(ItemStack it) {
		if (it == null) return null;
		ItemProperty ip = ItemProperty.getProperty(it);
		if (ip == null) return it;
		ip.repairtime++;
		ip.dura = ip.maxdura;
		return ItemProperty.makeItem(ip);
	}
	
	public static ItemStack RPGDamage(Player p, ItemStack it) {
		if (it == null) return null;
		return RPGDamage(p, it, 1);
	}
	
	public static ItemStack RPGDamage(Player p, ItemStack it, int v) {
		if (it == null) return null;
		ItemProperty ip = ItemProperty.getProperty(it);
		if (ip == null) return it;
		ip.dura -= v;
		if (ip.dura < 20) {
			p.sendMessage(ChatColor.GOLD + "你的一个装备即将损坏，请注意");
		}
		return ip.dura <= 0 ? null : ItemProperty.makeItem(ip);
	}
	
	public static void addLore(ItemStack it, String lore) {
		if (it == null) return;
		List<String> newLore = it.getItemMeta().getLore();
		if (newLore == null) newLore = new ArrayList<String>();
		newLore.add(lore);
		setLore(it, newLore);
	}
	
	public static void setLore(ItemStack it, List<String> lore) {
		if (it == null) return;
		ItemMeta im = it.getItemMeta();
		im.setLore(lore);
		it.setItemMeta(im);
	}
	
	public static void setName(ItemStack it, String name) {
		if (it == null) return;
		ItemMeta im = it.getItemMeta();
		im.setDisplayName(name);
		it.setItemMeta(im);
	}

	public static ItemStack makeItem(
			String name,
			String m,
			int hp,
			int str,
			int def,
			int maxdura,
			int dura,
			int level,
			int doublehit,
			int repairtime,
			String special, 
			int stoneLevel) {
		ItemStack it = getRPGItem(m);
		applyName(it, name);
		addLore(it, name);
		if (hp != 0) applyAddHp(it, hp);
		if (str != 0) applyAddStr(it, str);
		if (def != 0) applyAddDef(it, def);
		if (maxdura != 0) applyMaxDurability(it, maxdura);
		if (dura != 0) applyDurability(it, dura);
		if (special != null && !special.isEmpty()) applySpecial(it, special);
		if (stoneLevel != 0) applyStoneLevel(it, stoneLevel);
		if (doublehit != 0) applyDoubleHit(it, doublehit);
		if (repairtime != 0) applyRepairTime(it, repairtime);
		applyRequireLevel(it, level);
		return it;
	}
	public static ItemStack getRPGItem(String m) {
		ItemStack is = ItemUtil.getMaterialFromString(m);
		applyRPGLore(is);
		return is;
	}
	public static void applyName(ItemStack it, String name) {
		ItemMeta im = it.getItemMeta();
		im.setDisplayName(name);
		it.setItemMeta(im);
	}
	public static void applyRequireLevel(ItemStack it, int level) {
		addLore(it, "装备等级:" + level);
	}
	public static void applySpecial(ItemStack it, String special) {
		addLore(it, "特效:" + special);
	}
	public static void applyRPGLore(ItemStack it) {
		addLore(it, TaskArmorValidator.AUTHORIZED_TEXT);
	}
	public static void applyStoneLevel(ItemStack it, int v) {
		addLore(it, "镶嵌等级:" + v);
	}
	public static void applyAddStr(ItemStack it, int v) {
		addLore(it, "攻击力+" + v);
	}
	public static void applyAddHp(ItemStack it, int v) {
		addLore(it, "生命值+" + v);
	}
	public static void applyAddDef(ItemStack it, int v) {
		addLore(it, "防御力+" + v);
	}
	public static void applyDurability(ItemStack it, int v) {
		addLore(it, "耐久度:" + v);
	}
	public static void applyMaxDurability(ItemStack it, int v) {
		addLore(it, "最大耐久:" + v);
	}
	public static void applyDoubleHit(ItemStack it, int v) {
		addLore(it, "暴击率:" + v);
	}
	public static void applyRepairTime(ItemStack it, int v) {
		addLore(it, "修理次数:" + v);
	}
	public static boolean isExplodeBow(ItemStack it) {
		if (it.getType() == Material.BOW) {
			if (it.getItemMeta() == null || it.getItemMeta().getLore() == null) return false;
			for (String s : it.getItemMeta().getLore()) {
				if (s.contains("特效") && s.contains("爆炸")) {
					return true;
				}
			}
		}
		return false;
	}
}
