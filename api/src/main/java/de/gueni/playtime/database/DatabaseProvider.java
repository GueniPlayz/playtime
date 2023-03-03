package de.gueni.playtime.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class is managing the connection to the database.
 * It should provide a connection to the database which can be used by the other classes.
 */
public class DatabaseProvider {
    private final String host, user, password, database;
    private final int port;
    private HikariDataSource source;

    /**
     * Creates a new instance of the DbProvider.
     *
     * @param host     The host of the database.
     * @param user     The username of the database.
     * @param password The password of the database.
     * @param database The name of the database.
     * @param port     The port of the database.
     */
    private DatabaseProvider( String host, String user, String password, String database, int port ) {
        this.database = database;
        this.host = host;
        this.user = user;
        this.password = password;
        this.port = port;
    }

    /**
     * Creates a new instance of the DbProvider.
     *
     * @param host     The host of the database.
     * @param user     The username of the database.
     * @param password The password of the database.
     * @param database The name of the database.
     * @param port     The port of the database.
     * @return A new instance of the DbProvider.
     */
    public static DatabaseProvider create( String host, String user, String password, String database, int port ) {
        return new DatabaseProvider( host, user, password, database, port );
    }

    /**
     * Attempts to establish a connection to the database.
     *
     * @return True if the connection to the database was established, false otherwise.
     */
    public boolean connect() {
        var config = new HikariConfig();

        config.setDriverClassName( "com.mysql.cj.jdbc.Driver" );
        config.setJdbcUrl( String.format( "jdbc:mysql://%s:%d/%s", host, port, database ) );
        config.setMaximumPoolSize( 10 );
        config.setUsername( user );
        config.setPassword( password );

        source = new HikariDataSource( config );
        return isConnected();
    }

    /**
     * Attempts to close the connection to the database. If the connection is already closed, the method does nothing.
     */
    public void disconnect() {
        if ( isConnected() ) {
            source.close();
        }
    }

    /**
     * @return the underlying datasource.
     */
    public HikariDataSource getDataSource() {
        return source;
    }

    /**
     * Checks if the connection to the database is still alive.
     *
     * @return True if the connection is still alive, false otherwise.
     */
    public boolean isConnected() {
        return source != null;
    }

    /**
     * Attempts to establish a connection to the database. If the connection fails, the method returns null.
     *
     * @return A connection to the database or null if the connection failed.
     */
    @Nullable
    public Connection getConnection() throws SQLException {
        return source.getConnection();
    }
}