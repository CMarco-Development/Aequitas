package top.cmarco.aequitas.checks.implementations.jesus;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import top.cmarco.aequitas.checks.CheckInfo;
import top.cmarco.aequitas.checks.types.MovementCheck;
import top.cmarco.aequitas.data.PlayerData;
import top.cmarco.aequitas.data.containers.MovementContainer;

@CheckInfo(name = "Jesus (B)", threshold = 0xA)
public final class JesusB extends MovementCheck {


    /**
     * Constructs a new MovementCheck with the specified player data.
     *
     * @param playerData The player data associated with this movement check.
     */
    public JesusB(@NotNull PlayerData playerData) {
        super(playerData);
    }

    /**
     * Process the movement-related data to detect cheats.
     *
     * @param data The container holding movement-related information for the check.
     */
    @Override
    public void process(@NotNull MovementContainer data) {
        final Location from = data.getFrom();
        final Location to = data.getTo();
        final Vector deltaVector = to.subtract(from).toVector();

        final double threshold = 0.0030d;
        final double sqdDistance = deltaVector.lengthSquared();
        final boolean absentMovement = (sqdDistance <= threshold);
        final boolean isTouchingWater = super.getPlayerData().getMovementManager().isTouchingLiquid();

        //getAequitas().getLogger().info("distance² = " + sqdDistance);
        //getAequitas().getLogger().info("absentMovmt = " + absentMovement + " - isTouchingWater =" + isTouchingWater + " - isGround = " + data.isOnGround());

        if (absentMovement && isTouchingWater && data.isOnGround()) {
            super.fail();
        }

    }
}
