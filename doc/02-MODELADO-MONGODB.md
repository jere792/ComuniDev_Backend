# COMUNIDEV — Modelado MongoDB

## Decisiones clave de modelado

MongoDB se utilizará como base documental. Cada entidad se representa como una colección o como un subdocumento embebido, según el volumen, la frecuencia de actualización y la necesidad de consulta independiente.

### Reglas de modelado

- Usar referencias por ID entre agregados grandes o con alto crecimiento: usuarios, posts, comentarios, mensajes, vacantes, postulaciones y reacciones.
- Usar subdocumentos embebidos para información que siempre se consulta junto con el documento principal y tiene tamaño controlado: configuración, estado de actividad, links sociales, tecnologías del perfil, experiencia, educación y preferencias.
- Evitar embebir listas sin límite dentro de users, posts o conversations; por ejemplo, reacciones, comentarios, seguidores y mensajes deben vivir en sus propias colecciones.
- Usar `ObjectId` para identificadores de MongoDB y conservar fechas en formato ISO (ISODate).
- Todos los documentos principales deben incluir `createdAt` y `updatedAt`; conviene añadir `createdBy` cuando aplique.
- Las URLs de imágenes, videos, CV, certificados y archivos se almacenan en MongoDB, pero el archivo físico se aloja en Cloudinary.
- Los campos geográficos se deben guardar en formato GeoJSON: `{ "type": "Point", "coordinates": [longitud, latitud] }` y deben tener índice 2dsphere.

## Mapa de dominios y colecciones

| Dominio | Colecciones principales |
|---------|------------------------|
| Identidad y perfiles | users, developer_profiles, recruiter_profiles, companies |
| Conexiones y privacidad | connection_requests, connections, follows, blocked_users |
| Contenido social | posts, stories, reels, comments, reactions, saved_contents, hashtags |
| Asistencia técnica | technical_questions, technical_answers, question_votes |
| Mensajería y presencia | conversations, messages, activity_status_history, notifications |
| Empleabilidad | vacancies, applications, application_status_history |
| ODS 12 / EcoDev | ecotech_posts, recycling_points, ecotech_transactions, company_sustainability_practices |
| Gamificación y planes | user_points, point_transactions, badges, user_badges, plans, subscriptions |
| Seguridad y operación | reports, moderation_actions, support_tickets, audit_logs |
