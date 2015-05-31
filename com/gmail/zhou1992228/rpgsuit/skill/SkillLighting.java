package com.gmail.zhou1992228.rpgsuit.skill;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.util.TargetUtils;

public class SkillLighting extends PassiveSkill implements Listener {
	
	public Map<String, Long> cooldown = new HashMap<String, Long>();
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if (e.getMessage().equals("天雷滚滚")) {
			int lv = this.getLevel(e.getPlayer());
			if (lv == 0) { return; }
			int cd = (151 - lv) * 60 * 1000;
			long now = Calendar.getInstance().getTimeInMillis();
			final Player p = e.getPlayer();
			if (!cooldown.containsKey(e.getPlayer().getName()) ||
				now - cd > cooldown.get(e.getPlayer().getName())) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(RPGSuit.ins, new Runnable() {
					@Override
					public void run() { 
						lighting(p);
					}
				}, 1);
			} else {
				p.sendMessage("还没冷却呢");
			}
		}
	}
	
	public void lighting(Player p) {
		LivingEntity t = TargetUtils.getLivingTarget(p, 20);
		if (t == null) {
			p.sendMessage("眼前没有目标！");
			return;
		}
		long now = Calendar.getInstance().getTimeInMillis();
		cooldown.put(p.getName(), now);
		t.getWorld().strikeLightning(t.getLocation());
		t.getWorld().strikeLightningEffect(t.getLocation());
	}
	
	public SkillLighting(String n) {
		super(n);
		RPGSuit.ins.getServer().getPluginManager().registerEvents(this, RPGSuit.ins);
		expa = 0.15;
		mona = 1.8;
	}

	@Override
	public String getDescription() {
		return "我可以教你打雷，但是学费肯定是少不了的，想清楚了再来吧。";
	}

	@Override
	public String getDescription(Player p) {
		return "当前等级为: " + this.getLevel(p) + ", 冷却时间 " + (151 - this.getLevel(p)) + " 分钟";
	}

	@Override
	public void onLearn(Player p) {
		super.onLearn(p);
		p.sendMessage("大喊天雷滚滚即可召唤雷电");
	}
}
