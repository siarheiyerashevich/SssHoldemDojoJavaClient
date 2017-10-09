package com.nedogeek.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.nedogeek.util.CardsWeightCalculator;

public class HandStorage {

    private static volatile HandStorage instance;
    private static final int ALL_HANDS_SIZE = 1326;
    private Map<Integer, List<Card[]>> allHandsMap = new TreeMap<>();

    public static HandStorage getInstance() {

        HandStorage localInstance = instance;
        if (localInstance == null) {
            synchronized (HandStorage.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new HandStorage();
                    instance.init();
                }
            }
        }
        return localInstance;
    }

    private void init() {

        List<Card[]> allHands = new ArrayList<>(ALL_HANDS_SIZE);
        CardValue[] cardValues = CardValue.values();
        int cardValuesLen = cardValues.length;
        CardSuit[] cardSuites = CardSuit.values();
        int cardSuitesLen = cardSuites.length;
        for (int i = 0; i < cardValuesLen; i++) {
            for (int j = i; j < cardValuesLen; j++) {
                if (i == j) {
                    // pair - 6 hands
                    for (int k = 0; k < cardSuitesLen; k++) {
                        for (int n = k + 1; n < cardSuitesLen; n++) {
                            allHands.add(new Card[]{
                                    new Card(cardSuites[k], cardValues[i]),
                                    new Card(cardSuites[n], cardValues[j])});
                        }
                    }
                } else {
                    // not a pair - 16 hands
                    // TODO: here is not sorted suited from not suited
                    for (int k = 0; k < cardSuitesLen; k++) {
                        for (int n = 0; n < cardSuitesLen; n++) {
                            allHands.add(new Card[]{
                                    new Card(cardSuites[k], cardValues[i]),
                                    new Card(cardSuites[n], cardValues[j])});
                        }
                    }
                }
            }
        }

        for (Card[] hand : allHands) {
            int handWeight = CardsWeightCalculator.calculatePairWeight(
                    hand[0].getStringValue(),
                    hand[1].getStringValue(),
                    hand[0].getSuit() == hand[1].getSuit());
            allHandsMap.computeIfAbsent(handWeight, key -> new ArrayList<>(16)).add(hand);
        }

    }

    public static void main(String[] args) {

        int percantage = 3;
        List<Card[]> hands =  HandStorage.getInstance().getHandsRange(percantage);
        System.out.println(percantage + "%");
        for(Card[] hand : hands) {
            System.out.println(hand[0] + "" + hand[1]);
        }
    }

    /**
     * Get hands list based on percantage and cards weight
     * @param percentage
     *          percantage from 0 to 100%
     * @return
     */
    public List<Card[]> getHandsRange(double percentage) {
        if(percentage < 0) {
            percentage = 0;
        }
        if(percentage > 100) {
            percentage = 100;
        }
        double handsCountForPercantage = (double) ALL_HANDS_SIZE * percentage / 100;
        double selectedHandsCount = 0;
        List<Card[]> handsRange = new ArrayList<>((int) handsCountForPercantage + 16);
        for(List<Card[]> sublistOfHand : allHandsMap.values()) {
            handsRange.addAll(sublistOfHand);
            selectedHandsCount += sublistOfHand.size();
            if(selectedHandsCount > handsCountForPercantage) {
                break;
            }
        }
        return handsRange;
    }

    /**
     * Get hands list based on percantage and cards weight
     * @param percentage
     *          percantage from 0 to 100%
     * @return
     */
    public HandsRange getHandsRange2(double percentage) {
        if(percentage < 0) {
            percentage = 0;
        }
        if(percentage > 100) {
            percentage = 100;
        }
        double handsCountForPercantage = (double) ALL_HANDS_SIZE * percentage / 100;
        double selectedHandsCount = 0;
        List<Hand> handsRange = new ArrayList<>((int) handsCountForPercantage + 16);
        for(List<Card[]> sublistOfHand : allHandsMap.values()) {
            handsRange.addAll(sublistOfHand.stream()
                    .map(cards -> Hand.of(cards[0], cards[1]))
                    .collect(Collectors.toList()));
            selectedHandsCount += sublistOfHand.size();
            if(selectedHandsCount > handsCountForPercantage) {
                break;
            }
        }
        return new HandsRange(handsRange);
    }

    public List<Hand> getAllHands(List<Card> excludeCards) {

        return allHandsMap.values().stream()
                .flatMap(List::stream)
                .map(cards -> Hand.of(cards[0], cards[1]))
                .filter(hand -> !excludeCards.contains(hand.getFirstCard())
                        && !excludeCards.contains(hand.getSecondCard()))
                .collect(Collectors.toList());
    }
}
