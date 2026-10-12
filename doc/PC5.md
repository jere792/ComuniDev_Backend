# PC5 — Planificación ágil COMUNIDEV

> **Proyecto:** COMUNIDEV — Red social colaborativa para desarrolladores, asistencia técnica y empleabilidad.
> **Documento consolidado:** contexto, historias de usuario, criterios de aceptación, requerimientos no funcionales, plan de 16 sprints y arquitectura MVVM con modelo de datos local (app móvil).
> **Documentos de detalle:** `doc\11-BACKLOG-HU.md`, `doc\12-CRITERIOS-ACEPTACION.md`, `doc\13-RNF.md`, `doc\14-PLAN-SPRINTS.md`.

---

## 1. Contexto: ¿Qué es COMUNIDEV?

COMUNIDEV es una plataforma web orientada a desarrolladores, estudiantes de tecnología, reclutadores y empresas. Combina una red social profesional, una comunidad de resolución de problemas técnicos, bolsa de empleo geolocalizada y un módulo de sostenibilidad tecnológica vinculado al **ODS 12: Producción y consumo responsables**.

Permite a los desarrolladores construir un perfil profesional, publicar contenido técnico, crear historias y reels, formular preguntas técnicas, aportar soluciones, establecer conexiones profesionales, recibir notificaciones, buscar empleo y postular a vacantes. Los reclutadores registran empresas, publican vacantes y administran postulaciones. El sistema muestra en un mapa las empresas con vacantes activas y los puntos de reciclaje electrónico.

### 1.1 Objetivo general

Desarrollar una plataforma de comunidad tecnológica que facilite la colaboración entre desarrolladores, la resolución de problemas técnicos y el acceso a ofertas laborales geolocalizadas, promoviendo prácticas responsables frente al consumo y desecho de dispositivos electrónicos.

### 1.2 Roles del sistema

| Rol | Descripción |
|-----|-------------|
| **Visitante** | Consulta contenido público, ofertas laborales y mapa general sin iniciar sesión. |
| **Desarrollador** | Crea publicaciones y preguntas, responde consultas, administra su perfil y postula a vacantes. |
| **Reclutador** | Registra una empresa, publica vacantes y gestiona postulaciones. |
| **Moderador** | Revisa reportes y modera contenido inapropiado. |
| **Administrador** | Gestiona usuarios, roles, categorías, empresas, vacantes, puntos EcoDev y métricas. |

Un usuario puede tener varios roles almacenados, pero debe existir un `rolActivo` que defina la experiencia y permisos de la sesión actual.

### 1.3 Relación con el ODS 12

| Acción en COMUNIDEV | Aporte al ODS 12 |
|---------------------|------------------|
| Publicaciones sobre Green IT y reciclaje | Sensibilización sobre consumo tecnológico responsable. |
| Mapa de puntos de reciclaje electrónico | Facilita el descarte adecuado de equipos y accesorios. |
| Donación, reparación e intercambio de equipos | Promueve reutilización y extensión de vida útil. |
| Empresas con prácticas sostenibles | Visibiliza organizaciones comprometidas con gestión responsable de tecnología. |
| Indicadores ambientales | Permite medir el impacto comunitario de acciones sostenibles. |

### 1.4 Alcance del MVP

Para la primera versión se priorizan las historias marcadas como **Must** del backlog: autenticación, perfiles, publicaciones y feed, interacción (comentarios/reacciones), preguntas técnicas, conexiones, notificaciones, empresas, vacantes, postulaciones, mapa laboral y contenido EcoDev básico. Gamificación, planes y analítica quedan como backlog continuo.

---

## 2. Stack tecnológico (real del proyecto)

