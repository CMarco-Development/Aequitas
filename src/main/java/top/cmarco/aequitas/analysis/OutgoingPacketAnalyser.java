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
