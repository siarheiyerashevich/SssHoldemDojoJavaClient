package com.nedogeek.context;

import com.nedogeek.model.AggressionData;

import java.util.HashMap;
import java.util.Map;

public enum GameContext {
    INSTANCE;

    public static int MINIMAL_VALID_STATS_HAND_COUNT = 50;

    private int handsCount;
    private Map<String, AggressionData> aggressionMap = new HashMap<>();

    public int getHandsCount() {
        return handsCount;
    }

    public void incrementHandsCount() {
        handsCount++;
    }

    public Map<String, AggressionData> getAggressionMap() {
        return aggressionMap;
    }

    public void resetContext() {
        handsCount = 0;
        aggressionMap = new HashMap<>();
    }
}
