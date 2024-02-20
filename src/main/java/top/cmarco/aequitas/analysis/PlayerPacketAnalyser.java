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
