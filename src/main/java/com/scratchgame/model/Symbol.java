package com.scratchgame.model;

public class Symbol {
    private String name;
    private String type;
    private Double rewardMultiplier;
    private Integer extra;
    private String impact;

    public Symbol() {}
    public Symbol(String name, String type, Double rewardMultiplier, Integer extra, String impact) {
        this.name = name;
        this.type = type;
        this.rewardMultiplier = rewardMultiplier;
        this.extra = extra;
        this.impact = impact;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Double getRewardMultiplier() { return rewardMultiplier; }
    public void setRewardMultiplier(Double rewardMultiplier) { this.rewardMultiplier = rewardMultiplier; }

    public Integer getExtra() { return extra; }
    public void setExtra(Integer extra) { this.extra = extra; }

    public String getImpact() { return impact; }
    public void setImpact(String impact) { this.impact = impact; }
} 