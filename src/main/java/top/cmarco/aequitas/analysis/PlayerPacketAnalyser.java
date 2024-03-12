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

package top.cmarco.aequitas.analysis;

import net.minecraft.network.protocol.Packet;
import org.jetbrains.annotations.NotNull;
import top.cmarco.aequitas.data.PlayerData;

/**
 * Abstract interface for incoming and outgoing packets.
 * @param <T> an NMS packet listener.
 */
public sealed interface PlayerPacketAnalyser<T> permits IncomingPacketAnalyser, OutgoingPacketAnalyser {

    /**
     * Interface method for processing packets.
     * Abstract.
     * @param packet The packet that is passed in the context.
     * @param playerData The player data.
     */
    void process(@NotNull final Packet<? extends T> packet, @NotNull final PlayerData playerData);
}
