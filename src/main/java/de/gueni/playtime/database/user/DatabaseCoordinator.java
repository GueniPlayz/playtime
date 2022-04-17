package de.gueni.playtime.database.user;

import de.gueni.playtime.PlayTimePlugin;
import de.gueni.playtime.database.utils.DataHolder;
import de.gueni.playtime.database.utils.UUIDConverter;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class DatabaseCoordinator extends DataHolder {

    /**
     * Constructs a new {@link DataHolder} with the given {@link PlayTimePlugin}
     *
     * @param plugin the plugin using this holder
     */
    public DatabaseCoordinator( PlayTimePlugin plugin ) {
        super( plugin );
    }

    public long getPlayTime( Player player ) {
        try ( var conn = conn(); var stmt = conn.prepareStatement( "SELECT playtime FROM `playtime_players` WHERE `uuid` = ?" ) ) {
            stmt.setBytes( 1, UUIDConverter.convert( player.getUniqueId() ) );
            var rs = stmt.executeQuery();
            if ( rs.next() ) {
                return rs.getLong( "playtime" );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        return -1;
    }

    public void updatePlayTime( Player player, long playTime ) {
        var newTime = getPlayTime( player ) + playTime;

        try ( var conn = conn(); var stmt = conn.prepareStatement( "UPDATE `playtime_players` SET `playtime` = ? WHERE `uuid` = ?" ) ) {
            stmt.setLong( 1, newTime );
            stmt.setBytes( 2, UUIDConverter.convert( player.getUniqueId() ) );
            stmt.executeUpdate();
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    public void updateUser( Player player ) {
        if ( !userExists( player ) ) {
            createUser( player );
            return;
        }
        try ( var conn = conn(); var stmt = conn.prepareStatement( "UPDATE `playtime_players` SET `name` = ? WHERE `uuid` = ?" ) ) {
            stmt.setString( 1, player.getName() );
            stmt.setBytes( 2, UUIDConverter.convert( player.getUniqueId() ) );
            stmt.executeUpdate();
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    private void createUser( Player player ) {
        try ( var conn = conn(); var stmt = conn.prepareStatement( "INSERT INTO `playtime_players` (`uuid`, `name`) VALUES (?, ?)" ) ) {
            stmt.setBytes( 1, UUIDConverter.convert( player.getUniqueId() ) );
            stmt.setString( 2, player.getName() );
            stmt.executeUpdate();
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    public boolean userExists( Player player ) {
        try ( var conn = conn(); var stmt = conn.prepareStatement( "SELECT uuid FROM `playtime_players` WHERE `uuid` = ?" ) ) {
            stmt.setBytes( 1, UUIDConverter.convert( player.getUniqueId() ) );
            return stmt.executeQuery().next();
        } catch ( SQLException e ) {
            e.printStackTrace();
            return false;
        }
    }
}
