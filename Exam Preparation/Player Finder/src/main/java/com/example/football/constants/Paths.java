package com.example.football.constants;

import java.nio.file.Path;

public enum Paths {
    ;
    public static Path TOWNS_PATH_JSON = Path.of("src/main/resources/files/json/towns.json");
    public static Path TEAMS_PATH_JSON = Path.of("src/main/resources/files/json/teams.json");
    public static Path STATS_PATH_XML = Path.of("src/main/resources/files/xml/stats.xml");
    public static Path PLAYERS_PATH_XML = Path.of("src/main/resources/files/xml/players.xml");
}
