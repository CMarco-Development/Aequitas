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

/**
 * The ActionsManager class represents a manager for various player actions.
 * It tracks different actions such as placing, attacking, teleporting, and digging.
 */
public final class ActionsManager {

    /**
     * The player data associated with this ActionsManager.
     */
    private final PlayerData playerData;

    /**
     * Indicates whether the player is currently placing blocks.
     */
    private final boolean placing = false;

    /**
     * Indicates whether the player is currently attacking entities.
     */
    private final boolean attacking = false;

    /**
     * Indicates whether the player has recently teleported.
     */
    private final boolean teleported = false;

    /**
     * Indicates whether the player is currently digging blocks.
     */
    private final boolean digging = false;

    public ActionsManager(PlayerData playerData) {
        this.playerData = playerData;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public boolean isPlacing() {
        return placing;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public boolean isTeleported() {
        return teleported;
    }

    public boolean isDigging() {
        return digging;
    }
}