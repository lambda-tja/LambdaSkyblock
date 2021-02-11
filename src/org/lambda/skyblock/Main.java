package org.lambda.skyblock;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.lambda.skyblock.Events.BlockBreak;
import org.lambda.skyblock.Events.ClickNPC;
import org.lambda.skyblock.Events.Join;
import org.lambda.skyblock.GUI.ShopFarmer;
import org.lambda.skyblock.NPC.NPCDataManager;
import org.lambda.skyblock.NPC.NPC;

public class Main extends JavaPlugin implements Listener {

    public NPCDataManager npcDataManager;

    @Override
    public void onEnable() {

        this.npcDataManager = new NPCDataManager(this);
        this.npcDataManager.loadNPCs();

        System.out.println("NPCs loaded");

        this.getServer().getPluginManager().registerEvents(new ShopFarmer(), this);
        ShopFarmer.createStore();

        this.getServer().getPluginManager().registerEvents(new Join(), this);
        this.getServer().getPluginManager().registerEvents(new ClickNPC(), this);
        this.getServer().getPluginManager().registerEvents(new BlockBreak(), this);

        this.getCommand("createnpc").setExecutor(new NPC());

        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                PacketReader reader = new PacketReader();
                reader.inject(player);
            }
        }

    }

    @Override
    public void onDisable() {
        npcDataManager.getConfig().getBoolean("");
        NPC.removeNPCPacket();
        NPC.clearNPCS();

        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                PacketReader reader = new PacketReader();
                reader.uninject(player);
            }
        }

        System.out.println("Test");
    }
}
