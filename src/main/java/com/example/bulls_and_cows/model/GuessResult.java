package com.example.bulls_and_cows.model;

public class GuessResult {

    private final int bulls;
    private final int cows;
    private final boolean won;
    private final String message;

    public GuessResult(int bulls, int cows, boolean won, String message) {
        this.bulls = bulls;
        this.cows = cows;
        this.won = won;
        this.message = message;
    }

    public int getBulls() {
        return bulls;
    }

    public int getCows() {
        return cows;
    }

    public boolean isWon() {
        return won;
    }

    public String getMessage() {
        return message;
    }
}
