package top.cmarco.aequitas.checks.implementations.jesus;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import top.cmarco.aequitas.checks.CheckInfo;
import top.cmarco.aequitas.checks.types.MovementCheck;
import top.cmarco.aequitas.data.PlayerData;
import top.cmarco.aequitas.data.containers.MovementContainer;

/**
 * Speed check implementation.
 * Type A
 */
@CheckInfo(name = "Jesus (A)", threshold = 0xA)
public final class JesusA extends MovementCheck {

    /**
     * Constructs a new MovementCheck with the specified player data.
     *
     * @param playerData The player data associated with this movement check.
     */
    public JesusA(@NotNull final PlayerData playerData) {
        super(playerData);
    }

    /**
     * Process the movement-related data to detect cheats.
     *
     * @param data The container holding movement-related information for the check.
     */
    @Override
    public void process(@NotNull final MovementContainer data) {
        final Location from = data.getFrom();
        final Location to = data.getTo();
        final Vector deltaVector = to.subtract(from).toVector();

        final double epsilon = 0.0002d; // Movement tolerance ε

        final boolean checkEpsilonX = Math.abs(deltaVector.getX() % 1.0d) < epsilon;
        final boolean checkEpsilonZ = Math.abs(deltaVector.getZ() % 1.0d) < epsilon;
        final boolean stationary = checkEpsilonX && checkEpsilonZ;
        final boolean isOnGround = data.isOnGround();
        final boolean isTouchingWater = super.getPlayerData().getMovementManager().isTouchingLiquid();
        //final boolean checkYMovement = Math.abs(deltaVector.getY()) > 0.0d;

        /*
        final String debug = String.format("Stationary:%b - TouchingWater:%s - OnGround:%b - CheckY:%b",
                stationary, isTouchingWater, isOnGround, checkYMovement);
        final String debug2 = String.format("DeltaVector: %.1f %.1f %.1f", deltaVector.getX(), deltaVector.getY(), deltaVector.getZ());

        getAequitas().getLogger().info(debug);
        getAequitas().getLogger().info(debug2);

         */

        if (!stationary && isTouchingWater && isOnGround) {
            final double planarDistance = (deltaVector.getX() * deltaVector.getX()) + (deltaVector.getZ()*deltaVector.getZ());
            if (planarDistance > 0.008d) {
                super.fail();
            }
        }

    }
}
