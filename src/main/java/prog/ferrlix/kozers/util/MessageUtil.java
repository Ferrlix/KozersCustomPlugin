package prog.ferrlix.kozers.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
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
    public static MiniMessage.Builder builder = MiniMessage.builder();
    /**
     * old method made to manually parse a String, in-case I don't want to use MiniMessage
     * @param input String to parse
     * @return Component
     */
    public static Component legacystringToComponent(String input){
        TextComponent[] component = {text().build()};
        ArrayList<String> colorSplit = new ArrayList<>();
        colorSplit.addAll(Arrays.asList(input.split("[<>]")));
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
        return text().append(component[0]).build();
    }
    /**
     * use MiniMessage to deserialize a String
     * @param input String to deserialize
     * @return deserialized Component
     */
    public static @NotNull Component stringToComponent(String input){
        return MiniMessage.miniMessage().deserialize(input);
    }
}
