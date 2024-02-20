package top.cmarco.aequitas.checks;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import top.cmarco.aequitas.chat.MessageUtils;
import top.cmarco.aequitas.data.PlayerData;
import top.cmarco.aequitas.data.containers.AlertContainer;
import top.cmarco.aequitas.permissions.AequitasPerm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.UUID;

/**
 * The ViolationManager class is responsible for handling violations related to anticheat checks.
 * It is associated with a specific PlayerData instance and provides methods for flagging violations.
 */
public final class ViolationManager {

    private static final HashSet<UUID> enabledFlags = new HashSet<>();
    private static final HashSet<UUID> enabledAlerts = new HashSet<>();

    /**
     * The player data associated with this ViolationManager.
     */
    private final Check<?> check;

    public ViolationManager(@NotNull final Check<?> check) {
        this.check = Objects.requireNonNull(check);
    }

    private void sendAlert(@NotNull final Player player) {
        final String alertMsg = "&7Player &e" + this.check.getPlayerData().getPlayer().getName()
                + " &7failed check &a" + this.check.getCheckName();
        MessageUtils.sendWithLogo(player, alertMsg);
    }

    private void sendFlagged(@NotNull final Player player, int amount) {
        final String alertMsg = "&7Player &e" + this.check.getPlayerData().getPlayer().getName()
                + " &7flagged check &a" + this.check.getCheckName() + " &7(&a" + amount + "&7)";
        MessageUtils.sendWithLogo(player, alertMsg);
    }

    private final LinkedList<AlertContainer> alerts = new LinkedList<>();

    /**
     * Flags a violation, indicating that an anti-cheat check has detected suspicious behavior.
     * This method is called when a violation is detected during the anti-cheat check process.
     */
    public void flag() { // Implementation specific to flagging violations
        final PlayerData playerData = this.check.getPlayerData();
        final CheckInfo checkInfo = this.check.getCheckInfo();
        final long currentTime = System.currentTimeMillis();

        for (final AlertContainer alert : this.alerts) {
            if (alert.getAlertTime() == currentTime) {
                return;
            }
        }

        this.alerts.offerLast(new AlertContainer(currentTime, checkInfo));

        int violations = 0;
        for (final AlertContainer alert : this.alerts) {
            if (alert.getAlertTime() + 9E3 > currentTime) {
                violations++;
            }
        }

        final int foundViolations = violations;

        this.check.getAequitas().getServer().getOnlinePlayers().stream()
                .filter(p -> p.hasPermission(AequitasPerm.FLAGS.getNode()))
                .forEach(p -> sendFlagged(p, foundViolations));

        if (foundViolations > checkInfo.threshold()) {
            this.check.getAequitas().getServer().getOnlinePlayers().stream()
            .filter(p -> p.hasPermission(AequitasPerm.ALERTS.getNode()))
                    .forEach(this::sendAlert);

            this.alerts.clear();
        }

    }
}
