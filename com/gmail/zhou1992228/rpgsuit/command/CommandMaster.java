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
	
	final public static String NUM_APPRENTIC = "ͽ����";
	final public static String KEY_NUM_APPRENTIC_NOW = "������ͽ����";
	final public static String KEY_MASTER = "ʦ��";
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
				p.sendMessage("����ȷʹ������");
				return true;
			}
			if (op.equals("��ʦ")) {
				if (arg3.length < 2) { p.sendMessage("����������ݵ�ʦ��"); return true; }
				int level = PlayerUtil.getLevel(p);
				String master = UserInfoUtils.getString(p, KEY_MASTER, "");
				if (level > this.MAX_LEVEL_HAVE_MASTER) {
					p.sendMessage("����ô�߼�(> " + MAX_LEVEL_HAVE_MASTER + ")���Ѿ�����Ҫʦ����");
					return true;
				}
				if (!master.isEmpty()) {
					p.sendMessage("���Ѿ���ʦ����");
					return true;
				}
				Player m = RPGSuit.ins.getServer().getPlayer(arg3[1]);
				if (m == null) {
					p.sendMessage("����Ҳ�����/������");
					return true;
				}
				p.sendMessage("��������Ѿ�����");
				m.sendMessage(p.getName() + " �����Ϊʦ������ /shitu ��ͽ " + p.getName() + " ��������");
				req_list.put(p.getName(), m.getName());
			} else if (op.equals("��ͽ")) {
				if (arg3.length < 2) { p.sendMessage("�����������յ�ͽ��"); return true; }
				int level = PlayerUtil.getLevel(p);
				if (level < this.MIN_LEVEL_HAVE_APPRENTIC) {
					p.sendMessage("����ô�ͼ�(< " + this.MIN_LEVEL_HAVE_APPRENTIC + ")���Լ��������ɡ�");
					return true;
				}
				String aname = arg3[1];
				Player ap = RPGSuit.ins.getServer().getPlayer(aname);
				if (ap == null) {
					p.sendMessage("����Ҳ�����/������");
					return true;
				}
				if (req_list.get(arg3[1]) == null ||
					!req_list.get(arg3[1]).equals(p.getName())) {
					p.sendMessage("�����û����Ը����Ϊʦ");
					return true;
				}
				long num_a = UserInfoUtils.getInt(p, KEY_NUM_APPRENTIC_NOW);
				if (num_a >= 3) {
					p.sendMessage("���Ѿ����㹻���ͽ���ˣ��������ɹ����ո���İ�");
					return true;
				}
				UserInfoUtils.set(ap, KEY_MASTER, p.getName());
				UserInfoUtils.add(p, KEY_NUM_APPRENTIC_NOW, 1);
				p.sendMessage("��ɹ��� " + aname + " ��Ϊͽ��");
				ap.sendMessage(p.getName() + " �Ѿ�������Ϊͽ�ܣ���ʲô������������ɣ�");
				req_list.remove(aname);
			} else if (op.equals("��ҵ")) {
				String masterName = UserInfoUtils.getString(p, KEY_MASTER, "");
				if (masterName.isEmpty()) {
					p.sendMessage("��û��ʦ��");
					return true;
				}
				Player mp = RPGSuit.ins.getServer().getPlayer(UserInfoUtils.getString(p, KEY_MASTER, ""));
				if (mp == null) {
					p.sendMessage("���ʦ����������Ŷ������������һ����ף���ʱ�̰�");
					return true;
				}
				int level = PlayerUtil.getLevel(p);
				if (level < this.minLevelToGraduate(p) || level > this.maxLevelToGraduate(p)) {
					p.sendMessage("��ҵ����ȼ��� " + this.minLevelToGraduate(p) + "-" + this.maxLevelToGraduate(p) + " ����");
					return true;
				}
				Player ap = p;
				UserInfoUtils.add(mp, NUM_APPRENTIC, 1);
				UserInfoUtils.minus(mp, KEY_NUM_APPRENTIC_NOW, 1);
				UserInfoUtils.set(ap, "��ʦ��", UserInfoUtils.getString(ap, KEY_MASTER, ""));
				UserInfoUtils.set(ap, KEY_MASTER, "");
				sendReward(ap, false);
				sendReward(mp, true);
			} else if (op.equals("���")) {
				if (arg3.length < 2) { p.sendMessage("���������������ͽ��"); return true; }
				OfflinePlayer ap = RPGSuit.ins.getServer().getOfflinePlayer(arg3[1]);
				if (del_list.containsKey(p.getName())) {
					if (!UserInfoUtils.getString(ap, KEY_MASTER, "").equals(p.getName())) {
						p.sendMessage("�㲻�� " + arg3[1] + " ��ʦ����");
						return true;
					}
					long t = del_list.get(p.getName());
					if (Util.Now() - t < 60 * 1000) {
						p.sendMessage("�����Ҫ�� " + arg3[1] + " ����ʦͽ��ϵ���ǵĻ������´�һ�δ�����");
						del_list.put(p.getName(), Util.Now());
						return true;
					} else {
						UserInfoUtils.minus(p, KEY_NUM_APPRENTIC_NOW, 1);
						UserInfoUtils.set(ap, KEY_MASTER, "");
						p.sendMessage("���Ѿ��� " + ap.getName() + " ���ʦͽ��ϵ");
					}
				} else {
					if (!UserInfoUtils.getString(ap, KEY_MASTER, "").equals(p.getName())) {
						p.sendMessage("�㲻�� " + arg3[1] + " ��ʦ����");
						return true;
					}
					p.sendMessage("�����Ҫ�� " + arg3[1] + " ����ʦͽ��ϵ���ǵĻ������´�һ�δ�����");
					del_list.put(p.getName(), Util.Now());
					return true;
				}
			} else if (op.equals("�뿪")) {
				String masterName = UserInfoUtils.getString(p, KEY_MASTER, "");
				if (masterName.isEmpty()) {
					p.sendMessage("��û��ʦ��");
					return true;
				}
				OfflinePlayer mp = RPGSuit.ins.getServer().getOfflinePlayer(masterName);
				if (mp == null) {
					p.sendMessage("��û��ʦ����");
					return true;
				}
				if (del_list.containsKey(p.getName())) {
					long t = del_list.get(p.getName());
					if (t - Util.Now() > 60 * 1000) {
						p.sendMessage("�����Ҫ�� " + mp.getName() + " ����ʦͽ��ϵ���ǵĻ������´�һ�δ�����");
						del_list.put(p.getName(), Util.Now());
						return true;
					} else {
						UserInfoUtils.minus(mp, KEY_NUM_APPRENTIC_NOW, 1);
						UserInfoUtils.set(p, KEY_MASTER, "");
						p.sendMessage("���Ѿ��� " + mp.getName() + " ���ʦͽ��ϵ");
					}
				} else {
					p.sendMessage("�����Ҫ�� " + mp.getName() + " ����ʦͽ��ϵ���ǵĻ������´�һ�δ�����");
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
			p.sendMessage("���ͽ�ܱ�ҵ�ˣ�������Ľ�����  $" + money + "  ����ֵ:" + exp);
		} else {
			p.sendMessage("���ҵ�ˣ�������Ľ����� $" + money + "  ����ֵ:" + exp);
		}
		UserInfoUtils.giveItem(p, "$" + money);
		UserInfoUtils.giveItem(p, "s:����ֵ:" + exp);
	}
}
