package com.gmail.zhou1992228.rpgsuit;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.zhou1992228.rpgsuit.arena.ArenaManager;
import com.gmail.zhou1992228.rpgsuit.command.CommandArena;
import com.gmail.zhou1992228.rpgsuit.command.CommandCancelQuest;
import com.gmail.zhou1992228.rpgsuit.command.CommandCombine;
import com.gmail.zhou1992228.rpgsuit.command.CommandForTest;
import com.gmail.zhou1992228.rpgsuit.command.CommandGetDoubleExp;
import com.gmail.zhou1992228.rpgsuit.command.CommandGetMap;
import com.gmail.zhou1992228.rpgsuit.command.CommandGetRpgItem;
import com.gmail.zhou1992228.rpgsuit.command.CommandGetRpgStone;
import com.gmail.zhou1992228.rpgsuit.command.CommandITWNPC;
import com.gmail.zhou1992228.rpgsuit.command.CommandInfoViewer;
import com.gmail.zhou1992228.rpgsuit.command.CommandInlay;
import com.gmail.zhou1992228.rpgsuit.command.CommandLearn;
import com.gmail.zhou1992228.rpgsuit.command.CommandMaster;
import com.gmail.zhou1992228.rpgsuit.command.CommandNTP;
import com.gmail.zhou1992228.rpgsuit.command.CommandQuest;
import com.gmail.zhou1992228.rpgsuit.command.CommandRank;
import com.gmail.zhou1992228.rpgsuit.command.CommandRefresh;
import com.gmail.zhou1992228.rpgsuit.command.CommandRepair;
import com.gmail.zhou1992228.rpgsuit.command.CommandRpgBuy;
import com.gmail.zhou1992228.rpgsuit.command.CommandRpgBuyMed;
import com.gmail.zhou1992228.rpgsuit.command.CommandRpgMob;
import com.gmail.zhou1992228.rpgsuit.command.CommandStoneBuy;
import com.gmail.zhou1992228.rpgsuit.guild.GuildManager;
import com.gmail.zhou1992228.rpgsuit.guild.GuildSkill;
import com.gmail.zhou1992228.rpgsuit.item.ItemUtil;
import com.gmail.zhou1992228.rpgsuit.listener.DamageListener;
import com.gmail.zhou1992228.rpgsuit.listener.ExplodeArrowListener;
import com.gmail.zhou1992228.rpgsuit.listener.ExternalPluginListener;
import com.gmail.zhou1992228.rpgsuit.listener.InventoryInfoViewerListener;
import com.gmail.zhou1992228.rpgsuit.listener.ItemDamageListener;
import com.gmail.zhou1992228.rpgsuit.listener.ListenerForTest;
import com.gmail.zhou1992228.rpgsuit.listener.MobDeathListener;
import com.gmail.zhou1992228.rpgsuit.listener.MobSpawnListener;
import com.gmail.zhou1992228.rpgsuit.listener.PlayerDeathListener;
import com.gmail.zhou1992228.rpgsuit.listener.PlayerJoinListener;
import com.gmail.zhou1992228.rpgsuit.mob.FlyingManager;
import com.gmail.zhou1992228.rpgsuit.mob.MobGenerator;
import com.gmail.zhou1992228.rpgsuit.mob.MobUtil;
import com.gmail.zhou1992228.rpgsuit.mob.MonsterSkillBase;
import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.rpgsuit.player.RPGScoreBoard;
import com.gmail.zhou1992228.rpgsuit.quest.PetCatchListener;
import com.gmail.zhou1992228.rpgsuit.quest.Quest;
import com.gmail.zhou1992228.rpgsuit.quiz.QuizMaster;
import com.gmail.zhou1992228.rpgsuit.rpgentity.RPGEntityManager;
import com.gmail.zhou1992228.rpgsuit.skill.SkillBase;
import com.gmail.zhou1992228.rpgsuit.task.TaskMinuteUpdater;
import com.gmail.zhou1992228.rpgsuit.task.TaskMobValidator;
import com.gmail.zhou1992228.rpgsuit.task.TaskParticleChecker;
import com.gmail.zhou1992228.rpgsuit.task.TaskPlayerInfoUpdater;
import com.gmail.zhou1992228.rpgsuit.util.RewardUtil;
import com.gmail.zhou1992228.rpgsuit.util.VIPUtil;
import com.gmail.zhou1992228.treasuremap.TreasureMapUseListener;
import com.gmail.zhou1992228.userinfo.UserInfo;

public class RPGSuit extends JavaPlugin {

	final public static String DOUBLE_EXP_END_TIME = "双倍经验截止时间";
	final public static String DOUBLE_EXP_LEFT_HOURE = "剩余双倍时间";
	final public static String DAILY_TASK_LEFT_COUNT = "每日任务剩余次数"; 
	
