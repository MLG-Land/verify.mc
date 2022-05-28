package net.mlgland.verifymc;

import net.mlgland.verifymc.commands.VerifyCommand;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class VerifyMC extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("verify").setExecutor(new VerifyCommand());

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[VerifyMC] Plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[VerifyMC] Plugin has been disabled!");
    }

}
