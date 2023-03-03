package de.gueni.playtime.listener;

import de.gueni.playtime.PlayTimePlugin;
import de.gueni.playtime.database.user.DatabaseUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final PlayTimePlugin plugin;

    public PlayerJoinListener( PlayTimePlugin plugin ) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents( this, plugin );
    }

    @EventHandler
    public void onPlayerJoin( PlayerJoinEvent event ) {
        var player = event.getPlayer();
        var user = DatabaseUser.get( player );

        user.setJoinTime( System.currentTimeMillis() );

        plugin.getServer().getScheduler().runTaskAsynchronously( plugin,
                () -> {
                    plugin.getDatabaseCoordinator().updateUser( player );
                    user.setPlayTime( plugin.getDatabaseCoordinator().getPlayTime( player ) );
                }
        );
    }
}
