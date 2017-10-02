package com.nedogeek.model;

public class AggressionData {

    private long callCount;
    private long raiseCount;

    public AggressionData(long callCount, long raiseCount) {
        this.callCount = callCount;
        this.raiseCount = raiseCount;
    }

    public long getCallCount() {
        return callCount;
    }

    public void setCallCount(long callCount) {
        this.callCount = callCount;
    }

    public long getRaiseCount() {
        return raiseCount;
    }

    public void setRaiseCount(long raiseCount) {
        this.raiseCount = raiseCount;
    }
}
