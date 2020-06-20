package me.xemor.stoprenaming.events;

import me.xemor.stoprenaming.StopRenaming;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;

public class Rename implements Listener {

    HashSet<String> bannedNames = new HashSet<>();
    EnumSet<Material> bannedMaterials = EnumSet.noneOf(Material.class);
    boolean banAll;
    FileConfiguration fileConfiguration;
    StopRenaming stopRenaming;

    public Rename(FileConfiguration fileConfiguration, StopRenaming stopRenaming) {
        this.fileConfiguration = fileConfiguration;
        this.stopRenaming = stopRenaming;
        loadConfig();
    }

    public void reloadConfig() {
        stopRenaming.reloadConfig();
        this.fileConfiguration = stopRenaming.getConfig();
        loadConfig();
    }

    public void loadConfig() {
        this.banAll = fileConfiguration.getBoolean("banAllNames");
        List<String> bannedNames = fileConfiguration.getStringList("bannedNames");
        List<String> bannedMaterials = fileConfiguration.getStringList("bannedMaterials");
        for (String string : bannedNames) {
            this.bannedNames.add(string);
        }
        for (String string : bannedMaterials) {
            Material material = Material.getMaterial(string);
            this.bannedMaterials.add(material);
        }
    }

    @EventHandler
    public void onAnvil(InventoryClickEvent e) {
        if (e.getClickedInventory() instanceof AnvilInventory) {
            AnvilInventory anvil = (AnvilInventory) e.getClickedInventory();
            if (e.getSlotType() == InventoryType.SlotType.RESULT) {
                if (anvil.getRenameText().isEmpty()) {
                    return;
                }
                if (banAll) {
                    e.setCancelled(true);
                }
                else if (bannedNames.contains(anvil.getRenameText())) {
                    e.setCancelled(true);
                }
                else if (bannedMaterials.contains(e.getCurrentItem().getType())) {
                    e.setCancelled(true);
                }
                return;
            }
        }
    }

}
