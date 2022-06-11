package de.gueni.playtime.placeholder;

import de.gueni.playtime.PlayTimePlugin;
import de.gueni.playtime.database.user.DatabaseUser;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderHook extends PlaceholderExpansion {
    private final PlayTimePlugin plugin;

    public PlaceholderHook( PlayTimePlugin plugin ) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "playtime";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getAuthor() {
        return "GueniPlayz";
    }

    @Override
    public String onPlaceholderRequest( Player player, @NotNull String params ) {
        if ( params.equalsIgnoreCase( "time" ) ) {
            var user = DatabaseUser.get( player );
            return String.valueOf( user.getPlayTime() );
        }
        return null;
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }
}
