# COMUNIDEV — Plan de Sprints (Scrum, 16 semanas)

> **Duración:** 16 sprints de 1 semana cada uno.
> **Backlog de referencia:** `doc\11-BACKLOG-HU.md` | **Criterios:** `doc\12-CRITERIOS-ACEPTACION.md` | **RNF:** `doc\13-RNF.md`.
> **Stack real:** Java 17 + Spring Boot 3, MongoDB Atlas (Spring Data MongoDB), GraphQL (Spring for GraphQL) + REST (Swagger/OpenAPI), JWT (access + refresh), Spring Security, Cloudinary, GraphQL Subscriptions (WebSocket), arquitectura hexagonal + vertical slicing. Despliegue en Railway con Docker.

---

## 1. Marco Scrum

### Roles

| Rol | Responsabilidad |
|-----|-----------------|
| **Product Owner** | Prioriza el backlog, define el alcance del MVP y acepta las HU al cierre de cada sprint. |
| **Scrum Master** | Facilita las ceremonias, retira impedimentos, cuida el cumplimiento de la Definition of Done. |
| **Equipo de desarrollo** | Backend (Spring Boot), futuras integraciones frontend (Angular) y móvil (Kotlin). |
| **Stakeholder / Docente** | Revisión en la Review de cada sprint. |

### Ceremonias (semanales)

| Ceremonia | Duración | Cuándo | Objetivo |
|-----------|----------|--------|----------|
| **Sprint Planning** | 60–90 min | Lunes (día 1) | Seleccionar las HU del sprint, definir el objetivo y repartir tareas. |
| **Daily Scrum** | 10–15 min | Cada día | ¿Qué hice? ¿Qué haré? ¿Impedimentos? |
| **Sprint Review** | 45–60 min | Viernes (día 5) | Demo de lo entregado contra los criterios de aceptación; el PO acepta o rechaza HU. |
| **Retrospectiva** | 30–45 min | Viernes (tras la Review) | Mejora continua: qué bien, qué mejorar, acuerdos para el próximo sprint. |

### Backlog grooming

Refinamiento continuo durante la semana: estimar nuevas HU, desglosar tareas técnicas y mantener actualizadas las dependencias.

---

## 2. Definition of Done (DoD)

Una historia de usuario se considera terminada cuando:

1. ✅ El código está en la rama principal y el build (`./mvnw package`) compila sin errores.
2. ✅ Cumple **todos** sus criterios de aceptación Gherkin (caso feliz + validaciones).
3. ✅ Respeta las **reglas de negocio (RN)** y **RNF** asociados (seguridad, visibilidad, validación de entrada).
4. ✅ Arquitectura hexagonal respetada (domain sin dependencias de infraestructura; puertos y adaptadores).
5. ✅ Pruebas automatizadas del módulo pasan (`./mvnw test`) y CI verde en GitHub Actions.
6. ✅ La query/mutación expuesta en el esquema GraphQL está documentada (schema + resolvers).
7. ✅ No introduce deuda técnica grave pendiente (o queda registrada en el backlog con prioridad).
8. ✅ Aceptada por el Product Owner en la Sprint Review.

---

## 3. Capacidades y estimación

- Escala de estimación: Fibonacci (1, 2, 3, 5, 8, 13) en puntos de historia.
- Capacidad estimada por sprint (equipo de curso): **15–22 puntos**.
- Las sprints con carga alta (Mapa laboral, Vacantes) pueden recortar alcance manteniendo el objetivo; el PO decide en el Planning.

---

## 4. Reglas de negocio (RN)

