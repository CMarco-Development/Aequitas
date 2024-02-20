package top.cmarco.aequitas.exceptions;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * An Exception class that represents a critical failure
 * in injecting into a player's Channel Pipeline at runtime.
 */
public final class PipelineInjectException extends RuntimeException {

    private final Player failedInjectTarget; // the player this software was attempting to inject into.

    public PipelineInjectException(@NotNull final Player failedInjectTarget, @NotNull final String errorMessage) {
        super(errorMessage);
        this.failedInjectTarget = failedInjectTarget;
    }

    public Player getFailedInjectTarget() {
        return failedInjectTarget;
    }
}
