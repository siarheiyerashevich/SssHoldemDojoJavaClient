package com.nedogeek.montecarlo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

import com.nedogeek.model.Card;
import com.nedogeek.model.CardSuit;
import com.nedogeek.model.CardValue;
import com.nedogeek.model.Hand;
import com.nedogeek.model.HandStorage;
import com.nedogeek.model.HandsRange;
import com.nedogeek.model.PlayerHand;
import com.nedogeek.model.Round;

public class GameEmulator {

    private static final int HAND_SIZE = 2;
    private static final int BOARD_SIZE = 5;
    private static final CardSuit[] cardSuits = CardSuit.values();
    private static final CardValue[] cardValues = CardValue.values();
    private static final int EMULATION_TIMEOUT_INTERVAL = 250;
    private static final int PLAYERS_CARDS_LIMIT_FOR_ITERATION = 5000;

    public static void main(String[] args) {

        Hand myHand = Hand.of(CardSuit.SPADES, CardValue.ACE, CardSuit.HEARTS, CardValue.ACE);
//        myHand[0] = new Card(CardSuit.SPADES, CardValue.ACE);
//        myHand[1] = new Card(CardSuit.HEARTS, CardValue.ACE);

        Card[] board = new Card[BOARD_SIZE];
        board[0] = new Card(CardSuit.SPADES, CardValue.KING);
        board[1] = new Card(CardSuit.HEARTS, CardValue.QUEEN);
        board[2] = new Card(CardSuit.DIAMONDS, CardValue.JACK);
//        board[3] = new Card(CardSuit.DIAMONDS, CardValue.TWO);

        int existedBoardCount = 3;

        List<Integer> playersPercentage = new ArrayList<>();
        playersPercentage.add(1);
//        playersPercentage.add(5);
//        playersPercentage.add(3);
        int emulationCount = 100000;

        long time = System.currentTimeMillis();
        double[] equities = emulateGames(myHand, board, existedBoardCount, playersPercentage, emulationCount,
                Round.FLOP);
        time = System.currentTimeMillis() - time;
        System.out.println("Emulation count: " + emulationCount + ", time: " + time + " ms");
        System.out.println("Me: " + (equities[equities.length - 1] * 100) + "%");
        for (int i = 0; i < equities.length - 1; i++) {
            System.out.println("Player " + (i + 1) + ": " + (equities[i] * 100) + "%");
        }
    }

