package com.gmail.zhou1992228.rpgsuit.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.rpgsuit.player.RPGScoreBoard;
import com.gmail.zhou1992228.rpgsuit.rpgentity.RPGEntityManager;

public class TaskPlayerInfoUpdater implements Runnable{

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			PlayerUtil.updateUserInfo(p);
			RPGScoreBoard.UpdateScoreBoardFor(p);
			RPGEntityManager.Update();
		}
		PlayerUtil.rank.RefreshResult();
	}
}
