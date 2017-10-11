package com.nedogeek;


import com.nedogeek.context.GameContext;
import com.nedogeek.context.HandContext;
import com.nedogeek.context.MoveContext;
import com.nedogeek.context.StreetContext;
import com.nedogeek.model.MoveResponse;
import com.nedogeek.model.Round;
import com.nedogeek.strategy.StrategyFactory;
import com.nedogeek.util.MoveDataAnalyzer;
import com.nedogeek.util.ServerDataParser;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


import sun.rmi.runtime.Log;


public class Client {

    public static final String USER_NAME = "SSS";
    private static final String PASSWORD = "abc123";

    private static final String SERVER = "ws://10.6.99.50:8080/ws";

//    private Logger logger = Logger.getLogger("Client");

    private WebSocket.Connection connection;

    public static void main(String[] args) {
        Client client = new Client();
        client.con();
    }

    private void con() {
        WebSocketClientFactory factory = new WebSocketClientFactory();
        try {
            factory.start();

            WebSocketClient client = factory.newWebSocketClient();

            connection = client.open(new URI(SERVER + "?user=" + USER_NAME + "&password=" + PASSWORD),
                                     new WebSocket.OnTextMessage() {
                                         public void onOpen(Connection connection) {
                                             System.out.println("Opened");
                                         }

                                         public void onClose(int closeCode, String message) {
                                             System.out.println(GameContext.INSTANCE.getHandsCount() + " hands played");
                                             System.out.println("Closed");
                                         }

                                         public void onMessage(String data) {
                                             System.out.println(data + ",");
                                             ServerDataParser.parseMoveData(data);

                                             if (StreetContext.INSTANCE.getRound() == Round.PRE_FLOP) {
                                                 MoveDataAnalyzer.calculatePreFlopAggression();
                                             }

                                             StreetContext.INSTANCE.setRound(MoveDataAnalyzer.calculateRound());

                                             String event = MoveContext.INSTANCE.getEvent().get(0);
                                             if (event.equalsIgnoreCase("New game started")) {
                                                 GameContext.INSTANCE.incrementHandsCount();

                                                 HandContext.INSTANCE.resetContext();
                                                 HandContext.INSTANCE.setPosition(MoveDataAnalyzer.calculatePosition());
                                                 HandContext.INSTANCE
                                                         .setTableType(MoveDataAnalyzer.calculateTableType());
                                                 HandContext.INSTANCE.setInitialCardsWeight(
                                                         MoveDataAnalyzer.calculateInitialCardsWeight());
                                                 HandContext.INSTANCE
                                                         .setBigBlindAmount(MoveDataAnalyzer.calculateBigBlindAmount());

                                                 System.out.println(
                                                         "{\"newPosition\": " + HandContext.INSTANCE.getPosition() +
                                                         "},");
                                                 System.out.println("{\"newInitialCardsWeight\": " +
                                                                    HandContext.INSTANCE.getInitialCardsWeight() +
                                                                    "},");
                                             } else if (event.endsWith(" game round started.")) {
                                                 StreetContext.INSTANCE.resetContext();
                                             }

                                             if (USER_NAME.equalsIgnoreCase(MoveContext.INSTANCE.getMover()) &&
                                                 event.startsWith(USER_NAME)) {
                                                 MoveResponse moveResponse = MoveResponse.CHECK_MOVE_RESPONSE;
                                                 try {
                                                     StrategyFactory.INSTANCE.calculateRoundStrategy()
                                                             .evaluateResponse();
                                                 } catch (Exception e) {
//                                                     logger.log(Level.);
                                                     System.out.println("Unexpected exception");
                                                     e.printStackTrace();
                                                 }
                                                 try {
                                                     String response = moveResponse.getCommand().toString() +
                                                                       Optional.ofNullable(
                                                                               moveResponse.getRaiseAmount())
                                                                               .map(amount -> "," + amount)
                                                                               .orElse("");
                                                     System.out.println(
                                                             "{\"sendingResponse\": \"" + response + "\"},");
                                                     connection.sendMessage(response);
                                                 } catch (IOException e) {
                                                     e.printStackTrace();
                                                 }
                                             }
                                         }
                                     }).get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
