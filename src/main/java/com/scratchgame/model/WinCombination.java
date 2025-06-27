package com.scratchgame.model;

import java.util.List;

public class WinCombination {
    private String name;
    private Double rewardMultiplier;
    private String when;
    private Integer count;
    private String group;
    private List<List<String>> coveredAreas;

    public WinCombination() {}
    public WinCombination(String name, Double rewardMultiplier, String when, Integer count, String group, List<List<String>> coveredAreas) {
        this.name = name;
        this.rewardMultiplier = rewardMultiplier;
        this.when = when;
        this.count = count;
        this.group = group;
        this.coveredAreas = coveredAreas;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getRewardMultiplier() { return rewardMultiplier; }
    public void setRewardMultiplier(Double rewardMultiplier) { this.rewardMultiplier = rewardMultiplier; }

    public String getWhen() { return when; }
    public void setWhen(String when) { this.when = when; }

    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }

    public String getGroup() { return group; }
    public void setGroup(String group) { this.group = group; }

    public List<List<String>> getCoveredAreas() { return coveredAreas; }
    public void setCoveredAreas(List<List<String>> coveredAreas) { this.coveredAreas = coveredAreas; }
} 