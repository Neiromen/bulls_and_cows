package com.example.bulls_and_cows.controller.dto;

import java.util.UUID;

public record GameResponse(UUID gameId, String message) {
}
