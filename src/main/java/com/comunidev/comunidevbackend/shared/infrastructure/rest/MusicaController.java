package com.comunidev.comunidevbackend.shared.infrastructure.rest;

import com.comunidev.comunidevbackend.shared.dto.LyricsResponse;
import com.comunidev.comunidevbackend.shared.dto.MusicTrackResponse;
import com.comunidev.comunidevbackend.shared.infrastructure.musica.LyricsService;
import com.comunidev.comunidevbackend.shared.infrastructure.musica.MusicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/musica")
@CrossOrigin(origins = {"http://localhost:4200", "https://comunidev.pages.dev"}, allowCredentials = "true")
public class MusicaController {

    private final MusicService musicService;
    private final LyricsService lyricsService;

    public MusicaController(MusicService musicService, LyricsService lyricsService) {
        this.musicService = musicService;
        this.lyricsService = lyricsService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<MusicTrackResponse>> search(@RequestParam String q) {
        if (q == null || q.isBlank()) {
            return ResponseEntity.ok(List.of());
        }
        return ResponseEntity.ok(musicService.buscar(q));
    }

    @GetMapping("/lyrics")
    public ResponseEntity<LyricsResponse> lyrics(
            @RequestParam String artist,
            @RequestParam String track) {
        if (artist == null || artist.isBlank() || track == null || track.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        LyricsResponse lyrics = lyricsService.fetchLyrics(artist.trim(), track.trim());
        if (lyrics == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lyrics);
    }
}
