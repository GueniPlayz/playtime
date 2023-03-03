package de.gueni.playtime;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class PlayTime extends JavaPlugin {


    @Override
    public void onEnable() {

        if( PlayTimePlugin.getInstance() == null )  {
            getLogger().log( Level.SEVERE, "Playtime api not found. Disabling plugin!" );
            getServer().getPluginManager().disablePlugin( this );
            return;
        }
    }
}
