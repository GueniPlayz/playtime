package de.gueni.playtime.database.user;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class DatabaseUser {
    private static final Map<Player, DatabaseUser> USER_MAP = new HashMap<>();
    private final Player player;
    private long joinTime;

    private DatabaseUser( Player player ) {
        this.player = player;
    }

    public static DatabaseUser get( Player player ) {
        return USER_MAP.computeIfAbsent( player, DatabaseUser::new );
    }

    public void delete() {
        USER_MAP.remove( player );
    }

    public long getJoinTime() {
        return joinTime;
    }

    public void setJoinTime( long joinTime ) {
        this.joinTime = joinTime;
    }
}
