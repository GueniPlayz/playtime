CREATE TABLE IF NOT EXISTS playtime_players
(
    uuid     BINARY(16) UNIQUE NOT NULL PRIMARY KEY,
    name     VARCHAR(16)       NOT NULL,
    playtime LONG              NOT NULL DEFAULT 0
);