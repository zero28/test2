package com.gmail.zhou1992228.rpgsuit.util;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.gmail.zhou1992228.rpgsuit.item.ItemUtil;
import com.gmail.zhou1992228.rpgsuit.listener.PlayerJoinListener;
import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.rpgsuit.quiz.QuizMaster;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class InventoryInfoViewer implements InventoryHolder {
	Player p;
	public InventoryInfoViewer(Player pl) {
		p = pl;
	}

	@Override
	public Inventory getInventory() {
		return Bukkit.createInventory(null, 54);
	}

	public static void addInfosForPlayer(Inventory iv, Player p) {
		setInfo(iv, 0, new ItemStack(Material.SKULL_ITEM, 1, (short)3), "�ȼ�", String.format("%d", PlayerUtil.getLevel(p)));
		setInfo(iv, 1, new ItemStack(Material.WOOD_SWORD), "����", String.format("%d:װ������/ѧϰ��������߹�����", UserInfoUtils.getInt(p, "������")));
		setInfo(iv, 2, new ItemStack(Material.LEATHER_CHESTPLATE), "����", String.format("%d:װ���·�/ѧϰ��������߷�����", UserInfoUtils.getInt(p, "������")));
		setInfo(iv, 3, new ItemStack(Material.LEATHER_HELMET), "����ֵ", String.format("%d:װ��ͷ��/ѧϰ�������������ֵ", UserInfoUtils.getInt(p, "����ֵ")));
		setInfo(iv, 4, new ItemStack(Material.EXP_BOTTLE), "����ֵ", String.format("%d:��ɱ����/�������/ͨ���������Ի�þ���ֵ", UserInfoUtils.getInt(p, "����ֵ")));

		setInfo(iv, 6, new ItemStack(Material.BLAZE_POWDER), "ŭ��ֵ", String.format("%d:��������/�����﹥���Ի��ŭ��:�ͷż���ʱʹ��", UserInfoUtils.getInt(p, "ŭ��ֵ")));
		setInfo(iv, 7, new ItemStack(Material.WOOD_PICKAXE), "����ֵ", String.format("%d:�Զ��ָ�:��ҩ���������������", UserInfoUtils.getInt(p, "����")));
		
		setInfo(iv, 9, new ItemStack(Material.PAPER), "ÿ������ʣ�����", String.format("%d", UserInfoUtils.getInt(p, "ÿ������ʣ�����")));
		setInfo(iv, 10, new ItemStack(Material.PAPER), "ʣ�ྺ��������", String.format("%d", UserInfoUtils.getInt(p, "ʣ�ྺ��������")));
		setInfo(iv, 11, new ItemStack(Material.PAPER), "����ʣ��˫������ʱ��", String.format("%d", UserInfoUtils.getInt(p, "ʣ��˫��ʱ��")));
		setInfo(iv, 12, new ItemStack(Material.PAPER), "������������", String.format("%d", UserInfoUtils.getInt(p, PlayerJoinListener.KEY_DEATH_PROTECT)));
		setInfo(iv, 13, new ItemStack(Material.PAPER), QuizMaster.QUIZ_COUNT_KEY, String.format("%d", UserInfoUtils.getInt(p, QuizMaster.QUIZ_COUNT_KEY)));
		
		setInfo(iv, 45, new ItemStack(Material.INK_SACK, 1, (short) 15), "��ҩ", String.format("%d��:ʹ�ò���/������ҩ���ָ�����ֵ", UserInfoUtils.getInt(p, "������ҩ�ȼ�")));
		setInfo(iv, 46, new ItemStack(Material.LEATHER_BOOTS), "�����", String.format("%d��:ʹ����ë:��Ծ���", UserInfoUtils.getInt(p, "���ܶ�����ȼ�")));
		setInfo(iv, 47, new ItemStack(Material.BOW), "���", String.format("%d��:����ʹ��RPG��", UserInfoUtils.getInt(p, "��������ȼ�")));
		setInfo(iv, 48, new ItemStack(Material.LEATHER_LEGGINGS), "�Ṧ", String.format("%d��:���ٵ����˺�", UserInfoUtils.getInt(p, "�����Ṧ�ȼ�")));
		setInfo(iv, 49, new ItemStack(Material.IRON_SWORD), "����", String.format("%d��:ʹ��ŭ��������ǰ������:�ͷŷ�ʽ���Ҽ������", UserInfoUtils.getInt(p, "���ܽ����ȼ�")));
		setInfo(iv, 50, new ItemStack(Material.BLAZE_ROD), "������", String.format("%d��:ʹ��ŭ����������Χ���˲�����:�ͷŷ�ʽ���Ҽ������", UserInfoUtils.getInt(p, "���������ֵȼ�")));
		setInfo(iv, 51, new ItemStack(Material.REDSTONE_TORCH_ON), "����", String.format("%d��:�����׹������ٻ��׻�:Ȼ����û��ʲô��", UserInfoUtils.getInt(p, "���ܴ��׵ȼ�")));
	}
	
	public static void setInfo(Inventory iv, int slot, ItemStack it, String name, String lore) {
		ItemUtil.applyName(it, name);
		List<String> l = Arrays.asList(lore.split(":"));
		ItemUtil.setLore(it, l);
		iv.setItem(slot, it);
	}
}
