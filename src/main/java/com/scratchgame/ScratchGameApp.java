package com.scratchgame;

import com.scratchgame.model.GameConfig;
import com.scratchgame.util.ConfigLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scratchgame.engine.ScratchGameEngine;
import com.scratchgame.model.GameResult;

public class ScratchGameApp {
    public static void main(String[] args) {
        String configPath = null;
        Double betAmount = null;
        for (int i = 0; i < args.length; i++) {
            if ("--config".equals(args[i]) && i + 1 < args.length) {
                configPath = args[++i];
            } else if ("--betting-amount".equals(args[i]) && i + 1 < args.length) {
                betAmount = Double.parseDouble(args[++i]);
            }
        }
        if (configPath == null || betAmount == null) {
            System.err.println("PLEASE USE LIKE: java -jar <jar-file> --config <config.json> --betting-amount <amount>");
            System.exit(1);
        }
        try {
            GameConfig config = ConfigLoader.loadConfig(configPath);
            ScratchGameEngine engine = new ScratchGameEngine();
            GameResult result = engine.play(config, betAmount);
            ObjectMapper mapper = new ObjectMapper();
            String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
            System.out.println(jsonResult);
        } catch (Exception e) {
            System.err.println("Failed to load config: " + e.getMessage());
            System.exit(2);
        }
    }
} 