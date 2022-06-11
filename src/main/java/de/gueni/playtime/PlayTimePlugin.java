package de.gueni.playtime;

import de.gueni.playtime.database.DatabaseProvider;
import de.gueni.playtime.database.DatabaseSetup;
import de.gueni.playtime.database.user.DatabaseCoordinator;
import de.gueni.playtime.database.user.DatabaseUser;
import de.gueni.playtime.listener.PlayerJoinListener;
import de.gueni.playtime.listener.PlayerQuitListener;
import de.gueni.playtime.placeholder.PlaceholderHook;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class PlayTimePlugin extends JavaPlugin {
    // Adding the static getter as it is the easiest access to the plugin's instance.
    private static PlayTimePlugin instance;
    private DatabaseCoordinator coordinator;
    private DatabaseProvider provider;

    @Override
    public void onEnable() {
        // My apologies.
        instance = this;

        // Initializes the default configuration so the user can change the database credentials.
        saveDefaultConfig();

        // As there are some people that think reloading is a good idea...
        getServer().getOnlinePlayers().forEach( player -> {
            var user = DatabaseUser.get( player );
            user.setJoinTime( System.currentTimeMillis() );
        } );

        // Initializes the listener
        new PlayerJoinListener( this );
        new PlayerQuitListener( this );

        // Initializes the database with the given credentials in the config.yml
        provider = DatabaseProvider.create(
                getConfig().getString( "database.host" ),
                getConfig().getString( "database.username" ),
                getConfig().getString( "database.password" ),
                getConfig().getString( "database.database" ),
                getConfig().getInt( "database.port" )
        );

        // Tries to connect to the database
        if ( !provider.connect() ) {
            getLogger().log( Level.SEVERE, "Could not connect to database." );
            getServer().getPluginManager().disablePlugin( this );
            return;
        }

        // Setups the tables if they don't exist
        DatabaseSetup.initDatabase( this );
        coordinator = new DatabaseCoordinator( this, provider.getDataSource() );

        // Hooking PlaceholderAPI into the plugin
        if ( getConfig().getBoolean( "settings.placeholder_api" ) ) {
            if ( getServer().getPluginManager().getPlugin( "PlaceholderAPI" ) == null ) {
                getLogger().log( Level.WARNING, "§cPlaceholderAPI not found.. Disabling Placeholder-Support" );
                return;
            }

            new PlaceholderHook( this ).register();
            getLogger().log( Level.INFO, "§aHooked PlaceholderAPI into the plugin!" );
        }
    }

    @Override
    public void onDisable() {
        // If the server is restarting and there are still players on it, we need to save the playtime of the players
        getServer().getOnlinePlayers().forEach( player -> {
            var user = DatabaseUser.get( player );
            var playTime = System.currentTimeMillis() - user.getJoinTime();

            coordinator.updatePlayTime( player, playTime );
            user.delete();
        } );
        provider.disconnect();
    }

    // I'm sorry, I'm not even using it... :(
    public static PlayTimePlugin getInstance() {
        return instance;
    }

    public DatabaseCoordinator getDatabaseCoordinator() {
        return coordinator;
    }

    public DatabaseProvider getDatabaseProvider() {
        return provider;
    }
}
