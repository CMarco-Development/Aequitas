/*
 *     Aequitas - Anticheat for SpigotMC Servers
 *     Copyright © 2024 CMarco
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package top.cmarco.aequitas.listener;

import io.netty.channel.ChannelPipeline;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import top.cmarco.aequitas.Aequitas;
import top.cmarco.aequitas.chat.MessageUtils;
import top.cmarco.aequitas.data.PlayerData;
import top.cmarco.aequitas.exceptions.PipelineInjectException;
import top.cmarco.aequitas.network.AequitasChannel;
import top.cmarco.aequitas.utils.NetworkUtils;

/**
 * GlobalListener class that listens to player events.
 */
public final class GlobalListener implements Listener {

    private static final String DEFAULT_CHANNEL = "packet_handler";
    private static final String AEQUITAS_CHANNEL = "aegis_packet_handler";

    private final Aequitas aequitas;

    public GlobalListener(@NotNull final Aequitas aequitas) {
        this.aequitas = aequitas;
    }

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

            if (playerPipeline == null) {
                return;
            }

            aequitas.getExecutorPacket().execute(() -> {
                playerPipeline.addBefore(DEFAULT_CHANNEL, AEQUITAS_CHANNEL, playerAequitasChannel);
            });

            MessageUtils.sendWithLogo(aequitas.getServer().getConsoleSender(), "&aAdded &e" + player.getName() + " &ato checklist.");
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
                aequitas.getExecutorPacket().execute(() -> playerPipeline.remove(AEQUITAS_CHANNEL));
            }

        } catch (PipelineInjectException exception) {
            this.aequitas.getLogger().warning(exception.getLocalizedMessage());
        }
    }
}