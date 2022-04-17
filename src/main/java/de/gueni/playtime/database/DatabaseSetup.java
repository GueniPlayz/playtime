package de.gueni.playtime.database;

import de.gueni.playtime.PlayTimePlugin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * This class initializes the database.
 */
public class DatabaseSetup {

    private DatabaseSetup() throws InstantiationException {
        throw new InstantiationException( "This class is not meant to be instantiated" );
    }

    /**
     * This method reads the dbsetup.sql file from the resources folder and executes the given queries.
     * <p>
     *
     * @param plugin the plugin instance to obtain the logger and the database connection from
     * @throws IOException  if the file could not be read
     * @throws SQLException if the queries could not be executed
     */
    public static void initDatabase( PlayTimePlugin plugin ) {
        String setup = null;

        try ( var inputStream = DatabaseSetup.class.getClassLoader().getResourceAsStream( "dbsetup.sql" ) ) {
            setup = new String( inputStream.readAllBytes() );
        } catch ( IOException e ) {
            plugin.getLogger().log( Level.SEVERE, "§cCould not read dbsetup.sql file.", e );
        }
        var queries = setup.split( ";" );

        try ( var conn = plugin.getProvider().getConnection() ) {
            conn.setAutoCommit( false );
            for ( var query : queries ) {
                if ( query.isBlank() ) continue;
                try ( var stmt = conn.prepareStatement( query ) ) {
                    plugin.getLogger().info( "Executing query: " + query );
                    stmt.execute();
                }
                conn.commit();
            }
            plugin.getLogger().info( "Database setup complete." );
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }
}

