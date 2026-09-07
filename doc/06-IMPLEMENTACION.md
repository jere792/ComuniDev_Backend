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
