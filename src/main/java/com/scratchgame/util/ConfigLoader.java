package com.scratchgame.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.scratchgame.model.GameConfig;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {
    public static GameConfig loadConfig(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return mapper.readValue(new File(path), GameConfig.class);
    }
} 