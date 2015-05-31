package com.gmail.zhou1992228.rpgsuit.command;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.zhou1992228.rpgsuit.RPGSuit;
import com.gmail.zhou1992228.rpgsuit.mob.MobUtil;
import com.gmail.zhou1992228.rpgsuit.player.PlayerUtil;
import com.gmail.zhou1992228.rpgsuit.util.Util;
import com.gmail.zhou1992228.userinfo.UserInfoUtils;

public class CommandMaster implements CommandExecutor{
	
	final public static String NUM_APPRENTIC = "徒弟数";
	final public static String KEY_NUM_APPRENTIC_NOW = "培养中徒弟数";
	final public static String KEY_MASTER = "师傅";
	final public static int MAX_LEVEL_HAVE_MASTER = 8;
	final public static int MIN_LEVEL_HAVE_APPRENTIC = 28;
	final public static int MIN_LEVEL_TO_GRADUATE = 25;
	final public static int MAX_LEVEL_TO_GRADUATE = 30;
	public Map<String, String> req_list = new HashMap<String, String>();
	public Map<String, Long> del_list = new HashMap<String, Long>();
	
	@SuppressWarnings({ "static-access", "deprecation" })
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		if (arg0 instanceof Player) {
			Player p = (Player) arg0;
			String op = "";
			if (arg3.length > 0) {
				op = arg3[0];
			} else {
				p.sendMessage("请正确使用命令");
				return true;
			}
			if (op.equals("拜师")) {
				if (arg3.length < 2) { p.sendMessage("请输入你想拜的师傅"); return true; }
				int level = PlayerUtil.getLevel(p);
				String master = UserInfoUtils.getString(p, KEY_MASTER, "");
				if (level > this.MAX_LEVEL_HAVE_MASTER) {
					p.sendMessage("你这么高级(> " + MAX_LEVEL_HAVE_MASTER + ")，已经不需要师傅了");
					return true;
				}
				if (!master.isEmpty()) {
					p.sendMessage("你已经有师傅了");
					return true;
				}
				Player m = RPGSuit.ins.getServer().getPlayer(arg3[1]);
				if (m == null) {
					p.sendMessage("该玩家不在线/不存在");
					return true;
				}
				p.sendMessage("你的请求已经发送");
				m.sendMessage(p.getName() + " 想拜你为师，输入 /shitu 收徒 " + p.getName() + " 接受请求");
				req_list.put(p.getName(), m.getName());
			} else if (op.equals("收徒")) {
				if (arg3.length < 2) { p.sendMessage("请输入你想收的徒弟"); return true; }
				int level = PlayerUtil.getLevel(p);
				if (level < this.MIN_LEVEL_HAVE_APPRENTIC) {
					p.sendMessage("你这么低级(< " + this.MIN_LEVEL_HAVE_APPRENTIC + ")，自己先练练吧。");
					return true;
				}
				String aname = arg3[1];
				Player ap = RPGSuit.ins.getServer().getPlayer(aname);
				if (ap == null) {
					p.sendMessage("该玩家不在线/不存在");
					return true;
				}
				if (req_list.get(arg3[1]) == null ||
					!req_list.get(arg3[1]).equals(p.getName())) {
					p.sendMessage("该玩家没有意愿拜你为师");
					return true;
				}
				long num_a = UserInfoUtils.getInt(p, KEY_NUM_APPRENTIC_NOW);
				if (num_a >= 3) {
					p.sendMessage("你已经有足够多的徒弟了，先培养成功再收更多的吧");
					return true;
				}
				UserInfoUtils.set(ap, KEY_MASTER, p.getName());
				UserInfoUtils.add(p, KEY_NUM_APPRENTIC_NOW, 1);
				p.sendMessage("你成功将 " + aname + " 收为徒弟");
				ap.sendMessage(p.getName() + " 已经将你收为徒弟，有什么问题多问问他吧！");
				req_list.remove(aname);
			} else if (op.equals("毕业")) {
				String masterName = UserInfoUtils.getString(p, KEY_MASTER, "");
				if (masterName.isEmpty()) {
					p.sendMessage("你没有师傅");
					return true;
				}
				Player mp = RPGSuit.ins.getServer().getPlayer(UserInfoUtils.getString(p, KEY_MASTER, ""));
				if (mp == null) {
					p.sendMessage("你的师傅好像不在线哦，等他上线再一起庆祝这个时刻吧");
					return true;
				}
				int level = PlayerUtil.getLevel(p);
				if (level < this.minLevelToGraduate(p) || level > this.maxLevelToGraduate(p)) {
					p.sendMessage("毕业必须等级在 " + this.minLevelToGraduate(p) + "-" + this.maxLevelToGraduate(p) + " 以内");
					return true;
				}
				Player ap = p;
				UserInfoUtils.add(mp, NUM_APPRENTIC, 1);
				UserInfoUtils.minus(mp, KEY_NUM_APPRENTIC_NOW, 1);
				UserInfoUtils.set(ap, "老师傅", UserInfoUtils.getString(ap, KEY_MASTER, ""));
				UserInfoUtils.set(ap, KEY_MASTER, "");
				sendReward(ap, false);
				sendReward(mp, true);
			} else if (op.equals("逐出")) {
				if (arg3.length < 2) { p.sendMessage("请输入你想逐出的徒弟"); return true; }
				OfflinePlayer ap = RPGSuit.ins.getServer().getOfflinePlayer(arg3[1]);
				if (del_list.containsKey(p.getName())) {
					if (!UserInfoUtils.getString(ap, KEY_MASTER, "").equals(p.getName())) {
						p.sendMessage("你不是 " + arg3[1] + " 的师傅！");
						return true;
					}
					long t = del_list.get(p.getName());
					if (Util.Now() - t < 60 * 1000) {
						p.sendMessage("你真的要与 " + arg3[1] + " 脱离师徒关系吗，是的话请重新打一次此命令");
						del_list.put(p.getName(), Util.Now());
						return true;
					} else {
						UserInfoUtils.minus(p, KEY_NUM_APPRENTIC_NOW, 1);
						UserInfoUtils.set(ap, KEY_MASTER, "");
						p.sendMessage("你已经与 " + ap.getName() + " 解除师徒关系");
					}
				} else {
					if (!UserInfoUtils.getString(ap, KEY_MASTER, "").equals(p.getName())) {
						p.sendMessage("你不是 " + arg3[1] + " 的师傅！");
						return true;
					}
					p.sendMessage("你真的要与 " + arg3[1] + " 脱离师徒关系吗，是的话请重新打一次此命令");
					del_list.put(p.getName(), Util.Now());
					return true;
				}
			} else if (op.equals("离开")) {
				String masterName = UserInfoUtils.getString(p, KEY_MASTER, "");
				if (masterName.isEmpty()) {
					p.sendMessage("你没有师傅");
					return true;
				}
				OfflinePlayer mp = RPGSuit.ins.getServer().getOfflinePlayer(masterName);
				if (mp == null) {
					p.sendMessage("你没有师傅！");
					return true;
				}
				if (del_list.containsKey(p.getName())) {
					long t = del_list.get(p.getName());
					if (t - Util.Now() > 60 * 1000) {
						p.sendMessage("你真的要与 " + mp.getName() + " 脱离师徒关系吗，是的话请重新打一次此命令");
						del_list.put(p.getName(), Util.Now());
						return true;
					} else {
						UserInfoUtils.minus(mp, KEY_NUM_APPRENTIC_NOW, 1);
						UserInfoUtils.set(p, KEY_MASTER, "");
						p.sendMessage("你已经与 " + mp.getName() + " 解除师徒关系");
					}
				} else {
					p.sendMessage("你真的要与 " + mp.getName() + " 脱离师徒关系吗，是的话请重新打一次此命令");
					del_list.put(p.getName(), Util.Now());
					return true;
				}
			}
			return true;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public int minLevelToGraduate(Player p) {
		int mlv = PlayerUtil.getLevel(RPGSuit.ins.getServer().getOfflinePlayer(UserInfoUtils.getString(p, KEY_MASTER)));
		return Math.max(25, Math.min(mlv / 3 * 2, 50));
	}
	public int maxLevelToGraduate(Player p) {
		@SuppressWarnings("deprecation")
		int mlv = PlayerUtil.getLevel(RPGSuit.ins.getServer().getOfflinePlayer(UserInfoUtils.getString(p, KEY_MASTER)));
		return Math.max(Math.min(50, mlv / 3 * 2), 55);
	}
	
	public static void sendReward(Player p, boolean master) {
		int lv = PlayerUtil.getLevel(p);
		int money = lv * 600;
		int exp = PlayerUtil.mobCountToLevelUp(lv) * MobUtil.exp[lv];
		if (master) {
			p.sendMessage("你的徒弟毕业了，这是你的奖励：  $" + money + "  经验值:" + exp);
		} else {
			p.sendMessage("你毕业了，这是你的奖励： $" + money + "  经验值:" + exp);
		}
		UserInfoUtils.giveItem(p, "$" + money);
		UserInfoUtils.giveItem(p, "s:经验值:" + exp);
	}
}
