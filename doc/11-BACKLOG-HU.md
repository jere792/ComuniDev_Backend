# COMUNIDEV — Product Backlog (Historias de Usuario)

> Formato ágil Scrum. Estimación en **puntos de historia** (escala de Fibonacci: 1, 2, 3, 5, 8, 13) y prioridad **MoSCoW** (Must / Should / Could / Won't).
> Dependencias: otras HU que deben estar terminadas o avanzadas antes de iniciar esta.
> RN: reglas de negocio asociadas (ver `doc\14-PLAN-SPRINTS.md`).

---

## Épica A — Autenticación y acceso

| ID | Historia de usuario | Prioridad | Puntos | Dependencias | RN |
|----|---------------------|-----------|--------|--------------|----|
| HU-01 | Como visitante, quiero registrarme con correo electrónico y contraseña para crear una cuenta en COMUNIDEV. | Must | 5 | — | RN-01, RN-02 |
| HU-02 | Como usuario, quiero iniciar sesión con mi correo y contraseña para obtener un token de acceso (JWT) y usar la plataforma. | Must | 3 | HU-01 | RN-02 |
| HU-03 | Como usuario, quiero que el sistema emita un refresh token para renovar mi sesión sin volver a ingresar credenciales. | Must | 3 | HU-02 | — |
| HU-04 | Como usuario, quiero cerrar sesión para invalidar mi token y proteger mi cuenta en dispositivos compartidos. | Must | 2 | HU-02 | — |
| HU-05 | Como usuario con varios roles, quiero seleccionar mi rol activo (desarrollador, reclutador, moderador o administrador) para que la sesión refleje mis permisos actuales. | Must | 3 | HU-02 | RN-03, RN-06 |

---

## Épica B — Usuarios y perfiles

| ID | Historia de usuario | Prioridad | Puntos | Dependencias | RN |
|----|---------------------|-----------|--------|--------------|----|
| HU-06 | Como usuario, quiero ver y editar mi perfil básico (nombre, foto, biografía y ubicación) para mantener mi información actualizada. | Must | 3 | HU-02 | — |
| HU-07 | Como desarrollador, quiero completar mi perfil profesional con CV, tecnologías, experiencia, educación, proyectos, certificados y disponibilidad laboral para mostrar mi perfil profesional. | Must | 8 | HU-06 | RN-04 |
| HU-08 | Como reclutador, quiero crear mi perfil de reclutador con empresa, cargo y redes sociales para representar a mi organización. | Must | 5 | HU-06 | RN-07 |
| HU-09 | Como usuario, quiero subir mi foto de perfil y recursos (CV, certificados, imágenes) a Cloudinary desde el backend para enlazarlos en mis publicaciones y perfil. | Must | 3 | HU-06 | — |
| HU-10 | Como usuario, quiero configurar la privacidad de mi perfil (perfil, CV, certificados, experiencia, ubicación y quién puede enviarme mensajes) para controlar quién ve cada sección. | Should | 5 | HU-07 | RN-33 |
| HU-11 | Como usuario, quiero eliminar mi cuenta para que mis datos personales dejen de estar disponibles en la plataforma. | Could | 3 | HU-02 | — |

---

## Épica C — Publicaciones y feed

| ID | Historia de usuario | Prioridad | Puntos | Dependencias | RN |
|----|---------------------|-----------|--------|--------------|----|
| HU-12 | Como desarrollador, quiero publicar contenido con texto, imágenes o fragmentos de código para compartir conocimientos, proyectos o consultas técnicas. | Must | 8 | HU-02 | RN-08 |
| HU-13 | Como autor, quiero editar o eliminar mi publicación mientras no haya sido ocultada por moderación para corregir errores o retirar contenido. | Must | 3 | HU-12 | RN-11 |
| HU-14 | Como usuario, quiero ver un feed paginado de publicaciones respetando la visibilidad (público, seguidores, conexiones, solo yo) para informarme de la comunidad. | Must | 5 | HU-12, HU-28 | RN-33 |
| HU-15 | Como usuario, quiero guardar publicaciones en mis favoritos para consultarlas más tarde. | Should | 3 | HU-12 | — |

---

## Épica D — Comentarios y reacciones

| ID | Historia de usuario | Prioridad | Puntos | Dependencias | RN |
|----|---------------------|-----------|--------|--------------|----|
| HU-16 | Como usuario, quiero comentar publicaciones para participar en conversaciones técnicas. | Must | 5 | HU-12 | — |
| HU-17 | Como usuario, quiero responder comentarios (comentarios anidados de nivel superior y respuestas) para conversar de forma estructurada. | Must | 5 | HU-16 | — |
| HU-18 | Como usuario, quiero reaccionar a publicaciones y cambiar o eliminar mi reacción para expresar mi opinión sin duplicar reacciones. | Must | 5 | HU-12 | RN-15 |

---

## Épica E — Stories y reels

| ID | Historia de usuario | Prioridad | Puntos | Dependencias | RN |
|----|---------------------|-----------|--------|--------------|----|
| HU-19 | Como usuario, quiero crear stories de texto, imagen o video con música que expiren automáticamente a las 24 horas para compartir momentos breves. | Should | 5 | HU-09 | — |
| HU-20 | Como usuario, quiero consultar las stories activas de los usuarios que sigo y registrar quién las vio para conocer su audiencia. | Should | 3 | HU-19 | — |
| HU-21 | Como usuario, quiero crear y consultar reels (videos cortos) con música asociada para compartir contenido audiovisual técnico o comunitario. | Should | 5 | HU-09 | — |

---

## Épica F — Asistencia técnica (preguntas y respuestas)

| ID | Historia de usuario | Prioridad | Puntos | Dependencias | RN |
|----|---------------------|-----------|--------|--------------|----|
| HU-22 | Como desarrollador, quiero crear una pregunta técnica con título, descripción, código de ejemplo, etiquetas y categoría para solicitar ayuda a la comunidad. | Must | 8 | HU-02 | RN-09, RN-10 |
| HU-23 | Como desarrollador, quiero responder preguntas técnicas para ayudar a otros miembros de la comunidad. | Must | 5 | HU-22 | — |
| HU-24 | Como usuario, quiero listar y filtrar preguntas por categoría, etiqueta y estado (abierta / resuelta) para encontrar información relevante. | Must | 3 | HU-22 | RN-10 |

---

## Épica G — Votos, respuesta aceptada y reputación

| ID | Historia de usuario | Prioridad | Puntos | Dependencias | RN |
|----|---------------------|-----------|--------|--------------|----|
| HU-25 | Como autor de una pregunta, quiero marcar una respuesta como aceptada (solo una) para indicar qué solución resolvió mi problema. | Must | 3 | HU-23 | RN-12, RN-13 |
| HU-26 | Como usuario, quiero votar respuestas útiles (un voto por usuario y respuesta) para destacar soluciones confiables. | Should | 5 | HU-23 | RN-14 |
| HU-27 | Como desarrollador, quiero obtener puntos de reputación cuando aceptan mis respuestas o votan mis aportes para visibilizar mi contribución. | Could | 5 | HU-25, HU-26 | — |

---

## Épica H — Conexiones, seguimientos y privacidad social

| ID | Historia de usuario | Prioridad | Puntos | Dependencias | RN |
|----|---------------------|-----------|--------|--------------|----|
| HU-28 | Como usuario, quiero seguir o dejar de seguir a otros usuarios para ver su contenido en mi feed. | Must | 3 | HU-02 | — |
| HU-29 | Como usuario, quiero enviar solicitudes de conexión profesionales a otros usuarios para establecer relaciones de confianza. | Must | 5 | HU-28 | — |
| HU-30 | Como receptor de una solicitud, quiero aceptar o rechazar solicitudes de conexión para administrar mi red profesional. | Must | 5 | HU-29 | — |
| HU-31 | Como usuario, quiero listar mis conexiones y followers, y eliminar una conexión cuando lo desee. | Must | 3 | HU-30 | — |
| HU-32 | Como usuario, quiero bloquear y desbloquear usuarios para impedir que interactúen conmigo (conexiones, comentarios, mensajes). | Must | 3 | HU-28 | — |

---

## Épica I — Notificaciones y tiempo real

| ID | Historia de usuario | Prioridad | Puntos | Dependencias | RN |
|----|---------------------|-----------|--------|--------------|----|
| HU-33 | Como usuario, quiero recibir notificaciones por solicitudes de conexión, conexiones aceptadas, comentarios y reacciones, con contador de no leídas y opción de marcar como leídas o eliminarlas. | Must | 8 | HU-16, HU-18, HU-30 | — |
| HU-34 | Como usuario, quiero recibir notificaciones y cambios de estado de conexión en tiempo real mediante WebSocket (subscriptions GraphQL) sin recargar la página. | Should | 5 | HU-33 | — |

---

## Épica J — Búsqueda y búsqueda de talento

| ID | Historia de usuario | Prioridad | Puntos | Dependencias | RN |
|----|---------------------|-----------|--------|--------------|----|
| HU-35 | Como usuario, quiero buscar usuarios, publicaciones y preguntas por palabras clave y etiquetas para encontrar información relevante. | Should | 5 | HU-12, HU-22 | — |
| HU-36 | Como reclutador, quiero buscar desarrolladores con filtros (tecnología, disponibilidad, ubicación, pretensiones salariales) para encontrar candidatos. | Should | 8 | HU-07 | RN-04 |
| HU-37 | Como reclutador, quiero guardar búsquedas y perfiles de candidatos favoritos para retomarlos rápidamente. | Could | 5 | HU-36 | — |

---

## Épica K — Empresas

| ID | Historia de usuario | Prioridad | Puntos | Dependencias | RN |
|----|---------------------|-----------|--------|--------------|----|
| HU-38 | Como reclutador, quiero registrar el perfil de mi empresa con nombre, descripción, correo de contacto, ubicación y coordenadas (latitud/longitud) para publicar oportunidades y aparecer en el mapa. | Must | 8 | HU-08 | RN-18, RN-19 |
| HU-39 | Como administrador, quiero validar el registro de empresas y vincular cuentas de reclutadores para autorizar la publicación de vacantes. | Must | 5 | HU-38 | RN-07 |

---

## Épica L — Vacantes

| ID | Historia de usuario | Prioridad | Puntos | Dependencias | RN |
|----|---------------------|-----------|--------|--------------|----|
| HU-40 | Como reclutador, quiero crear y editar vacantes con título, tecnologías requeridas, modalidad, ubicación, número de vacantes, experiencia y fecha límite para encontrar desarrolladores. | Must | 8 | HU-38 | RN-20 |
| HU-41 | Como sistema, quiero cambiar automáticamente a "cerrada" una vacante cuya fecha límite venció, y permitir al reclutador activar o cerrar vacantes manualmente, para que solo se ofrezcan plazas vigentes. | Must | 5 | HU-40 | RN-21, RN-22 |
| HU-42 | Como desarrollador, quiero listar y filtrar vacantes por tecnología, modalidad (presencial, remota, híbrida), experiencia, tipo de contrato y ubicación para encontrar ofertas afines. | Must | 8 | HU-40 | RN-23 |

---

## Épica M — Postulaciones

| ID | Historia de usuario | Prioridad | Puntos | Dependencias | RN |
|----|---------------------|-----------|--------|--------------|----|
| HU-43 | Como desarrollador, quiero postular a una vacante activa usando mi perfil profesional o CV en PDF, sin poder postular dos veces de forma simultánea. | Must | 8 | HU-42, HU-07 | RN-04, RN-24, RN-25 |
| HU-44 | Como reclutador, quiero revisar las postulaciones de mis vacantes y cambiar su estado (enviada, en revisión, preseleccionada, entrevistada, rechazada, aceptada, retirada) para administrar el proceso de selección. | Must | 8 | HU-43 | RN-26, RN-27 |
| HU-45 | Como desarrollador, quiero consultar el historial de estados de mi postulación para conocer en qué etapa está mi proceso. | Should | 3 | HU-43 | RN-27 |

---

## Épica N — Mapa laboral

| ID | Historia de usuario | Prioridad | Puntos | Dependencias | RN |
|----|---------------------|-----------|--------|--------------|----|
| HU-46 | Como desarrollador, quiero visualizar en un mapa las empresas que tienen vacantes activas (agrupando marcadores cercanos) para conocer dónde existe mayor demanda laboral. | Must | 13 | HU-41 | RN-29, RN-30, RN-35 |
| HU-47 | Como usuario, quiero filtrar el mapa por ciudad, tecnología, modalidad y experiencia para acotar la búsqueda geográfica. | Should | 8 | HU-46 | RN-32 |
| HU-48 | Como usuario, quiero ver al seleccionar un marcador el detalle de la empresa (vacantes activas, tecnologías, modalidad) con enlace a sus vacantes. | Must | 5 | HU-46 | RN-30, RN-31 |

---

## Épica O — EcoDev y ODS 12

| ID | Historia de usuario | Prioridad | Puntos | Dependencias | RN |
|----|---------------------|-----------|--------|--------------|----|
| HU-49 | Como usuario, quiero publicar y consultar contenido sobre Green IT, reciclaje, reutilización, reparación y donación con etiquetas de sostenibilidad (ODS 12) para sensibilizar a la comunidad. | Must | 5 | HU-12 | RN-36 |
| HU-50 | Como usuario, quiero publicar equipos tecnológicos para donación, reparación, intercambio o reutilización indicando categoría, estado del equipo y ubicación aproximada. | Must | 8 | HU-49 | RN-37, RN-38, RN-42 |
| HU-51 | Como usuario, quiero ubicar en un mapa los puntos de reciclaje electrónico registrados y validados por un administrador, con tipos de residuos aceptados. | Must | 8 | HU-39 | RN-39 |
| HU-52 | Como usuario, quiero que el sistema contabilice los indicadores de impacto (equipos donados, reutilizados, reparados o reciclados) cuando la operación es confirmada por ambas partes o por un administrador, para ver el dashboard de impacto. | Could | 5 | HU-50 | RN-40, RN-41 |

---

## Épica P — Moderación, reportes y administración

| ID | Historia de usuario | Prioridad | Puntos | Dependencias | RN |
|----|---------------------|-----------|--------|--------------|----|
| HU-53 | Como usuario autenticado, quiero reportar publicaciones, preguntas, respuestas, vacantes o perfiles inapropiados indicando una razón válida para mantener una comunidad segura. | Must | 5 | HU-12 | RN-16, RN-17 |
| HU-54 | Como moderador, quiero revisar los reportes en estado "en revisión" y aplicar acciones (advertencia, ocultar contenido, suspender) para moderar la plataforma. | Must | 8 | HU-53 | RN-17 |
| HU-55 | Como administrador, quiero gestionar baneos y suspensiones de usuarios, de modo que un usuario suspendido no pueda publicar, comentar, responder, postular ni enviar mensajes. | Must | 8 | HU-54 | RN-05 |
| HU-56 | Como administrador, quiero gestionar usuarios, roles (solo ADMIN puede asignar moderador/administrador), categorías y etiquetas del sistema. | Should | 8 | HU-05 | RN-06, RN-10 |
| HU-57 | Como administrador, quiero gestionar tickets de soporte para atender incidencias de los usuarios. | Could | 5 | — | — |

---

## Épica Q — Gamificación, planes y analítica (backlog continuo)

> Historias de menor prioridad para el alcance del curso. Se estima iniciarlas al cierre de la semana 16 o en una iteración posterior.

| ID | Historia de usuario | Prioridad | Puntos | Dependencias | RN |
|----|---------------------|-----------|--------|--------------|----|
| HU-58 | Como desarrollador, quiero obtener puntos, niveles e insignias por responder preguntas, aportar soluciones útiles y participar en EcoDev para gamificar mi experiencia. | Could | 8 | HU-27, HU-52 | — |
| HU-59 | Como usuario, quiero consultar y suscribirme a planes de membresía (gratuito / premium) para acceder a funcionalidades adicionales. | Won't* | 8 | — | — |
| HU-60 | Como administrador, quiero visualizar un panel de métricas (publicaciones, vacantes, tecnologías demandadas, zonas con más empleos e impacto EcoDev) para tomar decisiones. | Could | 13 | HU-46, HU-52 | — |
| HU-61 | Como equipo, quiero completar pruebas E2E, revisión de todos los RNF, monitoreo y despliegue en Railway (con Docker) para cerrar el ciclo del proyecto. | Must | 5 | — | todos RNF |

---

## Resumen por épica

| Épica | HU | Puntos totales |
|-------|-----|----------------|
| A — Autenticación | HU-01…05 | 16 |
| B — Usuarios y perfiles | HU-06…11 | 27 |
| C — Publicaciones y feed | HU-12…15 | 19 |
| D — Comentarios y reacciones | HU-16…18 | 15 |
| E — Stories y reels | HU-19…21 | 13 |
| F — Asistencia técnica | HU-22…24 | 16 |
| G — Votos y reputación | HU-25…27 | 13 |
| H — Conexiones y privacidad social | HU-28…32 | 19 |
| I — Notificaciones y tiempo real | HU-33…34 | 13 |
| J — Búsqueda y talento | HU-35…37 | 18 |
| K — Empresas | HU-38…39 | 13 |
| L — Vacantes | HU-40…42 | 21 |
| M — Postulaciones | HU-43…45 | 19 |
| N — Mapa laboral | HU-46…48 | 26 |
| O — EcoDev y ODS 12 | HU-49…52 | 26 |
| P — Moderación y administración | HU-53…57 | 34 |
| Q — Gamificación y analítica (continuo) | HU-58…61 | 34 |
| **Total** | **61 HU** | **~342 puntos** |

\* Prioridad *Won't* para el alcance del curso: se documenta pero no se compromete entrega dentro de las 16 semanas.
