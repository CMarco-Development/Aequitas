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

package top.cmarco.aequitas.checks.types;

import org.jetbrains.annotations.NotNull;
import top.cmarco.aequitas.checks.Check;
import top.cmarco.aequitas.data.PlayerData;
import top.cmarco.aequitas.data.containers.MovementContainer;

/**
 * An abstract class representing a movement-related anticheat check.
 * This class extends the generic Check class with a specific type parameter of MovementContainer.
 *
 * @param <MovementContainer> The type of data container for movement-related information.
 */
public abstract class MovementCheck extends Check<MovementContainer> {

    /**
     * Constructs a new MovementCheck with the specified player data.
     *
     * @param playerData The player data associated with this movement check.
     */
    public MovementCheck(@NotNull final PlayerData playerData) {
        super(playerData);
    }

    /**
     * Process the movement-related data to detect cheats.
     *
     * @param data The container holding movement-related information for the check.
     */
    @Override
    public abstract void process(@NotNull final MovementContainer data);

}
