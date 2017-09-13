package me.tsccoding.pretutorial;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Updater extends JavaPlugin implements Listener {

    private String key = "key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=";

    @Override
    public void onEnable() {
        versionChecker();
    }

    private void versionChecker() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php").openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.getOutputStream().write((key + 34743).getBytes("UTF-8"));
            String version = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
            if (!version.equals("1.4")) {
                this.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "You do not have the most updated version of BetterBags");
            } else {
                this.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "BetterBags is fully updated");
            }
        } catch (IOException e) {
            this.getServer().getConsoleSender().sendMessage(ChatColor.RED + "ERROR: Could not make connection to SpigotMC.org");
            e.printStackTrace();
        }
    }


}
