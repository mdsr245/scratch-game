package com.scratchgame.model;

import java.util.List;
import java.util.Map;

public class GameResult {
    private List<List<String>> matrix;
    private double reward;
    private Map<String, List<String>> appliedWinningCombinations;
    private String appliedBonusSymbol;

    public GameResult() {}
    public GameResult(List<List<String>> matrix, double reward, Map<String, List<String>> appliedWinningCombinations, String appliedBonusSymbol) {
        this.matrix = matrix;
        this.reward = reward;
        this.appliedWinningCombinations = appliedWinningCombinations;
        this.appliedBonusSymbol = appliedBonusSymbol;
    }

    public List<List<String>> getMatrix() { return matrix; }
    public void setMatrix(List<List<String>> matrix) { this.matrix = matrix; }

    public double getReward() { return reward; }
    public void setReward(double reward) { this.reward = reward; }

    public Map<String, List<String>> getAppliedWinningCombinations() { return appliedWinningCombinations; }
    public void setAppliedWinningCombinations(Map<String, List<String>> appliedWinningCombinations) { this.appliedWinningCombinations = appliedWinningCombinations; }

    public String getAppliedBonusSymbol() { return appliedBonusSymbol; }
    public void setAppliedBonusSymbol(String appliedBonusSymbol) { this.appliedBonusSymbol = appliedBonusSymbol; }
} 