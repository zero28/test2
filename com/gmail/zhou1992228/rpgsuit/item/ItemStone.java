package com.gmail.zhou1992228.rpgsuit.item;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStone {
	public static final String STONE_TEXT = "��Ƕ��ʯ";
	int level;
	int hp;
	int str;
	int def;
	int doublehit;
	String name;
	String material;
	public static ItemStone getStone(ItemStack stone) {
		ItemStone is = null;
		if (stone == null || stone.getItemMeta() == null ||
			stone.getItemMeta().getLore() == null ||
			!stone.getItemMeta().getLore().contains(STONE_TEXT)) {
			return null;
		}
		is = new ItemStone();
		for (String des : stone.getItemMeta().getLore()) {
			if (des.startsWith("������")) { is.str = Integer.parseInt(des.split("[+]")[1]); }
			if (des.startsWith("������")) { is.def = Integer.parseInt(des.split("[+]")[1]); }
			if (des.startsWith("����ֵ")) { is.hp = Integer.parseInt(des.split("[+]")[1]); }
			if (des.startsWith("��ʯ�ȼ�")) { is.level = Integer.parseInt(des.split(":")[1]); }
			if (des.startsWith("������")) { is.doublehit = Integer.parseInt(des.split(":")[1]); }
		}
		is.name = stone.getItemMeta().getLore().get(1);
		return is;
	}
	
	public static ItemStack getItemStoneStack(ItemStone is) {
		return getItemStoneStack(is.name, is.material, is.level, is.hp, is.str, is.def, is.doublehit);
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack getItemStoneStack(String name, String material, int level, int hp, int str, int def, int doublehit) {
		int id = Integer.parseInt(material.split(":")[0]);
		int x = 0;
		if (material.split(":").length > 1) {
			x = Integer.parseInt(material.split(":")[1]);
		}
		ItemStack it = new ItemStack(id, 1, (short) x);
		ItemMeta im = it.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(STONE_TEXT);
		lore.add(name);
		if (level > 0) { lore.add("��ʯ�ȼ�:" + level); }
		if (hp > 0) { lore.add("����ֵ+" + hp); }
		if (str > 0) { lore.add("������+" + str); }
		if (def > 0) { lore.add("������+" + def); }
		if (doublehit > 0) {  lore.add("������+" + doublehit); }
		im.setLore(lore);
		im.setDisplayName(name);
		it.setItemMeta(im);
		return it;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack tryCombineStone(ItemStack item1, ItemStack item2) {
		ItemStone is1 = ItemStone.getStone(item1);
		if (is1 == null) { return null; }
		ItemStone is2 = ItemStone.getStone(item2);
		if (is2 == null) { return null; }
		if (is1.level != is2.level) { return null; }
		if (!is1.name.equals(is2.name)) { return null; }
		is1.level++;
		is1.material = item1.getType().getId() + ":" + item1.getDurability();
		return getItemStoneStack(is1);
	}
	
	public static ItemStack tryAddStone(Player p, ItemStack weapon, ItemStack stone) {
		ItemStone is = ItemStone.getStone(stone);
		if (is == null) return null;
		ItemProperty ip = ItemProperty.getProperty(weapon);
		if (ip == null) return null;
		if (ip.stoneLevel + 1 != is.level) return null;
		
		if (ip.hp == 0 && weapon.getType().name().contains("HELMET")) {
			p.sendMessage("�˱�ʯ������Ƕ��ñ����");
			return null;
		}
		if (ip.def == 0 && weapon.getType().name().contains("CHEST")) {
			p.sendMessage("�˱�ʯ������Ƕ���·���");
			return null;
		}
		if (ip.str != 0 && (weapon.getType().name().contains("CHEST") || weapon.getType().name().contains("HELMET"))) {
			p.sendMessage("�˱�ʯ������Ƕ�ڷ�����");
			return null;
		}
		
		ip.hp += is.hp;
		ip.str += is.str;
		ip.def += is.def;
		ip.doublehit += is.doublehit;
		ip.stoneLevel = is.level;
		ItemStack it = ItemProperty.makeItem(ip);
		if (it == null) {
			p.sendMessage("��Ƕ��ʯʱ�뽫����/���߷ŵ���һ�����ӣ���ʯ�ŵ��ڶ������ӣ���ʯ�ȼ�ӦΪ����/������Ƕ�ȼ�+1");
		}
		return it;
	}
	
}
