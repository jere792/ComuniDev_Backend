package com.comunidev.comunidevbackend.shared.infrastructure.musica;

import com.comunidev.comunidevbackend.shared.dto.LyricsResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class LyricsService {

    private static final Logger log = LoggerFactory.getLogger(LyricsService.class);
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public LyricsService() {
        this.httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public LyricsResponse fetchLyrics(String artistName, String trackName) {
        try {
            String encodedArtist = URLEncoder.encode(artistName, StandardCharsets.UTF_8);
            String encodedTrack = URLEncoder.encode(trackName, StandardCharsets.UTF_8);
            String url = "https://lrclib.net/api/get?artist_name=" + encodedArtist
                    + "&track_name=" + encodedTrack;

            log.info("Buscando letra en LRCLIB: {} - {}", artistName, trackName);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "ComuniDev/1.0 (https://comunidev.pages.dev)")
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 404) {
                log.info("No se encontró letra para {} - {}", artistName, trackName);
                return null;
            }

            if (response.statusCode() != 200) {
                log.error("LRCLIB respondió con status: {}", response.statusCode());
                return null;
            }

            JsonNode root = objectMapper.readTree(response.body());

            String plainLyrics = "";
            JsonNode plainNode = root.get("plainLyrics");
            if (plainNode != null && !plainNode.isNull() && !plainNode.asText().isBlank()) {
                plainLyrics = plainNode.asText();
            }

            String syncedLyrics = "";
            JsonNode syncedNode = root.get("syncedLyrics");
            if (syncedNode != null && !syncedNode.isNull() && !syncedNode.asText().isBlank()) {
                syncedLyrics = syncedNode.asText();
            }

            if (plainLyrics.isEmpty() && syncedLyrics.isEmpty()) {
                log.info("No hay letra para {} - {}", artistName, trackName);
                return null;
            }

            String track = root.has("trackName") ? root.get("trackName").asText() : trackName;
            String artist = root.has("artistName") ? root.get("artistName").asText() : artistName;

            log.info("Letra encontrada para {} - {}: plain={} sync={}", artist, track, plainLyrics.length(), syncedLyrics.length());
            return new LyricsResponse(track, artist, plainLyrics, syncedLyrics);
        } catch (Exception e) {
            log.error("Error fetching lyrics from LRCLIB: {}", e.getMessage(), e);
            return null;
        }
    }
}
