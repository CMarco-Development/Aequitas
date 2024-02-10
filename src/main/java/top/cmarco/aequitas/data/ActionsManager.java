package top.cmarco.aequitas.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * The ActionsManager class represents a manager for various player actions.
 * It tracks different actions such as placing, attacking, teleporting, and digging.
 */
@Data
@RequiredArgsConstructor
public final class ActionsManager {

    /**
     * The player data associated with this ActionsManager.
     */
    private final PlayerData playerData;

    /**
     * Indicates whether the player is currently placing blocks.
     */
    private final boolean placing = false;

    /**
     * Indicates whether the player is currently attacking entities.
     */
    private final boolean attacking = false;

    /**
     * Indicates whether the player has recently teleported.
     */
    private final boolean teleported = false;

    /**
     * Indicates whether the player is currently digging blocks.
     */
    private final boolean digging = false;
}