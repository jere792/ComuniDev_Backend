# COMUNIDEV — Implementación

## Orden recomendado de implementación

### Fase 1: Base y seguridad
- users, autenticación JWT, roles y configuración.
- developer_profiles y recruiter_profiles.
- companies y validación básica.
- MongoDB Atlas, Railway, Swagger y manejo global de errores.

### Fase 2: Comunidad técnica
- posts, comments, reactions, saved_contents.
- technical_questions, technical_answers, question_votes.
- Feed básico y búsqueda por etiquetas.
- notifications iniciales.

### Fase 3: Conexiones y mensajería
- connection_requests, connections, follows, blocked_users.
- Reglas de visibilidad de perfil y contenido.
- conversations, messages y WebSockets.
- Estados de actividad y notificaciones en tiempo real.

### Fase 4: Empleabilidad y mapa
- vacancies, applications, application_status_history.
- Mapa de empresas con vacantes activas y filtros.
- Perfil profesional, CV, certificados y portafolio.
- Vistas específicas para reclutadores y desarrolladores.

### Fase 5: Multimedia y sostenibilidad
- stories, story_views, reels.
- ecotech_posts, recycling_points, ecotech_transactions.
- Métricas de impacto ODS 12.

### Fase 6: Gamificación, planes y operación
- user_points, point_transactions, badges, user_badges.
- plans y subscriptions.
- reports, moderation_actions, support_tickets, audit_logs.
- Dashboards y analítica administrativa.

## Alcance mínimo viable (MVP)

Para no sobredimensionar el proyecto desde el primer sprint, el MVP debe incluir:

- Registro, login JWT, roles DEVELOPER y RECRUITER.
- Perfil de desarrollador con tecnologías, proyectos y carga de CV.
- Perfil de empresa y publicación de vacantes.
- Feed básico con publicaciones, comentarios y reacciones.
- Preguntas técnicas, respuestas y respuesta aceptada.
- Solicitudes de conexión y conexiones aceptadas.
- Privacidad básica: perfil público o solo conexiones.
- Mensajería directa básica y notificaciones WebSocket.
- Postulación a vacantes.
- Mapa de empresas con vacantes activas.
- Una primera función EcoDev: publicación de equipos para donación/reutilización y puntos de reciclaje verificados.
- Swagger para REST y una o dos consultas GraphQL relevantes, por ejemplo feed y mapa/vacantes.

Las historias, reels, música, planes pagados, insignias avanzadas, reportes complejos y analítica avanzada pueden presentarse como fases posteriores si el tiempo académico es limitado.

---

## Estado de implementación actual

### Módulos backend (Spring Boot + MongoDB + GraphQL)

| Módulo | Estado | Entidades | Resolvers GraphQL |
|--------|--------|-----------|-------------------|
| Users | ✅ Completo | User | getUser, updateUser, register, login, changePassword, uploadProfileImage |
| Auth | ✅ Completo | — | login (JWT), register (con verificación) |
| Recruiter Profiles | ✅ Completo | RecruiterProfile | recruiterProfile, createRecruiterProfile (upsert), updateRecruiterProfile |
| Developer Profiles | ⚠️ Parcial | DeveloperProfile (schema definido, sin resolver) | — |
| Follow | ✅ Completo | Follow | follow, unfollow, followers, following, isFollowing |
| Posts | ✅ Completo | Post | feed (paginated, followed-first), posts, post, createPost, updatePost, deletePost |
| Comments | ✅ Completo | Comment | comments, commentReplies, createComment, updateComment, deleteComment |
| Reactions | ✅ Completo | Reaction | react, unreact, changeReaction, reactions, myReaction |
| Stories | ✅ Completo | Story | stories (24h expiry), storyViews, createStory, viewStory, deleteStory |
| Reels | ✅ Completo | Reel | reels, reel, reelsByUser, createReel, updateReel, deleteReel |
| Upload | ✅ Completo | — | upload (Cloudinary multipart) |

### Endpoints GraphQL

- **GraphQL Playground:** `http://localhost:8080/api/v1/graphql`
- **Frontend Apollo:** `http://localhost:8080/api/v1/graphql`

### Subscriptions GraphQL (WebSocket)

- `onNewMessage` — nuevos mensajes en conversación
- `onMessageUpdated` — actualización de estado de mensaje
- `onNotification` — notificaciones en tiempo real

### Enums social

| Enum | Valores |
|------|---------|
| TipoReaccion | LIKE, LOVE, CELEBRATE, SUPPORT |
| TipoHistoria | TEXT, IMAGE, VIDEO |
| VisibilidadPost | PUBLICO, SEGUIDORES, CONEXIONES, SOLO_YO |

### Índices MongoDB (colecciones sociales)

| Colección | Índice único | Índices compuestos |
|-----------|-------------|-------------------|
| recruiter_profiles | userId | userId + fechaCreacion |
| developer_profiles | userId | — |
| follows | — | followerId + followingId |
| posts | — | autorId + createdAt |
| comments | — | postId + parentCommentId + createdAt |
| reactions | — | usuarioId + contenidoId + tipoContenido (unique) |
| stories | — | autorId + createdAt |
| reels | — | autorId + createdAt |

### Variables de entorno requeridas

```env
# MongoDB Atlas
MONGODB_URI=mongodb+srv://<user>:<pass>@<cluster>.mongodb.net/<db>

# JWT
JWT_SECRET=tu_clave_secreta
JWT_EXPIRATION=86400000

# Cloudinary
CLOUDINARY_CLOUD_NAME=your-cloud-name
CLOUDINARY_API_KEY=your-api-key
CLOUDINARY_API_SECRET=your-api-secret
```
