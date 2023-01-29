package com.starpony.paimon;

import java.io.*;
import java.util.Properties;

public class Configuration {
    private final String discordToken;

    Configuration() {
        // Загрузка конфигурации из переменных среды
        discordToken = System.getenv("DISCORD_BOT_TOKEN");
    }

    public String getDiscordToken() {
        return discordToken;
    }
}
