package top.mcpbs.games.chao;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.GameRule;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.potion.Effect;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;
import top.mcpbs.games.playerinfo.PlayerInfoTool;
import top.mcpbs.games.util.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class Chao extends PluginTask {

    public static Level chaolevel = null;
    public static HashMap<Player, Boolean> players = new HashMap();
    public static int determination = 0;
    public static Position spawn = null;

    public Chao(Plugin owner) {
        super(owner);
        Config config = new Config(owner.getDataFolder() + "/chao.yml");
        Server.getInstance().loadLevel((String) config.get("world"));
        chaolevel = Server.getInstance().getLevelByName((String) config.get("world"));
        chaolevel.getGameRules().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        chaolevel.setTime(6000);
        determination = (int) config.get("determination");
        ArrayList tmp = (ArrayList) config.get("spawn");
        double x = (double) tmp.get(0);
        double y = (double) tmp.get(1);
        double z = (double) tmp.get(2);
        spawn = new Position(x,y,z,chaolevel);
    }

    @Override
    public void onRun(int i) {
        for (Player player : Chao.players.keySet()){
            player.getFoodData().setLevel(20);
            Effect n = Effect.getEffect(Effect.NIGHT_VISION);
            n.setDuration(20*20);
            player.addEffect(n);

            if (player.getY() <= determination && players.get(player) == false){
                player.sendTitle("§cPVP模式§e开启！","§a努力击杀其他玩家吧！");
                player.sendMessage("§b游戏 §7» §cPVP模式§e开启！§a努力击杀其他玩家吧！");

                player.getInventory().clearAll();//give items
                if (player.getInventory().getHelmet().isNull()){
                    Item hel = Item.get(302,0);
                    player.getInventory().setHelmet(hel);
                }
                if (player.getInventory().getChestplate().isNull()){
                    Item che = Item.get(303,0);
                    player.getInventory().setChestplate(che);
                }
                if (player.getInventory().getLeggings().isNull()){
                    Item leg = Item.get(304,0);
                    player.getInventory().setLeggings(leg);
                }
                if (player.getInventory().getBoots().isNull()){
                    Item boo = Item.get(305,0);
                    player.getInventory().setBoots(boo);
                }

                Item bow = Item.get(261);
                player.getInventory().addItem(bow);

                Item a = Item.get(262,0,64);
                player.getInventory().addItem(a);

                Item c = Item.get(322,0,1);
                player.getInventory().addItem(c);

                Item d = Item.get(272,0,1);

                player.getInventory().addItem(d);

                Item e = Item.get(346,0,1);
                player.getInventory().addItem(e);

                Item f = Item.get(332,0,16);
                player.getInventory().addItem(f);

                players.put(player,true);//ok
            }

            if (player.getInventory().getHelmet().isNull()){//test null (on play)
                Item hel = Item.get(302,0);
                player.getInventory().setHelmet(hel);
            }
            if (player.getInventory().getChestplate().isNull()){
                Item che = Item.get(303,0);
                player.getInventory().setChestplate(che);
            }
            if (player.getInventory().getLeggings().isNull()){
                Item leg = Item.get(304,0);
                player.getInventory().setLeggings(leg);
            }
            if (player.getInventory().getBoots().isNull()){
                Item boo = Item.get(305,0);
                player.getInventory().setBoots(boo);
            }

            Item bow = Item.get(261);
            Item a = Item.get(262,0,64);
            Item c = Item.get(322,0,1);
            Item d = Item.get(272,0,1);
            Item e = Item.get(346,0,1);
            Item f = Item.get(332,0,16);
            if (!player.getInventory().contains(bow)){
                player.getInventory().addItem(bow);
            }
            if (!player.getInventory().contains(a)){
                player.getInventory().addItem(a);
            }
            if (!player.getInventory().contains(c)){
                player.getInventory().addItem(c);
            }
            if (!player.getInventory().contains(d)){
                player.getInventory().addItem(d);
            }
            if (!player.getInventory().contains(e)){
                player.getInventory().addItem(e);
            }
            if (!player.getInventory().contains(f)){
                player.getInventory().addItem(f);
            }

            ArrayList l = new ArrayList();
            l.add("击杀: " + "§a" + getkill(player));
            l.add(" ");
            l.add("死亡: " + "§a" + getDeath(player));
            l.add("  ");
            l.add("乱斗币: " + "§a" + String.format("%.2f", getCoin(player)));
            l.add("   ");
            int death = (int) getDeath(player);
            if (death == 0){
                death = 1;
            }
            double kdr = (double)getkill(player) / (double) death;
            l.add("KDR: " + "§a" + String.format("%.3f", kdr));
            l.add("    ");
            l.add("§eplay.mcpbs.top");
            Main.s.showScoreboard(player, "§l§e天坑大乱斗", l);
        }
    }

    public static int getPlaying(){
        return Chao.players.size();
    }

    public static void addHealth(Player player,int num){
        PlayerInfoTool.setInfo(player,"chao.health",PlayerInfoTool.getInfo(player,"chao.health",20) + num);
        player.sendMessage("§d血量 §7» §a最高血量增加了!当前:" + getHealth(player));
    }

    public static void remHealth(Player player,int num){
        PlayerInfoTool.setInfo(player,"chao.health",PlayerInfoTool.getInfo(player,"chao.health",20) - num);
    }

    public static int getHealth(Player player){
        return PlayerInfoTool.getInfo(player,"chao.health",20);
    }

    public static double getCoin(Player player){
        return PlayerInfoTool.getInfo(player,"chao.coin",0.0);
    }

    public static int getkill(Player player){
        return PlayerInfoTool.getInfo(player,"chao.kill",0);
    }

    public static int getDeath(Player player){
        return PlayerInfoTool.getInfo(player,"chao.death",0);
    }

    public static void addCoin(Player player,double num){
        PlayerInfoTool.setInfo(player,"chao.coin",PlayerInfoTool.getInfo(player,"chao.coin",0.0) + num);
        player.sendMessage("§e硬币 §7» §a你增加了" + num + "个大乱斗币");
    }

    public static void remCoin(Player player,double num){
        PlayerInfoTool.setInfo(player,"chao.coin",PlayerInfoTool.getInfo(player,"chao.coin",0.0) - num);
        player.sendMessage("§e硬币 §7» §6你扣除了" + num + "个大乱斗币");
    }

    public static void addKill(Player player,int num){
        PlayerInfoTool.setInfo(player,"chao.kill",PlayerInfoTool.getInfo(player,"chao.kill",0) + num);
    }

    public static void remKill(Player player,int num){
        PlayerInfoTool.setInfo(player,"chao.kill",PlayerInfoTool.getInfo(player,"chao.kill",0) - num);
    }

    public static void addDeath(Player player,int num){
        PlayerInfoTool.setInfo(player,"chao.death",PlayerInfoTool.getInfo(player,"chao.death",0) + num);
    }

    public static void remDeath(Player player,int num){
        PlayerInfoTool.setInfo(player,"chao.death",PlayerInfoTool.getInfo(player,"chao.death",0) - num);
    }

    public static void addDrawRate(Player player,double num){
        PlayerInfoTool.setInfo(player,"chao.draw_rate",PlayerInfoTool.getInfo(player,"chao.draw_rate",1.0) + num);
        player.sendMessage("§a金币倍率 §7» §e你的金币倍率增加了!当前" + getdrawRate(player));
    }

    public static void remDrawRateth(Player player,double num){
        PlayerInfoTool.setInfo(player,"chao.draw_rate",PlayerInfoTool.getInfo(player,"chao.draw_rate",1.0) - num);
    }

    public static double getdrawRate(Player player){
        return PlayerInfoTool.getInfo(player,"chao.draw_rate",1.0);
    }
}
