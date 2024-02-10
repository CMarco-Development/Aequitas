package top.cmarco.aequitas.data;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Stairs;
import org.bukkit.material.Step;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;
import top.cmarco.aequitas.checks.Check;
import top.cmarco.aequitas.checks.types.MovementCheck;
import top.cmarco.aequitas.data.containers.MovementContainer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.function.Predicate;

/**
 * The MovementManager class is responsible for managing and tracking player movement.
 * It maintains the last known positions of the player in the world.
 */
@RequiredArgsConstructor
@Getter
public final class MovementManager {

    /**
     * The player data associated with this MovementManager.
     */
    private final PlayerData playerData;

    /**
     * The last known X-coordinate of the player's position.
     */
    private double lastPosX = 0d;

    /**
     * The last known Y-coordinate of the player's position.
     */
    private double lastPosY = 0d;

    /**
     * The last known Z-coordinate of the player's position.
     */
    private double lastPosZ = 0d;

    /**
     * The last known X-Rotation of the player view axis.
     */
    private double lastRotX = 0d;

    /**
     * The last known Y-Rotation of the player view axis.
     */
    private double lastRotY = 0d;

    private boolean touchingAir = false;
    private boolean touchingLiquid = false;
    private boolean touchingClimbable = false;
    private boolean touchingHalfBlock = false;
    private boolean touchingIllegalBlock = false;
    private LivingEntity[] nearbyEntities;

    /**
     * Updates the last known player position and handles other relevant information.
     *
     * @param world     The world in which the player is located.
     * @param posX      The X-coordinate of the player's current position.
     * @param posY      The Y-coordinate of the player's current position.
     * @param posZ      The Z-coordinate of the player's current position.
     * @param onGround  A boolean indicating whether the player is currently on the ground.
     */
    public void handle(final World world,
                       final boolean hasPos,
                       final double posX, final double posY, final double posZ,
                       final boolean hasRot,
                       final double rotX, final double rotY,
                       final boolean onGround) {
        // Handling logic
        final Location from = new Location(world, this.lastPosX, this.lastPosY, this.lastPosZ);
        final Location to = new Location(world, posX, posY, posZ);
        final MovementContainer movement = new MovementContainer(from, to, onGround);
        final BoundingBox boundingBox = new BoundingBox(posX, posY, posZ, posX, posY, posZ);

        playerData.setLastMovement(movement);

        if (from.distanceSquared(to) == 0d) {   // Check if player has moved
            return;
        }

        // Make sure the player isn't flying and he isn't in a vehicle
        if (playerData.getPlayer().isInsideVehicle() || playerData.getPlayer().getAllowFlight()) {
            return;
        }

        handleCollisions(boundingBox, world);

        for (final Check<?> check : playerData.getCheckManager().getChecks()) {
            if (check instanceof MovementCheck movementCheck) {
                movementCheck.process(movement);
            }
        }

        if (hasPos) {   // Update the last known player position
            this.lastPosX = posX;
            this.lastPosY = posY;
            this.lastPosZ = posZ;
        }

        if (hasRot) {   // Update last known rotation
            this.lastRotX = rotX;
            this.lastRotY = rotY;
        }
    }

    private static boolean checkBlocks(@NotNull final BoundingBox boundingBox, @NotNull final World world,
                                       @NotNull final Predicate<Material> predicate) {
        final int first = (int) Math.floor(boundingBox.getMinX());
        final int second = (int) Math.ceil(boundingBox.getMaxX());
        final int third = (int) Math.floor(boundingBox.getMinY());
        final int forth = (int) Math.ceil(boundingBox.getMaxY());
        final int fifth = (int) Math.floor(boundingBox.getMinZ());
        final int sixth = (int) Math.ceil(boundingBox.getMaxZ());

        for (int i = first; i < second; ++i) {
            for (int j = third; j < forth; ++j) {
                for (int k = fifth; k < sixth; ++k) {
                    final Block block = world.getBlockAt(i, j, k);
                    if (!predicate.test(block.getType())) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private static Collection<Material> STAIRS = null;
    private static Collection<Material> SLABS = null;

    static {
        HashSet<Material> tempStairs = new HashSet<>(0x1F);
        HashSet<Material> tempSlabs = new HashSet<>(0x16);
        for (final Material material : Material.values()) {
            if (material.name().endsWith("_STAIRS")) {
                tempStairs.add(material);
            } else if (material.name().endsWith("_SLAB")) {
                tempSlabs.add(material);
            }
        }

        STAIRS = EnumSet.copyOf(tempStairs);
        SLABS = EnumSet.copyOf(tempSlabs);
        tempStairs = null;
        tempSlabs = null;
    }

    private static final Predicate<Material> IS_AIR = (Material::isAir);
    private static final Predicate<Material> IS_LIQUID = (m -> m == Material.WATER || m == Material.LAVA);
    private static final Predicate<Material> IS_CLIMB = (m -> STAIRS.contains(m) || SLABS.contains(m));
    private static final Predicate<Material> IS_LADDER = (m -> m == Material.LADDER);
    private static final Predicate<Material> IS_STRANGE = (m -> m == Material.BREWING_STAND || m == Material.LILY_PAD);

    private synchronized void handleCollisions(@NotNull final BoundingBox boundingBox, @NotNull final World world) {
        boundingBox.expand(0.500d, 0.070d, 0.500d).shift(0.0d, -0.550d, 0.0d);

        final boolean touchingAir = checkBlocks(boundingBox, world, IS_AIR);
        final boolean touchingLiquid = checkBlocks(boundingBox, world, IS_LIQUID);
        final boolean touchingHalfBlock = checkBlocks(boundingBox, world, IS_CLIMB);
        final boolean touchingClimbable = checkBlocks(boundingBox, world, IS_LADDER);
        final boolean touchingIllegalBlock = checkBlocks(boundingBox, world, IS_STRANGE);

        this.touchingAir = (touchingAir && !touchingIllegalBlock);
        this.touchingLiquid = (touchingLiquid);
        this.touchingHalfBlock = (touchingHalfBlock);
        this.touchingClimbable = (touchingClimbable);
        this.touchingIllegalBlock = (touchingIllegalBlock);
    }
}
