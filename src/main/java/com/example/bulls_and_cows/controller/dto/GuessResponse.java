package com.example.bulls_and_cows.controller.dto;

public record GuessResponse(int bulls, int cows, boolean won, String message) {
}
