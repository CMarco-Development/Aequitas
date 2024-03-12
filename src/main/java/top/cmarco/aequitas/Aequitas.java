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

package top.cmarco.aequitas;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import top.cmarco.aequitas.analysis.AnalysisManager;
import top.cmarco.aequitas.commands.AequitasCommand;
import top.cmarco.aequitas.config.AequitasConfig;
import top.cmarco.aequitas.data.PlayerDataManager;
import top.cmarco.aequitas.listener.GlobalListener;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Main class of this software.
 */
public final class Aequitas extends JavaPlugin {

    @Override
    public void onEnable() { // Plugin startup logic
        this.setupConfig();
        this.registerDataManager();
        this.registerListeners();
        this.registerCommands();
    }

    @Override
    public void onDisable() { // Plugin shutdown logic
    }


    private PlayerDataManager playerDataManager = null;
    /* ------------------------------------------ */
    private GlobalListener globalListener = null;
    private boolean isRegisteredListeners = false;
    /* ------------------------------------------ */
    private final AnalysisManager analysisManager = new AnalysisManager();
    /* ------------------------------------------ */
    private final Executor executorAlert = Executors.newSingleThreadExecutor();
    private final Executor executorPacket = Executors.newSingleThreadExecutor();
    /* ----------------------------------------- */
    private AequitasCommand aequitasCommand = null;
    private AequitasConfig aequitasConfig = null;

    private void setupConfig() {
        if (aequitasConfig != null) {
            return;
        }

        this.aequitasConfig = new AequitasConfig(this);
    }

    private void registerCommands() {
        this.aequitasCommand = new AequitasCommand(this);

        getCommand("aequitas").setExecutor(this.aequitasCommand);
    }

    public void runTaskSync(@NotNull final Runnable runnable) {
        super.getServer().getScheduler().runTask(this, runnable);
    }

    private void registerDataManager() {
        if (playerDataManager != null) {
            getLogger().warning("WARNING! Tried to register data manager twice.");
            return;
        }

        this.playerDataManager = new PlayerDataManager(this);
    }

    /**
     * Register all the listeners used by this server.
     * Should NEVER be called twice.
     */
    private void registerListeners() {
        final PluginManager pluginManager = this.getServer().getPluginManager();

        if (isRegisteredListeners) {
            getLogger().warning("WARNING! Attempted to register listeners twice.");
            return;
        }

        this.globalListener = new GlobalListener(this);
        pluginManager.registerEvents(this.globalListener, this);

        this.isRegisteredListeners = true; // marking the listeners as registered.
    }

    /* -------------------------------------------------------------------------------------- */

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public GlobalListener getGlobalListener() {
        return globalListener;
    }

    public boolean isRegisteredListeners() {
        return isRegisteredListeners;
    }

    public AnalysisManager getAnalysisManager() {
        return analysisManager;
    }

    public Executor getExecutorAlert() {
        return executorAlert;
    }

    public Executor getExecutorPacket() {
        return executorPacket;
    }

    public AequitasConfig getAequitasConfig() {
        return aequitasConfig;
    }
}
