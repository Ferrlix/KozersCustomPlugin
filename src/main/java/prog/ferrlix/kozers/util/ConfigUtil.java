package prog.ferrlix.kozers.util;

import org.apache.commons.io.IOUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import prog.ferrlix.kozers.Kozers;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configuration utilities
 */
public class ConfigUtil {
    private File file;
    private FileConfiguration config;
    public ConfigUtil(Plugin plugin, String path){
        this(plugin.getDataFolder().getAbsolutePath() + "/" + path);
    }
    public ConfigUtil(String path){
        register(path);
    }
    public boolean save(){
        try {
            this.config.save(this.file);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    protected void register(String path){
        try{
        this.file = new File(path);
        if (this.file.length() == 0){writeInputStreamToFile(Kozers.plugin().getResource("config.yml"), this.file);}
        this.config = YamlConfiguration.loadConfiguration(this.file);
        matchConfig();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void load(Plugin plugin, String path){
        register(plugin.getDataFolder().getAbsolutePath() + "/" + path);
    }
    public File getFile(){return this.file;}
    public FileConfiguration getConfig(){return this.config;}
    public static ConfigUtil getDefaultConfig(){
        return new ConfigUtil(Kozers.plugin(), "config.yml");
    }
    public void matchConfig() {
        try {
            InputStream is = Kozers.plugin().getResource(file.getName());
            if (is != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(file);
                for (String key : defConfig.getConfigurationSection("").getKeys(false))
                    if (!config.contains(key)) config.set(key, defConfig.getConfigurationSection(key));

                for (String key : config.getConfigurationSection("").getKeys(false))
                    if (!defConfig.contains(key)) config.set(key, null);

                config.save(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void writeInputStreamToFile(InputStream inputStream, File file){
        try(OutputStream outputStream = new FileOutputStream(file)){
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            // handle exception here
        }
    }
}