| Componente | Tecnología |
|------------|------------|
| Backend | Java 21 + Spring Boot 4.0.5 |
| API principal | GraphQL (Spring for GraphQL) — `/api/v1/graphql` + GraphiQL |
| API secundaria | REST (auth, upload, música, talent search) + Swagger/OpenAPI |
| Base de datos | MongoDB Atlas (Spring Data MongoDB) |
| Patrón | Arquitectura hexagonal (puertos y adaptadores) + vertical slicing |
| Autenticación | JWT (access + refresh), Spring Security, roles DEVELOPER / RECRUITER / MODERATOR / ADMIN |
| Archivos | Cloudinary (imágenes, videos, CV) |
| Tiempo real | GraphQL Subscriptions sobre WebSocket |
| Frontend (previsto) | Angular |
| Móvil (futuro) | Kotlin + Jetpack Compose |
| Mapa | Leaflet + OpenStreetMap |
| Despliegue | Railway + Docker |

---

## 3. Historias de Usuario (resumen del Product Backlog)

Backlog completo (61 HU) en `doc\11-BACKLOG-HU.md`. Formato: *"Como [rol], quiero [acción] para [beneficio]"* con prioridad MoSCoW y puntos de historia (Fibra).

| Épica | Rango HU | Puntos | Prioridad principal |
|-------|----------|--------|---------------------|
| A — Autenticación y acceso | HU-01…05 | 16 | Must |
| B — Usuarios y perfiles | HU-06…11 | 27 | Must |
| C — Publicaciones y feed | HU-12…15 | 19 | Must |
| D — Comentarios y reacciones | HU-16…18 | 15 | Must |
| E — Stories y reels | HU-19…21 | 13 | Should |
| F — Asistencia técnica | HU-22…24 | 16 | Must |
| G — Votos y reputación | HU-25…27 | 13 | Must / Should |
| H — Conexiones y privacidad social | HU-28…32 | 19 | Must |
| I — Notificaciones y tiempo real | HU-33…34 | 13 | Must / Should |
| J — Búsqueda y talento | HU-35…37 | 18 | Should |
| K — Empresas | HU-38…39 | 13 | Must |
| L — Vacantes | HU-40…42 | 21 | Must |
| M — Postulaciones | HU-43…45 | 19 | Must |
| N — Mapa laboral | HU-46…48 | 26 | Must |
| O — EcoDev y ODS 12 | HU-49…52 | 26 | Must |
| P — Moderación y administración | HU-53…57 | 34 | Must / Should |
| Q — Gamificación y analítica | HU-58…61 | 34 | Could / Won't (continuo) |

---

## 4. Criterios de aceptación (Gherkin — principales)

Catálogo completo (120+ escenarios) en `doc\12-CRITERIOS-ACEPTACION.md`.

### 4.1 Registro de usuarios

```gherkin
  Escenario: Registro exitoso
    Dado que el visitante no tiene una cuenta registrada
    Cuando ingresa un correo válido y una contraseña que cumple las reglas de seguridad
    Y selecciona la opción "Crear cuenta"
    Entonces el sistema crea la cuenta con el rol DEVELOPER por defecto
    Y muestra el mensaje "Registro exitoso"

  Escenario: Registro con correo duplicado
    Dado que ya existe una cuenta con el correo "usuario@correo.com"
    Cuando un visitante intenta registrarse con el mismo correo
    Entonces el sistema rechaza el registro
    Y muestra el mensaje "El correo ya se encuentra registrado"
```

### 4.2 Creación de publicaciones

```gherkin
  Escenario: Publicación válida
    Dado que el desarrollador inició sesión
    Cuando escribe contenido en el formulario de publicación y selecciona "Publicar"
    Entonces el sistema registra la publicación con su autor y fecha
    Y la muestra en el feed según su nivel de visibilidad

  Escenario: Publicación vacía
    Dado que el desarrollador inició sesión
    Cuando intenta publicar sin texto ni archivo adjunto
    Entonces el sistema no registra la publicación
    Y muestra el mensaje "Debe ingresar contenido o adjuntar un archivo"
```

### 4.3 Resolución de preguntas técnicas

