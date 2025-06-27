package com.scratchgame.model;

import java.util.List;
import java.util.Map;

public class ProbabilityConfig {
    public static class StandardSymbolProbability {
        private int column;
        private int row;
        private Map<String, Integer> symbolProbabilities;

        public StandardSymbolProbability() {}
        public StandardSymbolProbability(int column, int row, Map<String, Integer> symbolProbabilities) {
            this.column = column;
            this.row = row;
            this.symbolProbabilities = symbolProbabilities;
        }
        public int getColumn() { return column; }
        public void setColumn(int column) { this.column = column; }
        public int getRow() { return row; }
        public void setRow(int row) { this.row = row; }
        public Map<String, Integer> getSymbols() { return symbolProbabilities; }
        public void setSymbols(Map<String, Integer> symbolProbabilities) { this.symbolProbabilities = symbolProbabilities; }
    }

    public static class BonusSymbolProbability {
        private Map<String, Integer> symbolProbabilities;
        public BonusSymbolProbability() {}
        public BonusSymbolProbability(Map<String, Integer> symbolProbabilities) {
            this.symbolProbabilities = symbolProbabilities;
        }
        public Map<String, Integer> getSymbols() { return symbolProbabilities; }
        public void setSymbols(Map<String, Integer> symbolProbabilities) { this.symbolProbabilities = symbolProbabilities; }
    }

    private List<StandardSymbolProbability> standardSymbols;
    private BonusSymbolProbability bonusSymbols;

    public List<StandardSymbolProbability> getStandardSymbols() { return standardSymbols; }
    public void setStandardSymbols(List<StandardSymbolProbability> standardSymbols) { this.standardSymbols = standardSymbols; }
    public BonusSymbolProbability getBonusSymbols() { return bonusSymbols; }
    public void setBonusSymbols(BonusSymbolProbability bonusSymbols) { this.bonusSymbols = bonusSymbols; }
} 