package top.mcpbs.games.waitroom;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import top.mcpbs.games.name.NameTool;
import top.mcpbs.games.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class WaitRoom {
    public static HashMap<Integer,WaitRoom> waitrooms = new HashMap<>();
    public Level roomlevel;
    public final Position pos;
    public int roomid;
    public WaitRoom(){
        int id = 0;
        int max = 0;
        ArrayList<Integer> tmp = new ArrayList();
        for (int idt : WaitRoom.waitrooms.keySet()){
            tmp.add(idt);
            max = idt;
        }
        for (int idtt : tmp){
            if (idtt > max){
                max = idtt;
            }
        }
        for (int i = 1; i <= (max + 1); i++){
            if (!WaitRoom.waitrooms.containsKey(i)){
                id = i;
                break;
            }
        }
        if (id == 0){
            id = 1;
        }
        this.roomid = id;

        FileUtil f = new FileUtil();
        if (new File(Server.getInstance().getDataPath() + "/worlds/waitlevel" + id).exists()){
            f.deleteDirectory(new File(Server.getInstance().getDataPath() + "/worlds/waitlevel" + id));
        }
        new File(Server.getInstance().getDataPath() + "/worlds/waitlevel" + id + "/region").mkdirs();
        f.copyDir(Server.getInstance().getDataPath() + "/worlds/waittmp/region",Server.getInstance().getDataPath() + "/worlds/waitlevel" + id + "/region");
        Server.getInstance().generateLevel("waitlevel" + id);
        this.roomlevel = Server.getInstance().getLevelByName("waitlevel" + this.roomid);
        this.pos = new Position(0,100,0,this.roomlevel);

        WaitRoom.waitrooms.put(this.roomid,this);
    }

    public void joinWaitRoom(Player player){
        player.teleport(this.pos);
        NameTool.setPlayerNameTag(player,"/name/");
        NameTool.setPlayerDisplayName(player,"/name/");
        for (Player p : this.roomlevel.getPlayers().values()){
            p.sendMessage("§a游戏 §7» " + player.getName() + " 加入了游戏 " + "§8[等待人数:" + this.roomlevel.getPlayers().size() + "]");
        }
    }

    public void remWaitRoom(){
        WaitRoom.waitrooms.remove(this.roomid);
        this.roomlevel.unload();
        FileUtil f = new FileUtil();
        f.deleteDirectory(new File(Server.getInstance().getDataPath() + "/worlds/" + "waitlevel" + this.roomid));
    }
}
