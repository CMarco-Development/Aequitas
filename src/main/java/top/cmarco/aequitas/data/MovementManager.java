package top.cmarco.aequitas.data;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;
import top.cmarco.aequitas.checks.Check;
import top.cmarco.aequitas.checks.types.MovementCheck;
import top.cmarco.aequitas.data.containers.MovementContainer;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.function.Predicate;

/**
 * The MovementManager class is responsible for managing and tracking player movement.
 * It maintains the last known positions of the player in the world.
 */
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
    private Entity[] nearbyEntities;
    private BoundingBox lastBoundingBox = null;

    public MovementManager(PlayerData playerData) {
        this.playerData = playerData;
    }

    /**
     * Updates the last known player position and handles other relevant information.
     *
     * @param world     The world in which the player is located.
     * @param posX      The X-coordinate of the player's current position.
     * @param posY      The Y-coordinate of the player's current position.
     * @param posZ      The Z-coordinate of the player's current position.
     * @param onGround  A boolean indicating whether the player is currently on the ground.
     */
    synchronized public void handle(final World world, final boolean hasPos, final double posX, final double posY, final double posZ, final boolean hasRot, final double rotX, final double rotY, final boolean onGround) {
        // Handling logic
        final Location from = new Location(world, this.lastPosX, this.lastPosY, this.lastPosZ);
        final Location to = new Location(world, posX, posY, posZ);

        final MovementContainer movement = new MovementContainer(from, to, onGround);
        final BoundingBox boundingBox = new BoundingBox(posX, posY, posZ, posX, posY, posZ);

        playerData.setLastMovement(movement);

        //if (from.distanceSquared(to) == 0d) {   // Check if player has moved
         //   return;
        //}

        // Make sure the player isn't flying and he isn't in a vehicle
        if (playerData.getPlayer().isInsideVehicle() || playerData.getPlayer().getAllowFlight()) {
            return;
        }

        final Collection<Entity> tempEntities = playerData.getPlayer().getNearbyEntities(3.0d,3.0d,3.0d);

        //for (final Entity entity : tempEntities) {
           // final EntityType entityType = entity.getType();
           // if (IS_VEHICLE.test(entityType)) {
          //      return;
         //   }
        //}

        this.nearbyEntities = tempEntities.toArray(new Entity[0]);
        this.lastBoundingBox = boundingBox;
        this.playerData.getBoundingBoxes().offerLast(boundingBox);

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
    private static final Predicate<EntityType> IS_VEHICLE = (t -> t == EntityType.BOAT || t == EntityType.CHEST_BOAT
    || t == EntityType.MINECART_TNT || t == EntityType.MINECART_CHEST || t == EntityType.MINECART_FURNACE ||
            t == EntityType.MINECART_HOPPER || t == EntityType.MINECART_MOB_SPAWNER || t == EntityType.MINECART_COMMAND);

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

    public PlayerData getPlayerData() {
        return playerData;
    }

    public double getLastPosX() {
        return lastPosX;
    }

    public double getLastPosY() {
        return lastPosY;
    }

    public double getLastPosZ() {
        return lastPosZ;
    }

    public double getLastRotX() {
        return lastRotX;
    }

    public double getLastRotY() {
        return lastRotY;
    }

    public boolean isTouchingAir() {
        return touchingAir;
    }

    public boolean isTouchingLiquid() {
        return touchingLiquid;
    }

    public boolean isTouchingClimbable() {
        return touchingClimbable;
    }

    public boolean isTouchingHalfBlock() {
        return touchingHalfBlock;
    }

    public boolean isTouchingIllegalBlock() {
        return touchingIllegalBlock;
    }

    public Entity[] getNearbyEntities() {
        return nearbyEntities;
    }

    public BoundingBox getLastBoundingBox() {
        return lastBoundingBox;
    }

    public static Collection<Material> getSTAIRS() {
        return STAIRS;
    }

    public static Collection<Material> getSLABS() {
        return SLABS;
    }

    public void setLastPosX(double lastPosX) {
        this.lastPosX = lastPosX;
    }

    public void setLastPosY(double lastPosY) {
        this.lastPosY = lastPosY;
    }

    public void setLastPosZ(double lastPosZ) {
        this.lastPosZ = lastPosZ;
    }

    public void setLastRotX(double lastRotX) {
        this.lastRotX = lastRotX;
    }

    public void setLastRotY(double lastRotY) {
        this.lastRotY = lastRotY;
    }

    public void setTouchingAir(boolean touchingAir) {
        this.touchingAir = touchingAir;
    }

    public void setTouchingLiquid(boolean touchingLiquid) {
        this.touchingLiquid = touchingLiquid;
    }

    public void setTouchingClimbable(boolean touchingClimbable) {
        this.touchingClimbable = touchingClimbable;
    }

    public void setTouchingHalfBlock(boolean touchingHalfBlock) {
        this.touchingHalfBlock = touchingHalfBlock;
    }

    public void setTouchingIllegalBlock(boolean touchingIllegalBlock) {
        this.touchingIllegalBlock = touchingIllegalBlock;
    }

    public void setNearbyEntities(Entity[] nearbyEntities) {
        this.nearbyEntities = nearbyEntities;
    }

    public void setLastBoundingBox(BoundingBox lastBoundingBox) {
        this.lastBoundingBox = lastBoundingBox;
    }

    public static void setSTAIRS(Collection<Material> STAIRS) {
        MovementManager.STAIRS = STAIRS;
    }

    public static void setSLABS(Collection<Material> SLABS) {
        MovementManager.SLABS = SLABS;
    }
}
