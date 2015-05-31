package com.gmail.zhou1992228.rpgsuit.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.skill.SkillAction.Action;
import com.gmail.zhou1992228.rpgsuit.util.Util;

public class SkillPaternUtil implements Listener {
	public static SkillPaternUtil ins = new SkillPaternUtil();
	public Map<String, List<SkillAction>> info = new HashMap<String, List<SkillAction>>();
	public ArrayList<ActionSkill> registered_skill = new ArrayList<ActionSkill>();
	
	public void register(ActionSkill skill) {
		registered_skill.add(skill);
	}
	
	
	public SkillPaternUtil() {
		RPGSuit.ins.getServer().getPluginManager().registerEvents(this, RPGSuit.ins);
	}
	
	private void CheckSkill(Player p) {
		List<SkillAction> l = info.get(p.getName());
		for (ActionSkill skill : registered_skill) {
			if (skill.okToRelease(l)) {
				skill.tryUse(p);
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		List<SkillAction> l = info.get(p.getName());
		if (l == null) {
			l = new ArrayList<SkillAction>();
			info.put(p.getName(), l);
		}
		l.add(new SkillAction(
				e.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_AIR ? 
				Action.LEFT : e.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_AIR ?
				Action.RIGHT : null,
				Util.Now(),
				p.getLocation().getPitch(),
				p.getLocation().getYaw(),
				p.getItemInHand()));
		if (l.size() > 10) {
			l.remove(0);
		}
		CheckSkill(p);
	}
	
	@EventHandler
	public void onShift(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		List<SkillAction> l = info.get(p.getName());
		if (l == null) {
			l = new ArrayList<SkillAction>();
			info.put(p.getName(), l);
		}
		l.add(new SkillAction(
				Action.SHIFT,
				Util.Now(),
				p.getLocation().getPitch(),
				p.getLocation().getYaw(),
				p.getItemInHand()));
		if (l.size() > 10) {
			l.remove(0);
		}
		CheckSkill(p);
	}
	
	@EventHandler
	public void onDash(PlayerToggleSprintEvent e) {
		Player p = e.getPlayer();
		List<SkillAction> l = info.get(p.getName());
		if (l == null) {
			l = new ArrayList<SkillAction>();
			info.put(p.getName(), l);
		}
		l.add(new SkillAction(
				Action.DASH,
				Util.Now(),
				p.getLocation().getPitch(),
				p.getLocation().getYaw(),
				p.getItemInHand()));
		if (l.size() > 10) {
			l.remove(0);
		}
		CheckSkill(p);
	}
}
