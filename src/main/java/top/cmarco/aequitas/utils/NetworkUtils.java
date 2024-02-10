package top.cmarco.aequitas.utils;

import io.netty.channel.ChannelPipeline;
import net.minecraft.network.Connection;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.network.ServerPlayerConnection;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.cmarco.aequitas.exceptions.PipelineInjectException;

import java.lang.reflect.Field;
import java.nio.channels.Pipe;

/**
 * Utility class for interacting with network-related functionality in Bukkit/Spigot.
 */
public final class NetworkUtils {

    private NetworkUtils() {
        throw new RuntimeException("This class should not be instantiated.");
    }

    /**
     * Retrieves the ServerPlayerConnection associated with a Bukkit Player.
     *
     * @param player The Bukkit Player
     * @return The ServerPlayerConnection
     */
    @NotNull
    public static ServerPlayerConnection getPlayerConnection(@NotNull final Player player) {
        return ((CraftPlayer) player).getHandle().connection;
    }

    public static Field connectionField = null;

    /**
     * Retrieves the ChannelPipeline associated with the network connection of a Bukkit Player.
     *
     * @param player The Bukkit Player
     * @return The ChannelPipeline of the player's network connection
     * @throws RuntimeException if the required field is not found or cannot be accessed
     */
    @Nullable
    public static ChannelPipeline getPlayerPipeline(@NotNull final Player player) throws PipelineInjectException {
        final ServerGamePacketListenerImpl serverGamePacketListener = ((CraftPlayer) player).getHandle().connection;

        if (connectionField == null) {
            // Lazy initialization of connectionField
            for (final Field declaredField : serverGamePacketListener.getClass().getSuperclass().getDeclaredFields()) {
                if (declaredField.getType().equals(Connection.class)) {
                    connectionField = declaredField;
                    break;
                }
            }

            if (connectionField == null) {
                final String errorMessage = "WARNING: Could not find \"connection\" protected-level field in "
                        + serverGamePacketListener.getClass().getSuperclass().getCanonicalName();
                throw new PipelineInjectException(player, errorMessage);
            }
        }

        connectionField.setAccessible(true);

        try {
            // Retrieve the connection and channel information
            final net.minecraft.network.Connection connection = (net.minecraft.network.Connection) connectionField.get(serverGamePacketListener);
            final io.netty.channel.Channel connectionChannel = connection.channel;
            return connectionChannel.pipeline();
        } catch (IllegalAccessException illegalAccessException) {
            final String errorMessage = "WARNING: Error obtaining pipeline for player: " + player.getName();
            throw new PipelineInjectException(player, errorMessage);
        }

    }

}