```gherkin
  Escenario: Crear una pregunta técnica
    Dado que el desarrollador inició sesión
    Cuando registra título, descripción, código de ejemplo, al menos una etiqueta y una categoría
    Y selecciona "Publicar pregunta"
    Entonces el sistema publica la pregunta en la sección de asistencia técnica

  Escenario: Marcar respuesta aceptada
    Dado que el autor visualiza las respuestas de su pregunta
    Cuando selecciona "Marcar como solución" en una respuesta
    Entonces el sistema identifica la respuesta como aceptada
    Y no permite aceptar una segunda respuesta para la misma pregunta
```

### 4.4 Vacantes y postulaciones

```gherkin
  Escenario: Publicar una vacante válida
    Dado que el reclutador está vinculado a una empresa registrada y validada
    Cuando completa título, tecnologías, modalidad, ubicación y fecha límite válida
    Y selecciona "Publicar vacante"
    Entonces el sistema registra la vacante con estado ACTIVA
    Y la muestra en la lista de empleos y en el mapa laboral

  Escenario: Postulación duplicada
    Dado que el desarrollador ya tiene una postulación activa a una vacante
    Cuando intenta postular nuevamente a la misma vacante
    Entonces el sistema bloquea la acción
    Y muestra el mensaje "Ya existe una postulación activa para esta vacante"
```

### 4.5 Mapa laboral

```gherkin
  Escenario: Mostrar empresas en el mapa
    Dado que existen empresas con vacantes activas y coordenadas válidas
    Cuando el usuario ingresa al mapa laboral
    Entonces el sistema muestra un marcador por cada empresa o grupo de empresas cercanas
    Y cada marcador muestra la cantidad de vacantes activas

  Escenario: Filtrar vacantes por tecnología
    Dado que el usuario se encuentra en el mapa laboral
    Cuando selecciona la tecnología "Angular" y aplica los filtros
    Entonces el sistema muestra únicamente empresas con vacantes que requieren Angular
```

### 4.6 EcoDev y ODS 12

```gherkin
  Escenario: Publicar equipo en donación
    Dado que el usuario inició sesión
    Cuando registra una publicación con categoría, estado del equipo, ubicación aproximada y etiqueta "Donación"
    Y selecciona "Publicar"
    Entonces el sistema registra el anuncio en la sección EcoDev
    Y lo clasifica como contenido relacionado con ODS 12

  Escenario: Punto de reciclaje validado
    Dado que un administrador validó un punto de reciclaje electrónico
    Cuando un usuario consulta el mapa EcoDev
    Entonces el sistema muestra el punto de reciclaje
    Y muestra su nombre, ubicación y tipos de residuos aceptados
```

### 4.7 Moderación

```gherkin
  Escenario: Reportar una publicación
    Dado que un usuario autenticado visualiza una publicación inapropiada
    Cuando selecciona "Reportar" y registra una razón válida
    Entonces el sistema crea un reporte con estado EN_REVISION
    Y notifica a los moderadores

  Escenario: Moderar contenido reportado
    Dado que un moderador visualiza un reporte pendiente
    Cuando decide ocultar la publicación por incumplir las normas
    Entonces el sistema cambia el estado de la publicación a OCULTA
    Y notifica al autor sobre la acción tomada
```

---

## 5. Requerimientos No Funcionales (resumen)

Catálogo completo (43 RNF) en `doc\13-RNF.md`.

