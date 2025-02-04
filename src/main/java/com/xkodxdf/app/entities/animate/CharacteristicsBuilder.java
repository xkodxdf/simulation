package com.xkodxdf.app.entities.animate;

import java.util.concurrent.ThreadLocalRandom;

public class CharacteristicsBuilder {

    private int baseHealthPoints = 100;
    private int baseViewRadius = 1;
    private int baseMetabolicRate = 5;
    private int baseHungerThreshold = 20;
    private int baseStarvationThreshold = 100;
    private int baseSatiateHungerDecrease = 20;
    private int baseSatiateHealthIncrease = 20;
    private int baseAttackStrength = 0;

    protected Characteristics build() {
        return new Characteristics(
                baseHealthPoints,
                baseViewRadius,
                baseMetabolicRate,
                baseHungerThreshold,
                baseStarvationThreshold,
                baseSatiateHungerDecrease,
                baseSatiateHealthIncrease,
                baseAttackStrength
        );
    }

    protected CharacteristicsBuilder setHealthPointsInRange(int from, int to) {
        this.baseHealthPoints = getRandomIntFromTo(from, to);
        return this;
    }

    protected CharacteristicsBuilder setViewRadiusInRange(int from, int to) {
        this.baseViewRadius = getRandomIntFromTo(from, to);
        return this;
    }

    protected CharacteristicsBuilder setMetabolicRateInRange(int from, int to) {
        this.baseMetabolicRate = getRandomIntFromTo(from, to);
        return this;
    }

    protected CharacteristicsBuilder setHungerThresholdInRange(int from, int to) {
        this.baseHungerThreshold = getRandomIntFromTo(from, to);
        return this;
    }

    protected CharacteristicsBuilder setStarvationThresholdInRange(int from, int to) {
        this.baseStarvationThreshold = getRandomIntFromTo(from, to);
        return this;
    }

    protected CharacteristicsBuilder setSatiateHungerDecreaseInRange(int from, int to) {
        this.baseSatiateHungerDecrease = getRandomIntFromTo(from, to);
        return this;
    }

    protected CharacteristicsBuilder setSatiateHealthIncreaseInRange(int from, int to) {
        this.baseSatiateHealthIncrease = getRandomIntFromTo(from, to);
        return this;
    }

    protected CharacteristicsBuilder setAttackStrengthInRange(int from, int to) {
        this.baseAttackStrength = getRandomIntFromTo(from, to);
        return this;
    }

    private int getRandomIntFromTo(int fromIncl, int toIncl) {
        return ThreadLocalRandom.current().nextInt(fromIncl, toIncl + 1);
    }
}
