package com.nedogeek.util;

public class CardsWeightCalculator {

    public static int calculatePairWeight(String firstCard, String secondCard, boolean sameSuit) {
        switch (firstCard) {
            case "A":
                switch (secondCard) {
                    case "A":
                        return 1;
                    case "K":
                        return sameSuit ? 4 : 11;
                    case "Q":
                        return sameSuit ? 6 : 18;
                    case "J":
                        return sameSuit ? 8 : 27;
                    case "10":
                        return sameSuit ? 12 : 42;
                    case "9":
                        return sameSuit ? 19 : 76;
                    case "8":
                        return sameSuit ? 24 : 91;
                    case "7":
                        return sameSuit ? 30 : 102;
                    case "6":
                        return sameSuit ? 34 : 113;
                    case "5":
                        return sameSuit ? 28 : 101;
                    case "4":
                        return sameSuit ? 32 : 104;
                    case "3":
                        return sameSuit ? 33 : 109;
                    case "2":
                        return sameSuit ? 39 : 117;
                }
            case "K":
                switch (secondCard) {
                    case "A":
                        return sameSuit ? 4 : 11;
                    case "K":
                        return 2;
                    case "Q":
                        return sameSuit ? 7 : 20;
                    case "J":
                        return sameSuit ? 9 : 31;
                    case "10":
                        return sameSuit ? 14 : 45;
                    case "9":
                        return sameSuit ? 22 : 81;
                    case "8":
                        return sameSuit ? 37 : 112;
                    case "7":
                        return sameSuit ? 44 : 122;
                    case "6":
                        return sameSuit ? 53 : 125;
                    case "5":
                        return sameSuit ? 55 : 128;
                    case "4":
                        return sameSuit ? 58 : 132;
                    case "3":
                        return sameSuit ? 60 : 133;
                    case "2":
                        return sameSuit ? 59 : 135;
                }
            case "Q":
                switch (secondCard) {
                    case "A":
                        return sameSuit ? 6 : 18;
                    case "K":
                        return sameSuit ? 7 : 20;
                    case "Q":
                        return 3;
                    case "J":
                        return sameSuit ? 13 : 35;
                    case "10":
                        return sameSuit ? 15 : 49;
                    case "9":
                        return sameSuit ? 25 : 83;
                    case "8":
                        return sameSuit ? 43 : 115;
                    case "7":
                        return sameSuit ? 61 : 131;
                    case "6":
                        return sameSuit ? 66 : 137;
                    case "5":
                        return sameSuit ? 69 : 141;
                    case "4":
                        return sameSuit ? 71 : 143;
                    case "3":
                        return sameSuit ? 72 : 144;
                    case "2":
                        return sameSuit ? 75 : 146;
                }
            case "J":
                switch (secondCard) {
                    case "A":
                        return sameSuit ? 8 : 27;
                    case "K":
                        return sameSuit ? 9 : 31;
                    case "Q":
                        return sameSuit ? 13 : 35;
                    case "J":
                        return 5;
                    case "10":
                        return sameSuit ? 16 : 47;
                    case "9":
                        return sameSuit ? 26 : 50;
                    case "8":
                        return sameSuit ? 41 : 108;
                    case "7":
                        return sameSuit ? 64 : 129;
                    case "6":
                        return sameSuit ? 79 : 147;
                    case "5":
                        return sameSuit ? 82 : 149;
                    case "4":
                        return sameSuit ? 86 : 152;
                    case "3":
                        return sameSuit ? 87 : 153;
                    case "2":
                        return sameSuit ? 89 : 155;
                }
            case "10":
                switch (secondCard) {
                    case "A":
                        return sameSuit ? 12 : 42;
                    case "K":
                        return sameSuit ? 14 : 45;
                    case "Q":
                        return sameSuit ? 15 : 49;
                    case "J":
                        return sameSuit ? 16 : 47;
                    case "10":
                        return 10;
                    case "9":
                        return sameSuit ? 23 : 73;
                    case "8":
                        return sameSuit ? 38 : 100;
                    case "7":
                        return sameSuit ? 57 : 124;
                    case "6":
                        return sameSuit ? 74 : 140;
                    case "5":
                        return sameSuit ? 93 : 157;
                    case "4":
                        return sameSuit ? 95 : 158;
                    case "3":
                        return sameSuit ? 96 : 160;
                    case "2":
                        return sameSuit ? 98 : 162;
                }
            case "9":
                switch (secondCard) {
                    case "A":
                        return sameSuit ? 19 : 76;
                    case "K":
                        return sameSuit ? 22 : 81;
                    case "Q":
                        return sameSuit ? 25 : 83;
                    case "J":
                        return sameSuit ? 26 : 50;
                    case "10":
                        return sameSuit ? 23 : 73;
                    case "9":
                        return 17;
                    case "8":
                        return sameSuit ? 40 : 99;
                    case "7":
                        return sameSuit ? 54 : 119;
                    case "6":
                        return sameSuit ? 68 : 134;
                    case "5":
                        return sameSuit ? 88 : 150;
                    case "4":
                        return sameSuit ? 106 : 164;
                    case "3":
                        return sameSuit ? 107 : 165;
                    case "2":
                        return sameSuit ? 111 : 166;
                }
            case "8":
                switch (secondCard) {
                    case "A":
                        return sameSuit ? 24 : 91;
                    case "K":
                        return sameSuit ? 37 : 112;
                    case "Q":
                        return sameSuit ? 43 : 115;
                    case "J":
                        return sameSuit ? 41 : 108;
                    case "10":
                        return sameSuit ? 38 : 100;
                    case "9":
                        return sameSuit ? 40 : 99;
                    case "8":
                        return 21;
                    case "7":
                        return sameSuit ? 48 : 114;
                    case "6":
                        return sameSuit ? 62 : 126;
                    case "5":
                        return sameSuit ? 78 : 139;
                    case "4":
                        return sameSuit ? 94 : 156;
                    case "3":
                        return sameSuit ? 116 : 167;
                    case "2":
                        return sameSuit ? 118 : 168;
                }
            case "7":
                switch (secondCard) {
                    case "A":
                        return sameSuit ? 30 : 102;
                    case "K":
                        return sameSuit ? 44 : 122;
                    case "Q":
                        return sameSuit ? 61 : 131;
                    case "J":
                        return sameSuit ? 64 : 129;
                    case "10":
                        return sameSuit ? 57 : 124;
                    case "9":
                        return sameSuit ? 54 : 119;
                    case "8":
                        return sameSuit ? 48 : 114;
                    case "7":
                        return 29;
                    case "6":
                        return sameSuit ? 56 : 121;
                    case "5":
                        return sameSuit ? 67 : 130;
                    case "4":
                        return sameSuit ? 85 : 145;
                    case "3":
                        return sameSuit ? 103 : 161;
                    case "2":
                        return sameSuit ? 120 : 169;
                }
            case "6":
                switch (secondCard) {
                    case "A":
                        return sameSuit ? 34 : 113;
                    case "K":
                        return sameSuit ? 53 : 125;
                    case "Q":
                        return sameSuit ? 66 : 137;
                    case "J":
                        return sameSuit ? 79 : 147;
                    case "10":
                        return sameSuit ? 74 : 140;
                    case "9":
                        return sameSuit ? 68 : 134;
                    case "8":
                        return sameSuit ? 62 : 126;
                    case "7":
                        return sameSuit ? 56 : 121;
                    case "6":
                        return 36;
                    case "5":
                        return sameSuit ? 63 : 123;
                    case "4":
                        return sameSuit ? 70 : 136;
                    case "3":
                        return sameSuit ? 90 : 148;
                    case "2":
                        return sameSuit ? 110 : 163;
                }
            case "5":
                switch (secondCard) {
                    case "A":
                        return sameSuit ? 28 : 101;
                    case "K":
                        return sameSuit ? 55 : 128;
                    case "Q":
                        return sameSuit ? 69 : 141;
                    case "J":
                        return sameSuit ? 82 : 149;
                    case "10":
                        return sameSuit ? 93 : 157;
                    case "9":
                        return sameSuit ? 88 : 150;
                    case "8":
                        return sameSuit ? 78 : 139;
                    case "7":
                        return sameSuit ? 67 : 130;
                    case "6":
                        return sameSuit ? 63 : 123;
                    case "5":
                        return 46;
                    case "4":
                        return sameSuit ? 65 : 127;
                    case "3":
                        return sameSuit ? 77 : 138;
                    case "2":
                        return sameSuit ? 92 : 151;
                }
            case "4":
                switch (secondCard) {
                    case "A":
                        return sameSuit ? 32 : 104;
                    case "K":
                        return sameSuit ? 58 : 132;
                    case "Q":
                        return sameSuit ? 71 : 143;
                    case "J":
                        return sameSuit ? 86 : 152;
                    case "10":
                        return sameSuit ? 95 : 158;
                    case "9":
                        return sameSuit ? 106 : 164;
                    case "8":
                        return sameSuit ? 94 : 156;
                    case "7":
                        return sameSuit ? 85 : 145;
                    case "6":
                        return sameSuit ? 70 : 136;
                    case "5":
                        return sameSuit ? 65 : 127;
                    case "4":
                        return 50;
                    case "3":
                        return sameSuit ? 84 : 142;
                    case "2":
                        return sameSuit ? 97 : 154;
                }
            case "3":
                switch (secondCard) {
                    case "A":
                        return sameSuit ? 33 : 109;
                    case "K":
                        return sameSuit ? 60 : 133;
                    case "Q":
                        return sameSuit ? 72 : 144;
                    case "J":
                        return sameSuit ? 87 : 153;
                    case "10":
                        return sameSuit ? 96 : 160;
                    case "9":
                        return sameSuit ? 107 : 165;
                    case "8":
                        return sameSuit ? 116 : 167;
                    case "7":
                        return sameSuit ? 103 : 161;
                    case "6":
                        return sameSuit ? 90 : 148;
                    case "5":
                        return sameSuit ? 77 : 138;
                    case "4":
                        return sameSuit ? 84 : 142;
                    case "3":
                        return 52;
                    case "2":
                        return sameSuit ? 105 : 159;
                }
            case "2":
                switch (secondCard) {
                    case "A":
                        return sameSuit ? 39 : 117;
                    case "K":
                        return sameSuit ? 59 : 135;
                    case "Q":
                        return sameSuit ? 75 : 146;
                    case "J":
                        return sameSuit ? 89 : 155;
                    case "10":
                        return sameSuit ? 98 : 162;
                    case "9":
                        return sameSuit ? 111 : 166;
                    case "8":
                        return sameSuit ? 118 : 168;
                    case "7":
                        return sameSuit ? 120 : 169;
                    case "6":
                        return sameSuit ? 110 : 163;
                    case "5":
                        return sameSuit ? 92 : 151;
                    case "4":
                        return sameSuit ? 97 : 154;
                    case "3":
                        return sameSuit ? 105 : 159;
                    case "2":
                        return 51;
                }
        }

        throw new IllegalArgumentException("Cards pair not found for: " + firstCard + ", " + secondCard);
    }
}
