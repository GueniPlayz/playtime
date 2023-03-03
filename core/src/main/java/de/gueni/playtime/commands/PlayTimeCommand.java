package de.gueni.playtime.commands;

import de.gueni.playtime.database.user.DatabaseUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayTimeCommand implements CommandExecutor {
    @Override
    public boolean onCommand( @NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args ) {
        if ( !( sender instanceof Player player ) ) {

        } else {
            if ( args.length == 0 ) {
                var user = DatabaseUser.get( player );
                player.sendMessage( "Your playtime: " + user.getPlayTime() );
            }
        }
        return false;
    }

    private String formatMillis( long time ) {
        return String.format( "%dh,%dm,%ds",  )
    }
}
