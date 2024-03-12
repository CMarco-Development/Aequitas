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

package top.cmarco.aequitas.data;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.cmarco.aequitas.Aequitas;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages player data, providing methods to retrieve, remove, and get all player data entries.
 */
public final class PlayerDataManager {

    private final Aequitas aequitas;

    /**
     * Map to store player data with UUID as the key.
     */
    @NotNull
    private final Map<UUID, PlayerData> playerDataMap = new ConcurrentHashMap<>();

    public PlayerDataManager(@NotNull final Aequitas aequitas) {
        this.aequitas = aequitas;
    }

    /**
     * Retrieves player data for the given player. Creates a new entry if not present.
     *
     * @param player The player for whom to retrieve the data.
     * @return The player data associated with the player.
     */
    @NotNull
    public PlayerData getData(@NotNull final Player player) {
        return this.playerDataMap.computeIfAbsent(player.getUniqueId(), uuid -> new PlayerData(player, this.aequitas));
    }

    /**
     * Removes player data for the given player.
     *
     * @param player The player for whom to remove the data.
     * @return The removed player data, or null if not present.
     */
    @Nullable
    public PlayerData remove(@NotNull final Player player) {
        return this.playerDataMap.remove(player.getUniqueId());
    }

    /**
     * Retrieves all player data entries.
     *
     * @return Collection of all player data entries.
     */
    @NotNull
    public Collection<PlayerData> getEntries() {
        return this.playerDataMap.values();
    }
}
