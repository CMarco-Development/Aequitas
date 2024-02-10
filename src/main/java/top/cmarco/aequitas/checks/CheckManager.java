package top.cmarco.aequitas.checks;

import lombok.AccessLevel;
import lombok.Getter;
import top.cmarco.aequitas.checks.implementations.SpeedA;
import top.cmarco.aequitas.data.PlayerData;

import java.util.HashSet;


/**
 * The CheckManager class is responsible for storing and managing instances of anti-cheat checks.
 * It maintains a set of Check objects and provides methods for check registration.
 */
@Getter
public final class CheckManager {

    /**
     * The player data associated with this CheckManager.
     */
    @Getter(AccessLevel.NONE)
    private final PlayerData playerData;

    /**
     * The set containing all registered anti-cheat checks.
     */
    private final HashSet<Check<?>> checks = new HashSet<>();

    /**
     * Constructs a new CheckManager with the specified player data and registers all default checks.
     *
     * @param playerData The player data associated with this CheckManager.
     */
    public CheckManager(PlayerData playerData) {
        this.playerData = playerData;
        this.registerAllChecks();
    }

    /**
     * Registers all default anti-cheat checks.
     */
    private void registerAllChecks() {
        // Example: Adding all checks individually.
        this.checks.add(new SpeedA(this.playerData));
    }
}