package com.example.bulls_and_cows.model;

import java.util.UUID;

public class Game {

    private final UUID id;
    private final String secretNumber;
    private GameStatus status;

    public Game(UUID id, String secretNumber) {
        this.id = id;
        this.secretNumber = secretNumber;
        this.status = GameStatus.ACTIVE;
    }

    public UUID getId() {
        return id;
    }

    public String getSecretNumber() {
        return secretNumber;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public enum GameStatus {
        ACTIVE,
        WON,
        ENDED
    }
}
