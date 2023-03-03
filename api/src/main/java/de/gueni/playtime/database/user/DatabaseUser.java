package de.gueni.playtime.database.user;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class DatabaseUser {
    private static final Map<Player, DatabaseUser> USER_MAP = new HashMap<>();
    private final Player player;
    private long joinTime, playTime;

    private DatabaseUser( Player player ) {
        this.player = player;
    }

    public static DatabaseUser get( Player player ) {
        return USER_MAP.computeIfAbsent( player, DatabaseUser::new );
    }

    public void delete() {
        USER_MAP.remove( player );
    }

    /**
     * Please note: This method will always return the playtime the user had on the join.
     * The playtime will not update until the user quits. <br>
     *
     * @return The playtime the user had on the join.
     */
    public long getPlayTime() {
        return playTime;
    }

    public void setPlayTime( long playTime ) {
        this.playTime = playTime;
    }

    /**
     * You don't want to access the method unless you want to know at which time the user joined.
     * This method only returns {@link System#currentTimeMillis()} at the time of the call.
     * <br>
     *
     * @return The time the user joined.
     */
    public long getJoinTime() {
        return joinTime;
    }

    public void setJoinTime( long joinTime ) {
        this.joinTime = joinTime;
    }
}
