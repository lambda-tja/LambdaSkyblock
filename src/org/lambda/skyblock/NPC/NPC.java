package org.lambda.skyblock.NPC;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_16_R3.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.lambda.skyblock.Main;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NPC implements CommandExecutor {

    private static List<EntityPlayer> NPC = new ArrayList<EntityPlayer>();

    public static void createNPC(String name, String skinTexture, String skinSignature, int x, int y, int z, int yaw, int pitch) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld)Bukkit.getWorld("Spawn")).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), ChatColor.DARK_AQUA + "" + ChatColor.BOLD + name);
        EntityPlayer npc = new EntityPlayer(server, world, gameProfile, new PlayerInteractManager(world));
        npc.getDataWatcher().set(new DataWatcherObject<>(16, DataWatcherRegistry.a), (byte)127);
        npc.setLocation(x, y, z, yaw, pitch);

        gameProfile.getProperties().put("textures", new Property("textures", skinTexture, skinSignature));

        addNPCPacket(npc);
        NPC.add(npc);
}

    public static void addNPCPacket(EntityPlayer npc) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            connection.sendPacket(new PacketPlayOutEntityMetadata(npc.getId(), npc.getDataWatcher(), true));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
            //connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
            Bukkit.getScheduler().scheduleSyncDelayedTask(org.lambda.skyblock.Main.getPlugin(Main.class), new Runnable() {
                @Override
                public void run() {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
                        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
                    }
                }
            }, 100);
        }
    }

    public static void addJoinPacket(Player player) {
        for (EntityPlayer npc : NPC) {
            PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            connection.sendPacket(new PacketPlayOutEntityMetadata(npc.getId(), npc.getDataWatcher(), true));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
            //connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
            Bukkit.getScheduler().scheduleSyncDelayedTask(org.lambda.skyblock.Main.getPlugin(Main.class), new Runnable() {
                @Override
                public void run() {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
                        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
                    }
                }
            }, 100);
        }
    }

    public static void removeNPCPacket() {
        for (EntityPlayer npc : NPC) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
                //connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
                connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getId()));
            }
        }
    }

    public static List<EntityPlayer> getNPCs() {
        return NPC;
    }

    public static void clearNPCS() {
        NPC = new ArrayList<EntityPlayer>();
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (s.equalsIgnoreCase("createnpc")) {
            if (!(commandSender instanceof Player)) {
                return true;
            }
            /*
            Player player = (Player) commandSender;
            if (strings.length == 0) {
                createNPC(player.getName());
                player.sendMessage("NPC CREATED +");
                return true;
            }
            createNPC(strings[0]);
            player.sendMessage("NPC CREATED");
             */
        }
        return false;
    }
}
