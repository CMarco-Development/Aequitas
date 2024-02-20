package top.cmarco.aequitas.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import top.cmarco.aequitas.Aequitas;

public final class AequitasConfig {

    private final Aequitas aequitas;
    private FileConfiguration configuration;

    public AequitasConfig(@NotNull final Aequitas aequitas) {
        this.aequitas = aequitas;
        aequitas.saveDefaultConfig();
        this.configuration = aequitas.getConfig();
    }

    public String formatLogo(@NotNull final String text) {
        return text.replace("{LOGO}", this.getLogo());
    }

    private String getStringFormatFromAddr(@NotNull final  String yamlAddr) {
        return this.formatLogo(this.configuration.getString(yamlAddr));
    }

    public String getLogo() {
        return this.configuration.getString("messages.logo");
    }

    public String getMissingPermission() {
        return this.getStringFormatFromAddr("messages.missing-permission");
    }

    public String getUnknownCommand() {
        return this.getStringFormatFromAddr("messages.unknown-command");
    }
}
