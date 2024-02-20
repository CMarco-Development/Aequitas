package top.cmarco.aequitas.data;

import net.minecraft.server.network.ServerPlayerConnection;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;
import top.cmarco.aequitas.Aequitas;
import top.cmarco.aequitas.checks.CheckManager;
import top.cmarco.aequitas.data.containers.MovementContainer;
import top.cmarco.aequitas.data.types.VelocityData;
import top.cmarco.aequitas.utils.NetworkUtils;

import java.util.LinkedList;
import java.util.Objects;

/**
 * Represents data associated with a player, including the player instance and server player connection.
 */
public final class PlayerData {

    private final Aequitas aequitas;

    /**
     * The associated player instance.
     */
    private final Player player;

    /**
     * The server player connection of the associated player.
     */
    private final ServerPlayerConnection playerConnection;

    private final VelocityData velocityData;
    private final ActionsManager actionsManager;
    private final CheckManager checkManager;
    private final MovementManager movementManager;

    /* --------------------------------------- */

    private MovementContainer lastMovement = new MovementContainer(null, null, false);

    /* --------------------------------------- */

    private LinkedList<BoundingBox> boundingBoxes = new LinkedList<>();

    /**
     * Constructs a new PlayerData instance for the given player.
     *
     * @param player The player for whom to create the PlayerData.
     */
    public PlayerData(final @NotNull Player player, @NotNull final Aequitas aequitas) {
        this.player = Objects.requireNonNull(player);
        this.aequitas = Objects.requireNonNull(aequitas);
        this.playerConnection = NetworkUtils.getPlayerConnection(player);
        this.velocityData = new VelocityData();
        this.actionsManager = new ActionsManager(this);
        this.checkManager = new CheckManager(this);
        this.movementManager = new MovementManager(this);
    }

    public Aequitas getAequitas() {
        return aequitas;
    }

    public Player getPlayer() {
        return player;
    }

    public ServerPlayerConnection getPlayerConnection() {
        return playerConnection;
    }

    public VelocityData getVelocityData() {
        return velocityData;
    }

    public ActionsManager getActionsManager() {
        return actionsManager;
    }

    public CheckManager getCheckManager() {
        return checkManager;
    }

    public MovementManager getMovementManager() {
        return movementManager;
    }

    public MovementContainer getLastMovement() {
        return lastMovement;
    }

    public LinkedList<BoundingBox> getBoundingBoxes() {
        return boundingBoxes;
    }

    public void setLastMovement(MovementContainer lastMovement) {
        this.lastMovement = lastMovement;
    }

    public void setBoundingBoxes(LinkedList<BoundingBox> boundingBoxes) {
        this.boundingBoxes = boundingBoxes;
    }
}