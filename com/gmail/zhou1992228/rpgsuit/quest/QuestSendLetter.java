package com.gmail.zhou1992228.rpgsuit.quest;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.zhou1992228.rpgsuit.item.ItemUtil;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class QuestSendLetter extends Quest {
	public String from, to;
	public String require, reward;

	public QuestSendLetter(FileConfiguration config, String name) {
		super(name);
		from = config.getString("quest." + name + ".from");
		to = config.getString("quest." + name + ".to");
		reward = config.getString("quest." + name + ".reward");
	}
	@Override
	public void OnQuestComplete(Player p) {
	}

	@Override
	public void OnTakingQuest(Player p) {
		if (Quest.haveQuest(p, name)) {
			return;
		} else {
			Quest.addQuest(p, name);
		}
		giveLetter(p, from, to);
		p.sendMessage("���ҽ����͵�" + to + "�ǣ�Ȼ�󽫻��Ŵ�������");
	}

	@Override
	public boolean tryComplete(String npcName, Player p) {
		p.sendMessage("лл��������ţ�������ı���");
		UserInfoUtils.giveItems(p, reward);
		Quest.removeQuest(p, name);
		return true;
	}
	@Override
	public boolean OnInteractWith(String npcName, Player p) {
		if (npcName.equals(to) && hasLetter(p, from, to)) {
			giveLetter(p, to, from);
			p.sendMessage("���ǻ��ţ��뾡���ͻ�ȥ");
			return true;
		} else if (npcName.equals(from) && hasLetter(p, to, from)) {
			this.tryComplete(npcName, p);
			return true;
		}
		return false;
	}
	private boolean hasLetter(Player p, String fromName, String npcName) {
		ItemStack it = p.getItemInHand();
		if (it != null && it.getItemMeta() != null && it.getItemMeta().getLore() != null) {
			if (it.getItemMeta().getLore().get(0).equals(fromName + "��" + npcName + "����")) {
				p.setItemInHand(null);
				return true;
			}
		}
		return false;
	}
	private void giveLetter(Player p, String fromName, String toName) {
		ItemStack it = new ItemStack(Material.PAPER);
		ItemUtil.addLore(it, fromName + "��" + toName + "����");
		p.getInventory().addItem(it);
	}
	@Override
	public String getDescription(Player p) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void Save() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void Load() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onCancel(Player p) {
		Quest.removeQuest(p, name);
		
	}
}
