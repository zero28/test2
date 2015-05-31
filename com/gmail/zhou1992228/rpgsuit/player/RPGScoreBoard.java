package com.gmail.zhou1992228.rpgsuit.player;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.gmail.zhou1992228.rpgsuit.task.TaskMinuteUpdater;
import com.gmail.zhou1992228.rpgsuit.util.DamageHandler;
import com.gmail.zhou1992228.userinfo.UserInfo;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class RPGScoreBoard {
	public static ScoreboardManager manager;
	
	public static void UpdateScoreBoardFor(Player p) {
		Scoreboard board = manager.getNewScoreboard();
		Objective obj = board.registerNewObjective("����", "����");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.getScore("����ֵ").setScore((int)(PlayerUtil.getHp(p) / 20.0 * ((Damageable)p).getHealth()));
		obj.getScore("�ȼ�").setScore((int)UserInfoUtils.getInt(p, "�ȼ�"));
		obj.getScore("������").setScore((int)UserInfoUtils.getInt(p, "������"));
		obj.getScore("������").setScore((int)UserInfoUtils.getInt(p, "������"));
		obj.getScore("ս����").setScore((int)UserInfoUtils.getInt(p, "ս����"));
		obj.getScore("ŭ��ֵ").setScore((int)UserInfoUtils.getInt(p, DamageHandler.ANGRY_KEY));
		obj.getScore("��Ǯ").setScore((int)UserInfo.econ.getBalance(p));
		obj.getScore("����").setScore((int)UserInfoUtils.getInt(p, TaskMinuteUpdater.KEY_STRENGTH));
		long exp = UserInfoUtils.getInt(p, "����ֵ");
		long nextExp = PlayerUtil.levelExp[(int)UserInfoUtils.getInt(p, "�ȼ�")];
		while (exp > Integer.MAX_VALUE) {
			exp /= 10;
		}
		while (nextExp > Integer.MAX_VALUE) {
			nextExp /= 10;
		}
		obj.getScore("����ֵ").setScore((int)exp);
		obj.getScore("�¼�����ֵ").setScore((int)nextExp);
		p.setScoreboard(board);
	}
}
