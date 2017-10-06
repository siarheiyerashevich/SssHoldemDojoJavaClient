package com.nedogeek.strategy;

import com.nedogeek.model.MoveResponse;

public class CheckStrategy implements Strategy {

    @Override
    public MoveResponse evaluateResponse() {
        return MoveResponse.CHECK_MOVE_RESPONSE;
    }
}
