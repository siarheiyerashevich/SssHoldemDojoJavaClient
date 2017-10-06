package com.nedogeek.strategy.preflop;

import com.nedogeek.context.HandContext;
import com.nedogeek.model.MoveResponse;
import com.nedogeek.model.Position;
import com.nedogeek.model.TableType;
import com.nedogeek.strategy.Strategy;
import com.nedogeek.util.MoveDataAnalyzer;

public abstract class PreFlopActionStrategy implements Strategy {

    @Override
    public MoveResponse evaluateResponse() {
        double winProbability = MoveDataAnalyzer.calculateHandWinProbability();
        if (winProbability >= calculateRaiseProbabilityLimit()) {
            int raiseAmount = MoveDataAnalyzer.calculateRaiseAmount();
            int callAmount = MoveDataAnalyzer.calculateCallAmount();
            int ownBalance = MoveDataAnalyzer.calculateOwnBalance();

            System.out.println("{\"raising\": {" +
                               "\"raiseAmount\":" + raiseAmount + ","
                               + "\"callAmount\":" + callAmount + ","
                               + "\"ownBalance\":" + ownBalance + ","
                               + "\"winProbability\":" + winProbability + ","
                               + "}},");

            if (callAmount > raiseAmount) {
                return MoveResponse.RAISE_MOVE_RESPONSE.withAmount(callAmount * 3);
            }

            return ownBalance > raiseAmount * 2 ?
                   MoveResponse.RAISE_MOVE_RESPONSE.withAmount(raiseAmount) :
                   MoveResponse.ALL_IN_MOVE_RESPONSE;
        } else if (winProbability >= calculateCallProbabilityLimit()) {
            int callAmount = MoveDataAnalyzer.calculateCallAmount();
            int ownBalance = MoveDataAnalyzer.calculateOwnBalance();

            System.out.println("{\"calling\": {" +
                               "\"callAmount\":" + callAmount + ","
                               + "\"ownBalance\":" + ownBalance + ","
                               + "\"winProbability\":" + winProbability + ","
                               + "}},");

            return ownBalance > callAmount * 2 ?
                   MoveResponse.CALL_MOVE_RESPONSE :
                   MoveResponse.ALL_IN_MOVE_RESPONSE;
        } else {
            return MoveResponse.CHECK_MOVE_RESPONSE;
        }
    }

    private double calculateRaiseProbabilityLimit() {
        double initialRaiseProbabilityLimit = getInitialRaiseProbabilityLimit();
        double positionMargin = calculatePositionMargin();
        double tableTypeMargin = calculateTableTypeMargin();
        double result = initialRaiseProbabilityLimit - positionMargin - tableTypeMargin;

        System.out.println("{\"raiseProbabilityLimit\": {" +
                           "\"initialRaiseProbabilityLimit\":" + initialRaiseProbabilityLimit + ","
                           + "\"positionMargin\":" + positionMargin + ","
                           + "\"tableTypeMargin\":" + tableTypeMargin + ","
                           + "\"result\":" + result + ","
                           + "}},");

        return result;
    }

    private double calculateCallProbabilityLimit() {
        double initialCallProbabilityLimit = getInitialCallProbabilityLimit();
        double positionMargin = calculatePositionMargin();
        double tableTypeMargin = calculateTableTypeMargin();
        double result = initialCallProbabilityLimit - positionMargin - tableTypeMargin;

        System.out.println("{\"callProbabilityLimit\": {" +
                           "\"initialCallProbabilityLimit\":" + initialCallProbabilityLimit + ","
                           + "\"positionMargin\":" + positionMargin + ","
                           + "\"tableTypeMargin\":" + tableTypeMargin + ","
                           + "\"result\":" + result + ","
                           + "}},");

        return result;
    }

    private double calculatePositionMargin() {
        Position position = HandContext.INSTANCE.getPosition();
        switch (position) {
            case BIG_BLIND:
            case SMALL_BLIND:
                return 0.1;
            case BUTTON:
            case CUT_OFF:
                return 0.05;
            case MIDDLE_POSITION:
                return 0;
            case UNDER_THE_GUN:
                return -0.02;
            default:
                return 0;
        }
    }

    private double calculateTableTypeMargin() {
        TableType tableType = HandContext.INSTANCE.getTableType();
        switch (tableType) {
            case LONG:
                return 0;
            case MEDIUM:
                return 0.05;
            case SHORT:
                return 0.1;
            case HEADS_UP:
                return 0.2;
            default:
                return 0;
        }
    }

    public abstract double getInitialRaiseProbabilityLimit();

    public abstract double getInitialCallProbabilityLimit();
}
