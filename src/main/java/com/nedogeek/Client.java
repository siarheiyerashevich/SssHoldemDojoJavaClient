package com.nedogeek;


import com.nedogeek.model.HandData;
import com.nedogeek.model.MoveData;
import com.nedogeek.model.MoveResponse;
import com.nedogeek.strategy.BasicStrategy;
import com.nedogeek.strategy.Strategy;
import com.nedogeek.util.MoveDataAnalyzer;
import com.nedogeek.util.ServerDataParser;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


public class Client {

    public static final String USER_NAME = "someUser";
    private static final String PASSWORD = "somePassword";

    private static final String SERVER = "ws://localhost:8080/ws";

    private WebSocket.Connection connection;
    private Strategy strategy = new BasicStrategy();
    private HandData handData = new HandData();

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
                                             System.out.println("Closed");
                                         }

                                         public void onMessage(String data) {
                                             System.out.println(data + ",");
                                             MoveData moveData = ServerDataParser.parseMoveData(data);

                                             if (moveData.getEvent().get(0).equalsIgnoreCase("New game started")) {
                                                 handData.setPosition(MoveDataAnalyzer.calculatePosition(moveData));
                                                 handData.setInitialCardsWeight(
                                                         MoveDataAnalyzer.calculateInitialCardsWeight(moveData));

                                                 System.out.println("{\"newPosition\": " + handData.getPosition() +
                                                                    "},");
                                                 System.out.println("{\"newInitialCardsWeight\": " +
                                                                    handData.getInitialCardsWeight() + "},");
                                             }

                                             if (USER_NAME.equalsIgnoreCase(moveData.getMover()) &&
                                                 moveData.getEvent().get(0).startsWith(USER_NAME)) {
                                                 System.out.println("{\"handledData\": " + data + "},");
                                                 MoveResponse moveResponse =
                                                         strategy.evaluateResponse(handData, moveData);
                                                 try {
                                                     String response = moveResponse.getCommand().toString() +
                                                                       Optional.ofNullable(
                                                                               moveResponse.getRaiseAmount())
                                                                               .map(amount -> "," + amount).orElse("");
                                                     System.out.println("{\"sendingResponse\": \"" + response + "\"},");
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
