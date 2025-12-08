package com.example.wordlespring.dto;

import java.util.Map;

public record RequestWordleDto(
        String requiredLetters,
        String excludedLetters,
        Map<Integer, Character> fixedPositions
) {}


