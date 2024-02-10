package top.cmarco.aequitas.checks.types;

import org.jetbrains.annotations.NotNull;
import top.cmarco.aequitas.checks.Check;
import top.cmarco.aequitas.data.PlayerData;
import top.cmarco.aequitas.data.containers.MovementContainer;

/**
 * An abstract class representing a movement-related anticheat check.
 * This class extends the generic Check class with a specific type parameter of MovementContainer.
 *
 * @param <MovementContainer> The type of data container for movement-related information.
 */
public abstract class MovementCheck extends Check<MovementContainer> {

    /**
     * Constructs a new MovementCheck with the specified player data.
     *
     * @param playerData The player data associated with this movement check.
     */
    public MovementCheck(@NotNull final PlayerData playerData) {
        super(playerData);
    }

    /**
     * Process the movement-related data to detect cheats.
     *
     * @param data The container holding movement-related information for the check.
     */
    @Override
    public abstract void process(@NotNull final MovementContainer data);

}
