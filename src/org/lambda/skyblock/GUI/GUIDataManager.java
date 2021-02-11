package org.lambda.skyblock.GUI;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.lambda.skyblock.Main;
import org.lambda.skyblock.NPC.NPC;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class GUIDataManager {

    private static final String FILE_NAME = "GUIs.yml";

    private Main plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;

    public void loadGUI() {
        if (this.dataConfig == null) {
            reloadConfig();
        }
        ConfigurationSection configSection = dataConfig.getConfigurationSection("GUIS");
        for (String npcID : configSection.getKeys(false)) {
            String name = configSection.getString(npcID+".Name");
            String skinTexture = configSection.getString(npcID+".SkinTexture");
            String skinSignature = configSection.getString(npcID+".SkinSignature");
            int posX = Integer.parseInt(configSection.getString(npcID+".PosX"));
            int posY = Integer.parseInt(configSection.getString(npcID+".PosY"));
            int posZ = Integer.parseInt(configSection.getString(npcID+".PosZ"));
            int yaw = Integer.parseInt(configSection.getString(npcID+".Yaw"));
            int pitch = Integer.parseInt(configSection.getString(npcID+".Pitch"));
            NPC.createNPC(name, skinTexture, skinSignature, posX, posY, posZ, yaw, pitch);
        }
    }

    public GUIDataManager(Main plugin) {
        this.plugin = plugin;
        // Save/Init config
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (this.configFile == null) {
            this.configFile = new File(this.plugin.getDataFolder(), FILE_NAME);
        }
        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);
        InputStream defaultStream = this.plugin.getResource(FILE_NAME);
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (this.dataConfig == null) {
            reloadConfig();
        }
        return this.dataConfig;
    }

    public void saveConfig() {
        if (this.dataConfig == null || this.configFile == null) {
            return;
        }
        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, e);
        }
    }

    public void saveDefaultConfig() {
        if (this.configFile == null) {
            this.configFile = new File(this.plugin.getDataFolder(), "GUIs.yml");
        }
        if (!this.configFile.exists()) {
            this.plugin.saveResource("GUIs.yml", false);
        }
    }
}
