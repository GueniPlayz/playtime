package de.gueni.playtime.database.utils;

import de.gueni.playtime.PlayTimePlugin;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class is used to hold the connection to the database. It is used to prevent duplicate code
 */
public class DataHolder {
    private final PlayTimePlugin plugin;
    private final DataSource source;

    /**
     * Constructs a new {@link DataHolder} with the given {@link PlayTimePlugin} and {@link DataSource}.
     *
     * @param plugin the plugin using this holder
     */
    public DataHolder( PlayTimePlugin plugin ) {
        this.plugin = plugin;
        this.source = plugin.getProvider().getDataSource();
    }

    /**
     * The plugin that uses this holder.
     *
     * @return the plugin that uses this holder
     */
    protected PlayTimePlugin plugin() {
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

