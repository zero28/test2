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
		return "����ѧ����ɣ�ѧ�����Ժ�Ϳ�������ŭ����ʹ��RPG�Ĺ�����";
	}

	@Override
	public String getDescription(Player p) {
		int level = this.getLevel(p);
		return "��ǰ�ȼ�Ϊ: " + this.getLevel(p) + ",   ������ʹ�� " + level + " �� ����";
	}

	public boolean tryActive(Player p) {
		int level = this.getLevel(p);
		if (level == 0) return false;
		if (UserInfoUtils.getInt(p, DamageHandler.ANGRY_KEY) < 20) return false;
		UserInfoUtils.minus(p, DamageHandler.ANGRY_KEY, 20);
		StatusUtil.AddStatus(p, Status.CAN_SHOT_ARROW, level);
		p.sendMessage("�㼤�������������ܣ�");
		return true;
	}
}
