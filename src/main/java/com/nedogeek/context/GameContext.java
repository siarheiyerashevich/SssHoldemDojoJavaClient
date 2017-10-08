package com.nedogeek.context;

import com.nedogeek.model.AggressionData;

import java.util.HashMap;
import java.util.Map;

public enum GameContext {
    INSTANCE;

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
    }
}
