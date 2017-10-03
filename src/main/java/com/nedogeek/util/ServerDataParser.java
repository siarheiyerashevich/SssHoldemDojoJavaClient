package com.nedogeek.util;

import com.nedogeek.model.Card;
import com.nedogeek.model.CardSuit;
import com.nedogeek.model.CardValue;
import com.nedogeek.model.MoveData;
import com.nedogeek.model.Player;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServerDataParser {

    public static MoveData parseMoveData(String incomingJson) {
        MoveData moveData = new MoveData();

        JSONObject json = new JSONObject(incomingJson);
        if (json.has("deskPot")) {
            moveData.setPot(json.getInt("deskPot"));
        }
        if (json.has("mover")) {
            moveData.setMover(json.getString("mover"));
        }
        if (json.has("dealer")) {
            moveData.setDealer(json.getString("dealer"));
        }
        if (json.has("gameRound")) {
            moveData.setGameRound(json.getString("gameRound"));
        }
        if (json.has("event")) {
            moveData.setEvent(parseEvent(json.getJSONArray("event")));
        }
        if (json.has("players")) {
            moveData.setPlayers(parsePlayers(json.getJSONArray("players")));
        }

        if (json.has("deskCards")) {
            moveData.setDeskCards(parseCards(((JSONArray) json.get("deskCards"))));
        }

        if (json.has("combination")) {
            moveData.setCardCombination(json.getString("combination"));
        }

        return moveData;
    }

    private static List<String> parseEvent(JSONArray eventJSON) {
        List<String> events = new ArrayList<>();

        for (int i = 0; i < eventJSON.length(); i++) {
            events.add(eventJSON.getString(i));
        }

        return events;
    }

    private static List<Player> parsePlayers(JSONArray playersJSON) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < playersJSON.length(); i++) {
            JSONObject playerJSON = (JSONObject) playersJSON.get(i);
            int balance = 0;
            int bet = 0;
            String status = "";
            String name = "";
            List<Card> cards = new ArrayList<>();

            if (playerJSON.has("balance")) {
                balance = playerJSON.getInt("balance");
            }
            if (playerJSON.has("pot")) {
                bet = playerJSON.getInt("pot");
            }
            if (playerJSON.has("status")) {
                status = playerJSON.getString("status");
            }
            if (playerJSON.has("name")) {
                name = playerJSON.getString("name");
            }
            if (playerJSON.has("cards")) {
                cards = parseCards((JSONArray) playerJSON.get("cards"));
            }

            players.add(new Player(name, balance, bet, status, cards));
        }

        return players;
    }

    private static List<Card> parseCards(JSONArray cardsJSON) {
        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < cardsJSON.length(); i++) {
            String cardSuit = ((JSONObject) cardsJSON.get(i)).getString("cardSuit");
            String cardValue = ((JSONObject) cardsJSON.get(i)).getString("cardValue");

            cards.add(new Card(CardSuit.valueOf(cardSuit), CardValue.valueOf(cardValue)));
        }

        return cards;
    }
}
