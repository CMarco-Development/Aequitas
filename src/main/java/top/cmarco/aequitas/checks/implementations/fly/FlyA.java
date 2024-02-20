package top.cmarco.aequitas.checks.implementations.fly;

import org.jetbrains.annotations.NotNull;
import top.cmarco.aequitas.checks.CheckInfo;
import top.cmarco.aequitas.checks.types.MovementCheck;
import top.cmarco.aequitas.data.PlayerData;
import top.cmarco.aequitas.data.containers.MovementContainer;

@CheckInfo(name = "Fly (A)", threshold = 20)
public final class FlyA extends MovementCheck {

    /**
     * Constructs a new MovementCheck with the specified player data.
     *
     * @param playerData The player data associated with this movement check.
     */
    public FlyA(@NotNull final PlayerData playerData) {
        super(playerData);
    }

    /**
     * Process the movement-related data to detect cheats.
     *
     * @param data The container holding movement-related information for the check.
     */
    @Override
    public void process(@NotNull final MovementContainer data) {

    }
}
