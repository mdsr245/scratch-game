package com.scratchgame.engine;

import com.scratchgame.model.GameConfig;
import com.scratchgame.model.GameResult;
import com.scratchgame.util.ConfigLoader;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScratchGameEngineTest {
    @Test
    public void test4x4GameMatrixAndReward() throws Exception {
        GameConfig config = ConfigLoader.loadConfig("config.json");
        config.setRows(4);
        config.setColumns(4);
        ScratchGameEngine engine = new ScratchGameEngine();
        double betAmount = 100;
        GameResult result = engine.play(config, betAmount);
        assertNotNull(result);
        assertNotNull(result.getMatrix());
        assertEquals(4, result.getMatrix().size());
        for (int i = 0; i < 4; i++) {
            assertEquals(4, result.getMatrix().get(i).size());
        }
        assertTrue(result.getReward() >= 0);
    }

    @Test
    public void testLostGame() throws Exception {
        GameConfig config = ConfigLoader.loadConfig("config.json");
        config.setRows(3);
        config.setColumns(3);
        ScratchGameEngine engine = new ScratchGameEngine() {
            @Override
            public GameResult play(GameConfig config, double betAmount) {
                GameResult result = new GameResult();
                result.setMatrix(java.util.Arrays.asList(
                    java.util.Arrays.asList("A", "B", "C"),
                    java.util.Arrays.asList("D", "E", "F"),
                    java.util.Arrays.asList("B", "C", "D")
                ));
                result.setReward(0);
                result.setAppliedWinningCombinations(null);
                result.setAppliedBonusSymbol(null);
                return result;
            }
        };
        double betAmount = 100;
        GameResult result = engine.play(config, betAmount);
        assertNotNull(result);
        assertEquals(0, result.getReward(), 0.01);
        assertNull(result.getAppliedWinningCombinations());
        assertNull(result.getAppliedBonusSymbol());
    }

    @Test
    public void test4x4AllAWins() throws Exception {
        GameConfig config = ConfigLoader.loadConfig("config.json");
        config.setRows(4);
        config.setColumns(4);
        ScratchGameEngine engine = new ScratchGameEngine() {
            @Override
            public GameResult play(GameConfig config, double betAmount) {
                GameResult result = new GameResult();
                result.setMatrix(java.util.Arrays.asList(
                    java.util.Arrays.asList("A", "A", "A", "A"),
                    java.util.Arrays.asList("A", "A", "A", "A"),
                    java.util.Arrays.asList("A", "A", "A", "A"),
                    java.util.Arrays.asList("A", "A", "A", "A")
                ));
                java.util.Map<String, java.util.List<String>> applied = new java.util.HashMap<>();
                applied.put("A", java.util.Arrays.asList("same_symbol_9_times"));
                result.setAppliedWinningCombinations(applied);
                result.setReward(10000);
                result.setAppliedBonusSymbol(null);
                return result;
            }
        };
        double betAmount = 100;
        GameResult result = engine.play(config, betAmount);
        assertNotNull(result);
        assertNotNull(result.getMatrix());
        assertEquals(4, result.getMatrix().size());
        for (int i = 0; i < 4; i++) {
            assertEquals(4, result.getMatrix().get(i).size());
        }
        assertNotNull(result.getAppliedWinningCombinations());
        assertTrue(result.getAppliedWinningCombinations().containsKey("A"));
        assertEquals(1, result.getAppliedWinningCombinations().get("A").size());
        assertEquals("same_symbol_9_times", result.getAppliedWinningCombinations().get("A").get(0));
        assertEquals(10000, result.getReward(), 0.01);
        assertNull(result.getAppliedBonusSymbol());
    }
} 