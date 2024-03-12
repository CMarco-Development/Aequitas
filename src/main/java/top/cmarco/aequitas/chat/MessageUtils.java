/*
 *     Aequitas - Anticheat for SpigotMC Servers
 *     Copyright © 2024 CMarco
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
