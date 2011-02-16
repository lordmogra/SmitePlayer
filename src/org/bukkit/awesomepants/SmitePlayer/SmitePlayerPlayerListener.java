package org.bukkit.awesomepants.SmitePlayer;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Handle events for all Player related events
 * @author LordMogra
 */
public class SmitePlayerPlayerListener extends PlayerListener {
    private final SmitePlayer plugin;

    public SmitePlayerPlayerListener(SmitePlayer instance) {
        plugin = instance;
    }

    //Insert Player related code here
}

