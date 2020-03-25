package ru.spliterash.arrowsprotection;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class ArrowsProtection extends JavaPlugin {
    @Getter
    private List<Material> items = new ArrayList<>();
    @Getter
    private double distance;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.distance = getConfig().getDouble("distance", 3);
        for (String item : getConfig().getStringList("items")) {
            Material material = Material.matchMaterial(item);
            if (material != null) {
                items.add(material);
            }
        }
        Bukkit.getPluginManager().registerEvents(new Handler(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