    public static double[] emulateGames(Hand myHand, Card[] board, int existedBoardCount,
            List<Integer> playersPercentage, int emulationCount, Round round) {

        List<Card> existedCards = new ArrayList<>();
        existedCards.add(myHand.getFirstCard());
        existedCards.add(myHand.getSecondCard());
        for (Card card : board) {
            if (card != null) {
                existedCards.add(card);
            } else {
                break;
            }
        }
        existedCards.add(new Card(CardSuit.DIAMONDS, CardValue.ACE));
        existedCards.add(new Card(CardSuit.DIAMONDS, CardValue.KING));
        List<Hand> ternRiverList =
                round == Round.FLOP ? HandStorage.getInstance().getAllHands(existedCards) : new ArrayList<>();

//        for(Card card : existedCards) {
//
//            System.out.println("Existed card " + card);
//        }
//        System.out.println(ternRiverList.size());
//        for(Hand ternRiver : ternRiverList) {
//
//            System.out.println(ternRiver);
//        }
        List<HandsRange> playersHands = new ArrayList<>();
        int playersOpponentCount = playersPercentage.size();
        int playerIndex = 0;
        long time = 0;
        // FIXME: Use simple method with random players cards in case of a lot of players hands
        for (Integer playerPercentage : playersPercentage) {
            HandsRange handsRange = HandStorage.getInstance().getHandsRange2(playerPercentage);
            handsRange.getHands().removeIf(myHand::partiallyEquals);
            handsRange.setPlayerIndex(playerIndex++);
            playersHands.add(handsRange);
        }
//        playersHands.get(0).getHands().clear();
//        playersHands.get(0).getHands().add(Hand.of(CardSuit.DIAMONDS, CardValue.ACE, CardSuit.DIAMONDS, CardValue.KING));
        int possiblePlayersCardsPerGame = playersHands.stream().mapToInt(f -> f.getHands().size()).sum();
        boolean playersCardsIteration = possiblePlayersCardsPerGame < PLAYERS_CARDS_LIMIT_FOR_ITERATION;
        List<List<Hand>> playersCardsPerGame = null;
        if (playersCardsIteration) {
            playersCardsPerGame = getPlayersCardsPerGame(playersHands);
        } else {
            playersCardsPerGame = playersHands.stream().map(HandsRange::getHands).collect(Collectors.toList());
        }
//        playersCardsPerGame.forEach(GameEmulator::printHands);
        time = System.currentTimeMillis() - time;
        System.out.println("Player cards calculation: " + playersCardsPerGame.size() + ", time: " + time + " ms");

        int playersCount = playersHands.size();

        double[] playersEquityArr = new double[playersCount + 1];
        for (int j = 0; j < playersCount + 1; j++) {
            playersEquityArr[j] = 0;
        }

        SplittableRandom boardRandom = new SplittableRandom();
        int realEmulationCount = 0;

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < emulationCount; i++) {
            if (playersCardsIteration) {

                List<Double[]> gameResults;
                if (!ternRiverList.isEmpty()) {
                    gameResults = emulateGamePerAllPlayersHandsAndBoards(myHand, playersCardsPerGame,
                            board, existedBoardCount, playersOpponentCount, ternRiverList);
                } else {
                    gameResults = emulateGamePerAllPlayersHands(myHand, playersCardsPerGame,
                            board, existedBoardCount, playersOpponentCount, boardRandom);
                }

                realEmulationCount += gameResults.size();
                for (Double[] gameResult : gameResults) {
                    for (int j = 0; j < playersCount + 1; j++) {
                        playersEquityArr[j] += gameResult[j];
                    }
                }
            } else {
                realEmulationCount++;
                Double[] gameResults = emulateGameWithRandomPlayerCard(myHand, playersCardsPerGame,
                        board, existedBoardCount, boardRandom);
                for (int j = 0; j < playersCount + 1; j++) {
                    playersEquityArr[j] += gameResults[j];
                }
            }

            if ((System.currentTimeMillis() - startTime) > EMULATION_TIMEOUT_INTERVAL) {
                break;
            }
            if (!ternRiverList.isEmpty()) {
                break;
            }
//                long delta = System.currentTimeMillis() - time;
//                System.out.println("Real Emulation count: " + realEmulationCount + ", emulation count " + i
//                        + " , playersCardsPerGame " + playersCardsPerGame.size()
//                        + " , time = " + delta);
//            }
        }
        for (int j = 0; j < playersCount + 1; j++) {
            playersEquityArr[j] = playersEquityArr[j] / realEmulationCount;
        }

