package top.cmarco.aequitas.chat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;

import java.awt.*;


public final class MessageUtils {

    public static final Color WARM_ORANGE = new Color(255, 166, 77);
    public static TextComponent LOGO = null;

    static {
        TextComponent open = new TextComponent(ChatColor.GRAY + "[");
        TextComponent logo = new TextComponent("Aequitas");
        logo.setColor(ChatColor.of(WARM_ORANGE));
        TextComponent close = new TextComponent(ChatColor.GRAY + "]" + ChatColor.RESET);
        LOGO = new TextComponent(open, logo, close);
    }

    public static void sendWithLogo(final CommandSender player, final String message) {
        final String coloured = ChatColor.translateAlternateColorCodes('&', message);
        player.spigot().sendMessage(LOGO, new TextComponent(coloured));
    }


}
