package com.nedogeek.strategy.postflop;

import java.util.List;
import java.util.stream.Collectors;

import com.nedogeek.context.MoveContext;
import com.nedogeek.model.MoveResponse;
import com.nedogeek.model.Player;
import com.nedogeek.util.MoveDataAnalyzer;

public class RaisePostFlopStrategy extends PostFlopActionStrategy {

    @Override
    public MoveResponse evaluateResponse() {

        List<String> players = MoveContext.INSTANCE.getPlayers().stream().map(Player::getName).collect(Collectors.toList());
        double ownEquity = getEquityVsPairPlus(players);
        int ownBalance = MoveDataAnalyzer.calculateOwnBalance();
        int callAmount = MoveDataAnalyzer.calculateCallAmount();
        int pot = MoveContext.INSTANCE.getPot();
        double ev = ownEquity * pot - (1 - ownEquity) * callAmount;
        double evPossibleAllIn = ownEquity * pot - (1 - ownEquity) * ownBalance;

        if(ev > 0) {

            if(ownBalance < callAmount * 3) {
                if(evPossibleAllIn > 0) {
                    return MoveResponse.ALL_IN_MOVE_RESPONSE;
                } else {
                    return MoveResponse.CHECK_MOVE_RESPONSE;
                }
            }
            // TODO: Add 3-bet/allin logic

            return MoveResponse.CALL_MOVE_RESPONSE;
        }

        return MoveResponse.CHECK_MOVE_RESPONSE;
    }
}
