package top.cmarco.aequitas.listener;

import io.netty.channel.ChannelPipeline;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import top.cmarco.aequitas.Aequitas;
import top.cmarco.aequitas.data.PlayerData;
import top.cmarco.aequitas.exceptions.PipelineInjectException;
import top.cmarco.aequitas.network.AequitasChannel;
import top.cmarco.aequitas.utils.NetworkUtils;

/**
 * GlobalListener class that listens to player events.
 */
@RequiredArgsConstructor
public final class GlobalListener implements Listener {

    private static final String DEFAULT_CHANNEL = "packet_handler";
    private static final String AEQUITAS_CHANNEL = "aegis_packet_handler";

    private final Aequitas aequitas;

    /**
     * Handles player interact events.
     *
     * @param event The PlayerInteractEvent
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if (event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }
    }

    /**
     * Handles player join events.
     *
     * @param event The PlayerJoinEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(final PlayerJoinEvent event) {
        try {
            final Player player = event.getPlayer();
            final PlayerData playerData = aequitas.getPlayerDataManager().getData(player);
            final AequitasChannel playerAequitasChannel = new AequitasChannel(playerData, aequitas);
            final ChannelPipeline playerPipeline = NetworkUtils.getPlayerPipeline(player);

            if (playerPipeline != null) {
                return;
            }

            playerPipeline.addBefore(DEFAULT_CHANNEL, AEQUITAS_CHANNEL, playerAequitasChannel);
        } catch (PipelineInjectException exception) {
            // Handling the inner exception called from networking utils (could not get pipeline).
            this.aequitas.getLogger().warning(exception.getLocalizedMessage());
        }
    }

    /**
     * Handles player leave events.
     *
     * @param event The PlayerQuitEvent
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onLeave(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        try {
            final ChannelPipeline playerPipeline = NetworkUtils.getPlayerPipeline(player);

            // double-checking the pipeline has been successfully removed.
            if (playerPipeline != null && playerPipeline.get(AEQUITAS_CHANNEL) != null) {
                playerPipeline.remove(AEQUITAS_CHANNEL);
            }

        } catch (PipelineInjectException exception) {
            this.aequitas.getLogger().warning(exception.getLocalizedMessage());
        }
    }
}