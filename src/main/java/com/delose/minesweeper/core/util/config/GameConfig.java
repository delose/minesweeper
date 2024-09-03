package com.delose.minesweeper.core.util.config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * Configuration class for the Minesweeper game.
 * Provides constants and methods for configuring the game's settings.
 */
public class GameConfig {

    private static final GameConfig INSTANCE = new GameConfig();

    private int minGridSize;
    private int maxGridSize;
    private double maxMineRatio;

    private GameConfig() {
        loadConfig();
    }

    public static GameConfig getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    private void loadConfig() {
        Yaml yaml = new Yaml();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("config.yml")) {
            Map<String, Object> yamlMap = yaml.load(in);
            Map<String, Object> minesweeper = (Map<String, Object>) yamlMap.get("minesweeper");

            Map<String, Integer> gridConfig = (Map<String, Integer>) minesweeper.get("grid");
            this.minGridSize = gridConfig.get("min-size");
            this.maxGridSize = gridConfig.get("max-size");

            Map<String, Double> minesConfig = (Map<String, Double>) minesweeper.get("mines");
            this.maxMineRatio = minesConfig.get("max-ratio");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration from config.yml", e);
        }
    }

    public int getMinGridSize() {
        return minGridSize;
    }

    public int getMaxGridSize() {
        return maxGridSize;
    }

    public double getMaxMineRatio() {
        return maxMineRatio;
    }
}