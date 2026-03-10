package com.example.bulls_and_cows.service;

import com.example.bulls_and_cows.model.Game;
import com.example.bulls_and_cows.model.GuessResult;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {

    private static final int NUMBER_LENGTH = 4;
    private static final String DIGITS = "0123456789";

    private final Map<UUID, Game> games = new ConcurrentHashMap<>();
    private final SecureRandom random = new SecureRandom();

    public Game startGame() {
        String secretNumber = generateSecretNumber();
        Game game = new Game(UUID.randomUUID(), secretNumber);
        games.put(game.getId(), game);
        return game;
    }

    public GuessResult guess(UUID gameId, String guess) {
        Game game = games.get(gameId);
        if (game == null) {
            throw new GameNotFoundException("Игра не найдена: " + gameId);
        }
        if (game.getStatus() != Game.GameStatus.ACTIVE) {
            throw new GameNotActiveException("Игра уже завершена");
        }

        validateGuess(guess);

        String secret = game.getSecretNumber();
        int bulls = 0;
        int cows = 0;

        for (int i = 0; i < NUMBER_LENGTH; i++) {
            char c = guess.charAt(i);
            if (secret.charAt(i) == c) {
                bulls++;
            } else if (secret.indexOf(c) >= 0) {
                cows++;
            }
        }

        if (bulls == NUMBER_LENGTH) {
            game.setStatus(Game.GameStatus.WON);
            return new GuessResult(bulls, cows, true, "4 быка, вы победили!");
        }

        return new GuessResult(bulls, cows, false, bulls + " бык(ов), " + cows + " коров(а/ы)");
    }

    public String endGame(UUID gameId) {
        Game game = games.get(gameId);
        if (game == null) {
            throw new GameNotFoundException("Игра не найдена: " + gameId);
        }
        game.setStatus(Game.GameStatus.ENDED);
        return game.getSecretNumber();
    }

    private String generateSecretNumber() {
        Set<Character> used = new HashSet<>();
        StringBuilder sb = new StringBuilder(NUMBER_LENGTH);

        while (sb.length() < NUMBER_LENGTH) {
            int idx = random.nextInt(DIGITS.length());
            char digit = DIGITS.charAt(idx);
            if (used.add(digit)) {
                sb.append(digit);
            }
        }
        return sb.toString();
    }

    private void validateGuess(String guess) {
        if (guess == null || guess.length() != NUMBER_LENGTH) {
            throw new InvalidGuessException("Число должно состоять из " + NUMBER_LENGTH + " цифр");
        }
        Set<Character> digits = new HashSet<>();
        for (char c : guess.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new InvalidGuessException("Допускаются только цифры");
            }
            if (!digits.add(c)) {
                throw new InvalidGuessException("Цифры должны быть уникальными");
            }
        }
    }

    public static class GameNotFoundException extends RuntimeException {
        public GameNotFoundException(String message) {
            super(message);
        }
    }

    public static class GameNotActiveException extends RuntimeException {
        public GameNotActiveException(String message) {
            super(message);
        }
    }

    public static class InvalidGuessException extends RuntimeException {
        public InvalidGuessException(String message) {
            super(message);
        }
    }
}
