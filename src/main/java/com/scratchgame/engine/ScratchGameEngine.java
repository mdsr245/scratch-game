package com.scratchgame.engine;

import com.scratchgame.model.GameConfig;
import com.scratchgame.model.GameResult;
import com.scratchgame.model.*;
import java.util.*;

public class ScratchGameEngine {
    private Random random = new Random();

    public GameResult play(GameConfig config, double betAmount) {
        int rows = config.getRows() != null ? config.getRows() : 3;
        int columns = config.getColumns() != null ? config.getColumns() : 3;
        List<List<String>> matrix = new ArrayList<>();

        Map<String, Map<String, Integer>> cellProbabilities = new HashMap<>();
        if (config.getProbabilities() != null && config.getProbabilities().getStandardSymbols() != null) {
            for (ProbabilityConfig.StandardSymbolProbability prob : config.getProbabilities().getStandardSymbols()) {
                String key = prob.getRow() + ":" + prob.getColumn();
                cellProbabilities.put(key, prob.getSymbols());
            }
        }
        Map<String, Integer> defaultProb = null;
        if (!cellProbabilities.isEmpty()) {
            defaultProb = cellProbabilities.values().iterator().next();
        }

        for (int r = 0; r < rows; r++) {
            List<String> row = new ArrayList<>();
            for (int c = 0; c < columns; c++) {
                String key = r + ":" + c;
                Map<String, Integer> probs = cellProbabilities.getOrDefault(key, defaultProb);
                String symbol = pickSymbolByProbability(probs);
                row.add(symbol);
            }
            matrix.add(row);
        }

        String bonusSymbol = pickSymbolByProbability(
            config.getProbabilities().getBonusSymbols().getSymbols()
        );
        int bonusRow = random.nextInt(rows);
        int bonusCol = random.nextInt(columns);
        matrix.get(bonusRow).set(bonusCol, bonusSymbol);

        Map<String, Integer> symbolCounts = new HashMap<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                String s = matrix.get(r).get(c);
                Symbol symbolObj = config.getSymbols().get(s);
                if (symbolObj != null && "standard".equals(symbolObj.getType())) {
                    symbolCounts.put(s, symbolCounts.getOrDefault(s, 0) + 1);
                }
            }
        }
        Map<String, List<String>> appliedWinningCombinations = new HashMap<>();
        double totalReward = 0.0;
        Map<String, List<Map.Entry<String, WinCombination>>> groupToCombinations = new HashMap<>();
        for (Map.Entry<String, WinCombination> entry : config.getWinCombinations().entrySet()) {
            String group = entry.getValue().getGroup();
            if (group != null) {
                groupToCombinations.computeIfAbsent(group, k -> new ArrayList<>()).add(entry);
            }
        }
        for (String symbol : symbolCounts.keySet()) {
            double symbolReward = 0.0;
            List<String> applied = new ArrayList<>();
            double base = betAmount * config.getSymbols().get(symbol).getRewardMultiplier();
            double multiplier = 1.0;
            for (Map.Entry<String, List<Map.Entry<String, WinCombination>>> groupEntry : groupToCombinations.entrySet()) {
                String group = groupEntry.getKey();
                List<Map.Entry<String, WinCombination>> combos = groupEntry.getValue();
                Map.Entry<String, WinCombination> best = null;
                for (Map.Entry<String, WinCombination> entry : combos) {
                    WinCombination wc = entry.getValue();
                    boolean applies = false;
                    if ("same_symbols".equals(wc.getWhen()) && symbolCounts.get(symbol) >= (wc.getCount() != null ? wc.getCount() : 0)) {
                        applies = true;
                    } else if ("linear_symbols".equals(wc.getWhen()) && wc.getCoveredAreas() != null) {
                        for (List<String> area : wc.getCoveredAreas()) {
                            boolean allMatch = true;
                            for (String pos : area) {
                                String[] parts = pos.split(":");
                                int r = Integer.parseInt(parts[0]);
                                int c = Integer.parseInt(parts[1]);
                                if (!symbol.equals(matrix.get(r).get(c))) {
                                    allMatch = false;
                                    break;
                                }
                            }
                            if (allMatch) {
                                applies = true;
                                break;
                            }
                        }
                    }
                    if (applies) {
                        if (best == null) {
                            best = entry;
                        } else {
                            WinCombination bestWC = best.getValue();
                            int bestCount = bestWC.getCount() != null ? bestWC.getCount() : 0;
                            int currCount = wc.getCount() != null ? wc.getCount() : 0;
                            if (currCount > bestCount || (currCount == bestCount && wc.getRewardMultiplier() > bestWC.getRewardMultiplier())) {
                                best = entry;
                            }
                        }
                    }
                }
                if (best != null) {
                    multiplier *= best.getValue().getRewardMultiplier();
                    applied.add(best.getKey());
                }
            }
            if (!applied.isEmpty()) {
                symbolReward = base * multiplier;
                totalReward += symbolReward;
                appliedWinningCombinations.put(symbol, applied);
            }
        }
        String appliedBonus = null;
        if (totalReward > 0) {
            Symbol bonusObj = config.getSymbols().get(bonusSymbol);
            if (bonusObj != null && "bonus".equals(bonusObj.getType())) {
                appliedBonus = bonusSymbol;
                if ("multiply_reward".equals(bonusObj.getImpact())) {
                    totalReward *= bonusObj.getRewardMultiplier();
                } else if ("extra_bonus".equals(bonusObj.getImpact())) {
                    totalReward += bonusObj.getExtra();
                }
            }
        }
        GameResult result = new GameResult();
        result.setMatrix(matrix);
        result.setReward(Math.round(totalReward));
        result.setAppliedWinningCombinations(appliedWinningCombinations.isEmpty() ? null : appliedWinningCombinations);
        result.setAppliedBonusSymbol(appliedBonus);
        return result;
    }

    private String pickSymbolByProbability(Map<String, Integer> probs) {
        if (probs == null || probs.isEmpty()) return null;
        int total = probs.values().stream().mapToInt(i -> i).sum();
        int rand = random.nextInt(total);
        int cumulative = 0;
        for (Map.Entry<String, Integer> entry : probs.entrySet()) {
            cumulative += entry.getValue();
            if (rand < cumulative) {
                return entry.getKey();
            }
        }
        return probs.keySet().iterator().next();
    }
} 