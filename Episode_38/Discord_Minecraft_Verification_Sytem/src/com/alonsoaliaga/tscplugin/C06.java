package com.alonsoaliaga.tscplugin;

import com.alonsoaliaga.tscplugin.Trash.Token;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.*;

public class C06 extends ListenerAdapter implements CommandExecutor, Listener {
    public Main plugin;
    public HashMap<UUID,String>uuidCodeMap;
    public HashMap<UUID,String>uuidIdMap;
    public List<UUID>verifiedmembers;
    public Guild guild;
    JDA jda;
    public C06(Main main) {
        this.plugin = main;
        startBot();
        uuidCodeMap = new HashMap<>();
        uuidIdMap = new HashMap<>();
        verifiedmembers = new ArrayList<>();
        jda.addEventListener(this);
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        plugin.getCommand("verify").setExecutor(this);
        Bukkit.getScheduler().runTaskLater(plugin,()->guild = jda.getGuilds().get(0),100L);
    }
    private void startBot() {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken(Token.token).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(event.getAuthor().isBot()||event.getAuthor().isFake()||event.isWebhookMessage())return;
        String[] args = event.getMessage().getContentRaw().split(" "); //!link args args args
        if(args[0].equalsIgnoreCase("!link")){ //!link AlonsoAliaga
            if(event.getMember().getRoles().stream().filter(role -> role.getName().equals("Verified")).findAny().orElse(null) != null){
                event.getChannel().sendMessage(":x: **|** Error! "+event.getAuthor().getAsMention()+", you are already verified!").queue();
                return;
            }
			if(uuidIdMap.values().contains(event.getAuthor().getId())){
				event.getChannel().sendMessage(":x: **|** Error! "+event.getAuthor().getAsMention()+", you already have a code generated!").queue();
				return;
			}
            if(args.length!=2){
                event.getChannel().sendMessage(":x: **|** Error! You need to specify a player!").queue();
                return;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if(target==null){
                event.getChannel().sendMessage(":x: **|** Error! The player is not online!").queue();
                return;
            }
            String randomcode = new Random().nextInt(800000)+200000+"AA"; //6581446AA
            uuidCodeMap.put(target.getUniqueId(),randomcode);
            uuidIdMap.put(target.getUniqueId(),event.getAuthor().getId());
            event.getAuthor().openPrivateChannel().complete().sendMessage("Hey! Your verification has been generated!\n" +
                    "Use this command in game: ``/verify "+randomcode+"``").queue();
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(plugin.playerData.contains("Data."+e.getPlayer().getUniqueId().toString())){
            verifiedmembers.add(e.getPlayer().getUniqueId());
        }
    }
    @EventHandler //This is to remove the player from all lists and maps when he leaves the server.
    public void onQuit(PlayerQuitEvent e){
        verifiedmembers.remove(e.getPlayer().getUniqueId());
        uuidCodeMap.remove(e.getPlayer().getUniqueId());
        uuidIdMap.remove(e.getPlayer().getUniqueId());
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) { //  /verify randomcodeAA
        if(!(sender instanceof Player)){
            sender.sendMessage("§cOnly players can execute this command!");
            return true;
        }
        Player player = (Player) sender;
        if(plugin.playerData.contains("Data."+player.getUniqueId().toString())){
            player.sendMessage("§cSorry! You are already verified!");
            return true;
        }
        if(!uuidCodeMap.containsKey(player.getUniqueId())){
            player.sendMessage("§cNot pending verification process!");
            return true;
        }
        if(args.length!=1){
            player.sendMessage("§cUsage: /verify [code]");
            return true;
        }
        String actualcode = uuidCodeMap.get(player.getUniqueId());
        if(!actualcode.equals(args[0])){
            player.sendMessage("§cCode is not valid! Check again!");
            return true;
        }
        String discordid = uuidIdMap.get(player.getUniqueId());
        Member target = guild.getMemberById(discordid);
        if(target==null){
            uuidCodeMap.remove(player.getUniqueId());
            uuidIdMap.remove(player.getUniqueId());
            player.sendMessage("§cError! It seems that you left our Discord server!");
            return true;
        }
        plugin.playerData.set("Data."+player.getUniqueId().toString(),discordid);
        try {
            plugin.playerData.save(plugin.data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        uuidCodeMap.remove(player.getUniqueId());
        uuidIdMap.remove(player.getUniqueId());
        verifiedmembers.add(player.getUniqueId());
        Role verifiedrole = guild.getRolesByName("Verified",false).get(0);
        guild.getController().addSingleRoleToMember(target,verifiedrole).queue();
        target.getUser().openPrivateChannel().complete().sendMessage(":white_check_mark: **|** Verification successfully, you have linked your account with Mc account: "+player.getName()).queue();
        player.sendMessage("§aYou have been verified correctly! You linked your account with member: "+target.getUser().getName()+"#"+target.getUser().getDiscriminator());
        return true;
    }
}