| Categoría | Contenido principal | Ejemplos |
|-----------|---------------------|----------|
| **Seguridad** (RNF-01…10) | JWT access+refresh con rotación, BCrypt, autorización por rol, validación de privacidad en servidor, rate limiting, secretos en variables de entorno. | RNF-01, RNF-05, RNF-07 |
| **Rendimiento** (RNF-11…15) | Respuestas < 500 ms, índices MongoDB, paginación obligatoria, agregación eficiente del mapa. | RNF-11, RNF-12, RNF-13 |
| **Disponibilidad** (RNF-16…20) | Despliegue en Railway con CI, Docker reproducible, health check, MongoDB Atlas TLS y backups. | RNF-16, RNF-18 |
| **Escalabilidad** (RNF-21…24) | Backend stateless, horizontal scaling, modelo de datos sin migraciones monolíticas, archivos en Cloudinary. | RNF-21, RNF-24 |
| **Privacidad** (RNF-25…29) | Ubicación personal no pública sin consentimiento, auditoría de moderación, eliminación de cuenta. | RNF-25, RNF-28 |
| **Usabilidad API** (RNF-30…34) | GraphQL versionado + GraphiQL, Swagger en REST, errores consistentes, suscripciones WebSocket. | RNF-30, RNF-34 |
| **Mantenibilidad** (RNF-35…40) | Arquitectura hexagonal, vertical slicing, pruebas automáticas, CI, sin secretos en repo. | RNF-35, RNF-37, RNF-38 |
| **Tiempo real** (RNF-41…43) | Eventos solo al usuario destino, suscripciones autenticadas, corte al hacer logout. | RNF-42, RNF-43 |

---

## 6. Plan de 16 sprints (1 semana c/u)

Plan detallado (objetivo, entregables, RN/RNF por sprint) en `doc\14-PLAN-SPRINTS.md`.

| Sprint | Semana | Objetivo | HU | Puntos |
|--------|--------|----------|-----|--------|
| 1 | 1 | Arranque del proyecto y autenticación | HU-01…05 | 16 |
| 2 | 2 | Usuarios, perfiles y subida de archivos | HU-06…09 | 19 |
| 3 | 3 | Publicaciones y feed con visibilidad | HU-12…15 | 19 |
| 4 | 4 | Comentarios anidados y reacciones | HU-16…18 | 15 |
| 5 | 5 | Stories efímeras y reels | HU-19…21 | 13 |
| 6 | 6 | Preguntas técnicas (crear y responder) | HU-22…24 | 16 |
| 7 | 7 | Respuesta aceptada, votos y reputación | HU-25…27 | 13 |
| 8 | 8 | Conexiones y privacidad social | HU-10, 28…31 | 21 |
| 9 | 9 | Bloqueos, notificaciones y tiempo real | HU-32…34 | 16 |
| 10 | 10 | Búsqueda general y búsqueda de talento | HU-11, 35…37 | 18 |
| 11 | 11 | Empresas: registro y validación | HU-38…39 | 13 |
| 12 | 12 | Vacantes: CRUD, ciclo de vida y filtros | HU-40…42 | 21 |
| 13 | 13 | Postulaciones y gestión de selección | HU-43…45 | 19 |
| 14 | 14 | Mapa laboral geolocalizado | HU-46…48 | 26 |
| 15 | 15 | EcoDev y ODS 12 | HU-49…52 | 26 |
| 16 | 16 | Moderación, administración y cierre | HU-53…57, 61 | 36 |
| — | + | Backlog continuo (post-curso) | HU-58…60 | 29 |

### Marco Scrum

- **Roles:** Product Owner, Scrum Master, Equipo de desarrollo, Stakeholder/Docente.
- **Ceremonias semanales:** Sprint Planning (lunes), Daily Scrum (diario), Sprint Review (viernes), Retrospectiva (viernes).
- **Definition of Done:** código en main + build verde, criterios Gherkin cumplidos, RN/RNF respetados, arquitectura hexagonal, pruebas pasando, PO acepta en Review.

---

## 7. Reglas de negocio (destacadas)

Catálogo completo RN-01…RN-42 en `doc\14-PLAN-SPRINTS.md` (sección 4).