| Código | Regla |
|--------|-------|
| RN-01 | Todo usuario debe registrarse con un correo electrónico único y válido. |
| RN-02 | La contraseña debe tener como mínimo 8 caracteres e incluir al menos una letra y un número. |
| RN-03 | Un usuario solo puede tener un rol principal activo: desarrollador, reclutador, moderador o administrador. |
| RN-04 | Un desarrollador debe completar al menos nombre, ubicación y una tecnología antes de postular a una vacante. |
| RN-05 | Un usuario suspendido no puede publicar, comentar, responder, postular ni enviar mensajes. |
| RN-06 | Solo un administrador puede asignar los roles de moderador y administrador. |
| RN-07 | Un reclutador solo puede publicar vacantes después de validar su cuenta y vincularse a una empresa registrada. |
| RN-08 | Toda publicación debe tener contenido textual o al menos un archivo o imagen adjunta. |
| RN-09 | Una pregunta técnica debe tener título, descripción y al menos una etiqueta tecnológica. |
| RN-10 | Una pregunta debe pertenecer a una categoría: frontend, backend, móvil, base de datos, redes, DevOps o ciberseguridad. |
| RN-11 | El autor puede editar o eliminar su publicación mientras no haya sido bloqueada por moderación. |
| RN-12 | Solo el autor de una pregunta puede marcar una respuesta como aceptada. |
| RN-13 | Una pregunta solo puede tener una respuesta aceptada. |
| RN-14 | Un usuario no puede votar más de una vez por la misma respuesta. |
| RN-15 | Un usuario no puede reaccionar más de una vez con el mismo tipo de reacción a una publicación. |
| RN-16 | Se prohíbe publicar spam, malware, enlaces fraudulentos, contenido ofensivo, discriminatorio o datos personales sin consentimiento. |
| RN-17 | El contenido reportado queda en estado "en revisión" hasta que un moderador o administrador tome una decisión. |
| RN-18 | Una empresa debe registrar nombre, descripción, correo de contacto y ubicación geográfica antes de publicar una vacante. |
| RN-19 | Cada empresa debe contar con coordenadas de latitud y longitud válidas para aparecer en el mapa. |
| RN-20 | Una vacante debe indicar título, empresa, tecnologías requeridas, modalidad, ubicación, fecha límite y estado. |
| RN-21 | Una vacante activa debe tener fecha límite igual o posterior a la fecha actual. |
| RN-22 | Una vacante vencida cambia automáticamente a estado "cerrada" y deja de aceptar postulaciones. |
| RN-23 | Las modalidades permitidas son presencial, remota e híbrida. |
| RN-24 | Un desarrollador no puede postular dos veces a la misma vacante mientras su postulación esté activa. |
| RN-25 | La postulación debe incluir perfil profesional completo o CV adjunto en formato PDF. |
| RN-26 | Solo el reclutador autorizado de una empresa puede consultar las postulaciones de sus vacantes. |
| RN-27 | Los estados de postulación serán: enviada, en revisión, preseleccionada, entrevistada, rechazada, aceptada y retirada. |
| RN-28 | Está prohibido publicar vacantes falsas, discriminatorias, engañosas o que soliciten pagos al postulante. |
| RN-29 | El mapa mostrará únicamente empresas que tengan al menos una vacante activa. |
| RN-30 | Cada marcador debe mostrar empresa, cantidad de vacantes activas, tecnologías requeridas y modalidad laboral. |
| RN-31 | La cantidad de vacantes del marcador se actualiza cuando una oferta se publica, vence, se cierra o se elimina. |
| RN-32 | El mapa permitirá filtrar por ciudad, distrito, tecnología, modalidad, experiencia y tipo de contrato. |
| RN-33 | La ubicación exacta de un desarrollador no será pública sin su consentimiento explícito. |
| RN-34 | Solo se mostrarán direcciones corporativas, áreas aproximadas o ubicaciones autorizadas de empresas. |
| RN-35 | El sistema agrupará marcadores cercanos cuando existan muchas empresas en una zona. |
| RN-36 | Las publicaciones sostenibles deben usar una o más etiquetas: Green IT, reciclaje, reutilización, reparación, donación, eficiencia energética u ODS 12. |
| RN-37 | Un anuncio de donación, reparación, intercambio o reutilización debe incluir categoría, estado del equipo, ubicación aproximada y medio de contacto interno. |
| RN-38 | Está prohibido publicar baterías dañadas o residuos electrónicos peligrosos sin advertencia de seguridad. |
| RN-39 | Los puntos de reciclaje electrónico deben ser registrados o validados por un administrador antes de mostrarse públicamente. |
| RN-40 | Las prácticas sostenibles declaradas por una empresa permanecerán como "pendientes de verificación" hasta contar con evidencia validada. |
| RN-41 | Los indicadores ambientales solo contabilizarán equipos donados, reutilizados, reparados o reciclados cuando la operación esté confirmada por ambas partes o por un administrador. |
| RN-42 | El sistema debe promover reparación, reutilización o donación de equipos funcionales antes de recomendar su descarte. |

