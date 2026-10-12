  # COMUNIDEV — Laboratorio: Persistencia de datos, modelos y APIs

  > Enunciado del laboratorio: en un documento (Word o PDF) se debe entregar
  > 1. Definir la persistencia de datos del negocio (diagrama E-R).
  > 2. Crear las clases (modelos) y APIs (datos de entrada y retorno).
  > 3. Avance del backend: servicio a usar, tipo de base de datos, lenguaje y/o framework backend (versión) y consideraciones de seguridad.

  ---

  ## 1. Persistencia de datos del negocio (Diagrama E-R)

  COMUNIDEV persiste en **MongoDB Atlas** (modelo de documentos). El diagrama E-R siguiente es la **visión lógica del negocio**: las entidades y sus relaciones. En MongoDB cada entidad corresponde a una colección y las relaciones se resuelven por referencia (`_id` de otra colección) o se embeben cuando son parte intrínseca del documento.

  ### 1.1 Diagrama E-R

  ```mermaid
  erDiagram
      USERS ||--o{ DEVELOPER_PROFILES : "tiene (1..1)"
      USERS ||--o{ RECRUITER_PROFILES : "tiene (1..1)"
      USERS ||--o{ POSTS : "publica"
      USERS ||--o{ COMMENTS : "comenta"
      USERS ||--o{ REACTIONS : "reacciona"
      USERS ||--o{ STORIES : "crea"
      USERS ||--o{ REELS : "crea"
      USERS ||--o{ FOLLOWS : "sigue (followerId)"
      USERS ||--o{ CONNECTION_REQUESTS : "solicita (solicitanteId)"
      USERS ||--o{ CONNECTIONS : "conecta"
      USERS ||--o{ BLOCKS : "bloquea (bloqueadorId)"
      USERS ||--o{ NOTIFICATIONS : "recibe (usuarioDestinoId)"
      USERS ||--o{ TECHNICAL_QUESTIONS : "pregunta"
      USERS ||--o{ TECHNICAL_ANSWERS : "responde"
      USERS ||--o{ APPLICATIONS : "postula (desarrolladorId)"
      USERS ||--o{ SAVED_SEARCHES : "guarda (recruiterId)"

      RECRUITER_PROFILES ||--o{ COMPANIES : "representa (companyId)"
      COMPANIES ||--o{ VACANCIES : "publica"
      COMPANIES ||--o{ RECYCLING_POINTS : "gestiona"

      POSTS ||--o{ COMMENTS : "recibe"
      POSTS ||--o{ REACTIONS : "recibe"
      COMMENTS ||--o{ COMMENTS : "responde (parentCommentId)"
      COMMENTS ||--o{ REACTIONS : "recibe"

      TECHNICAL_QUESTIONS ||--o{ TECHNICAL_ANSWERS : "recibe"
      TECHNICAL_QUESTIONS }o--o{ CATEGORIES : "clasifica en"
      TECHNICAL_QUESTIONS }o--o{ TAGS : "etiqueta en"
      TECHNICAL_ANSWERS ||--o{ QUESTION_VOTES : "recibe"
      TECHNICAL_ANSWERS ||--o{ TECHNICAL_QUESTIONS : "acepta (respuestaAceptadaId)"

      VACANCIES ||--o{ APPLICATIONS : "recibe"
      APPLICATIONS ||--o{ APPLICATION_STATUS_HISTORY : "registra estados"

      STORIES ||--o{ STORY_VIEWS : "registra vistas"

      USERS ||--o{ ECOTECH_POSTS : "publica (ODS 12)"
      ECOTECH_POSTS ||--o{ ECOTECH_TRANSACTIONS : "origina"
      COMPANIES ||--o{ COMPANY_SUSTAINABILITY_PRACTICES : "declara"

      USERS ||--o{ USER_POINTS : "acumula"
      USERS ||--o{ USER_BADGES : "obtiene"
      USER_POINTS ||--o{ POINT_TRANSACTIONS : "registra"
      USERS ||--o{ REPORTS : "genera"
      REPORTS ||--o{ MODERATION_ACTIONS : "resuelve"
      USERS ||--o{ SUPPORT_TICKETS : "abre"
      USERS ||--o{ AUDIT_LOGS : "genera actividad"
  ```

  ### 1.2 Descripción de las entidades principales

  | Entidad (colección) | Descripción | Relaciones clave |
  |---------------------|-------------|------------------|
  | `users` | Cuenta de usuario: identidad, roles (`DEVELOPER`, `RECRUITER`, `MODERATOR`, `ADMIN`), rol activo, privacidad y contadores. | Referencia desde todas las colecciones por `autorId` / `usuarioId`. |
  | `developer_profiles` | Perfil profesional: CV, tecnologías, experiencia, educación, proyectos, certificados, disponibilidad y pretensiones salariales. | 1..1 con `users.userId`. |
  | `recruiter_profiles` | Perfil de reclutador: empresa, cargo, redes sociales. | 1..1 con `users`; N..1 con `companies`. |
  | `companies` | Empresas con ubicación y coordenadas (latitud/longitud) para el mapa laboral. | 1..N con `vacancies`. |
  | `posts` | Publicaciones con contenido embebido (`PostContenido`: texto, imágenes, videos, código), visibilidad y estadísticas. | N..1 con `users`; 1..N con `comments`, `reactions`. |
  | `comments` | Comentarios de nivel superior y respuestas anidadas (`parentCommentId`). | N..1 con `posts` y con `comments`. |
  | `reactions` | Reacción única por usuario y contenido (`LIKE`, `LOVE`, `CELEBRATE`, `SUPPORT`), con índice único. | N..1 con `users` y con el contenido reaccionado. |
  | `stories` / `story_views` | Historias efímeras (24 h) y registro de quién las vio. | N..1 con `users`; 1..N con `story_views`. |
  | `reels` | Videos cortos con música asociada. | N..1 con `users`. |
  | `follows`, `connection_requests`, `connections`, `blocks` | Grafo social: seguidores, solicitudes y conexiones profesionales, bloqueos. | Relaciones N..N entre `users`. |
  | `technical_questions` / `technical_answers` / `question_votes` | Asistencia técnica: preguntas con etiquetas/categoría, respuestas votadas y una respuesta aceptada. | N..1 con `users`; relación de respuesta aceptada. |
  | `vacancies` / `applications` / `application_status_history` | Vacantes con estados e historial de postulaciones (enviada → aceptada/rechazada). | N..1 con `companies`; N..1 con `users` (postulante). |
  | `notifications` | Notificaciones (`SOLICITUD_CONEXION`, `CONEXION_ACEPTADA`, `COMENTARIO`, `REACCION`) con estado leída/no leída. | N..1 con `users`. |
  | `ecotech_posts`, `recycling_points`, `ecotech_transactions` | Módulo EcoDev / ODS 12: contenido sostenible, puntos de reciclaje validados e indicadores de impacto. | N..1 con `users` y `companies`. |
  | `user_points`, `point_transactions`, `badges`, `user_badges`, `plans`, `subscriptions` | Gamificación y planes de membresía. | N..1 con `users`. |
  | `reports`, `moderation_actions`, `support_tickets`, `audit_logs`, `user_bans` | Soporte, moderación y auditoría. | N..1 con `users`; `moderation_actions` 1..1 con `reports`. |

  > Detalle completo de campos por colección: `doc\03-ENTIDADES.md` y `doc\MONGODB-ATLAS-COLLECTIONS.md`.

  ---

  ## 2. Clases (modelos) y APIs (datos de entrada y retorno)

  ### 2.1 Clases modelo (Java — Spring Data MongoDB)

  Arquitectura hexagonal: los modelos viven en `*/domain` con anotación `@Document` y heredan de `AggregateRoot<String>`.

  **Modelo `User`** (`users/domain/User.java`):

  ```java
  @Getter
  @Setter
  @Document(collection = "users")
  public class User extends AggregateRoot<String> {
      private String nombre;
      private String nombreUsuario;
      private String email;
      private String passwordHash;          // BCrypt, nunca texto plano
      private String telefono;
      private String fotoPerfilUrl;
      private String bannerUrl;
      private String bio;
      private String sitioWeb;
      private Ubicacion ubicacion;          // valor embebido
      private Set<UserRole> roles;          // DEVELOPER, RECRUITER, MODERATOR, ADMIN
      private UserRole rolActivo;
      private UserStatus estadoCuenta;
      private Boolean emailVerificado = false;
      private ActivityStatus estadoActividad;
      private UserConfiguration configuracion;   // tema, idioma, notificaciones, privacidad
      private Integer seguidoresCount = 0;
      private Integer siguiendoCount = 0;
      private Integer conexionesCount = 0;
      private BanInfo baneo;
      private Instant createdAt;
      private Instant updatedAt;
  }
  ```

  **Modelo `Post`** (`post/domain/Post.java`):

  ```java
  @Getter
  @Setter
  @Document(collection = "posts")
  public class Post extends AggregateRoot<String> {
      private String autorId;                       // referencia a users
      private String tipo = "TEXTO";
      private PostContenido contenido = new PostContenido();  // embebido
      private List<String> etiquetas = new ArrayList<>();
      private String categoria;
      private String visibilidad = "PUBLICO";       // PUBLICO | SEGUIDORES | CONEXIONES | SOLO_YO
      private String estadoModeracion = "APROBADO";
      private PostEstadisticas estadisticas = new PostEstadisticas();
      private Instant createdAt;
      private Instant updatedAt;

      public static class PostContenido {
          private String texto;
          private List<String> imagenes;
          private List<String> videos;
          private List<String> archivos;
          private List<String> bloquesCodigo;
          private List<String> enlaces;
      }

      public static class PostEstadisticas {
          private Integer vistas = 0;
          private Integer reaccionesCount = 0;
          private Integer comentariosCount = 0;
          private Integer compartidosCount = 0;
          private Integer guardadosCount = 0;
      }
  }
  ```

  > Patrón repetido en todos los módulos: `Comment`, `Reaction`, `Story`, `Reel`, `Follow`, `Connection`, `ConnectionRequest`, `Block`, `Notification`, `Vacancy`, `Application`, `SavedSearch`, etc. (ver `src\main\java\com\comunidev\comunidevbackend\`).

  ### 2.2 APIs — datos de entrada y retorno

  #### GraphQL (principal) — `/api/v1/graphql`

  **Mutación de registro (entrada/salida):**

  ```graphql
  # Entrada
  mutation {
    register(input: {
      email: "ana@correo.com"
      password: "Secreta123"
      nombre: "Ana Márquez"
      nombreUsuario: "anaDev"
    }) {
      # Salida
      token
      refreshToken
      user { id nombre nombreUsuario email rolActivo }
    }
  }
  ```

  **Mutación de publicación:**

  ```graphql
  # Entrada
  mutation {
    createPost(input: {
      tipo: "TEXTO"
      contenido: { texto: "¿Cómo proteger rutas en Angular con JWT?" }
      etiquetas: ["Angular", "JWT"]
      categoria: "frontend"
      visibilidad: "PUBLICO"
    }) {
      # Salida
      id
      autorId
      contenido { texto }
      etiquetas
      visibilidad
      createdAt
      estadisticas { vistas reaccionesCount comentariosCount }
    }
  }
  ```

  **Query paginada:**

  ```graphql
  # Entrada
  query {
    feed(page: 0, size: 10) {
      # Salida
      content { id autor { nombre nombreUsuario fotoPerfilUrl } contenido { texto } createdAt }
      totalPages
      totalElements
    }
  }
  ```

  #### REST (secundaria) — documentada en Swagger `/swagger-ui.html`

  | Método y ruta | Operación | Datos de entrada | Datos de retorno |
  |---------------|-----------|------------------|------------------|
  | `POST /api/v1/auth/login` | Iniciar sesión | `LoginRequest { email, password }` | `LoginResponse { token, refreshToken, user }` |
  | `POST /api/v1/auth/register` | Registrarse | `CreateUserRequest { email, password, nombre, nombreUsuario }` | `UserResponse { id, email, nombre, rolActivo, ... }` |
  | `PUT /api/v1/users/{id}` | Actualizar usuario | `UpdateUserRequest { nombre, bio, telefono, ... }` | `UserResponse` |
  | `POST /api/v1/upload` | Subir archivo (multipart) | `file` (imagen, PDF, video) | `ApiResponse { url, publicId }` (Cloudinary) |
  | `GET /api/v1/talent/search` | Buscar desarrolladores | Query: `tecnologia`, `disponibilidad`, `ubicacion`, `page`, `size` | `PaginationResponse<DeveloperProfileResponse>` |
  | `GET /api/v1/musica/search?q=` | Buscar música (iTunes) | `q` (término) | `List<MusicTrackResponse>` |
  | `GET /api/v1/musica/lyrics` | Letras sincronizadas (LRCLIB) | `artist`, `track` | `LyricsResponse { plainLyrics, syncedLyrics }` |

  #### Subscriptions GraphQL (WebSocket)

  | Subscription | Entrada | Salida (evento) |
  |--------------|---------|-----------------|
  | `notificationReceived(userId)` | `userId` | `Notification { id, tipo, mensaje, leida, createdAt }` |
  | `connectionStatusChanged(userId)` | `userId` | estado de la solicitud/conexión |

  ---

  ## 3. Avance del backend

  | Fase | Módulos | Estado |
  |------|---------|--------|
  | **Fase 1** — Base y seguridad | Users, Auth (JWT), Recruiter Profiles, Developer Profiles (parcial), Upload (Cloudinary) | ✅ Completa |
  | **Fase 2** — Comunidad técnica | Posts, Comments, Reactions, Stories, Reels | ✅ Completa |
  | **Fase 3** — Conexiones | Follows, Connection Requests, Connections, Blocks, Notifications | ✅ Completa |
  | **Fase 4** — Empleabilidad | Talent Search (búsqueda + guardados, REST y GraphQL) | ✅ Completa |
  | **Fase 5** — Multimedia y sostenibilidad | Companies, EcoDev | 🔲 Pendiente |
  | **Fase 6** — Gamificación y operación | Vacancies/Applications, Questions/Answers, Moderación, Support, Points, Plans, Messaging | 🔲 Pendiente |

  - **Arquitectura:** hexagonal (puertos y adaptadores) con vertical slicing por módulo.
  - **Endpoints activos:** GraphQL en `/api/v1/graphql` (+ GraphiQL), REST en Swagger (`/swagger-ui.html`), subscriptions en WebSocket.
  - Detalle: `doc\06-IMPLEMENTACION.md`.

  ---

  ## 4. Servicio a usar

  | Servicio | Uso en COMUNIDEV |
  |----------|------------------|
  | **MongoDB Atlas** (M0 gratuito) | Base de datos en la nube (documentos), URI con `mongodb+srv://`. |
  | **Railway** | Despliegue y hosting del backend (build automático desde GitHub, Docker). |
  | **Cloudinary** | Almacenamiento y entrega de imágenes, videos, CV y certificados. |
  | **GitHub Actions** | CI/CD: build y pruebas en cada push/PR. |
  | **iTunes Search API / LRCLIB** | Búsqueda de música y letras sincronizadas para stories/reels. |
  | **Leaflet + OpenStreetMap** | Mapa laboral y de puntos de reciclaje (frontend). |

  ---

  ## 5. Tipo de base de datos

  - **Clasificación:** Base de datos **NoSQL de tipo documental** (MongoDB Atlas).
  - **Por qué documental para COMUNIDEV:**
    - Documentos autodescriptivos: perfiles, posts y vacantes tienen estructuras anidadas y variables (CV, contenido embebido, estadísticas).
    - Esquema flexible: evoluciona sin migraciones esquemáticas rígidas.
    - Escalado horizontal en Atlas (réplicas y sharding).
    - Índices compuestos y únicos para consultas de feed, reacciones y conexiones.
  - **Acceso desde el backend:** Spring Data MongoDB (`spring-boot-starter-data-mongodb`) con repositorios y adaptadores por módulo.

  ---

  ## 6. Lenguaje y/o framework backend (versión)

  | Componente | Versión (según `pom.xml`) |
  |------------|----------------------------|
  | **Lenguaje** | **Java 21** (LTS, `java.version=21`) |
  | **Framework backend** | **Spring Boot 4.0.5** (`spring-boot-starter-parent`) |
  | GraphQL | `spring-boot-starter-graphql` (Spring for GraphQL) |
  | Persistencia | `spring-boot-starter-data-mongodb` (Spring Data MongoDB) |
  | Seguridad | `spring-security-crypto` (BCrypt) + JWT propio |
  | Documentación API | springdoc-openapi (Swagger UI) **2.8.6** |
  | Tiempo real | `spring-boot-starter-websocket` |
  | Validación | `spring-boot-starter-validation` (Jakarta Validation) |
  | Archivos | Cloudinary `cloudinary-http5` **2.4.0** |
  | Utilidades | Lombok |
  | Build | Maven Wrapper (`./mvnw`), plugin `spring-boot-maven-plugin` |

  ---

  ## 7. Consideraciones de seguridad

  1. **Autenticación JWT:** token de acceso + refresh token con rotación; expiración configurada (`JWT_EXPIRATION`).
  2. **Contraseñas:** hash con **BCrypt** (`passwordHash`), nunca texto plano; mínimo 8 caracteres con letra y número (RN-02).
  3. **Autorización por rol:** Spring Security valida roles `DEVELOPER`, `RECRUITER`, `MODERATOR`, `ADMIN`; el servidor es la única fuente de permisos (RNF-04).
  4. **Privacidad y visibilidad:** las reglas de visibilidad (público, seguidores, conexiones, solo yo) y del perfil se validan en el backend, incluso si el cliente consulta directo por GraphQL (RNF-05).
  5. **Secretos en variables de entorno:** `MONGODB_URI`, `JWT_SECRET`, credenciales de Cloudinary nunca en el código; solo en `.env` fuera del repositorio (`env.local.example` como plantilla).
  6. **Validación de entrada:** Jakarta Validation en DTOs (email válido, enums, coordenadas, tamaños) — rechaza entradas malformadas.
  7. **Rate limiting:** límite de intentos en login/registro para mitigar fuerza bruta (RNF-06).
  8. **Archivos:** solo tipos MIME permitidos y tamaño máximo; almacenamiento en Cloudinary, no en disco del servidor (RNF-09).
  9. **Suspensiones:** un usuario suspendido no puede escribir en la plataforma (RN-05); baneos persistidos en `user_bans`.
  10. **Auditoría:** acciones de moderación registradas en `audit_logs` con actor, acción y fecha (RNF-28).
  11. **HTTPS/CORS:** despliegue en Railway con HTTPS; CORS restringido a los orígenes del frontend.
  12. **Transporte de datos:** MongoDB Atlas con conexión TLS (`mongodb+srv://`).

  ---

  ## Referencias internas

  - Visión general: `doc\01-VISION-GENERAL.md`
  - Entidades y colecciones: `doc\03-ENTIDADES.md`, `doc\MONGODB-ATLAS-COLLECTIONS.md`
  - Estado de implementación: `doc\06-IMPLEMENTACION.md`
  - Seguridad: `doc\07-SEGURIDAD.md`
  - Reglas de negocio: `doc\14-PLAN-SPRINTS.md` (sección 4)
  - Requerimientos no funcionales: `doc\13-RNF.md`