| Código | Regla |
|--------|-------|
| RN-01 | Correo electrónico único y válido para registrarse. |
| RN-02 | Contraseña mínimo 8 caracteres con letra y número. |
| RN-04 | Perfil completo (nombre, ubicación, tecnología) antes de postular. |
| RN-05 | Usuario suspendido no puede publicar, comentar, responder, postular ni enviar mensajes. |
| RN-09 | Pregunta técnica con título, descripción y al menos una etiqueta. |
| RN-12 / RN-13 | Solo el autor acepta; una sola respuesta aceptada por pregunta. |
| RN-18 / RN-19 | Empresa con datos y coordenadas válidas antes de publicar vacantes. |
| RN-22 | Vacante vencida cambia automáticamente a cerrada. |
| RN-24 | Sin postulaciones duplicadas activas. |
| RN-29…35 | Reglas del mapa laboral: solo empresas con vacantes activas, marcadores con conteo, filtros, privacidad de ubicación. |
| RN-36…42 | Reglas EcoDev: etiquetas ODS 12, validación de puntos de reciclaje, indicadores de impacto confirmados. |

---

## 8. Arquitectura MVVM y modelo de datos local (app móvil)

La aplicación móvil de COMUNIDEV (Kotlin + Jetpack Compose) adopta el patrón **MVVM** (Model–View–ViewModel) con un flujo unidireccional de datos y persistencia local mediante **Room** (estrategia *offline-first*).

### 8.1 Visión de la arquitectura

```
┌─────────────────────────────────────────────────────────────┐
│  VIEW (Jetpack Compose)                                     │
│  Pantallas/Composables — renderizan el UiState, emiten      │
│  eventos de usuario                                         │
└──────────────────────────┬──────────────────────────────────┘
                           │ eventos (Intents)
                           ▼
┌─────────────────────────────────────────────────────────────┐
│  VIEWMODEL (StateFlow<UiState>)                             │
│  Expone estado inmutable, orquesta UseCases                 │
└──────────────────────────┬──────────────────────────────────┘
                           │ invoca
                           ▼
┌─────────────────────────────────────────────────────────────┐
│  DOMAIN (UseCases)                                          │
│  Reglas de negocio del cliente                              │
└──────────────────────────┬──────────────────────────────────┘
                           │ consulta / persiste
                           ▼
┌─────────────────────────────────────────────────────────────┐
│  DATA (Repository)                                          │
│  Orquesta fuentes remotas y locales; caché y sincronización │
└──────────────┬─────────────────────────────┬────────────────┘
               │                             │
               ▼                             ▼
┌───────────────────────────┐   ┌───────────────────────────┐
│ REMOTE                    │   │ LOCAL (Room)              │
│ GraphQL/REST (backend     │   │ Entities + DAOs,          │
│ Spring Boot), WebSocket   │   │ single source of truth    │
└───────────────────────────┘   └───────────────────────────┘
```

Inyección de dependencias con **Hilt**; estado con `StateFlow`/`SharedFlow`; navegación con Compose Navigation.

### 8.2 Responsabilidades por capa

| Capa | Componente | Responsabilidad | Ejemplo |
|------|-----------|-----------------|---------|
| **View** | Composables / Screens | Renderizar `UiState`, emitir eventos (intents) al ViewModel | `FeedScreen`, `PostDetailScreen`, `VacancyMapScreen` |
| **ViewModel** | `ViewModel` + `UiState` | Exponer estado inmutable con `StateFlow`, manejar eventos y errores | `FeedViewModel`, `AuthViewModel` |
| **Domain** | UseCases | Reglas de negocio del cliente (validaciones, transformaciones) | `GetFeedUseCase`, `CreatePostUseCase`, `SyncNotificationsUseCase` |
| **Data** | Repository | Orquestar fuentes remota y local, manejar caché y sincronización | `PostRepository`, `UserRepository` |
| **Remote** | Cliente GraphQL/REST + WebSocket | Comunicación con el backend Spring Boot | `GraphQlApi`, `AuthApi`, `NotificationWs` |
| **Local** | Room (Entities + DAOs) | Persistencia offline y *single source of truth* | `PostDao`, `UserEntity`, `PendingMutationDao` |

### 8.3 Estrategia offline-first