---

## 5. Plan de 16 sprints

### Resumen ejecutivo

| Sprint | Semana | Objetivo | HU | Puntos | Épicas |
|--------|--------|----------|-----|--------|--------|
| 1 | 1 | Arranque del proyecto y autenticación | HU-01…05 | 16 | A |
| 2 | 2 | Usuarios, perfiles y subida de archivos | HU-06…09 | 19 | B |
| 3 | 3 | Publicaciones y feed con visibilidad | HU-12…15 | 19 | C |
| 4 | 4 | Comentarios anidados y reacciones | HU-16…18 | 15 | D |
| 5 | 5 | Stories efímeras y reels | HU-19…21 | 13 | E |
| 6 | 6 | Preguntas técnicas (crear y responder) | HU-22…24 | 16 | F |
| 7 | 7 | Respuesta aceptada, votos y reputación | HU-25…27 | 13 | G |
| 8 | 8 | Conexiones y privacidad social | HU-10, 28…31 | 21 | B, H |
| 9 | 9 | Bloqueos, notificaciones y tiempo real | HU-32…34 | 16 | H, I |
| 10 | 10 | Búsqueda general y búsqueda de talento | HU-11, 35…37 | 18 | B, J |
| 11 | 11 | Empresas: registro y validación | HU-38…39 | 13 | K |
| 12 | 12 | Vacantes: CRUD, ciclo de vida y filtros | HU-40…42 | 21 | L |
| 13 | 13 | Postulaciones y gestión de selección | HU-43…45 | 19 | M |
| 14 | 14 | Mapa laboral geolocalizado | HU-46…48 | 26 | N |
| 15 | 15 | EcoDev y ODS 12 | HU-49…52 | 26 | O |
| 16 | 16 | Moderación, administración y cierre (hardening) | HU-53…57, 61 | 36 | P, Q |
| — | + | Backlog continuo (post-semana 16) | HU-58…60 | 29 | Q |

---

### Detalle por sprint

#### Sprint 1 — Arranque y autenticación
- **Objetivo:** Proyecto base operativo (Docker, CI, variables de entorno) y flujo completo de registro, login y sesión JWT.
- **HU:** HU-01 (registro), HU-02 (login), HU-03 (refresh token), HU-04 (logout), HU-05 (rol activo / multi-rol).
- **Entregables:** `Dockerfile`, workflow de CI en GitHub Actions, módulo `auth` con JWT access+refresh, endpoints de registro/login, esquema GraphQL `auth.graphqls`.
- **RN/RNF clave:** RN-01, RN-02, RN-03, RN-06 | RNF-01…04, RNF-07, RNF-16…20.

#### Sprint 2 — Usuarios, perfiles y upload
- **Objetivo:** Gestión completa de cuentas y perfiles profesionales con subida de archivos a Cloudinary.
- **HU:** HU-06 (perfil básico), HU-07 (perfil desarrollador), HU-08 (perfil reclutador), HU-09 (upload Cloudinary).
- **Entregables:** módulos `users`, `developer_profile`, `recruiter_profile`, `shared/adapter/rest/UploadController`.
- **RN/RNF clave:** RN-04, RN-07 | RNF-09, RNF-11.

#### Sprint 3 — Publicaciones y feed
- **Objetivo:** Contenido social base con niveles de visibilidad y feed paginado.
- **HU:** HU-12 (crear post), HU-13 (editar/eliminar), HU-14 (feed paginado con visibilidad), HU-15 (guardados).
- **Entregables:** módulo `post` con `createPost`, `feed` (seguidos primero), `saved_contents`; índices `autorId+createdAt`.
- **RN/RNF clave:** RN-08, RN-11, RN-33 | RNF-05, RNF-12, RNF-13.

