package top.mcpbs.games.particle;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;

public class Particle extends Command {
    public Particle(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage("不能在控制台使用此指令!");
        }else{
            Player player = (Player)sender;
            Forms.showParticleSelectForm(player);
        }
        return true;
    }
}
