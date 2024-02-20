package top.cmarco.aequitas.analysis;

import net.minecraft.network.ServerboundPacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import top.cmarco.aequitas.Aequitas;
import top.cmarco.aequitas.data.MovementManager;
import top.cmarco.aequitas.data.PlayerData;

/**
 * Handles incoming packets.
 * CLIENT -> SERVER
 */
public final class IncomingPacketAnalyser implements PlayerPacketAnalyser<ServerboundPacketListener> {

    /**
     * Interface method for processing packets.
     * Abstract.
     *
     * @param packet     The packet that is passed in the context.
     * @param playerData The player data.
     */
    @Override
    public void process(@NotNull final Packet<? extends ServerboundPacketListener> packet, @NotNull final PlayerData playerData) {
        final Aequitas aequitas = playerData.getAequitas();

        aequitas.runTaskSync(() -> {
            if (packet instanceof ServerboundMovePlayerPacket serverboundMovePlayerPacket) {
                final MovementManager movementManager = playerData.getMovementManager();
                final World world = playerData.getPlayer().getWorld();
                final boolean hasPos = serverboundMovePlayerPacket.hasPos;
                final double posX = serverboundMovePlayerPacket.x;
                final double posY = serverboundMovePlayerPacket.y;
                final double posZ = serverboundMovePlayerPacket.z;
                final boolean hasRot = serverboundMovePlayerPacket.hasRot;
                final double rotX = serverboundMovePlayerPacket.xRot;
                final double rotY = serverboundMovePlayerPacket.yRot;
                final boolean onGround = serverboundMovePlayerPacket.isOnGround();

                movementManager.handle(world, hasPos, posX, posY, posZ, hasRot, rotX, rotY, onGround);
            } else if (packet instanceof ServerboundMoveVehiclePacket serverboundMoveVehiclePacket) {


            }
        });
    }
}