#### Sprint 4 — Comentarios y reacciones
- **Objetivo:** Interacción sobre publicaciones con comentarios anidados y reacciones controladas.
- **HU:** HU-16 (comentarios), HU-17 (respuestas anidadas), HU-18 (reacciones: crear, cambiar, eliminar).
- **Entregables:** módulos `comment` y `reaction`; índice único `usuarioId+contenidoId+tipoContenido`.
- **RN/RNF clave:** RN-15 | RNF-08, RNF-12.

#### Sprint 5 — Stories y reels
- **Objetivo:** Contenido efímero (24 h) y videos cortos con música.
- **HU:** HU-19 (crear story), HU-20 (vistas de story), HU-21 (reels con música).
- **Entregables:** módulos `story` (con `StoryView`, expiración 24 h) y `reel`; integración de `MusicService`/`LyricsService` existentes.
- **RN/RNF clave:** RNF-13.

#### Sprint 6 — Preguntas técnicas
- **Objetivo:** Módulo de asistencia técnica con preguntas etiquetadas y categorizadas.
- **HU:** HU-22 (crear pregunta), HU-23 (responder), HU-24 (listar/filtrar).
- **Entregables:** módulos `technical_questions`, `technical_answers`, `categories`; validación de etiquetas y categoría.
- **RN/RNF clave:** RN-09, RN-10.

#### Sprint 7 — Respuesta aceptada, votos y reputación
- **Objetivo:** Cierre del ciclo de calidad de respuestas: solución aceptada y votación.
- **HU:** HU-25 (respuesta aceptada única), HU-26 (votos), HU-27 (reputación básica).
- **Entregables:** mutaciones de aceptación y votación; contadores de reputación.
- **RN/RNF clave:** RN-12, RN-13, RN-14.

#### Sprint 8 — Conexiones y privacidad social
- **Objetivo:** Red profesional con follows, solicitudes de conexión y configuración de privacidad de perfil.
- **HU:** HU-10 (privacidad configurable), HU-28 (follows), HU-29 (enviar solicitud), HU-30 (aceptar/rechazar), HU-31 (listar/eliminar conexiones).
- **Entregables:** módulos `follow`, `connection_request`, `connection`; reglas de visibilidad del `04-VISIBILIDAD.md` implementadas en backend.
- **RN/RNF clave:** RN-33 | RNF-05.

#### Sprint 9 — Bloqueos, notificaciones y tiempo real
- **Objetivo:** Seguridad social (bloqueos) y notificaciones con entregas en tiempo real.
- **HU:** HU-32 (bloquear/desbloquear), HU-33 (notificaciones CRUD + contador), HU-34 (subscriptions WebSocket).
- **Entregables:** módulo `block`, módulo `notification`, suscripciones `notificationReceived` y `connectionStatusChanged`.
- **RN/RNF clave:** RNF-34, RNF-41…43.

#### Sprint 10 — Búsqueda y talento
- **Objetivo:** Encontrar contenido y candidatos: búsqueda general + filtros de desarrolladores + guardados.
- **HU:** HU-11 (eliminar cuenta), HU-35 (búsqueda general), HU-36 (talent search), HU-37 (búsquedas y perfiles guardados).
- **Entregables:** módulo `talent_search` (REST + GraphQL), colecciones `saved_searches`, `saved_profiles`.
- **RN/RNF clave:** RN-04 | RNF-13.

#### Sprint 11 — Empresas
- **Objetivo:** Empresas registradas con coordenadas y validadas por administración.
- **HU:** HU-38 (registrar empresa), HU-39 (validación y vínculo reclutador-empresa).
- **Entregables:** módulo `companies` (schema ya definido), flujo de validación admin.
- **RN/RNF clave:** RN-07, RN-18, RN-19.

#### Sprint 12 — Vacantes
- **Objetivo:** Bolsa laboral funcional: vacantes activas con ciclo de vida y filtros.
- **HU:** HU-40 (crear/editar), HU-41 (activar/cerrar/vencimiento automático), HU-42 (listar/filtrar).
- **Entregables:** módulo `vacancies`, proceso de vencimiento, filtros por tecnología/modalidad/experiencia/ubicación.
- **RN/RNF clave:** RN-20…23, RN-28 | RNF-11.

