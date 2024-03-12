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

import net.minecraft.network.ClientboundPacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import org.jetbrains.annotations.NotNull;
import top.cmarco.aequitas.Aequitas;
import top.cmarco.aequitas.data.PlayerData;

/**
 * Analyses packets sent to the client.
 * SERVER -> CLIENT
 */
public final class OutgoingPacketAnalyser implements PlayerPacketAnalyser<ClientboundPacketListener> {


    /**
     * Interface method for processing packets.
     * Abstract.
     *
     * @param packet     The packet that is passed in the context.
     * @param playerData The player data.
     */
    @Override
    public void process(final @NotNull Packet<? extends ClientboundPacketListener> packet, @NotNull final PlayerData playerData) {
        final Aequitas aequitas = playerData.getAequitas();

        aequitas.runTaskSync(() -> {
            if (packet instanceof ClientboundPlayerPositionPacket outPosPacket) {
                final boolean matchId = outPosPacket.getId() == playerData.getPlayer().getEntityId();

                if (!matchId) {
                    return;
                }

                playerData.getVelocityData().addVelocityEntry(outPosPacket.getX(), outPosPacket.getY(), outPosPacket.getZ());
            } else if (packet instanceof ClientboundTeleportEntityPacket outTeleportPacket) {

            } else {

            }
        });
    }
}
