package top.cmarco.aequitas.network;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.ClientboundPacketListener;
import net.minecraft.network.ServerboundPacketListener;
import net.minecraft.network.protocol.Packet;
import top.cmarco.aequitas.Aequitas;
import top.cmarco.aequitas.data.PlayerData;

@RequiredArgsConstructor
public final class AequitasChannel extends ChannelDuplexHandler {

    private final PlayerData playerData;
    private final Aequitas aequitas;

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
