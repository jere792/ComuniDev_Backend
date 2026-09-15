# COMUNIDEV — Estado de Implementación

> Última actualización:Septiembre 2026

---

## Resumen ejecutivo

| Fase | Módulos | Estado |
|------|---------|--------|
| **Fase 1** — Base y seguridad | Users, Auth, Profiles, Upload | ✅ Completa |
| **Fase 2** — Comunidad técnica | Posts, Comments, Reactions, Stories, Reels | ✅ Completa |
| **Fase 3** — Conexiones | Follows, Connection Requests, Connections, Blocks, Notifications | ✅ Completa |
| **Fase 4** — Empleabilidad | Talent Search | ✅ Completa |
| **Fase 5** — Multimedia y sostenibilidad | — | 🔲 Pendiente |
| **Fase 6** — Gamificación y operación | — | 🔲 Pendiente |

---

## 1. Módulos backend — Estado detallado

### Fase 1: Base y seguridad

| Módulo | Estado | Entidades | Resolvers / Endpoints |
|--------|--------|-----------|----------------------|
| **Users** | ✅ Completo | `User` | `me`, `user`, `users`, `updateUser`, `deleteUser` |
| **Auth** | ✅ Completo | — | `login` (JWT), `register` (con verificación email) |
| **Recruiter Profiles** | ✅ Completo | `RecruiterProfile` | `recruiterProfile`, `myRecruiterProfile`, `createRecruiterProfile`, `updateRecruiterProfile` |
| **Developer Profiles** | ⚠️ Parcial | `DeveloperProfile` (schema + dominio, sin resolver) | — |
| **Upload (Cloudinary)** | ✅ Completo | — | REST controller con multipart upload |

### Fase 2: Comunidad técnica

| Módulo | Estado | Entidades | Resolvers GraphQL |
|--------|--------|-----------|-------------------|
| **Posts** | ✅ Completo | `Post` | `posts`, `post`, `feed` (paginado, seguidos primero), `createPost`, `updatePost`, `deletePost` |
| **Comments** | ✅ Completo | `Comment` | `comments`, `comment`, `commentReplies`, `createComment`, `updateComment`, `deleteComment` |
| **Reactions** | ✅ Completo | `Reaction` | `reactions`, `myReaction`, `react`, `unreact`, `changeReaction` |
| **Stories** | ✅ Completo | `Story` | `stories` (24h expiry), `story`, `storyViews`, `createStory`, `viewStory`, `deleteStory` |
| **Reels** | ✅ Completo | `Reel` | `reels`, `reel`, `reelsByUser`, `createReel`, `updateReel`, `deleteReel` |

### Fase 3: Conexiones y seguridad social

| Módulo | Estado | Entidades | Resolvers GraphQL |
|--------|--------|-----------|-------------------|
| **Follows** | ✅ Completo | `Follow` | `followers`, `following`, `isFollowing`, `follow`, `unfollow` |
| **Connection Requests** | ✅ Completo | `ConnectionRequest` | `connectionRequests`, `connectionStatus`, `sendConnectionRequest`, `acceptConnection`, `rejectConnection` |
| **Connections** | ✅ Completo | `Connection` | `connections`, `removeConnection` |
| **Blocks** | ✅ Completo | `Block` | `isBlocked`, `block`, `unblock` |
| **Notifications** | ✅ Completo | `Notification` | `notifications`, `unreadCount`, `markNotificationAsRead`, `markAllAsRead`, `deleteNotification` |

### Fase 4: Empleabilidad

| Módulo | Estado | Entidades | Resolvers / Endpoints |
|--------|--------|-----------|----------------------|
| **Talent Search** | ✅ Completo | — | `searchDevelopers`, `savedProfiles`, `isProfileSaved`, `savedSearches` (REST + GraphQL) |

### Fase 5: Multimedia y sostenibilidad (pendiente)

| Módulo | Estado |
|--------|--------|
| Companies | 🔲 Schema definido, sin implementar |
| EcoDev | 🔲 Schema definido, sin implementar |

### Fase 6: Gamificación y operación (pendiente)

| Módulo | Estado |
|--------|--------|
| Vacancies / Applications | 🔲 Schema definido, sin implementar |
| Questions / Answers | 🔲 Schema definido, sin implementar |
| Moderation | 🔲 Schema definido, sin implementar |
| Support Tickets | 🔲 Schema definido, sin implementar |
| Points / Gamification | 🔲 Schema definido, sin implementar |
| Plans / Subscriptions | 🔲 Schema definido, sin implementar |
| Messaging | 🔲 Schema definido, sin implementar |

---

## 2. Enums sociales

| Enum | Valores |
|------|---------|
| `TipoReaccion` | `LIKE`, `LOVE`, `CELEBRATE`, `SUPPORT` |
| `TipoHistoria` | `TEXT`, `IMAGE`, `VIDEO` |
| `VisibilidadPost` | `PUBLICO`, `SEGUIDORES`, `CONEXIONES`, `SOLO_YO` |
| `EstadoSolicitud` | `PENDIENTE`, `ACEPTADA`, `RECHAZADA` |
| `TipoNotificacion` | `SOLICITUD_CONEXION`, `CONEXION_ACEPTADA`, `COMENTARIO`, `REACCION` |

---

## 3. Endpoints

### GraphQL

| Ruta | Descripción |
|------|-------------|
| `/api/v1/graphql` | Endpoint principal (queries + mutations) |
| `/graphiql` | IDE interactivo para pruebas |

### REST (Swagger)

| Ruta | Descripción |
|------|-------------|
| `/swagger-ui.html` | Documentación interactiva |
| `/v3/api-docs` | Spec OpenAPI 3.0 |

---

## 4. Subscriptions GraphQL (WebSocket) — Pendiente

| Subscription | Estado |
|-------------|--------|
| `onNewMessage` | 🔲 No implementado |
| `onMessageUpdated` | 🔲 No implementado |
| `onNotification` | 🔲 No implementado |

---

## 5. Índices MongoDB

| Colección | Índice único | Índices compuestos |
|-----------|-------------|-------------------|
| `users` | `email` | `nombreUsuario` |
| `recruiter_profiles` | `userId` | `userId + fechaCreacion` |
| `developer_profiles` | `userId` | — |
| `follows` | — | `followerId + followingId` |
| `posts` | — | `autorId + createdAt` |
| `comments` | — | `postId + parentCommentId + createdAt` |
| `reactions` | — | `usuarioId + contenidoId + tipoContenido` (unique) |
| `stories` | — | `autorId + createdAt` |
| `reels` | — | `autorId + createdAt` |
| `connection_requests` | — | `solicitanteId + receptorId + estado` |
| `connections` | — | `usuarioMenorId + usuarioMayorId` |
| `blocks` | — | `bloqueadorId + bloqueadoId` (unique) |
| `notifications` | — | `usuarioDestinoId + leida + createdAt` |

---

## 6. Variables de entorno

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

---

## 7. Arquitectura

- **Patrón:** Hexagonal (puertos y adaptadores)
- **Organización:** Vertical slicing por módulo (`domain/` → `ports/` → `data-access/` → `feature/` → `ui/`)
- **Base de datos:** MongoDB Atlas
- **API:** GraphQL (principal) + REST (auth, upload, talent search)
- **Auth:** JWT con roles `DEVELOPER`, `RECRUTADOR`, `MODERADOR`, `ADMIN`
