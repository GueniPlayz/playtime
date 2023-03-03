package de.gueni.playtime.database.utils;

import de.gueni.playtime.PlayTimePlugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class is used to hold the connection to the database. It is used to prevent duplicate code
 * From: https://github.com/RainbowDashLabs/BasicSQLPlugin/blob/1e76321a0ab889cb3ae5245e659c83a626f69aea/src/main/java/de/chojo/simplecoins/data/util/PluginDataHolder.java
 */
public class DataHolder {
    private final JavaPlugin plugin;
    private final DataSource source;

    /**
     * Constructs a new {@link DataHolder} with the given {@link JavaPlugin} and {@link DataSource}.
     *
     * @param plugin the plugin using this holder
     * @param source the datasource to use
     */
    public DataHolder( JavaPlugin plugin, DataSource source ) {
        this.plugin = plugin;
        this.source = source;
    }

    /**
     * The plugin that uses this holder.
     *
     * @return the plugin that uses this holder
     */
    protected JavaPlugin plugin() {
        return plugin;
    }

    /**
     * Attempts to establish a connection with the data source that this {@link DataHolder} contains.
     *
     * @return a new connection from the data sources
     * @throws SQLException                 if a database access error occurs
     * @throws java.sql.SQLTimeoutException when the driver has determined that the timeout value specified by the
     *                                      {@code setLoginTimeout} method has been exceeded and has at least tried to
     *                                      cancel the current database connection attempt
     */
    protected Connection conn() throws SQLException {
        return source.getConnection();
    }
}

