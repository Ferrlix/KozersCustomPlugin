package prog.ferrlix.kozers.messages;

import net.kyori.adventure.text.format.TextColor;
import prog.ferrlix.kozers.Kozers;
import prog.ferrlix.kozers.util.ConfigUtil;

import java.awt.*;

public class Colors{
    static ConfigUtil config = Kozers.config;
    public static TextColor content(){return TextColor.color(96, 204, 255);}
    public static TextColor header(){return TextColor.color(59, 110, 255);}
    public static TextColor error(){return TextColor.color(255, 57, 77);}
    public static TextColor black(){return TextColor.color(0, 0, 0);}
    public static TextColor white(){return TextColor.color(255, 255, 255);}

    /**
     * the prefix color from config.yml
     * @return the TextColor result from the hex code at messages.prefix.color in config.yml
     */
    public static TextColor prefix(){
        String configColor;
        float[] rgb = {255,255,255};
        try {
            configColor = (String) config.getConfig().get("messages.prefix.color");
            assert configColor != null:"Prefix Color does not exist in config!";
            Color color = Color.decode(configColor);
            rgb = color.getRGBColorComponents(rgb); // this array can be null, method creates a new one
        } catch (Exception e) {
            Kozers.logger().warning("Plugin prefix color from config threw an exception! (It is probably invalid)");
            Kozers.logger().warning(e.getMessage());
            Kozers.logger().warning(e.getCause().getMessage());
            return white();
        }return TextColor.color(rgb[0],rgb[1],rgb[2]);
    }
}
