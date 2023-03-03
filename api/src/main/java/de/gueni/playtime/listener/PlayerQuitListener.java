package de.gueni.playtime.listener;

import de.gueni.playtime.PlayTimePlugin;
import de.gueni.playtime.database.user.DatabaseUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private final PlayTimePlugin plugin;

    public PlayerQuitListener( PlayTimePlugin plugin ) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents( this, plugin );
    }

    @EventHandler
    public void handleQuit( PlayerQuitEvent event ) {
        var player = event.getPlayer();
        var user = DatabaseUser.get( player );
        var playTime = System.currentTimeMillis() - user.getJoinTime();

        plugin.getServer().getScheduler().runTaskAsynchronously( plugin,
                () -> plugin.getDatabaseCoordinator().updatePlayTime( player, playTime )
        );
        user.delete();
    }
}
