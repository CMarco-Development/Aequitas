package top.cmarco.aequitas.data.containers;

import org.bukkit.Location;

/**
 * The MovementContainer class serves as a container for movement-related information
 * from one location to another, along with an indication of whether the entity is on the ground.
 */
public final class MovementContainer {

    public MovementContainer(final Location from, final Location to, final boolean isOnGround) {
        this.from = from;
        this.to = to;
        this.isOnGround = isOnGround;
    }

    /**
     * The starting location of the movement.
     */
    private final Location from;

    /**
     * The ending location of the movement.
     */
    private final Location to;

    /**
     * A boolean indicating whether the entity is on the ground during the movement.
     */
    private final boolean isOnGround;

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public boolean isOnGround() {
        return isOnGround;
    }
}