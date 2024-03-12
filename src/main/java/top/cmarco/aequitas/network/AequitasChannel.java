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

package top.cmarco.aequitas.network;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.ClientboundPacketListener;
import net.minecraft.network.ServerboundPacketListener;
import net.minecraft.network.protocol.Packet;
import org.jetbrains.annotations.NotNull;
import top.cmarco.aequitas.Aequitas;
import top.cmarco.aequitas.data.PlayerData;


public final class AequitasChannel extends ChannelDuplexHandler {

    private final PlayerData playerData;
    private final Aequitas aequitas;

    public AequitasChannel(@NotNull final PlayerData playerData, @NotNull final Aequitas aequitas) {
        this.playerData = playerData;
        this.aequitas = aequitas;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);

        try {
            // This casting should always be safe under SpigotMC Server implementation.
            final Packet<ClientboundPacketListener> packet = (Packet<ClientboundPacketListener>) msg;
            this.aequitas.getAnalysisManager().getOutgoingPacketAnalyser().process(packet, playerData);
        }
        catch (final Throwable throwable) {
            this.aequitas.getLogger().warning("ERROR! During Packet Writing.");
            this.aequitas.getLogger().warning(throwable.getLocalizedMessage());
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);

        try {
            // This casting should always be safe under SpigotMC Server implementation.
            final Packet<ServerboundPacketListener> packet = (Packet<ServerboundPacketListener>) msg;
            this.aequitas.getAnalysisManager().getIncomingPacketAnalyser().process(packet, this.playerData);
        }
        catch (final Throwable throwable) {
            this.aequitas.getLogger().warning("ERROR! During Packet Reading.");
            this.aequitas.getLogger().warning(throwable.getLocalizedMessage());
        }
    }
}
