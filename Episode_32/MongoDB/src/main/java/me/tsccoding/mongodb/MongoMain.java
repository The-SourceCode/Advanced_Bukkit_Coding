package me.tsccoding.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class MongoMain extends JavaPlugin implements Listener {
    private MongoCollection<Document> collection;

    private HashMap<UUID,PlayerData> playerDataHashMap;

    @Override
    public void onEnable() {
        this.playerDataHashMap = new HashMap<>();
        this.getServer().getPluginManager().registerEvents(this, this);

        String uri = "mongodb://Admin:admin@mongodb-shard-00-00-altt9.mongodb.net:27017,mongodb-shard-00-01-altt9.mongodb.net:27017,mongodb-shard-00-02-altt9.mongodb.net:27017/admin?replicaSet=MongoDB-shard-0&ssl=true";
        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(clientURI);

        MongoDatabase mongoDatabase = mongoClient.getDatabase("MongoDB");
        collection = mongoDatabase.getCollection("Minecraft");

        this.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[MONGODB] Database Connected");
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Document playerdoc = new Document("UUID", player.getUniqueId().toString());
        Document found = (Document) collection.find(playerdoc).first();
        if (found == null){
            playerdoc.append("GOLD", 100);
            playerdoc.append("MAGIC", 400);
            collection.insertOne(playerdoc);
            playerDataHashMap.put(player.getUniqueId(), new PlayerData(player.getUniqueId().toString(),100,400));
            this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Player created in Database");
        }else{
            int gold = found.getInteger("GOLD");
            int magic = found.getInteger("MAGIC");
            playerDataHashMap.put(player.getUniqueId(), new PlayerData(player.getUniqueId().toString(),gold,magic));
            this.getServer().getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "Player found in Database");

        }
    }

}
