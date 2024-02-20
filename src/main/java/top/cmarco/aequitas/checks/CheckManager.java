package top.cmarco.aequitas.checks;

import org.jetbrains.annotations.NotNull;
import top.cmarco.aequitas.checks.implementations.fly.FlyA;
import top.cmarco.aequitas.checks.implementations.jesus.JesusA;
import top.cmarco.aequitas.checks.implementations.jesus.JesusB;
import top.cmarco.aequitas.data.PlayerData;

import java.util.HashSet;
import java.util.Objects;


/**
 * The CheckManager class is responsible for storing and managing instances of anti-cheat checks.
 * It maintains a set of Check objects and provides methods for check registration.
 */
public final class CheckManager {

    /**
     * The player data associated with this CheckManager.
     */
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
    public CheckManager(@NotNull final PlayerData playerData) {
        this.playerData = Objects.requireNonNull(playerData);
        this.registerAllChecks();
    }

    /**
     * Registers all default anti-cheat checks.
     */
    private void registerAllChecks() {
        // Example: Adding all checks individually.
        this.checks.add(new JesusA(this.playerData));
        this.checks.add(new JesusB(this.playerData));
        this.checks.add(new FlyA(this.playerData));
    }

    public HashSet<Check<?>> getChecks() {
        return checks;
    }
}