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
		if (e.getMessage().equals("���׹���")) {
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
				p.sendMessage("��û��ȴ��");
			}
		}
	}
	
	public void lighting(Player p) {
		LivingEntity t = TargetUtils.getLivingTarget(p, 20);
		if (t == null) {
			p.sendMessage("��ǰû��Ŀ�꣡");
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
		return "�ҿ��Խ�����ף�����ѧ�ѿ϶����ٲ��˵ģ�������������ɡ�";
	}

	@Override
	public String getDescription(Player p) {
		return "��ǰ�ȼ�Ϊ: " + this.getLevel(p) + ", ��ȴʱ�� " + (151 - this.getLevel(p)) + " ����";
	}

	@Override
	public void onLearn(Player p) {
		super.onLearn(p);
		p.sendMessage("�����׹��������ٻ��׵�");
	}
}
