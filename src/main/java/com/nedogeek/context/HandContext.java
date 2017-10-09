package com.nedogeek.context;

import com.nedogeek.model.Position;
import com.nedogeek.model.TableType;

public enum HandContext {
    INSTANCE;

    private Position position;
    private TableType tableType;
    private int initialCardsWeight;
    private int bigBlindAmount;
    private String aggressor;

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

    public void resetContext() {
        position = null;
        tableType = null;
        initialCardsWeight = -1;
        bigBlindAmount = 0;
        aggressor = null;
    }
}
