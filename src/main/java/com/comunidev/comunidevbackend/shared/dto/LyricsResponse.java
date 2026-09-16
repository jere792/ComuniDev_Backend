package com.comunidev.comunidevbackend.shared.dto;

public record LyricsResponse(
    String trackName,
    String artistName,
    String plainLyrics,
    String syncedLyrics
) {}
