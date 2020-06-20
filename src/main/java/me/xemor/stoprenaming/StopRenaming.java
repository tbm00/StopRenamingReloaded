package me.xemor.stoprenaming;

import me.xemor.stoprenaming.commands.StopRenamingCommand;
import me.xemor.stoprenaming.events.Rename;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class StopRenaming extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();
        FileConfiguration fileConfiguration = this.getConfig();
        Rename rename = new Rename(fileConfiguration, this);
        this.getServer().getPluginManager().registerEvents(rename, this);
        StopRenamingCommand stopRenamingCommand = new StopRenamingCommand(rename);
        PluginCommand stopRenaming = this.getCommand("stoprenaming");
        stopRenaming.setTabCompleter(stopRenamingCommand);
        stopRenaming.setExecutor(stopRenamingCommand);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
