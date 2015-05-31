package com.gmail.zhou1992228.rpgsuit.item;

import org.bukkit.inventory.ItemStack;

import com.gmail.zhou1992228.rpgsuit.task.TaskArmorValidator;

public class ItemProperty {
	public int getMaxdura() {
		return maxdura;
	}

	public void setMaxdura(int maxdura) {
		this.maxdura = maxdura;
	}

	public String name;
	public String m;
	public int hp, str, def, dura, level;
	public boolean hasSpecial;
	public String special;
	public int cost;
	public int stoneLevel;
	public int doublehit;
	public int repairtime;
	public int maxdura;
	
	public int getHp() {
		return hp;
	}

	public ItemProperty setHp(int hp) {
		this.hp = hp;
		return this;
	}

	public int getStr() {
		return str;
	}

	public ItemProperty setStr(int str) {
		this.str = str;
		return this;
	}

	public int getDef() {
		return def;
	}

	public ItemProperty setDef(int def) {
		this.def = def;
		return this;
	}

	public int getDura() {
		return dura;
	}

	public ItemProperty setDura(int dura) {
		this.dura = dura;
		return this;
	}

	public ItemProperty Clone() {
		ItemProperty ip = new ItemProperty();
		ip.name = name;
		ip.m = m;
		ip.hp = hp;
		ip.str = str;
		ip.def = def;
		ip.dura = dura;
		ip.level = level;
		ip.hasSpecial =hasSpecial;
		ip.special = special;
		ip.cost = cost;
		ip.stoneLevel = stoneLevel;
		ip.doublehit = doublehit;
		ip.repairtime = repairtime;
		ip.maxdura = maxdura;
		return ip;
	}
	
	public static ItemProperty getItemProperty(
			String name,
			String material,
			int hp,
			int str,
			int def,
			int broken,
			int level,
			String special,
			int cost,
			int doublehit,
			int repairtime,
			int stonelv) {
		ItemProperty ip = new ItemProperty();
		ip.name = name;
		ip.m = material;
		ip.hp = hp;
		ip.str = str;
		ip.def = def;
		ip.maxdura = ip.dura = broken;
		ip.level = level;
		ip.special = special;
		ip.cost = cost;
		ip.stoneLevel = stonelv;
		ip.doublehit = doublehit;
		ip.repairtime = repairtime;
		return ip;
	}

	@SuppressWarnings("deprecation")
	public static ItemProperty getProperty(ItemStack it) {
		if (it == null || it.getItemMeta() == null || it.getItemMeta().getLore() == null) return null;
		if (!it.getItemMeta().getLore().contains(TaskArmorValidator.AUTHORIZED_TEXT)) return null;
		ItemProperty ip = new ItemProperty();
		int count = 0;
		for (String s : it.getItemMeta().getLore()) {
			if (s.startsWith("������")) { ip.str = Integer.parseInt(s.split("[+]")[1]); ++count;} else
			if (s.startsWith("������")) { ip.def = Integer.parseInt(s.split("[+]")[1]); ++count;} else
			if (s.startsWith("����ֵ")) { ip.hp = Integer.parseInt(s.split("[+]")[1]); ++count;} else
			if (s.startsWith("����;�")) { ip.maxdura = Integer.parseInt(s.split(":")[1]); ++count;} else
			if (s.startsWith("�;ö�")) { ip.dura = Integer.parseInt(s.split(":")[1]); ++count;} else
			if (s.startsWith("��Ч")) { ip.special = s.split(":")[1]; ++count;} else
			if (s.startsWith("װ���ȼ�")) { ip.level = Integer.parseInt(s.split(":")[1]); ++count;} else
			if (s.startsWith("��Ƕ�ȼ�")) { ip.stoneLevel = Integer.parseInt(s.split(":")[1]); ++count;} else
			if (s.startsWith("������")) { ip.doublehit = Integer.parseInt(s.split(":")[1]); ++count;} else
			if (s.startsWith("�������")) { ip.repairtime = Integer.parseInt(s.split(":")[1]); ++count;} else
			if (!s.equals(TaskArmorValidator.AUTHORIZED_TEXT)) { ip.name = s; }
		}
		if (count == 0) return null;
		if (ip.name == null || ip.name.isEmpty()) {
			ip.name = it.getItemMeta().getDisplayName();
		}
		ip.m = it.getTypeId() + "";
		if (it.getDurability() != 0) {
			ip.m += ":" + it.getDurability();
		}
		return ip;
	}

	public static ItemStack makeItem(ItemProperty ip) {
		return ItemUtil.makeItem(ip.name, ip.m, ip.hp, ip.str, ip.def, ip.maxdura, ip.dura, ip.level, ip.doublehit, ip.repairtime, ip.special, ip.stoneLevel);
	}
}
