package com.xkodxdf.app.entities.animate;

public class Characteristics {

    private final int healthPoints;
    private final int viewRadius;
    private final int metabolicRate;
    private final int hungerThreshold;
    private final int starvationThreshold;
    private final int satiateHungerDecrease;
    private final int satiateHealthIncrease;
    private final int attackStrength;

    protected Characteristics(int healthPoints, int viewRadius, int metabolicRate, int hungerThreshold,
                              int starvationThreshold, int satiateHungerDecrease, int satiateHealthIncrease,
                              int attackStrength) {
        this.healthPoints = healthPoints;
        this.viewRadius = viewRadius;
        this.metabolicRate = metabolicRate;
        this.hungerThreshold = hungerThreshold;
        this.starvationThreshold = starvationThreshold;
        this.satiateHungerDecrease = satiateHungerDecrease;
        this.satiateHealthIncrease = satiateHealthIncrease;
        this.attackStrength = attackStrength;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getViewRadius() {
        return viewRadius;
    }

    public int getMetabolicRate() {
        return metabolicRate;
    }

    public int getHungerThreshold() {
        return hungerThreshold;
    }

    public int getStarvationThreshold() {
        return starvationThreshold;
    }

    public int getSatiateHungerDecrease() {
        return satiateHungerDecrease;
    }

    public int getSatiateHealthIncrease() {
        return satiateHealthIncrease;
    }

    public int getAttackStrength() {
        return attackStrength;
    }
}
