# COMUNIDEV — Visión General

## 1. Visión general

COMUNIDEV es una plataforma web y móvil orientada a desarrolladores, estudiantes de tecnología, reclutadores y empresas. Combina una red social profesional, una comunidad de resolución de problemas técnicos, mensajería, bolsa de empleo geolocalizada y un módulo de sostenibilidad tecnológica vinculado al ODS 12: Producción y consumo responsables.

La plataforma permite a los desarrolladores construir un perfil profesional, compartir publicaciones, crear historias y reels, formular preguntas técnicas, aportar soluciones, establecer conexiones profesionales, conversar por mensajería privada y postular a vacantes. Los reclutadores pueden registrar empresas, publicar vacantes y administrar postulaciones. El sistema también muestra en un mapa las empresas que tienen vacantes activas y los puntos de reciclaje electrónico.

El sistema será desarrollado con:

- **Frontend web:** Angular.
- **Aplicación móvil:** Kotlin nativo para Android, usando Jetpack Compose y arquitectura MVVM.
- **Backend:** Java con Spring Boot.
- **Arquitectura backend:** arquitectura hexagonal (ports and adapters) organizada mediante vertical slicing.
- **Base de datos:** MongoDB Atlas.
- **Despliegue backend:** Railway.
- **APIs:** REST documentada con Swagger/OpenAPI, GraphQL para consultas complejas y WebSockets para eventos en tiempo real.
- **Archivos:** Cloudinary para imágenes, videos, CV y certificados.
- **Mapas:** Leaflet + OpenStreetMap o Google Maps.
- **Seguridad:** Spring Security, JWT de acceso y refresh token, roles y permisos.

## 2. Objetivos funcionales

COMUNIDEV debe permitir:

- Crear y administrar cuentas con distintos roles.
- Construir perfiles profesionales para desarrolladores, incluyendo CV, certificados, proyectos, tecnologías y disponibilidad laboral.
- Publicar contenido técnico, imágenes, videos, fragmentos de código y contenido relacionado con el ODS 12.
- Crear historias efímeras con imagen, video, texto y música.
- Publicar reels o videos cortos educativos, técnicos o comunitarios.
- Reaccionar, comentar, responder comentarios, guardar y reportar contenido.
- Crear preguntas técnicas y recibir respuestas de la comunidad.
- Marcar una respuesta como solución aceptada y asignar reputación por aportes útiles.
- Enviar solicitudes de conexión profesionales que deben ser aceptadas o rechazadas por el receptor.
- Aplicar privacidad configurable para definir qué partes del perfil puede ver cada tipo de usuario.
- Enviar mensajes privados, recibir notificaciones y conocer estados de actividad.
- Registrar empresas, publicar vacantes, postular y gestionar procesos de selección.
- Mostrar empresas con vacantes activas en un mapa geográfico y permitir filtros por tecnología, modalidad, experiencia y ubicación.
- Incentivar Green IT, donación, reparación, reutilización y reciclaje de equipos tecnológicos mediante el módulo EcoDev.
- Aplicar gamificación mediante puntos, niveles, insignias y planes de membresía.
- Brindar soporte, moderación de contenido y administración del sistema.

## 3. Roles del sistema

| Rol | Código | Descripción y permisos principales |
|-----|--------|-----------------------------------|
| Desarrollador / Usuario | DEVELOPER | Crea perfil profesional, agrega CV y certificados, publica contenido, crea preguntas, responde, solicita conexiones, conversa, sigue contenido, postula a vacantes y participa en EcoDev. |
| Reclutador | RECRUITER | Representa a una empresa, publica y administra vacantes, visualiza postulaciones de sus vacantes y contacta candidatos según permisos. No puede usar un perfil de CV como desarrollador. |
| Moderador / Soporte | MODERATOR | Gestiona tickets de ayuda, atiende incidencias, revisa denuncias, modera contenido, oculta publicaciones y aplica sanciones dentro de los permisos asignados. |
| Administrador | ADMIN | Gestiona usuarios, roles, empresas, categorías, planes, puntos de reciclaje, reportes, métricas y configuración global. |

Un usuario puede almacenar más de un rol en el sistema si se requiere, pero debe existir un `rolActivo` para definir la experiencia y permisos de la sesión actual. Por ejemplo, un usuario podría tener DEVELOPER y MODERATOR, pero navegar con uno como rol activo.

## Contexto final

COMUNIDEV no es solo una red social genérica. Es una red profesional y técnica para desarrolladores, con asistencia comunitaria, perfiles de empleabilidad, conexiones aceptadas, mensajería, vacantes geolocalizadas y sostenibilidad tecnológica. La lógica crítica del sistema es la privacidad: seguir a alguien no equivale a tener conexión profesional. Las conexiones requieren solicitud y aceptación. El dueño del perfil define de manera granular quién puede ver perfil, CV, certificados, experiencia, ubicación y quién puede enviarle mensajes. Todo el backend debe respetar esas reglas, incluso si el frontend intenta acceder directamente a recursos restringidos.
