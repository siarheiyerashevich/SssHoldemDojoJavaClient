package com.nedogeek.model;

public class MoveResponse {

    private Commands command;
    private Integer raiseAmount;

    public MoveResponse(Commands command) {
        this.command = command;
    }

    public MoveResponse(Commands command, Integer raiseAmount) {
        this(command);
        this.raiseAmount = raiseAmount;
    }

    public Commands getCommand() {
        return command;
    }

    public void setCommand(Commands command) {
        this.command = command;
    }

    public Integer getRaiseAmount() {
        return raiseAmount;
    }

    public void setRaiseAmount(Integer raiseAmount) {
        this.raiseAmount = raiseAmount;
    }
}
