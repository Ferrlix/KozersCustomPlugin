package prog.ferrlix.kozers.util;

import org.apache.commons.io.IOUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import prog.ferrlix.kozers.Kozers;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Singletons Class for Configuration Utilities
 * Each file has its own instance, which are managed in ConfigUtilInstanceHelper
 */
public class ConfigUtil {
    private File file;
    private FileConfiguration config;
    ConfigUtil(String path){
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
    private static Map<String,ConfigUtil> soleInstances = null;

    /**
     * Get the instance from the instance map
     * every file has one instance for itself and no more
     * @param plugin this plugin instance, from static field in main class
     * @param fileName name of file in the plugin folder
     * @return The configuration
     */
    public static synchronized ConfigUtil getInstance(Plugin plugin,String fileName){
        String path = plugin.getDataFolder().getAbsolutePath() + "/" + fileName;
        if (!new File(path).exists()){
            Kozers.logger.severe("Path in data folder %s does not exist!".formatted(path));
        }
        ConfigUtil instance = soleInstances.get(path);
        if (instance == null)
            soleInstances.put(path, new ConfigUtil(path));
        instance.register(path);
        return instance;
    }
    protected void register(String path){
        try{
            File tmpFile = new File(path);
            if (tmpFile.length() == 0){
                String[] paths = path.split("/");
                path = paths[paths.length - 1];
                InputStream resource = Kozers.plugin.getResource(path);
                if (resource != null){
                    writeInputStreamToFile(Kozers.plugin.getResource(path), tmpFile);}}
            this.file = tmpFile;
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
    public void matchConfig() {
        try {
            InputStream is = Kozers.plugin.getResource(file.getName());
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
