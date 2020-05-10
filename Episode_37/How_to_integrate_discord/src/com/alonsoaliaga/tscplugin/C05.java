package com.alonsoaliaga.tscplugin;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import javax.security.auth.login.LoginException;

public class C05 extends ListenerAdapter implements Listener {
    public Main plugin;
    public JDA jda;
    public C05(Main main) {
        this.plugin = main;
        startBot();
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        jda.addEventListener(this);
    }
    private void startBot() {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken(plugin.getConfig().getString("Bot.Token")).buildBlocking();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent e){
        String message = e.getMessage();
        TextChannel textChannel = jda.getTextChannelsByName("general-tsc",true).get(0);
        textChannel.sendMessage("**"+e.getPlayer().getName()+":** "+message).queue();
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(event.getAuthor().isBot() || event.getAuthor().isFake() || event.isWebhookMessage())return;
        String message = event.getMessage().getContentRaw();
        User user = event.getAuthor();
        Bukkit.broadcastMessage("§9§l"+user.getName()+"#"+user.getDiscriminator()+": §e"+message);
    }
}
