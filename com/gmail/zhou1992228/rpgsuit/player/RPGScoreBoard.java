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
		Objective obj = board.registerNewObjective("属性", "属性");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.getScore("生命值").setScore((int)(PlayerUtil.getHp(p) / 20.0 * ((Damageable)p).getHealth()));
		obj.getScore("等级").setScore((int)UserInfoUtils.getInt(p, "等级"));
		obj.getScore("攻击力").setScore((int)UserInfoUtils.getInt(p, "攻击力"));
		obj.getScore("防御力").setScore((int)UserInfoUtils.getInt(p, "防御力"));
		obj.getScore("战斗力").setScore((int)UserInfoUtils.getInt(p, "战斗力"));
		obj.getScore("怒气值").setScore((int)UserInfoUtils.getInt(p, DamageHandler.ANGRY_KEY));
		obj.getScore("金钱").setScore((int)UserInfo.econ.getBalance(p));
		obj.getScore("体力").setScore((int)UserInfoUtils.getInt(p, TaskMinuteUpdater.KEY_STRENGTH));
		long exp = UserInfoUtils.getInt(p, "经验值");
		long nextExp = PlayerUtil.levelExp[(int)UserInfoUtils.getInt(p, "等级")];
		while (exp > Integer.MAX_VALUE) {
			exp /= 10;
		}
		while (nextExp > Integer.MAX_VALUE) {
			nextExp /= 10;
		}
		obj.getScore("经验值").setScore((int)exp);
		obj.getScore("下级经验值").setScore((int)nextExp);
		p.setScoreboard(board);
	}
}
