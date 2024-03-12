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
