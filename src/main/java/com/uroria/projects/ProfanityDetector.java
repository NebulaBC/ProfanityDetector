package com.uroria.projects;

import java.util.Arrays;

public final class ProfanityDetector {

    public static String replaceCharacterReplacements(String text) {
        return text.replace("€", "e")
                .replace("€", "e")
                .replace("@", "a")
                .replace("!", "i")
                .replace("1", "i")
                .replace("3", "e")
                .replace("4", "a")
                .replace("?", "i")
                .replace("#", "a");
    }

    public static boolean containsOffensiveWords(String text, double sensitivity, String... offensiveWords) {
        text = text.toLowerCase();
        text = replaceCharacterReplacements(text);

        text = text.replace(".", " ").replace(",", " ");

        String textWithoutSpace = text.replace(" ", "");
        if (Arrays.stream(offensiveWords).anyMatch(textWithoutSpace::contains)) return true;

        for (String word : text.split(" ")) {
            word = removeDuplicates(word);
            for (String offensiveWord : offensiveWords) {
                if (word.equals(offensiveWord)) return true;
                double offensive = findSimilarity(word, offensiveWord);
                if (offensive > sensitivity) return true;
            }
        }

        return false;
    }

    public static double getOffensiveWordPercentage(String text, String... offensiveWords) {
        text = text.toLowerCase();
        text = replaceCharacterReplacements(text);
        double percentage = 0.0;
        for (String word : text.split(" ")) {
            for (String offensiveWord : offensiveWords) {
                double offensive = findSimilarity(word, offensiveWord);
                percentage = percentage + offensive;
            }
        }
        percentage = percentage / text.length();
        return percentage;
    }

    public static double findSimilarity(String comparedWord, String word) {
        if (comparedWord.equalsIgnoreCase(word)) return 1.0;
        double maxLength = Double.max(comparedWord.length(), word.length());
        if (maxLength > 0) return (maxLength - getLevenshteinDistance(comparedWord, word)) / maxLength;
        return 1.0;
    }

    /**
     * @author MaximDe
     * Example:
     *  Message:
     *      "Fuck you"
     *  Filtered message:
     *      "**** you"
     *
     * @return filtered message
     */
    public static String replaceOffensiveWords(String message, String replacementBadWord, String replacementInvalidChar, double sensitivity, String... wordList) {
        String filteredMessage = message;
        // Replace all invalid chars with the replacementInvalidChar character
        filteredMessage = filteredMessage.replaceAll("[^a-zA-Z0-9?!%&/=:;öäüÖÄÜß\"$€´`'@(){}\\-_,.#*\\s]", replacementInvalidChar);

        for (String word : filteredMessage.split(" ")) {
            for (String offensiveWord : wordList) {
                double offensive = findSimilarity(word.toLowerCase(), offensiveWord);
                if (word.toLowerCase().equals(offensiveWord) || offensive > sensitivity) {
                    String asterisks = new String(new char[word.length()]).replace("\0", replacementBadWord);
                    filteredMessage = filteredMessage.replaceAll(word, asterisks);
                }
            }
        }
        return filteredMessage;
    }


    private static int getLevenshteinDistance(String x, String y) {
        int m = x.length();
        int n = y.length();
        int[][] T = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            T[i][0] = i;
        }
        for (int j = 1; j <= n; j++) {
            T[0][j] = j;
        }
        int cost;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                cost = x.charAt(i - 1) == y.charAt(j - 1) ? 0: 1;
                T[i][j] = Integer.min(Integer.min(T[i - 1][j] + 1, T[i][j - 1] + 1),
                        T[i - 1][j - 1] + cost);
            }
        }
        return T[m][n];
    }

    private static String removeDuplicates(String text) {
        if (text == null) {
            return null;
        }
        char[] chars = text.toCharArray();
        char prev = 0;
        int k = 0;
        for (char c: chars) {
            if (prev != c) {
                chars[k++] = c;
                prev = c;
            }
        }
        return new String(chars).substring(0, k);
    }
}
