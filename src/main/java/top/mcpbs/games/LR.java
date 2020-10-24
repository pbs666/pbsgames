package top.mcpbs.games;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.weather.EntityLightning;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.ParticleEffect;
import cn.nukkit.level.Position;
import top.mcpbs.games.room.Room;

public class LR implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        event.setJoinMessage("[§a+§f]" + "§e" + event.getPlayer().getName());
        event.getPlayer().setMaxHealth(20);
        event.getPlayer().setHealth(event.getPlayer().getMaxHealth());
        this.Lightning(Main.lobby);
    }

    public void Lightning(Position position) {
        EntityLightning l = new EntityLightning(position.getChunk(),EntityLightning.getDefaultNBT(position));
        l.setEffect(false);
        l.spawnToAll();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Main.s.delCache(event.getPlayer());
        event.getQuitMessage().setText("[§c-§f]" + "§e" + event.getPlayer().getName());
        event.getPlayer().getInventory().clearAll();
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event){
        if (event.getEntity() instanceof Player && Room.awaiting.containsKey(event.getEntity())){
            event.setCancelled();
        }
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event){
        event.setCancelled();
        Level level = event.getPlayer().getLevel();
        for (Player p : level.getPlayers().values()){
            p.sendMessage(event.getPlayer().getDisplayName() + ": §7" + event.getMessage());
        }
    }

    @EventHandler
    public void onEntityAttack(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player){
            event.getEntity().getLevel().addParticleEffect(event.getEntity().getPosition().add(0,1,0), ParticleEffect.CRITICAL_HIT);
        }
    }

    @EventHandler
    public void onPacket(DataPacketReceiveEvent event){
        Server.getInstance().broadcastMessage(String.valueOf(event.getPacket().pid()));
    }
}
