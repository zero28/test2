package com.gmail.zhou1992228.rpgsuit.skill;

import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.util.DamageHandler;
import com.gmail.zhou1992228.rpgsuit.util.StatusUtil;
import com.gmail.zhou1992228.rpgsuit.util.StatusUtil.Status;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class SkillShotArrow extends PassiveSkill {
	
	public static SkillShotArrow ins;

	public SkillShotArrow(String n) {
		super(n);
		ins = this;
		this.expa = 0.5;
		this.mona = 2.7;
	}

	@Override
	public String getDescription() {
		return "跟我学射箭吧，学会了以后就可以消耗怒气来使用RPG的弓箭了";
	}

	@Override
	public String getDescription(Player p) {
		int level = this.getLevel(p);
		return "当前等级为: " + this.getLevel(p) + ",   激活后可使用 " + level + " 秒 弓箭";
	}

	public boolean tryActive(Player p) {
		int level = this.getLevel(p);
		if (level == 0) return false;
		if (UserInfoUtils.getInt(p, DamageHandler.ANGRY_KEY) < 20) return false;
		UserInfoUtils.minus(p, DamageHandler.ANGRY_KEY, 20);
		StatusUtil.AddStatus(p, Status.CAN_SHOT_ARROW, level);
		p.sendMessage("你激活了你的射箭技能！");
		return true;
	}
}
