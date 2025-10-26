package com.d.tdd;

public class StringHelper {

    private static final int PREFIX_LENGTH = 2;
    private static final String CHAR_TO_REMOVE = "A";

    public String replaceAInFirst2Positions(String str) {

        if (str == null) return null;
        if (str.length() < PREFIX_LENGTH) {
            return str.replace(CHAR_TO_REMOVE, "");
        }

        String firstTwoChars = str.substring(0, PREFIX_LENGTH);
        String rest = str.substring(PREFIX_LENGTH);

        return firstTwoChars.replace(CHAR_TO_REMOVE, "") + rest;
    }

    public boolean areFirstTwoAndLastTwoCharsTheSame(String str) {

        if (str == null || str.length() < PREFIX_LENGTH) return false;

        String firstTwoChars = str.substring(0, PREFIX_LENGTH);
        String lastTwoChars = str.substring(str.length() - PREFIX_LENGTH);

        return firstTwoChars.equals(lastTwoChars);
    }
}
