package top.cmarco.aequitas.checks;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import top.cmarco.aequitas.data.PlayerData;

/**
 * An abstract class representing a generic anticheat check.
 *
 * @param <T> The type of data being analyzed by the anticheat check.
 */
@RequiredArgsConstructor
public abstract class Check<T> {

    /**
     * The player data associated with this anticheat check.
     */
    private final PlayerData playerData;

    /**
     * Process the data to detect cheats.
     *
     * @param data The data of type T being analyzed by the anticheat check.
     */
    public abstract void process(@NotNull final T data);

    /**
     * Method to be called when the anticheat check fails, indicating the presence of cheats.
     * It flags the associated violation manager in the player data.
     */
    protected void fail() {
        this.playerData.getViolationManager().flag();
    }
}