package prog.ferrlix.kozers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import prog.ferrlix.kozers.commands.MainCommand;
import prog.ferrlix.kozers.events.AsyncChatEvent;
import prog.ferrlix.kozers.events.PlayerQuitEvent;
import prog.ferrlix.kozers.integration.towny.chat.TownyChat;
import prog.ferrlix.kozers.util.ConfigUtil;

import java.util.logging.Logger;

/**
 * Main plugin class
 */
public final class Kozers extends JavaPlugin {
    /**
     * public instance of the plugin accessible anywhere
     */
    public static Kozers instance;
    public static TownyChat townyChat;
    public static AsyncChatEvent chatEvent;
    public static PlayerQuitEvent quitEvent;
    public static ConfigUtil config;
    public Kozers(){instance = this;}
    public static Logger logger(){return plugin().getLogger();}
    public static Plugin plugin(){return Bukkit.getServer().getPluginManager().getPlugin("Kozers");}
    @Override
    public void onEnable() {
        // Plugin startup logic
        Kozers.logger().info("Hello, Kozers is enabling!");
        instantiateStaticVariables();
        config.load(this,"config.yml");
        registerCommands();
        registerEvents();
        //should split the chat event and the command / things into seperate classes
        Kozers.logger().info("Kozers fully enabled!");
    }
    public void reload(){
        this.getLogger().info("Kozers Custom Plugin Reloading!");
        onDisable();
        onEnable();
        this.getLogger().info("Kozers Custom Plugin Successfully Reloaded!");
    }
    private void instantiateStaticVariables(){
        config = new ConfigUtil(this,"config.yml");
        townyChat = new TownyChat();
        quitEvent = new PlayerQuitEvent();
        chatEvent = new AsyncChatEvent();
    }
    private void registerCommands(){
        townyChat.getNationCommand().register();
        townyChat.getTownCommand().register();
        new MainCommand().getMainCommand().register();
    }
    private void registerEvents(){
        getServer().getPluginManager().registerEvents(chatEvent, this);
        getServer().getPluginManager().registerEvents(quitEvent, this);
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Kozers.logger().info("Kozers is Disabling!");
        Kozers.logger().info("Kozers disabled, goodbye!");
    }
}
