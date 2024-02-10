package top.cmarco.aequitas.data.containers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;

/**
 * The MovementContainer class serves as a container for movement-related information
 * from one location to another, along with an indication of whether the entity is on the ground.
 */
@RequiredArgsConstructor
@Getter
public final class MovementContainer {

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

}