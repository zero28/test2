package com.gmail.zhou1992228.rpgsuit.item;

import org.bukkit.inventory.ItemStack;

public class StoneProperty {
	public String name;
	public String m;
	public int hp, str, def;
	public int getHp() {
		return hp;
	}

	public StoneProperty setHp(int hp) {
		this.hp = hp;
		return this;
	}

	public int getStr() {
		return str;
	}

	public StoneProperty setStr(int str) {
		this.str = str;
		return this;
	}

	public int getDef() {
		return def;
	}

	public StoneProperty setDef(int def) {
		this.def = def;
		return this;
	}

	public int getStoneLevel() {
		return stoneLevel;
	}

	public StoneProperty setStoneLevel(int stoneLevel) {
		this.stoneLevel = stoneLevel;
		return this;
	}

	public int cost;
	public int stoneLevel;
	public int doublehit;
	
	public StoneProperty Clone() {
		StoneProperty ip = new StoneProperty();
		ip.name = name;
		ip.m = m;
		ip.hp = hp;
		ip.str = str;
		ip.def = def;
		ip.cost = cost;
		ip.stoneLevel = stoneLevel;
		ip.doublehit = doublehit;
		return ip;
	}
	
	public static StoneProperty getItemProperty(
			String name,
			String material,
			int hp,
			int str,
			int def,
			int cost,
			int doublehit,
			int stonelv) {
		StoneProperty ip = new StoneProperty();
		ip.name = name;
		ip.m = material;
		ip.hp = hp;
		ip.str = str;
		ip.def = def;
		ip.cost = cost;
		ip.stoneLevel = stonelv;
		ip.doublehit = doublehit;
		return ip;
	}

	public static StoneProperty getProperty(ItemStack it) {
		if (it == null || it.getItemMeta() == null || it.getItemMeta().getLore() == null) return null;
		StoneProperty ip = new StoneProperty();
		for (String s : it.getItemMeta().getLore()) {
			if (s.startsWith("攻击力")) { ip.str = Integer.parseInt(s.split("[+]")[1]); }
			if (s.startsWith("防御力")) { ip.def = Integer.parseInt(s.split("[+]")[1]); }
			if (s.startsWith("生命值")) { ip.hp = Integer.parseInt(s.split("[+]")[1]); }
			if (s.startsWith("宝石等级")) { ip.stoneLevel = Integer.parseInt(s.split(":")[1]); }
			if (s.startsWith("暴击率")) { ip.doublehit = Integer.parseInt(s.split("+")[1]); }
		}
		ip.name = it.getItemMeta().getDisplayName();
		ip.m = it.getType().name();
		if (it.getDurability() != 0) {
			ip.m += ":" + it.getDurability();
		}
		return ip;
	}

	public static ItemStack makeItem(StoneProperty ip) {
		return ItemStone.getItemStoneStack(ip.name, ip.m, ip.stoneLevel, ip.hp, ip.str, ip.def, ip.doublehit);
	}
}