- **Room como fuente de verdad local:** las pantallas leen primero de Room; el repositorio actualiza la caché con la respuesta remota.
- **Política por módulo:**
  - *Cache-first:* feed, perfil, notificaciones (lecturas frecuentes, tolerancia a datos leves).
  - *Network-first:* búsquedas, vacantes, mapa (datos que cambian seguido).
  - *Write-through:* escrituras (posts, reacciones) se aplican optimistamente en Room y se confirman contra el backend.
- **Cola de mutaciones offline:** las acciones del usuario se registran en `PendingMutationEntity` y se sincronizan al recuperar conectividad, con confirmación o *rollback*.
- **Sesión:** tokens (access + refresh) almacenados localmente y renovados automáticamente al arrancar la app o recibir 401.
- **Tiempo real:** suscripción WebSocket que actualiza Room con notificaciones y estados de conexión, notificando a la UI vía `Flow`.

### 8.4 Modelo de datos local (Room)

Entidades principales y relaciones:

| Entidad | Campos clave | Relaciones / índices |
|---------|--------------|----------------------|
| `UserEntity` | id, nombreUsuario, nombre, email, avatarUrl, rolActivo, estado | 1..N con `PostEntity`, `CommentEntity` |
| `TokenEntity` | accessToken, refreshToken, expiresAt | Una fila por sesión activa |
| `PostEntity` | id, autorId, contenido, mediaUrls, visibilidad, createdAt, likeCount, commentCount, syncStatus | Índice local `autorId + createdAt` |
| `CommentEntity` | id, postId, parentCommentId, autorId, contenido, createdAt | Índice local `postId + parentCommentId` (comentarios anidados) |
| `ReactionEntity` | id, usuarioId, contenidoId, tipoContenido, tipo | Índice local `usuarioId + contenidoId + tipoContenido` (único) |
| `DraftPostEntity` | id, contenido, mediaUris, updatedAt | Borradores del usuario |
| `NotificationEntity` | id, tipo, leida, createdAt, payloadJson | Índice local `leida + createdAt` |
| `VacancyEntity` / `CompanyEntity` | datos de vacantes y empresas para la sección de empleo | Cache del mapa y listados |
| `PendingMutationEntity` | id, tipoOperacion, payloadJson, createdAt, estado | Cola offline de escrituras |

> El modelo local es un **subconjunto en caché** del modelo GraphQL del backend (`doc\03-ENTIDADES.md`), orientado a consulta y sincronización, no a duplicar todo el dominio.

### 8.5 Flujo de sincronización

1. **Arranque:** la app lee `TokenEntity`; si el access token expiró, usa el refresh token para renovarlo.
2. **Lectura:** Room emite los datos en caché al ViewModel/UI; en paralelo el repositorio consulta el backend y actualiza Room (la UI se refresca automáticamente por el `Flow` de Room).
3. **Escritura:** mutación optimista en Room + registro en `PendingMutationEntity`; el repositorio envía la mutación al backend y al confirmarla marca la operación como sincronizada (o la revierte si falla).
4. **Tiempo real:** la suscripción WebSocket (notificaciones, estado de conexión) actualiza Room; los DAOs notifican a la UI sin refresco manual.

---

## 9. Conclusión

COMUNIDEV integra una comunidad técnica, asistencia colaborativa, bolsa laboral geolocalizada y acciones vinculadas con el ODS 12 en una única plataforma. La planificación ágil propuesta — 61 historias de usuario, más de 120 escenarios Gherkin, 43 requerimientos no funcionales y 16 sprints semanales con marco Scrum — permite construir el sistema de forma incremental priorizando el MVP (Must) y dejando gamificación, planes y analítica como backlog continuo. El stack real (Spring Boot 4.0.5, MongoDB Atlas, GraphQL + REST, JWT, Cloudinary, WebSocket) sostiene una arquitectura hexagonal mantenible, escalable y segura, mientras que la app móvil respalda la experiencia de usuario con arquitectura MVVM y persistencia local Room (offline-first). Todo alineado con los objetivos del proyecto y el alcance del curso.
