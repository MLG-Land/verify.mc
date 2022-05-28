package net.mlgland.verifymc.commands;

import net.mlgland.verifymc.secrets.EncoderSecrets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

public class VerifyCommand implements CommandExecutor {

    private static String encodeURL(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

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
        String encodedParam = Base64.getEncoder().encodeToString(json.getBytes());
        String hash = null;
        try {
            String secret = EncoderSecrets.secret;
            String message = json;

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            hash = Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(message.getBytes()));
            System.out.println(hash);
        } catch (Exception e) {
            System.out.println("Error");
        }
        String query = encodedParam + "." + hash;
        String encodedQuery = encodeURL(query);


        String resultURL = baseURL + encodedQuery;
        player.sendMessage("Click this link to verify your discord account: " + resultURL);

        return true;

    }

}
