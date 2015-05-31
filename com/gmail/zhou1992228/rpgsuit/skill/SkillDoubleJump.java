package com.gmail.zhou1992228.rpgsuit.skill;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;

public class SkillDoubleJump extends PassiveSkill implements Listener {
	public Map<String, Integer> counter = new HashMap<String, Integer>();

	public SkillDoubleJump(String n) {
		super(n);
		RPGSuit.ins.getServer().getPluginManager().registerEvents(this, RPGSuit.ins);
		expa = 0.2;
	}
	
	@EventHandler
	public void onClickFeather(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (e.getPlayer().getItemInHand() != null) {
				if (e.getPlayer().getItemInHand().getType() == Material.FEATHER) {
					tryJump(e.getPlayer());
				}
			}
		}
	}
	private void tryJump(Player p) {
		int lv = this.getLevel(p);
		if (lv == 0) return;
		if (p.getVelocity().getY() > 0) {
			p.sendMessage("�������̲��ܼ���ʹ��");
			return;
		}
		int max_jump = lv / 5 + 1;
		int count;
		if (counter.containsKey(p.getName())) {
			count = counter.get(p.getName());
		} else {
			count = 0;
		}
		Location loc1, loc2;
		loc1 = p.getLocation();
		loc2 = loc1.add(0, -1, 0);
		if (loc1.getBlock().getType() != Material.AIR ||
			loc2.getBlock().getType() != Material.AIR) {
			count = 0;
		}
		if (count >= max_jump) {
			p.sendMessage("���Ѿ�û������������");
			return;
		}
		Jump(p);
		if (p.getItemInHand().getAmount() == 1) {
			p.setItemInHand(null);
		} else {
			p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
		}
		counter.put(p.getName(), count + 1);
	}
	private void Jump(Player p) {
		p.setVelocity(p.getVelocity().multiply(1.3).setY(1.3));
		p.setFallDistance(0);
	}

	@Override
	public String getDescription() {
		return "������ë�������Ҽ����Ϳ���������������Ȼ����Ҫѧϰ����";
	}

	@Override
	public String getDescription(Player p) {
		return "��ǰ�ȼ�Ϊ: " + this.getLevel(p) + ", ����������Ծ " + (this.getLevel(p) / 5 + 1 ) + " ��";
	}

}
