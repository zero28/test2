package com.gmail.zhou1992228.rpgsuit.skill;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;

public class SkillFallProtect extends PassiveSkill implements Listener {
	
	@EventHandler
	public void onPlayerFall(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (e.getCause() == DamageCause.FALL) {
				int lv = this.getLevel((Player) e.getEntity());
				lv /= 5;
				double damage = Math.max(0, e.getDamage() - lv);
				e.setDamage(damage);
			}
		}
	}
	public SkillFallProtect(String n) {
		super(n);
		RPGSuit.ins.getServer().getPluginManager().registerEvents(this, RPGSuit.ins);
		expa = 0.2;
	}

	@Override
	public String getDescription() {
		return "我可以教你轻功，但是学费肯定是少不了的，想清楚了再来吧。";
	}

	@Override
	public String getDescription(Player p) {
		return "当前等级为: " + this.getLevel(p) + ", 减少掉落伤害 " + this.getLevel(p) / 5 + " 点";
	}
}