        long statsEmulCountPerSec = realEmulationCount * 1000 / (System.currentTimeMillis() - startTime);
        System.out.println("Real Emulation count: " + realEmulationCount + ", " + statsEmulCountPerSec + " count/sec");
        return playersEquityArr;
    }

    private static void printHands(List<Hand> hands) {

        StringBuilder builder = new StringBuilder();
        hands.forEach(builder::append);
        System.out.println(builder.toString());
    }

    private static List<List<Hand>> getPlayersCardsPerGame(List<HandsRange> playersCards) {

        List<List<Hand>> playersCardsPerGame = new ArrayList<>();
        applyPlayersCardsPerGame(playersCardsPerGame, playersCards, 0, new ArrayList<>(), playersCards.size());
        return playersCardsPerGame;
    }

    private static void applyPlayersCardsPerGame(List<List<Hand>> playersCardsPerGame,
            List<HandsRange> playersCards, int playerIndex,
            List<PlayerHand> existedPlayersCards, final int playersCount) {

        HandsRange handsRange = playersCards.get(playerIndex);
        for (Hand hand : handsRange.getHands()) {
            if (existAnyCard(existedPlayersCards, hand)) {
                continue;
            }
            PlayerHand playerHand = new PlayerHand(hand, handsRange.getPlayerIndex());
            existedPlayersCards.add(playerHand);
            if (existedPlayersCards.size() == playersCount) {
                existedPlayersCards.sort(Comparator.comparingInt(PlayerHand::getPlayerIndex));
                playersCardsPerGame.add(
                        existedPlayersCards.stream()
                                .map(PlayerHand::getHand)
                                .collect(Collectors.toList()));
            } else if (playerIndex < playersCount - 1) {
                applyPlayersCardsPerGame(playersCardsPerGame,
                        playersCards,
                        playerIndex + 1,
                        existedPlayersCards,
                        playersCount);
            }
            existedPlayersCards.remove(playerHand);
        }
    }

    private static boolean existAnyCard(List<PlayerHand> existedPlayersCards, Hand newHand) {

        for (PlayerHand playerHand : existedPlayersCards) {
            if (newHand.partiallyEquals(playerHand.getHand())) {
                return true;
            }
        }
        return false;
    }

    private static Double[] emulateGameWithRandomPlayerCard(Hand myHand, List<List<Hand>> playersHands,
            Card[] existedBoard, int existedBoardCount, SplittableRandom boardRandom) {

        // TODO: is it necessary to replace to array?
        List<Card> existedCards = new ArrayList<>(HAND_SIZE + BOARD_SIZE + playersHands.size()
                * HAND_SIZE);
        existedCards.add(myHand.getFirstCard());
        existedCards.add(myHand.getSecondCard());
        int opponentPlayersCount = playersHands.size();

        SplittableRandom playerRandom = new SplittableRandom();
        Card[] board = emulateBoard(existedCards, existedBoard, existedBoardCount, boardRandom);
        double[] playersCombinations = new double[opponentPlayersCount + 1];
        String message = "";
        for (int i = 0; i < opponentPlayersCount; i++) {
            Hand playerHand = emulateHand(existedCards, playerRandom, playersHands.get(i));
            playersCombinations[i] = FastCombination.getCombinationWeight(playerHand, board);
//            if (i > 0) {
//                message += " , ";
//            }
//            message += "Player " + (i + 1) + ":" + playerHand + " " + playersCombinations[i];
        }
        playersCombinations[opponentPlayersCount] = FastCombination.getCombinationWeight(myHand, board);
//        message += " Me: " + myHand + " " + playersCombinations[opponentPlayersCount];
//        message += " , Board:" + Arrays.toString(board);

        Double[] results = calculateGameResults(playersCombinations);
//        message += " , Results: " + Arrays.toString(results);
//        System.out.println(message);

        return results;
    }

    private static List<Double[]> emulateGamePerAllPlayersHandsAndBoards(Hand myHand, List<List<Hand>> playersHands,
            Card[] existedBoard, int existedBoardCount, int opponentPlayersCount, List<Hand> ternRivers) {

        // TODO: is it necessary to replace to array?
        List<Card> existedCards = new ArrayList<>(HAND_SIZE + BOARD_SIZE);
        existedCards.add(myHand.getFirstCard());
        existedCards.add(myHand.getSecondCard());
        List<Double[]> results = new ArrayList<>();
        for (Hand ternRiver : ternRivers) {

            existedBoard[3] = ternRiver.getFirstCard();
            existedBoard[4] = ternRiver.getSecondCard();

            Collections.addAll(existedCards, existedBoard);

            double myCombinationWeight = FastCombination.getCombinationWeight(myHand, existedBoard);

            boolean failedHand;
            double[] playersCombinations = new double[opponentPlayersCount + 1];
            int index;
            int ties = 0;
            for (List<Hand> playersHandsPerGame : playersHands) {
                // TODO: Think about double cycle
                failedHand = false;
                for (Hand playerHand : playersHandsPerGame) {
                    if (existedCards.contains(playerHand.getFirstCard())
                            || existedCards.contains(playerHand.getSecondCard())) {
                        failedHand = true;
                        break;
                    }
                }
                if (failedHand) {
                    continue;
                }
                index = 0;
                String message = "";
                for (Hand playerHand : playersHandsPerGame) {
                    playersCombinations[index++] = FastCombination.getCombinationWeight(playerHand, existedBoard);
//                    if (index > 1) {
//                        message += " , ";
//                    }
//                    message += "Player " + index + ":" + playerHand + " " + playersCombinations[index - 1];
                }
                playersCombinations[index] = myCombinationWeight;
                Double[] result = calculateGameResults(playersCombinations);
                results.add(result);

//                message += " Me: " + myHand + " " + playersCombinations[opponentPlayersCount];
//                message += " , Board:" + Arrays.toString(existedBoard);
//            if(result[index] < 1 && result[index] > 0) {
//                ties++;
//                System.out.println(message + " , Results: " + Arrays.toString(result));
//            }
            }

            existedCards.removeAll(Arrays.asList(existedBoard));

        }
//        System.out.println("Ties count: " + (ties * 100 / results.size()) + ", " + results.size() + " , " + ties );
        return results;
    }

    private static List<Double[]> emulateGamePerAllPlayersHands(Hand myHand, List<List<Hand>> playersHands,
            Card[] existedBoard, int existedBoardCount, int opponentPlayersCount, SplittableRandom boardRandom) {

        // TODO: is it necessary to replace to array?
        List<Card> existedCards = new ArrayList<>(HAND_SIZE + BOARD_SIZE);
        existedCards.add(myHand.getFirstCard());
        existedCards.add(myHand.getSecondCard());
        Card[] board = emulateBoard(existedCards, existedBoard, existedBoardCount, boardRandom);
        double myCombinationWeight = FastCombination.getCombinationWeight(myHand, board);

        List<Double[]> results = new ArrayList<>();
        boolean failedHand;
        double[] playersCombinations = new double[opponentPlayersCount + 1];
        int index;
        int ties = 0;
        for (List<Hand> playersHandsPerGame : playersHands) {
            // TODO: Think about double cycle
            failedHand = false;
            for (Hand playerHand : playersHandsPerGame) {
                if (existedCards.contains(playerHand.getFirstCard())
                        || existedCards.contains(playerHand.getSecondCard())) {
                    failedHand = true;
                    break;
                }
            }
            if (failedHand) {
                continue;
            }
            index = 0;
//            String message = "";
            for (Hand playerHand : playersHandsPerGame) {
                playersCombinations[index++] = FastCombination.getCombinationWeight(playerHand, board);
//                if (index > 1) {
//                    message += " , ";
//                }
//                message += "Player " + index + ":" + playerHand + " " + playersCombinations[index - 1];
            }
            playersCombinations[index] = myCombinationWeight;
            Double[] result = calculateGameResults(playersCombinations);
            results.add(result);

//            message += " Me: " + myHand + " " + playersCombinations[opponentPlayersCount];
//            message += " , Board:" + Arrays.toString(board);
//            if(result[index] < 1 && result[index] > 0) {
//            ties++;
//            System.out.println(message + " , Results: " + Arrays.toString(result));
//            }
        }
//        System.out.println("Ties count: " + (ties * 100 / results.size()) + ", " + results.size() + " , " + ties );
        return results;
    }

    private static Double[] calculateGameResults(double[] combinations) {

        // FIXME: Calculate based on both weights and non-same cards
        double max = 0;
        int len = combinations.length;
        // TODO: Rewrite to LIST?
        Double[] results = new Double[len];
        for (double combination : combinations) {
            double commonWeight = combination;
            if (commonWeight > max) {
                max = combination;
            }
        }
        // TODO: Calculate average if some players win
        int countWinPlayers = 0;
        for (double combination : combinations) {
            if (combination == max) {
                countWinPlayers++;
            }
        }
        // TODO: Check for zero but it's not possible
        double winPercentage = 1d / countWinPlayers;
        for (int i = 0; i < len; i++) {
            results[i] = combinations[i] == max ? winPercentage : 0;
        }
        return results;
    }

    private static Card[] emulateHand(List<Card> existedCards) {

        return emulateCards(HAND_SIZE, existedCards);
    }

    private static Card[] emulateBoard(List<Card> existedCards) {

        return emulateCards(BOARD_SIZE, existedCards);
    }

    private static Hand emulateHand(List<Card> existedCards, SplittableRandom random, List<Hand> playerHands) {

        Hand hand;
        do {
            hand = playerHands.get(random.nextInt(0, playerHands.size() - 1));
            // FIXME: remove my hand from players hand list
            // FIXME: potential infinite loop!!!
        } while (existedCards.contains(hand.getFirstCard()) || existedCards.contains(hand.getSecondCard()));
        existedCards.add(hand.getFirstCard());
        existedCards.add(hand.getSecondCard());
        return hand;
    }

    private static Card[] emulateBoard(List<Card> existedCards, Card[] board, int existedBoardCount,
            SplittableRandom random) {

        int cardsNeededForBoardCount = BOARD_SIZE - existedBoardCount;
        Card[] cardsNeededForBoard = emulateCards(cardsNeededForBoardCount, existedCards, random);
        System.arraycopy(cardsNeededForBoard, 0, board, existedBoardCount,
                BOARD_SIZE - existedBoardCount);
        return board;
    }

    private static Card[] emulateCards(int count, List<Card> existedCards) {

        SplittableRandom random = new SplittableRandom();
        return emulateCards(count, existedCards, random);
    }

    // TODO: use different random for suits and values!
    private static Card[] emulateCards(int count, List<Card> existedCards, SplittableRandom random) {

        Card[] cards = new Card[count];
        for (int i = 0; i < count; i++) {
            Card card;
            do {
                card = new Card(
                        cardSuits[random.nextInt(0, 3)],
                        cardValues[random.nextInt(0, 12)]
                );
            } while (existedCards.contains(card));
            cards[i] = card;
            existedCards.add(card);
        }
        return cards;
    }
}