	public static RPGSuit ins;
	public static MobGenerator mobGenerator;
	public static int updateTickScoreboard;
	public static FlyingManager flyingManager;
	public static List<String> mobEnableWorld;

	public static Economy econ = null;
	public static long now() {
		return Calendar.getInstance().getTimeInMillis();
	}
	public boolean setupEconomy() {
		if (UserInfo.instance.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = UserInfo.instance.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = (Economy) rsp.getProvider();
		return econ != null;
	}
	
	
	
	@Override
	public void onEnable() {
		ins = this;
		MobUtil.plugin = this;
		mobGenerator = new MobGenerator(this);
		setupEconomy();

		initConfig();
		ArenaManager.ins = new ArenaManager();

		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TaskMobValidator(), 10, 5 * 20);
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TaskPlayerInfoUpdater(), 10, updateTickScoreboard);
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TaskMinuteUpdater(), 10, 60 * 20);
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TaskParticleChecker(), 1, 1);
		
		this.getServer().getPluginManager().registerEvents(new DamageListener(), this);
		this.getServer().getPluginManager().registerEvents(new MobSpawnListener(), this);
		this.getServer().getPluginManager().registerEvents(new MobDeathListener(), this);
		this.getServer().getPluginManager().registerEvents(new ItemDamageListener(), this);
		this.getServer().getPluginManager().registerEvents(new ExplodeArrowListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
		this.getServer().getPluginManager().registerEvents(new TreasureMapUseListener(), this);
		this.getServer().getPluginManager().registerEvents(new ExternalPluginListener(), this);
		this.getServer().getPluginManager().registerEvents(new PetCatchListener(), this);
		this.getServer().getPluginManager().registerEvents(new QuizMaster(this), this);
		this.getServer().getPluginManager().registerEvents(new InventoryInfoViewerListener(), this);
		this.getServer().getPluginManager().registerEvents(new ListenerForTest(), this);
		
		this.getCommand("rpgmob").setExecutor(new CommandRpgMob());
		this.getCommand("ref").setExecutor(new CommandRefresh());
		this.getCommand("getrpgitem").setExecutor(new CommandGetRpgItem());
		this.getCommand("getrpgstone").setExecutor(new CommandGetRpgStone());
		this.getCommand("rpgbuy").setExecutor(new CommandRpgBuy(this));
		this.getCommand("stonebuy").setExecutor(new CommandStoneBuy(this));
		this.getCommand("rpgbuymed").setExecutor(new CommandRpgBuyMed(this));
		this.getCommand("combine").setExecutor(new CommandCombine(this));
		this.getCommand("inlay").setExecutor(new CommandInlay(this));
		this.getCommand("xiuli").setExecutor(new CommandRepair());
		this.getCommand("itwnpc").setExecutor(new CommandITWNPC());
		this.getCommand("getmap").setExecutor(new CommandGetMap());
		this.getCommand("ntp").setExecutor(new CommandNTP());
		this.getCommand("fortest2").setExecutor(new CommandForTest());
		this.getCommand("getdoubleexp").setExecutor(new CommandGetDoubleExp());
		this.getCommand("rw").setExecutor(new CommandQuest());
		this.getCommand("pm").setExecutor(new CommandRank());
		this.getCommand("learn").setExecutor(new CommandLearn());
		this.getCommand("aa").setExecutor(new CommandArena());
		this.getCommand("aa").setExecutor(new CommandArena());
		this.getCommand("shitu").setExecutor(new CommandMaster());
		this.getCommand("vipset").setExecutor(new VIPUtil());
		this.getCommand("cancelquest").setExecutor(new CommandCancelQuest());
		this.getCommand("viewinfo").setExecutor(new CommandInfoViewer());

		initStatic();
		PlayerUtil.rank.Load();
	}
	
	private void initConfig() {
		updateTickScoreboard = this.getConfig().getInt("updateTickScoreboard", 15);
		this.getConfig().set("updateTickScoreboard", updateTickScoreboard);

		mobEnableWorld = this.getConfig().getStringList("mobEnableWorld");
		if (mobEnableWorld.isEmpty()) {
			mobEnableWorld.add("mobworld");
			mobEnableWorld.add("jjc");
		}
		this.getConfig().set("mobEnableWorld", mobEnableWorld);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onDisable() {
		Quest.SaveAll();
		for (Player p : getServer().getOnlinePlayers()) {
			p.closeInventory();
		}
		PlayerUtil.rank.Save();
		GuildManager.ins.Save();
	}
	
	@SuppressWarnings("deprecation")
	private void initStatic() {
		PlayerUtil.expA = this.getConfig().getInt("player.expa", 5);
		PlayerUtil.expB = this.getConfig().getInt("player.expb", 35);
		PlayerUtil.expC = this.getConfig().getInt("player.expc", 50);
		PlayerUtil.expD = this.getConfig().getInt("player.expd", 100);
		MobUtil.expA = this.getConfig().getDouble("mobs.expa", 0.5);
		MobUtil.expB = this.getConfig().getDouble("mobs.expb", 2);
		MobUtil.expC = this.getConfig().getDouble("mobs.expc", 5);
		PlayerUtil.av1 = this.getConfig().getDouble("player.av1", 8);
		PlayerUtil.av2 = this.getConfig().getDouble("player.av2", 9);
		PlayerUtil.bv1 = this.getConfig().getDouble("player.bv1", 16);
		PlayerUtil.bv2 = this.getConfig().getDouble("player.bv2", 17);
		PlayerUtil.strv1 = this.getConfig().getDouble("player.strv1", 0.05);
		PlayerUtil.strv2 = this.getConfig().getDouble("player.strv2", 0);
		PlayerUtil.defv1 = this.getConfig().getDouble("player.defv1", 0.04);
		PlayerUtil.defv2 = this.getConfig().getDouble("player.defv2", 0);
		PlayerUtil.hpv1 = this.getConfig().getDouble("player.hpv1", 1);
		PlayerUtil.hpv2 = this.getConfig().getDouble("player.hpv2", 0);

		ItemUtil.hpv = this.getConfig().getInt("itemrepair.hpv", 10);
		ItemUtil.strv = this.getConfig().getInt("itemrepair.strv", 30);
		ItemUtil.defv = this.getConfig().getInt("itemrepair.defv", 30);
		ItemUtil.doublev = this.getConfig().getInt("itemrepair.doublev", 50);

		this.getConfig().set("player.expa", PlayerUtil.expA);
		this.getConfig().set("player.expb", PlayerUtil.expB);
		this.getConfig().set("player.expc", PlayerUtil.expC);
		this.getConfig().set("player.expd", PlayerUtil.expD);
		this.getConfig().set("player.av1", PlayerUtil.av1);
		this.getConfig().set("player.av2", PlayerUtil.av2);
		this.getConfig().set("player.bv1", PlayerUtil.bv1);
		this.getConfig().set("player.bv2", PlayerUtil.bv2);
		this.getConfig().set("player.strv1", PlayerUtil.strv1);
		this.getConfig().set("player.strv2", PlayerUtil.strv2);
		this.getConfig().set("player.defv1", PlayerUtil.defv1);
		this.getConfig().set("player.defv2", PlayerUtil.defv2);
		this.getConfig().set("player.hpv1", PlayerUtil.hpv1);
		this.getConfig().set("player.hpv2", PlayerUtil.hpv2);

		this.getConfig().set("itemrepair.hpv", ItemUtil.hpv);
		this.getConfig().set("itemrepair.strv", ItemUtil.strv);
		this.getConfig().set("itemrepair.defv", ItemUtil.defv);
		this.getConfig().set("itemrepair.doublev", ItemUtil.doublev);

		this.getConfig().set("mobs.expa", MobUtil.expA);
		this.getConfig().set("mobs.expb", MobUtil.expB);
		this.getConfig().set("mobs.expc", MobUtil.expC);

		MobUtil.init();
		PlayerUtil.init();
		GuildSkill.Init();
		MonsterSkillBase.InitMonsterSkill();
		RPGScoreBoard.manager = Bukkit.getScoreboardManager();
		
		this.saveConfig();
		Quest.InitNpcQuest();
		RewardUtil.Init();
		SkillBase.InitSkills();
		GuildManager.ins = new GuildManager(this);
		this.getServer().getScheduler().scheduleSyncRepeatingTask(ins, GuildManager.ins, 10, 30 * 60 * 20);
		flyingManager = new FlyingManager(this);
		for (Player p : getServer().getOnlinePlayers()) {
			RPGEntityManager.AddEntity(p);
		}
		for (String worldName : mobEnableWorld) {
			for (Entity e : getServer().getWorld(worldName).getEntities()) {
				if (e instanceof LivingEntity) {
					RPGEntityManager.AddEntity((LivingEntity) e);
				}
			}
		}
	}

    public static FileConfiguration getConfigWithName(String name) {
		File file = new File(RPGSuit.ins.getDataFolder(), name);
		if (file == null || !file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		return YamlConfiguration.loadConfiguration(file);
	}
    
    public static void SaveConfigToName(FileConfiguration config, String fileName) {
    	File file = new File(RPGSuit.ins.getDataFolder(), fileName);
    	if (file == null || !file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    	try {
			config.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
