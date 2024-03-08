package prog.ferrlix.kozers;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import prog.ferrlix.kozers.commands.*;
import prog.ferrlix.kozers.events.*;
import prog.ferrlix.kozers.util.ConfigUtil;

import java.util.logging.Logger;

/**
 * Main plugin class
 * technically a singleton because bukkit only allows one instance, and you cannot instantiate more
 * this allows static fields like `instance` to exist, otherwise it would get overwritten every time the class was instantiated
 */
public final class Kozers extends JavaPlugin {
    public static Kozers instance;
    public static Plugin plugin;
    public static Logger logger;
    public static boolean debug = true;
    ConfigUtil config;
    public Kozers(){
        instance = this;}
    @Override
    public void onEnable() {
        // Plugin startup logic
        Kozers.logger.info("Hello, Kozers is enabling!");
        instantiateStaticVariables();
        config.load(this,"config.yml");
        registerCommands();
        registerEvents();
        //should split the chat event and the command / things into seperate classes
        Kozers.logger.info("Kozers fully enabled!");
    }
    public void reload(){
        logger.info("Kozers Custom Plugin Reloading!");
        onDisable();
        onEnable();
        logger.info("Kozers Custom Plugin Successfully Reloaded!");
    }
    //this exists to update static variables upon reload, because I cannot re-instantiate the class and use static{}
    void instantiateStaticVariables(){
        plugin = this;
        logger = this.getLogger();
        config = ConfigUtil.getInstance(this,"config.yml");
        debug = (boolean) config.getConfig().get("settings.debug", debug);
    }
    void registerCommands(){
        new TownyChatCommands().getNationCommand().register();
        new TownyChatCommands().getTownCommand().register();
        new MainCommand().getMainCommand().register();
    }
    void registerEvents(){
        getServer().getPluginManager().registerEvents(new AsyncChatEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitEvent(), this);
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Kozers.logger.info("Kozers is Disabling!");
        Kozers.logger.info("Kozers disabled, goodbye!");
    }
}
