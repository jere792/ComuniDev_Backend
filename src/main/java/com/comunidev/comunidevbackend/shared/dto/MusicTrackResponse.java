package com.comunidev.comunidevbackend.shared.dto;

public record MusicTrackResponse(
    String id,
    String title,
    String artist,
    String coverUrl,
    String previewUrl
) {}
