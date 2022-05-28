package net.mlgland.verifymc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
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
        String json = "{\"username\":\"" + playerName + "\",\"uuid\":\"" + playerUUID + "\",\"time\":\"" + timestamp + "\"" + "}";
//        String encodedParam = Base64.getEncoder().encodeToString(json.getBytes());
        String hash = null;
        try {
            String secret = "why are you trying to hack us?";
            String message = json;

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            hash = Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(message.getBytes()));
            System.out.println(hash);
        } catch (Exception e) {
            System.out.println("Error");
        }
        String resultURL = baseURL + hash;
        player.sendMessage("Click this link to verify your discord account: " + resultURL);

        return true;

    }

}
