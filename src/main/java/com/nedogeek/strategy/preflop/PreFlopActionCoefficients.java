package com.nedogeek.strategy.preflop;

public class PreFlopActionCoefficients {

    public static final double NO_ACTIONS_RAISE_COEFFICIENT = 0.9;
    public static final double NO_ACTIONS_CALL_COEFFICIENT = 0.8;

    public static final double CALL_RAISE_COEFFICIENT = 0.9;
    public static final double CALL_CALL_COEFFICIENT = 0.8;

    public static final double RAISE_RAISE_COEFFICIENT = 0.93;
    public static final double RAISE_CALL_COEFFICIENT = 0.86;

    public static final double THREE_BET_RAISE_COEFFICIENT = 0.95;
    public static final double THREE_BET_CALL_COEFFICIENT = 0.9;

    public static final double FOUR_BET_PLUS_RAISE_COEFFICIENT = 0.97;
    public static final double FOUR_BET_PLUS_CALL_COEFFICIENT = 0.94;
}
