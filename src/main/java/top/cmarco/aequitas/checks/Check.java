package top.cmarco.aequitas.checks;

import org.jetbrains.annotations.NotNull;
import top.cmarco.aequitas.Aequitas;
import top.cmarco.aequitas.data.PlayerData;

import java.util.Objects;

/**
 * An abstract class representing a generic anticheat check.
 *
 * @param <T> The type of data being analyzed by the anticheat check.
 */
public abstract class Check<T> {

    private final Aequitas aequitas;
    private final ViolationManager violationManager;
    private final int threshold;
    private final String checkName;

    /**
     * The player data associated with this anti-cheat check.
     */
    private final PlayerData playerData;

    private final CheckInfo checkInfo;

    protected Check(@NotNull final PlayerData playerData) {
        this.violationManager = new ViolationManager(this);
        this.playerData = Objects.requireNonNull(playerData);
        this.aequitas = Objects.requireNonNull(playerData.getAequitas());

        final Class<? extends Check> checkClass = this.getClass();
        if (checkClass.isAnnotationPresent(CheckInfo.class)) {
            final CheckInfo checkData = checkClass.getAnnotation(CheckInfo.class);
            this.checkName = checkData.name();
            this.threshold = checkData.threshold();
            this.checkInfo = checkData;
        } else {
            throw new RuntimeException("Invalid Check info annotation.");
        }
    }

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
        this.violationManager.flag();
    }

    public Aequitas getAequitas() {
        return aequitas;
    }

    public ViolationManager getViolationManager() {
        return violationManager;
    }

    public int getThreshold() {
        return threshold;
    }

    public String getCheckName() {
        return checkName;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public CheckInfo getCheckInfo() {
        return checkInfo;
    }
}