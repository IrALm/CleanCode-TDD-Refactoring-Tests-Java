package com.b.simple.design.business.text;

public class TextHelper {

    private static final int FIRST_TWO_CHARS_LENGTH = 2;
    private static final String CHAR_TO_REMOVE = "A";

    /**
     * Swap the last two characters of a string.
     * Returns the original string if null or shorter than 2 chars.
     */
    public String swapLastTwoCharacters(String str) {

        if (str == null || str.length() < FIRST_TWO_CHARS_LENGTH) return str;

        int len = str.length();
        return str.substring(0, len - FIRST_TWO_CHARS_LENGTH)
                + str.charAt(len - 1)
                + str.charAt(len - FIRST_TWO_CHARS_LENGTH);
    }

    /**
     * Remove all 'A' characters from the first two positions of the string.
     * Returns the original string if null or empty.
     */
    public String truncateAInFirst2Positions(String str) {

        if (str == null || str.isEmpty()) return str;

        String prefix = str.substring(0, Math.min(FIRST_TWO_CHARS_LENGTH, str.length()))
                            .replace(CHAR_TO_REMOVE, "");
        String suffix = str.length() > FIRST_TWO_CHARS_LENGTH ? str.substring(FIRST_TWO_CHARS_LENGTH) : "";
        return prefix + suffix;

    }
}

