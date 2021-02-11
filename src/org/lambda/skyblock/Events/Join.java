package org.lambda.skyblock.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.lambda.skyblock.NPC.NPC;
import org.lambda.skyblock.PacketReader;

public class Join implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (NPC.getNPCs() == null) {
            return;
        }
        if (NPC.getNPCs().isEmpty()) {
            return;
        }
        NPC.addJoinPacket(e.getPlayer());

        PacketReader reader = new PacketReader();
        reader.inject(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        PacketReader reader = new PacketReader();
        reader.uninject(e.getPlayer());
    }

}
