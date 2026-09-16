# 10. Cambios de Sesión: Stories + Música

**Fecha:** 16 de Septiembre 2026
**Rama:** `upgrade: historia`
**Commits:** `720e215`

---

## Resumen de Cambios

Se implementó la integración completa de música en las Stories, incluyendo:
- Búsqueda de música vía iTunes API
- Letras sincronizadas vía LRCLIB API
- 4 modos de visualización (Portada, Solo Audio, Letra, Portada + Letra)
- Posicionamiento y escalado de elementos
- Auto-avance de stories (30s)
- Control de volumen

---

## Archivos Modificados/Creados

### 1. GraphQL Schema — `stories.graphqls`

**Ruta:** `src/main/resources/graphql/modules/stories.graphqls`

#### Tipo `StoryMusica` (NUEVO)

| Campo | Tipo | Default | Descripción |
|-------|------|---------|-------------|
| `trackId` | String | — | ID del track en iTunes |
| `trackName` | String | — | Nombre de la canción |
| `artistName` | String | — | Nombre del artista |
| `coverUrl` | String | — | URL de la portada del álbum |
| `previewUrl` | String | — | URL del preview de audio (30s) |
| `musicMode` | String | `"cover"` | Modo: `cover` / `audio` / `lyrics` / `cover+lyrics` |
| `lyricsText` | String | — | Texto de la letra |
| `lyricsPosX` | Float | `50.0` | Posición X de la letra (% 0-100) |
| `lyricsPosY` | Float | `50.0` | Posición Y de la letra (% 0-100) |
| `coverPosX` | Float | `50.0` | Posición X de la portada (% 0-100) |
| `coverPosY` | Float | `50.0` | Posición Y de la portada (% 0-100) |
| `lyricsScale` | Float | `1.0` | Factor de escala de la letra |
| `coverScale` | Float | `1.0` | Factor de escala de la portada |

#### Input `StoryMusicaInput` (NUEVO)

Mismos 13 campos que `StoryMusica` para la mutación `createStory`.

---

### 2. Modelo de Dominio — `Story.java`

**Ruta:** `src/main/java/com/comunidev/comunidevbackend/story/domain/Story.java`

#### Clase interna `StoryMusica` (NUEVA)

```java
@Getter
@Setter
public static class StoryMusica {
    private String trackId;
    private String trackName;
    private String artistName;
    private String coverUrl;
    private String previewUrl;
    private String musicMode = "cover";
    private String lyricsText;
    private Double lyricsPosX = 50.0;
    private Double lyricsPosY = 50.0;
    private Double coverPosX = 50.0;
    private Double coverPosY = 50.0;
    private Double lyricsScale = 1.0;
    private Double coverScale = 1.0;
}
```

Se agregó campo `musica` de tipo `StoryMusica` en `StoryContenido`.

---

### 3. Servicio de Música — `MusicService.java`

**Ruta:** `src/main/java/com/comunidev/comunidevbackend/shared/infrastructure/musica/MusicService.java`

**Propósito:** Buscar tracks en iTunes Search API.

**Endpoint consume:** `https://itunes.apple.com/search?term={query}&media=music&entity=song&limit=15`

**Método:** `buscar(String query) → List<MusicTrackResponse>`

**Flujo:**
1. Codifica la query en URL-safe
2. Hace GET a iTunes API con `User-Agent: ComuniDev/1.0`
3. Parsea JSON y extrae: `trackId`, `trackName`, `artistName`, `artworkUrl100` (escalado a 300x300), `previewUrl`
4. Filtra tracks sin `previewUrl` o `trackName`
5. Retorna lista de `MusicTrackResponse`

---

### 4. Servicio de Letras — `LyricsService.java`

**Ruta:** `src/main/java/com/comunidev/comunidevbackend/shared/infrastructure/musica/LyricsService.java`

**Propósito:** Obtener letras de canciones vía LRCLIB API (gratuita, sin API key).

**Endpoint consume:** `https://lrclib.net/api/get?artist_name={artist}&track_name={track}`

**Método:** `fetchLyrics(String artistName, String trackName) → LyricsResponse | null`

