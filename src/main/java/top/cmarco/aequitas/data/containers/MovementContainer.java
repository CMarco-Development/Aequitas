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

package top.cmarco.aequitas.data.containers;

import org.bukkit.Location;

/**
 * The MovementContainer class serves as a container for movement-related information
 * from one location to another, along with an indication of whether the entity is on the ground.
 */
public final class MovementContainer {

    public MovementContainer(final Location from, final Location to, final boolean isOnGround) {
        this.from = from;
        this.to = to;
        this.isOnGround = isOnGround;
    }

    /**
     * The starting location of the movement.
     */
    private final Location from;

    /**
     * The ending location of the movement.
     */
    private final Location to;

    /**
     * A boolean indicating whether the entity is on the ground during the movement.
     */
    private final boolean isOnGround;

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public boolean isOnGround() {
        return isOnGround;
    }
}