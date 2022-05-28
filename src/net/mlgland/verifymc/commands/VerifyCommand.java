package net.mlgland.verifymc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.Base64;

public class VerifyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player;

        if (sender instanceof Player) {
            player = (Player) sender;
        } else {
            System.out.println("[VerifyMC] [Warning] Sysadmin attempted to run player-only command on server console");
            return true;
        }

        String baseURL = "http://api.mlgland.net/accounts/linkdiscord/link?info=";
        String playerName = player.getName();
        String playerUUID = String.valueOf(player.getUniqueId());
        String timestamp = String.valueOf(Instant.now().getEpochSecond());
        String combined = playerName + "," + playerUUID + "," + timestamp;
        String encodedParam = Base64.getEncoder().encodeToString(combined.getBytes());
        String resultURL = baseURL + encodedParam;
        player.sendMessage("Click this link to verify you discord account: " + resultURL);

        return true;

    }

}
