package top.cmarco.aequitas.data;

import lombok.Data;
import lombok.Getter;
import net.minecraft.server.network.ServerPlayerConnection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import top.cmarco.aequitas.checks.CheckManager;
import top.cmarco.aequitas.checks.ViolationManager;
import top.cmarco.aequitas.data.containers.MovementContainer;
import top.cmarco.aequitas.data.types.VelocityData;
import top.cmarco.aequitas.utils.NetworkUtils;

/**
 * Represents data associated with a player, including the player instance and server player connection.
 */
@Data
public final class PlayerData {

    /**
     * The associated player instance.
     */
    private final Player player;

    /**
     * The server player connection of the associated player.
     */
    private final ServerPlayerConnection playerConnection;

    private final VelocityData velocityData = new VelocityData();
    private final ActionsManager actionsManager = new ActionsManager(this);
    private final CheckManager checkManager = new CheckManager(this);
    private final ViolationManager violationManager = new ViolationManager(this);
    private final MovementManager movementManager = new MovementManager(this);

    /* --------------------------------------- */

    private MovementContainer lastMovement = new MovementContainer(null, null, false);

    /**
     * Constructs a new PlayerData instance for the given player.
     *
     * @param player The player for whom to create the PlayerData.
     */
    public PlayerData(final @NotNull Player player) {
        this.player = player;
        this.playerConnection = NetworkUtils.getPlayerConnection(player);
    }
}