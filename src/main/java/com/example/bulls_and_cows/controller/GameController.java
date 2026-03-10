package com.example.bulls_and_cows.controller;

import com.example.bulls_and_cows.controller.dto.EndGameResponse;
import com.example.bulls_and_cows.controller.dto.GameResponse;
import com.example.bulls_and_cows.controller.dto.GuessRequest;
import com.example.bulls_and_cows.controller.dto.GuessResponse;
import com.example.bulls_and_cows.model.Game;
import com.example.bulls_and_cows.model.GuessResult;
import com.example.bulls_and_cows.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public ResponseEntity<GameResponse> startGame() {
        Game game = gameService.startGame();
        return ResponseEntity.ok(new GameResponse(game.getId(), "Игра начата. Угадайте 4-значное число с уникальными цифрами."));
    }

    @PostMapping("/{gameId}/guess")
    public ResponseEntity<GuessResponse> guess(@PathVariable UUID gameId, @RequestBody GuessRequest request) {
        GuessResult result = gameService.guess(gameId, request.guess());
        return ResponseEntity.ok(new GuessResponse(
                result.getBulls(),
                result.getCows(),
                result.isWon(),
                result.getMessage()
        ));
    }

    @PostMapping("/{gameId}/end")
    public ResponseEntity<EndGameResponse> endGame(@PathVariable UUID gameId) {
        String secretNumber = gameService.endGame(gameId);
        return ResponseEntity.ok(new EndGameResponse("Игра завершена", secretNumber));
    }
}
