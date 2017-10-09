package com.nedogeek.context;

import com.nedogeek.model.PlayerStatus;
import com.nedogeek.model.Position;
import com.nedogeek.model.TableType;

import java.util.HashMap;
import java.util.Map;

public enum HandContext {
    INSTANCE;

    private Position position;
    private TableType tableType;
    private int initialCardsWeight;
    private int bigBlindAmount;
    private String aggressor;
    private Map<String, PlayerStatus> preFlopStatusMap = new HashMap<>();

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public TableType getTableType() {
        return tableType;
    }

    public void setTableType(TableType tableType) {
        this.tableType = tableType;
    }

    public int getInitialCardsWeight() {
        return initialCardsWeight;
    }

    public void setInitialCardsWeight(int initialCardsWeight) {
        this.initialCardsWeight = initialCardsWeight;
    }

    public int getBigBlindAmount() {
        return bigBlindAmount;
    }

    public void setBigBlindAmount(int bigBlindAmount) {
        this.bigBlindAmount = bigBlindAmount;
    }

    public String getAggressor() {
        return aggressor;
    }

    public void setAggressor(String aggressor) {
        this.aggressor = aggressor;
    }

    public Map<String, PlayerStatus> getPreFlopStatusMap() {
        return preFlopStatusMap;
    }

    public void setPreFlopStatusMap(Map<String, PlayerStatus> preFlopStatusMap) {
        this.preFlopStatusMap = preFlopStatusMap;
    }

    public void resetContext() {
        position = null;
        tableType = null;
        initialCardsWeight = -1;
        bigBlindAmount = 0;
        aggressor = null;
        preFlopStatusMap = new HashMap<>();
    }
}
