package com.gmail.zhou1992228.rpgsuit.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.util.ParticleEffect;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class TaskParticleChecker implements Runnable {

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			Check(p);
		}
	}

	private void Check(Player p) {
		String special = UserInfoUtils.getString(p, "ÌØÐ§");		
		if (special != null && special.contains("»ðÑæ»·ÈÆ")) {
			ParticleEffect.sendToLocation(ParticleEffect.FIRE,
					p.getEyeLocation(), 0.7F, 0.7F, 0.7F, 0.0F, 1);
		}
	}

}
