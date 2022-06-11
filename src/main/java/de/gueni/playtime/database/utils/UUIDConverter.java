package de.gueni.playtime.database.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Converts between UUID and byte array. This is needed because the database stores UUIDs as byte arrays.
 * From: https://github.com/RainbowDashLabs/BasicSQLPlugin/blob/1e76321a0ab889cb3ae5245e659c83a626f69aea/src/main/java/de/chojo/simplecoins/util/UUIDConverter.java
 */
public class UUIDConverter {
    private UUIDConverter() throws InstantiationException {
        throw new InstantiationException( "This class is not meant to be instantiated" );
    }

    /**
     * Converts a UUID to a byte array.
     *
     * @param uuid The UUID to convert.
     * @return The converted byte array.
     */
    public static byte[] convert( UUID uuid ) {
        return ByteBuffer.wrap( new byte[16] )
                .putLong( uuid.getMostSignificantBits() )
                .putLong( uuid.getLeastSignificantBits() )
                .array();
    }

    /**
     * Converts a byte array to a UUID.
     *
     * @param bytes The byte array to convert.
     * @return The converted UUID.
     */
    public static UUID convert( byte[] bytes ) {
        var buffer = ByteBuffer.wrap( bytes );
        return new UUID( buffer.getLong(), buffer.getLong() );
    }
}