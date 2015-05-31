package com.gmail.zhou1992228.rpgsuit.quiz;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.mob.MobUtil;
import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.rpgsuit.util.RewardUtil;
import com.gmail.zhou1992228.rpgsuit.util.RewardUtil.RewardItem;
import com.gmail.zhou1992228.rpgsuit.util.Util;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class QuizMaster implements Listener, Runnable {
	public static String QUIZ_COUNT_KEY = "抢答成功次数";
	public List<String> quizs;
	public String now = "";
	public long time;
	public Random random = new Random();
	public int playerCount = 0;
	public static QuizMaster ins;
	public boolean active = true;
	public QuizMaster(RPGSuit plugin) {
		quizs = RPGSuit.getConfigWithName("quiz.yml").getStringList("quiz");
		ins = this;
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 10 * 20, 60 * 20);
	}
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if (!now.isEmpty()) {
			if (Util.Now() > time + 60 * 1000) {
				now = "";
				RPGSuit.ins.getServer().broadcastMessage(
						ChatColor.GREEN + "[问·答]" +
						ChatColor.GOLD + " 时间已到~等待下一个问题吧~");
				return;
			}
			if (e.getMessage().contains(now.split(":")[1])) {
				Player p = e.getPlayer();
				SendReward(p);
				now = "";
			}
		}
	}
	
	public void addRandomQuiz() {
		now = quizs.get(random.nextInt(quizs.size()));
		RPGSuit.ins.getServer().broadcastMessage(ChatColor.GREEN + "[问·答]" + ChatColor.GOLD + now.split(":")[0]);
		time = Util.Now();
	}

	private void SendReward(Player p) {
		double prob = random.nextDouble();
		if (prob < 0.2) {
			int lv = PlayerUtil.getLevel(p);
			int value = lv * 80;
			RewardItem ri = RewardUtil.getRandomStone(value);
			p.getInventory().addItem(ri.it.clone());
			RPGSuit.ins.getServer().broadcastMessage(
					ChatColor.GREEN + p.getName() + "    " + ChatColor.GOLD + "抢答成功！" +
					ChatColor.WHITE + "获得了  " + ChatColor.YELLOW + ri.name);
		} else if (prob < 0.4) {
			int lv = PlayerUtil.getLevel(p);
			RewardItem ri = RewardUtil.getRandomArmorWithLevel(lv);
			p.getInventory().addItem(ri.it.clone());
			RPGSuit.ins.getServer().broadcastMessage(
					ChatColor.GREEN + p.getName() + "    " + ChatColor.GOLD + "抢答成功！" +
					ChatColor.WHITE + "获得了  " + ChatColor.YELLOW + ri.name);
		} else {
			int lv = PlayerUtil.getLevel(p);
			int exp = MobUtil.exp[lv] * 5;
			int money = lv * 30;
			RPGSuit.ins.getServer().broadcastMessage(
					ChatColor.GREEN + p.getName() + "    " + ChatColor.GOLD + "抢答成功！");
			p.sendMessage("抢答成功！获得经验值: " + exp + "  金钱: " + money);
			UserInfoUtils.add(p, "经验值", exp);
			UserInfoUtils.giveItem(p, "$" + money);
		}
		UserInfoUtils.add(p, QUIZ_COUNT_KEY, 1);
	}
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		if (active) {
			playerCount += RPGSuit.ins.getServer().getOnlinePlayers().length;
			if (playerCount > 60) {
				playerCount -= 60;
				addRandomQuiz();
			}
		}
	}
}