#### Sprint 13 — Postulaciones
- **Objetivo:** Procesos de selección completos con estados e historial.
- **HU:** HU-43 (postular con perfil/CV PDF), HU-44 (gestión de estados por reclutador), HU-45 (historial para el postulante).
- **Entregables:** módulo `applications` con `application_status_history`; notificación al reclutador.
- **RN/RNF clave:** RN-04, RN-24…27 | RNF-05.

#### Sprint 14 — Mapa laboral
- **Objetivo:** Visualización geográfica de empresas con vacantes activas, con filtros y detalle.
- **HU:** HU-46 (mapa con agrupación), HU-47 (filtros del mapa), HU-48 (detalle desde marcador).
- **Entregables:** endpoints de agregación para el mapa (empresas + vacantes activas + coordenadas), contratos de datos para Leaflet en el frontend.
- **RN/RNF clave:** RN-29…35 | RNF-14.
- **Nota:** Es la HU más pesada del backlog (13 pts). Si hay sobrecarga, se recorta el alcance del MVP a "marcadores + filtro por tecnología" y la agrupación de marcadores queda como mejora.

#### Sprint 15 — EcoDev y ODS 12
- **Objetivo:** Módulo de sostenibilidad: contenido Green IT, anuncios de equipos y mapa de puntos de reciclaje.
- **HU:** HU-49 (publicaciones sostenibles), HU-50 (anuncios de equipos), HU-51 (puntos de reciclaje validados), HU-52 (indicadores de impacto).
- **Entregables:** módulos `ecotech` (schema definido), validación admin de puntos de reciclaje, contadores de impacto.
- **RN/RNF clave:** RN-36…42.

#### Sprint 16 — Moderación, administración y cierre
- **Objetivo:** Herramientas de moderación/administración operativas y endurecimiento final del proyecto (pruebas, RNF, despliegue).
- **HU:** HU-53 (reportes), HU-54 (acciones de moderación), HU-55 (baneos), HU-56 (gestión usuarios/roles/categorías), HU-57 (tickets de soporte), HU-61 (hardening, E2E y despliegue).
- **Entregables:** módulos `moderation`, `support`; auditoría de acciones; suite de pruebas E2E; checklist de RNF; despliegue de producción en Railway.
- **RN/RNF clave:** RN-05, RN-06, RN-16, RN-17, RN-28 | RNF-16…20, RNF-37, RNF-38.

#### Backlog continuo (fuera de las 16 semanas)
- **HU-58** gamificación (puntos, niveles, insignias), **HU-59** planes/suscripciones (Won't para el curso), **HU-60** panel de métricas del administrador.
- Se estima iniciar en iteraciones posteriores al cierre del ciclo académico.

---

## 6. Riesgos y mitigaciones

| Riesgo | Impacto | Mitigación |
|--------|---------|------------|
| Sprints 12–15 con carga alta (vacantes, mapa, EcoDev) | Alta | Recorte de alcance MVP por sprint; HU de baja prioridad se mueven al backlog continuo. |
| Frontend (Angular) no alcanza a integrarse durante el curso | Medio | El backend expone GraphQL/REST documentado; las demos pueden usar GraphiQL/Swagger. |
| Dependencia de servicios externos (Cloudinary, Atlas, iTunes/LRCLIB para música) | Medio | Mocks en pruebas; variables de entorno de respaldo. |
| Complejidad de reglas de visibilidad/privacidad | Alta | Centralizar la lógica de visibilidad en servicios de dominio; pruebas dedicadas en Sprint 8. |

---

## 7. Métricas de seguimiento

- **Velocidad:** puntos aceptados por sprint (objetivo 15–22).
- **Cumplimiento de DoD:** % de HU aceptadas a la primera en Review.
- **Burn-down:** gráfico semanal de puntos restantes del sprint.
- **Cobertura de pruebas:** evolución semanal de `./mvnw test`.
