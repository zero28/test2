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
			p.sendMessage("上升过程不能继续使用");
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
			p.sendMessage("你已经没有力气再跳了");
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
		return "拿着羽毛在手上右键，就可以跳跳跳啦，当然首先要学习技能";
	}

	@Override
	public String getDescription(Player p) {
		return "当前等级为: " + this.getLevel(p) + ", 可以连续跳跃 " + (this.getLevel(p) / 5 + 1 ) + " 次";
	}

}
