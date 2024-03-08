package prog.ferrlix.kozers.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import prog.ferrlix.kozers.Kozers;

import java.util.ArrayList;
import java.util.Arrays;

import static net.kyori.adventure.text.Component.text;


/**
 * Utilities commonly used in strings
 */
public class MessageUtil {
    /**
     * Should be the only MiniMessage.Builder instance
     */
    public static MiniMessage.Builder miniMessageBuilder = MiniMessage.builder();

    /**
     * old method made by Ferrlix to manually parse a String, incase I dont want to use MiniMessage
     * @param input String to parse
     * @return Component
     */
    public static Component legacystringToComponent(String input){
        TextComponent[] component = {text().build()};
        ArrayList<String> colorSplit = new ArrayList<>();
        colorSplit.addAll(Arrays.asList(input.split("[<>]")));
        //Kozers.logger().info("Strings will follow...");
        TextColor[] color = {TextColor.color(255, 255, 255)}; //default color in-case it doesn't start with one
        colorSplit.forEach(s -> {
            if (!s.isBlank()){
                if (s.matches("^#[A-Fa-f0-9]{6}$")){
                    color[0] = StringUtil.hexToTextColor(s);
                }else{
                    component[0] = component[0].append(text(s).color(color[0]));
                }
            }
        });
        Component finalComponent = text().append(component[0]).build();
        return finalComponent;
    }

    /**
     * use MiniMessage to deserialize a String
     * @param input String to deserialize
     * @return deserialized Component
     */
    public static Component stringToComponent(String input){
        Component component = text().append(text("Invalid String input")).build();
        try{
            component = MiniMessage.miniMessage().deserialize(input);
        }catch(Exception e){
            e.printStackTrace();
        }
        return component;
    }
}