**Flujo:**
1. Codifica artista y track en URL-safe
2. Hace GET a LRCLIB API con `User-Agent: ComuniDev/1.0`
3. Parsea JSON y extrae:
   - `plainLyrics` — texto plano de la letra
   - `syncedLyrics` — formato LRC con timestamps para sincronización
4. Retorna `null` si HTTP 404 o ambos campos vacíos
5. Retorna `LyricsResponse` con track, artista, letra plain y letra sincronizada

---

### 5. Controlador REST — `MusicaController.java`

**Ruta:** `src/main/java/com/comunidev/comunidevbackend/shared/infrastructure/rest/MusicaController.java`

**Base path:** `/api/v1/musica`

**CORS:** `localhost:4200`, `comunidev.pages.dev`, `comunidev-backend.onrender.com`

#### Endpoints

| Método | Path | Parámetros | Respuesta | Descripción |
|--------|------|------------|-----------|-------------|
| `GET` | `/search` | `?q={query}` | `200` → `List<MusicTrackResponse>` | Buscar tracks en iTunes |
| `GET` | `/lyrics` | `?artist={}&track={}` | `200` → `LyricsResponse`, `204` si no encuentra, `400` si faltan params | Obtener letras |

---

### 6. DTOs (NUEVOS)

#### `MusicTrackResponse.java`

**Ruta:** `src/main/java/com/comunidev/comunidevbackend/shared/dto/MusicTrackResponse.java`

```java
public record MusicTrackResponse(
    String id,
    String title,
    String artist,
    String coverUrl,
    String previewUrl
) {}
```

#### `LyricsResponse.java`

**Ruta:** `src/main/java/com/comunidev/comunidevbackend/shared/dto/LyricsResponse.java`

```java
public record LyricsResponse(
    String trackName,
    String artistName,
    String plainLyrics,
    String syncedLyrics
) {}
```

---

### 7. CommentGraphQLResolver (MEJORA MENOR)

**Ruta:** `src/main/java/com/comunidev/comunidevbackend/comment/adapter/in/graphql/CommentGraphQLResolver.java`

Se agregó sincronización del contador de comentarios en el post:
- `createComment`: incrementa `comentariosCount` en +1
- `deleteComment`: decrementa `comentariosCount` en -1
- Solo aplica a comentarios de tipo `POST` sin padre (nivel superior)

---

## Stack de Tecnologías Usado

| Capa | Tecnología |
|------|------------|
| API de Música | iTunes Search API (gratuita, sin key) |
| API de Letras | LRCLIB API (gratuita, sin key) |
| HTTP Client | Java 11+ `HttpClient` |
| Serialización | Jackson `ObjectMapper` |
| Persistencia | MongoDB Atlas |
| API | GraphQL (Spring GraphQL) + REST |

---

## Modos de Visualización

| Modo | `musicMode` | Descripción |
|------|-------------|-------------|
| Portada | `cover` | Muestra portada del álbum (arrastrable, escalable) |
| Solo Audio | `audio` | Solo suena la música, sin elemento visual |
| Letra | `lyrics` | Muestra letra sincronizada con la música |
| Portada + Letra | `cover+lyrics` | Muestra ambos: portada y letra |

---

## Formato LRC (Letras Sincronizadas)

LRCLIB devuelve letras en formato LRC:
```
[00:12.34] Soy tu letra sincronizada
[00:18.56] La siguiente línea de la canción
[00:25.78] Y otra línea más
```

El frontend parsea esto y muestra la línea actual según el `currentTime` del audio.

---

## Notas para Futuras Sesiones

1. **iTunes API**: Los previews duran 30 segundos. No hay API key requerida.
2. **LRCLIB API**: No todas las canciones tienen letras sincronizadas. Se usa `plainLyrics` como fallback.
3. **MongoDB**: Los campos de `StoryMusica` se almacenan como subdocumento embebido en `contenido`.
4. **Escalado**: `lyricsScale` y `coverScale` van de 0.3 a 3.0 (30% a 300%).
5. **Posiciones**: Son porcentajes (0-100) relativos al contenedor del preview.
