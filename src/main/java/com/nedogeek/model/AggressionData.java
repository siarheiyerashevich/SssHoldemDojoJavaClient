package com.nedogeek.model;

public class AggressionData {

    private long callCount;
    private long raiseCount;
    private long threeBetCount;
    private long fourBetPlusCount;

    public AggressionData() {
    }

    public AggressionData(long callCount, long raiseCount, long fourBetPlusCount) {
        this.callCount = callCount;
        this.raiseCount = raiseCount;
        this.fourBetPlusCount = fourBetPlusCount;
    }

    public long getCallCount() {
        return callCount;
    }

    public void incrementCallCount() {
        callCount++;
    }


    public void decrementCallCount() {
        callCount--;
    }

    public long getRaiseCount() {
        return raiseCount;
    }

    public void incrementRaiseCount() {
        raiseCount++;
    }

    public void decrementRaiseCount() {
        raiseCount--;
    }


    public long getThreeBetCount() {
        return threeBetCount;
    }


    public void incrementThreeBetCount() {
        threeBetCount++;
    }

    public void decrementThreeBetCount() {
        threeBetCount--;
    }

    public long getFourBetPlusCount() {
        return fourBetPlusCount;
    }


    public void incrementFourBetPlusCount() {
        fourBetPlusCount++;
    }
}
