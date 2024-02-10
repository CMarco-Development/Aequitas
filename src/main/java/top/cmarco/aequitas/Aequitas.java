package top.cmarco.aequitas;

import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import top.cmarco.aequitas.analysis.AnalysisManager;
import top.cmarco.aequitas.data.PlayerDataManager;
import top.cmarco.aequitas.listener.GlobalListener;

/**
 * Main class of this software.
 */
@Getter
public final class Aequitas extends JavaPlugin {

    private final PlayerDataManager playerDataManager = new PlayerDataManager();
    /* ------------------------------------------ */
    private final GlobalListener globalListener = new GlobalListener(this);
    private boolean isRegisteredListeners = false;
    /* ------------------------------------------ */
    private final AnalysisManager analysisManager = new AnalysisManager();
    /* ------------------------------------------ */

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

        pluginManager.registerEvents(this.globalListener, this);

        this.isRegisteredListeners = true; // marking the listeners as registered.
    }

    @Override
    public void onEnable() { // Plugin startup logic
        this.registerListeners();
    }

    @Override
    public void onDisable() { // Plugin shutdown logic
    }
}
