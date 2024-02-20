package top.cmarco.aequitas.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import top.cmarco.aequitas.Aequitas;
import top.cmarco.aequitas.config.AequitasConfig;

public class AequitasCommand implements CommandExecutor {

    private final Aequitas aequitas;
    private final AequitasConfig config ;

    public AequitasCommand(@NotNull final Aequitas aequitas) {
        this.aequitas = aequitas;
        this.config = aequitas.getAequitasConfig();
    }

    private static String color(final String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private  void missingPerm(final CommandSender sender) {
        sender.sendMessage(color(config.getMissingPermission()));
    }

    private void sendHelp(final CommandSender sender) {
        sender.sendMessage(color(config.getLogo() + " &aHelp Page"));
        sender.sendMessage(color("&7/&6aequitas help"));
        sender.sendMessage(color("&oEnable alerts for &nall&r&o players."));
        sender.sendMessage(color("&7/&6aequitas alerts"));
        sender.sendMessage(color("&oEnable alerts for one player only."));
        sender.sendMessage(color("&7/&6aequitas alerts &8<&eplayer&8>"));
        sender.sendMessage(color("&oEnable flags for one player only."));
        sender.sendMessage(color("&7/&6aequitas flags &8<&eplayer&8>"));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!commandSender.hasPermission("aequitas.commands")) {
            missingPerm(commandSender);
            return true;
        }

        if (strings.length == 0) {
            sendHelp(commandSender);
        }

        if (strings.length == 1) {

            if (strings[0].equalsIgnoreCase("alerts")) {

            } else {
                commandSender.sendMessage(color(config.getUnknownCommand()));
            }

        } else {
            commandSender.sendMessage(color(config.getUnknownCommand()));
        }

        return true;
    }
}
