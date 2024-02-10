package top.cmarco.aequitas.checks;

import lombok.RequiredArgsConstructor;
import top.cmarco.aequitas.data.PlayerData;

/**
 * The ViolationManager class is responsible for handling violations related to anticheat checks.
 * It is associated with a specific PlayerData instance and provides methods for flagging violations.
 */
@RequiredArgsConstructor
public final class ViolationManager {

    /**
     * The player data associated with this ViolationManager.
     */
    private final PlayerData playerData;

    /**
     * Flags a violation, indicating that an anticheat check has detected suspicious behavior.
     * This method is called when a violation is detected during the anticheat check process.
     */
    public void flag() { // Implementation specific to flagging violations
    }
}
