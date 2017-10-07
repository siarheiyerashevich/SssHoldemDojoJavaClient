package com.nedogeek.model;

public class MoveResponse {

    public static final MoveResponse CHECK_MOVE_RESPONSE = new MoveResponse(Commands.Check);
    public static final MoveResponse CALL_MOVE_RESPONSE = new MoveResponse(Commands.Call);
    public static final MoveResponse RAISE_MOVE_RESPONSE = new MoveResponse(Commands.Rise);
    public static final MoveResponse ALL_IN_MOVE_RESPONSE = new MoveResponse(Commands.AllIn);

    private Commands command;
    private Integer raiseAmount;

    private MoveResponse(Commands command) {
        this.command = command;
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

    public MoveResponse withAmount(Integer raiseAmount) {
        this.raiseAmount = raiseAmount;
        return this;
    }
}
