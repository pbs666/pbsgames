package top.mcpbs.games.npc;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.level.Position;
import cn.nukkit.nbt.tag.CompoundTag;
import top.mcpbs.games.Main;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class AddNPCCMD extends Command {
    public AddNPCCMD(String name, String description) {
        super(name, description);
        this.setPermission("op");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage("控制台不能使用此指令");
        }else{
            Position pos = new Position(Double.valueOf(strings[0]),Double.valueOf(strings[1]),Double.valueOf(strings[2]),((Player)sender).getLevel());
            Skin skin = new Skin();
            try {
                skin.setSkinData(ImageIO.read(new File(Main.plugin.getDataFolder() + "/npcskin/steve.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            new NPC(pos.getChunk(), NPCTool.getTag(pos).putCompound("Skin", (new CompoundTag()).putByteArray("Data", skin.getSkinData().data).putString("ModelId", skin.getSkinId())),strings[3],Boolean.valueOf(strings[4]),Boolean.valueOf(strings[5]),strings[6]);
            sender.sendMessage("§asuccessful create");
        }
        return true;
    }
}
/**
 * 0:x
 * 1:y
 * 2:z
 * 3:name
 * 4:isalwayssave
 * 5:iskeepface
 * 6:cmd
 * 7:skin
 */