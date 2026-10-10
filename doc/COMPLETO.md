# COMUNIDEV - Documentación Completa

> Generado el 2026-09-26. Concatenación de los 12 documentos del proyecto.
> Los originales no se modifican; este archivo es solo para consulta/copiado.

## Índice

- **COMUNIDEV — Visión General** &mdash; `doc\01-VISION-GENERAL.md`
- **COMUNIDEV — Modelado MongoDB** &mdash; `doc\02-MODELADO-MONGODB.md`
- **COMUNIDEV — Entidades de Negocio** &mdash; `doc\03-ENTIDADES.md`
- **COMUNIDEV — Reglas de Visibilidad y Relaciones** &mdash; `doc\04-VISIBILIDAD.md`
- **COMUNIDEV — Arquitectura** &mdash; `doc\05-ARQUITECTURA.md`
- **COMUNIDEV — Estado de Implementación** &mdash; `doc\06-IMPLEMENTACION.md`
- **COMUNIDEV — Seguridad y Privacidad** &mdash; `doc\07-SEGURIDAD.md`
- **COMUNIDEV — Glosario** &mdash; `doc\08-GLOSARIO.md`
- **COMUNIDEV — Flujos de Módulos** &mdash; `doc\09-FLUJOS.md`
- **10. Cambios de Sesión: Stories + Música** &mdash; `doc\10-CAMBIOS-SESION-STORIES-MUSICA.md`
- **COMUNIDEV — Colecciones MongoDB Atlas** &mdash; `doc\MONGODB-ATLAS-COLLECTIONS.md`
- **COMUNIDEV — Product Backlog (Historias de Usuario)** &mdash; `doc\11-BACKLOG-HU.md`
- **COMUNIDEV — Criterios de Aceptación (Gherkin)** &mdash; `doc\12-CRITERIOS-ACEPTACION.md`
- **COMUNIDEV — Requerimientos No Funcionales (RNF)** &mdash; `doc\13-RNF.md`
- **COMUNIDEV — Plan de Sprints (Scrum, 16 semanas)** &mdash; `doc\14-PLAN-SPRINTS.md`
- **PC5 — Planificación ágil COMUNIDEV** &mdash; `doc\PC5.md`
- **Read Me First** &mdash; `HELP.md`

---

<!-- FUENTE 1/12: doc\01-VISION-GENERAL.md -->

## COMUNIDEV — Visión General

### 1. Visión general

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

### 2. Objetivos funcionales

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

### 3. Roles del sistema

| Rol | Código | Descripción y permisos principales |
|-----|--------|-----------------------------------|
| Desarrollador / Usuario | DEVELOPER | Crea perfil profesional, agrega CV y certificados, publica contenido, crea preguntas, responde, solicita conexiones, conversa, sigue contenido, postula a vacantes y participa en EcoDev. |
| Reclutador | RECRUITER | Representa a una empresa, publica y administra vacantes, visualiza postulaciones de sus vacantes y contacta candidatos según permisos. No puede usar un perfil de CV como desarrollador. |
| Moderador / Soporte | MODERATOR | Gestiona tickets de ayuda, atiende incidencias, revisa denuncias, modera contenido, oculta publicaciones y aplica sanciones dentro de los permisos asignados. |
| Administrador | ADMIN | Gestiona usuarios, roles, empresas, categorías, planes, puntos de reciclaje, reportes, métricas y configuración global. |

Un usuario puede almacenar más de un rol en el sistema si se requiere, pero debe existir un `rolActivo` para definir la experiencia y permisos de la sesión actual. Por ejemplo, un usuario podría tener DEVELOPER y MODERATOR, pero navegar con uno como rol activo.

### Contexto final

COMUNIDEV no es solo una red social genérica. Es una red profesional y técnica para desarrolladores, con asistencia comunitaria, perfiles de empleabilidad, conexiones aceptadas, mensajería, vacantes geolocalizadas y sostenibilidad tecnológica. La lógica crítica del sistema es la privacidad: seguir a alguien no equivale a tener conexión profesional. Las conexiones requieren solicitud y aceptación. El dueño del perfil define de manera granular quién puede ver perfil, CV, certificados, experiencia, ubicación y quién puede enviarle mensajes. Todo el backend debe respetar esas reglas, incluso si el frontend intenta acceder directamente a recursos restringidos.

---

<!-- FUENTE 2/12: doc\02-MODELADO-MONGODB.md -->

## COMUNIDEV — Modelado MongoDB

### Decisiones clave de modelado

MongoDB se utilizará como base documental. Cada entidad se representa como una colección o como un subdocumento embebido, según el volumen, la frecuencia de actualización y la necesidad de consulta independiente.

#### Reglas de modelado

- Usar referencias por ID entre agregados grandes o con alto crecimiento: usuarios, posts, comentarios, mensajes, vacantes, postulaciones y reacciones.
- Usar subdocumentos embebidos para información que siempre se consulta junto con el documento principal y tiene tamaño controlado: configuración, estado de actividad, links sociales, tecnologías del perfil, experiencia, educación y preferencias.
- Evitar embebir listas sin límite dentro de users, posts o conversations; por ejemplo, reacciones, comentarios, seguidores y mensajes deben vivir en sus propias colecciones.
- Usar `ObjectId` para identificadores de MongoDB y conservar fechas en formato ISO (ISODate).
- Todos los documentos principales deben incluir `createdAt` y `updatedAt`; conviene añadir `createdBy` cuando aplique.
- Las URLs de imágenes, videos, CV, certificados y archivos se almacenan en MongoDB, pero el archivo físico se aloja en Cloudinary.
- Los campos geográficos se deben guardar en formato GeoJSON: `{ "type": "Point", "coordinates": [longitud, latitud] }` y deben tener índice 2dsphere.

### Mapa de dominios y colecciones

| Dominio | Colecciones principales |
|---------|------------------------|
| Identidad y perfiles | users, developer_profiles, recruiter_profiles, companies |
| Conexiones y privacidad | connection_requests, connections, follows, blocked_users |
| Contenido social | posts, stories, reels, comments, reactions, saved_contents, hashtags |
| Asistencia técnica | technical_questions, technical_answers, question_votes |
| Mensajería y presencia | conversations, messages, activity_status_history, notifications |
| Empleabilidad | vacancies, applications, application_status_history |
| ODS 12 / EcoDev | ecotech_posts, recycling_points, ecotech_transactions, company_sustainability_practices |
| Gamificación y planes | user_points, point_transactions, badges, user_badges, plans, subscriptions *(pendiente para futuro)* |
| Seguridad y operación | reports, moderation_actions, support_tickets, audit_logs |

---

<!-- FUENTE 3/12: doc\03-ENTIDADES.md -->

## COMUNIDEV — Entidades de Negocio

### 1. Identidad, cuentas y configuración

#### 1.1 users

**Propósito:** representa la cuenta de acceso de toda persona que utiliza COMUNIDEV. Es la entidad central de identidad, autenticación, roles, estado de actividad y configuración global.

Usuarios que usan esta entidad: desarrolladores, reclutadores, soporte, moderadores y administradores.

Campos recomendados:

```json
{
  "_id": "ObjectId",
  "nombre": "Carlos Ruiz",
  "nombreUsuario": "carlosdev",
  "email": "carlos@correo.com",
  "passwordHash": "hash-seguro",
  "telefono": "+51XXXXXXXXX",
  "fotoPerfilUrl": "https://...",
  "bannerUrl": "https://...",
  "bio": "Desarrollador orientado a aplicaciones web y móviles.",
  "sitioWeb": "https://miportafolio.pe",
  "ubicacion": {
    "pais": "Perú",
    "departamento": "Lima",
    "provincia": "Lima",
    "ciudad": "Lima",
    "distrito": "Surco",
    "direccion": "Av. Ejemplo 123"
  },
  "roles": ["DEVELOPER"],
  "rolActivo": "DEVELOPER",
  "estadoCuenta": "ACTIVA",
  "emailVerificado": true,
  "estadoActividad": {
    "estado": "ONLINE",
    "mensajePersonalizado": "Disponible para oportunidades",
    "ultimaVez": "ISODate"
  },
  "configuracion": {
    "tema": "SYSTEM",
    "idioma": "es",
    "notificaciones": {
      "push": true,
      "email": true,
      "mensajes": true,
      "comentarios": true,
      "reacciones": true,
      "conexiones": true,
      "vacantes": true
    },
    "privacidad": {
      "perfil": "PUBLICO",
      "experiencias": "CONEXIONES",
      "educacion": "CONEXIONES",
      "certificados": "CONEXIONES",
      "cv": "CONEXIONES",
      "proyectos": "PUBLICO",
      "tecnologias": "PUBLICO",
      "permiteMensajesDe": "CONEXIONES",
      "mostrarEmail": false,
      "mostrarUbicacion": false,
      "mostrarEstadoActividad": true
    }
  },
  "seguidoresCount": 0,
  "siguiendoCount": 0,
  "conexionesCount": 0,
  "baneo": {
    "baneado": false,
    "tipo": null,
    "razon": null,
    "fechaInicio": null,
    "fechaFin": null,
    "moderadorId": null,
    "contenidoOcultoGradualmente": false
  },
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Reglas principales:**

- `email` y `nombreUsuario` deben ser únicos.
- `passwordHash` nunca debe enviarse al frontend ni exponerse desde REST/GraphQL.
- `estadoCuenta` puede ser: ACTIVA, PENDIENTE_VERIFICACION, SUSPENDIDA, BLOQUEADA o ELIMINADA.
- `estadoActividad.estado` puede ser: ONLINE, AUSENTE, OCUPADO, NO_MOLESTAR u OFFLINE.
- La configuración de privacidad pertenece al dueño del perfil: el backend debe aplicarla cuando otra persona consulta información.
- `baneo.baneado = true` indica que el usuario tiene un baneo activo.
- `baneo.tipo` puede ser: TEMPORAL_LEVE, TEMPORAL_GRAVE, PERMANENTE, SHADOW_BAN.
- `baneo.fechaFin` se usa para baneos temporales; al expirar, `baneo.baneado` pasa a `false`.
- `baneo.contenidoOcultoGradualmente` se usa en shadow bans para ocultar contenido progresivamente.

**Índices sugeridos:**

- Único: `email`.
- Único: `nombreUsuario`.
- Índice: `roles`.
- Índice: `estadoCuenta`.

---

#### 1.2 developer_profiles

**Propósito:** almacena el perfil profesional detallado de un usuario con rol DEVELOPER. Se mantiene separado de users porque contiene información extensa, laboral y configurable por privacidad.

**Relación:** un user con rol DEVELOPER tiene como máximo un `developer_profile`.

Campos recomendados:

```json
{
  "_id": "ObjectId",
  "userId": "ObjectId",
  "tituloProfesional": "Desarrollador Full Stack",
  "tecnologias": [
    { "nombre": "Angular", "nivel": "AVANZADO", "aniosExperiencia": 2 },
    { "nombre": "Spring Boot", "nivel": "INTERMEDIO", "aniosExperiencia": 1 }
  ],
  "habilidadesBlandas": ["Trabajo en equipo", "Comunicación"],
  "experiencias": [
    {
      "empresa": "Empresa ejemplo",
      "puesto": "Desarrollador Backend",
      "descripcion": "Desarrollo de APIs y servicios.",
      "fechaInicio": "2025-01",
      "fechaFin": null,
      "actual": true
    }
  ],
  "educacion": [
    {
      "institucion": "Universidad ejemplo",
      "grado": "Ingeniería de Sistemas",
      "fechaInicio": "2021-03",
      "fechaFin": null,
      "actual": true
    }
  ],
  "certificados": [
    {
      "titulo": "Curso de Spring Boot",
      "institucion": "Plataforma educativa",
      "fechaObtencion": "2025-06",
      "credencialUrl": "https://...",
      "archivoUrl": "https://..."
    }
  ],
  "proyectos": [
    {
      "titulo": "COMUNIDEV",
      "descripcion": "Red social de desarrolladores.",
      "tecnologias": ["Angular", "Spring Boot", "MongoDB"],
      "repositorioUrl": "https://github.com/...",
      "demoUrl": "https://...",
      "imagenes": ["https://..."]
    }
  ],
  "cv": {
    "archivoUrl": "https://.../cv.pdf",
    "nombreArchivo": "cv-carlos-ruiz.pdf",
    "actualizadoEn": "ISODate",
    "visiblePara": "CONEXIONES"
  },
  "disponibilidadLaboral": {
    "buscandoEmpleo": true,
    "modalidades": ["REMOTO", "HIBRIDO"],
    "tiposContrato": ["TIEMPO_COMPLETO", "PRACTICAS"],
    "pretensionSalarial": { "moneda": "PEN", "minimo": 2500, "maximo": 4000 }
  },
  "enlaces": {
    "github": "https://github.com/...",
    "linkedin": "https://linkedin.com/in/...",
    "portafolio": "https://..."
  },
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Reglas principales:**

- Solo puede existir si el usuario posee el rol DEVELOPER.
- `userId` debe ser único.
- El CV y certificados se guardan como URL de archivo; los archivos se almacenan fuera de MongoDB.
- La información laboral no se debe devolver si la configuración de privacidad del usuario no lo permite.
- La coordenada de desarrollador no debe mostrarse públicamente de forma exacta por defecto; puede mostrarse solo ciudad/distrito si el usuario lo autoriza.

**Índices sugeridos:**

- Único: `userId`.
- Índice multikey: `tecnologias.nombre`.
- Índice geoespacial: `ubicacion.coordenadas` (2dsphere), solo si se usarán búsquedas de talento por cercanía.

---

#### 1.3 recruiter_profiles

**Propósito:** contiene información profesional de un usuario con rol RECRUITER; permite identificar al representante de una o varias empresas.

```json
{
  "_id": "ObjectId",
  "userId": "ObjectId",
  "cargo": "Talent Acquisition Specialist",
  "ruc": "20123456789",
  "lema": "Conectando talento con oportunidades",
  "empresasDescripcion": "Trabajo con empresas del sector fintech y retail.",
  "fechaCreacion": "2020",
  "modalidadTrabajo": "HIBRIDO",
  "redesSociales": {
    "linkedin": "https://linkedin.com/in/analopez",
    "instagram": "https://instagram.com/analopez",
    "tiktok": "",
    "facebook": ""
  },
  "empresas": [
    { "companyId": "ObjectId", "cargoEnEmpresa": "RECRUITER", "activo": true }
  ],
  "verificado": false,
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Reglas principales:**

- Solo puede existir para usuarios con rol RECRUITER.
- Un reclutador puede pertenecer a una o varias empresas.
- Solo un reclutador vinculado y autorizado puede crear o gestionar vacantes de una empresa.
- El reclutador no tiene CV de candidato ni puede postular desde su rol de reclutador.
- Los campos `nombres`, `apellidos` se eliminaron (ya están en `users.nombre`).
- El campo `telefono` se movió a `users.telefono`.
- El campo `linkedinUrl` se movió a `redesSociales.linkedin`.
- El campo `anioCreacion` (Int) fue migrado a `fechaCreacion` (String) para soportar fecha completa.
- El campo `empresasDescripcion` guarda la descripción en texto libre de las empresas con las que trabaja el reclutador (separado de `lema`).
- `createRecruiterProfile` aplica upsert: si ya existe un perfil para el `userId`, actualiza los campos enviados en lugar de crear un duplicado.

**Índices sugeridos:**

- Único: `userId` (implementado con `@Indexed(unique = true)`).

---

#### 1.4 companies

**Propósito:** representa una empresa que ofrece oportunidades laborales para desarrolladores. Aparece en el mapa cuando posee al menos una vacante activa.

```json
{
  "_id": "ObjectId",
  "nombre": "DevSolutions Perú",
  "descripcion": "Empresa de desarrollo de software.",
  "sector": "Tecnología",
  "sitioWeb": "https://...",
  "logoUrl": "https://...",
  "bannerUrl": "https://...",
  "ubicacion": {
    "pais": "Perú",
    "ciudad": "Lima",
    "distrito": "San Isidro",
    "direccion": "Av. Ejemplo 123",
    "coordenadas": { "type": "Point", "coordinates": [-77.036528, -12.097107] }
  },
  "contacto": { "email": "rrhh@empresa.com", "telefono": "+51XXXXXXXXX" },
  "redes": { "linkedin": "https://...", "github": "https://..." },
  "estadoVerificacion": "PENDIENTE",
  "vacantesActivasCount": 0,
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Reglas principales:**

- Debe registrar nombre, correo de contacto y ubicación geográfica antes de publicar vacantes.
- Solo debe ser visible en el mapa laboral si tiene vacantes activas.
- Las coordenadas deben ser válidas y usar formato GeoJSON.
- Una empresa puede tener varios reclutadores autorizados mediante `recruiter_profiles.empresas`.

**Índices sugeridos:**

- Índice de texto: `nombre`, `descripcion`, `sector`.
- Índice geoespacial: `ubicacion.coordenadas` (2dsphere).
- Índice: `estadoVerificacion`.

---

### 2. Seguimientos, conexiones y privacidad

COMUNIDEV usa dos relaciones sociales distintas:

- **Seguimiento (follow):** relación unilateral. A puede seguir a B para recibir contenido de B; B no necesita aceptar. Es útil para construir el feed.
- **Conexión (connection):** relación profesional con solicitud y aceptación. A envía solicitud a B; B la acepta o rechaza. Una conexión es simétrica y puede habilitar vista de información restringida, mensajería y contactos profesionales.

Esta separación es importante porque una persona puede querer permitir que otros vean sus publicaciones públicas sin que todos tengan acceso a su CV, certificados o mensajería privada.

#### 2.1 connection_requests

**Propósito:** registra solicitudes de conexión pendientes, aceptadas, rechazadas o canceladas.

```json
{
  "_id": "ObjectId",
  "solicitanteId": "ObjectId",
  "receptorId": "ObjectId",
  "mensaje": "Hola, me gustaría conectar contigo.",
  "estado": "PENDIENTE",
  "createdAt": "ISODate",
  "respondidoEn": null,
  "updatedAt": "ISODate"
}
```

**Estados:** PENDIENTE, ACEPTADA, RECHAZADA, CANCELADA.

**Reglas principales:**

- Un usuario no puede enviarse solicitud a sí mismo.
- No puede existir más de una solicitud pendiente para el mismo par de usuarios en la misma dirección.
- No se puede enviar solicitud si ya existe una conexión activa entre ambos.
- El receptor es el único que puede aceptar o rechazar.
- Al aceptar, se crea un documento en `connections` y la solicitud cambia a ACEPTADA.
- Al cancelar, solo el solicitante puede cambiar el estado a CANCELADA.

**Índices sugeridos:**

- Único parcial o compuesto: `solicitanteId`, `receptorId`, `estado` para solicitudes pendientes.
- Índice: `receptorId`, `estado`.
- Índice: `solicitanteId`, `estado`.

---

#### 2.2 connections

**Propósito:** representa una relación profesional aceptada entre dos usuarios. La conexión es simétrica: ambos son contactos del otro.

```json
{
  "_id": "ObjectId",
  "usuarioMenorId": "ObjectId",
  "usuarioMayorId": "ObjectId",
  "iniciadorId": "ObjectId",
  "estado": "ACTIVA",
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Nota técnica:** para evitar duplicados, los IDs se guardan ordenados de forma determinística en `usuarioMenorId` y `usuarioMayorId`.

**Estados:** ACTIVA, ELIMINADA, BLOQUEADA.

**Reglas principales:**

- Solo se crea después de aceptar una solicitud válida.
- La existencia de una conexión no obliga a mostrar toda la información: el perfil sigue respetando las preferencias de privacidad del dueño.
- Puede habilitar mensajería cuando `permiteMensajesDe = CONEXIONES`.
- Cualquiera de los dos usuarios puede eliminar la conexión.

**Índices sugeridos:**

- Único: `usuarioMenorId`, `usuarioMayorId`.
- Índice: `usuarioMenorId`.
- Índice: `usuarioMayorId`.

---

#### 2.3 follows

**Propósito:** representa el seguimiento unilateral de usuarios, empresas o etiquetas. Sirve para personalizar el feed, recibir novedades y seguir vacantes de empresas.

```json
{
  "_id": "ObjectId",
  "seguidorId": "ObjectId",
  "tipoSeguido": "USER",
  "seguidoId": "ObjectId",
  "createdAt": "ISODate"
}
```

**Valores de tipoSeguido:** USER, COMPANY, HASHTAG, TECHNOLOGY.

**Reglas principales:**

- No se puede seguir dos veces a la misma entidad.
- Un usuario no puede seguirse a sí mismo.
- Seguir no equivale a conectar y no concede acceso a datos configurados como CONEXIONES.
- El seguimiento de empresas permite recibir notificaciones de sus nuevas vacantes.

**Índices sugeridos:**

- Único: `seguidorId`, `tipoSeguido`, `seguidoId`.
- Índice: `tipoSeguido`, `seguidoId`.

---

#### 2.4 blocked_users

**Propósito:** guarda los bloqueos entre usuarios para proteger la experiencia y evitar contacto no deseado.

```json
{
  "_id": "ObjectId",
  "bloqueadorId": "ObjectId",
  "bloqueadoId": "ObjectId",
  "motivo": "ACOSO",
  "createdAt": "ISODate"
}
```

**Reglas principales:**

- Un bloqueo impide solicitudes de conexión, seguimiento, mensajería y visualización de contenido privado entre el par de usuarios.
- El bloqueo debe eliminar o inhabilitar conexiones existentes.
- Solo el bloqueador puede retirar el bloqueo.

---

### 3. Contenido social

#### 3.1 posts

**Propósito:** almacena publicaciones permanentes del feed: textos, imágenes, archivos, código, videos y enlaces.

```json
{
  "_id": "ObjectId",
  "autorId": "ObjectId",
  "tipo": "POST",
  "contenido": {
    "texto": "Comparto una guía para implementar JWT.",
    "imagenes": ["https://..."],
    "videos": [],
    "archivos": [
      { "nombre": "guia-jwt.pdf", "url": "https://...", "mimeType": "application/pdf" }
    ],
    "bloquesCodigo": [
      { "lenguaje": "java", "codigo": "public class ..." }
    ],
    "enlaces": ["https://..."]
  },
  "etiquetas": ["SpringBoot", "JWT", "Backend"],
  "categoria": "TECNOLOGIA",
  "visibilidad": "PUBLICO",
  "estadoModeracion": "VISIBLE",
  "estadisticas": {
    "vistas": 0,
    "reaccionesCount": 0,
    "comentariosCount": 0,
    "compartidosCount": 0,
    "guardadosCount": 0
  },
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Valores relevantes:**

- `categoria`: TECNOLOGIA, EMPLEO, PROYECTOS, COMUNIDAD, ECO_DEV, OTRO.
- `visibilidad`: PUBLICO, SEGUIDORES, CONEXIONES, PRIVADO.
- `estadoModeracion`: VISIBLE, EN_REVISION, OCULTO, ELIMINADO.

**Reglas principales:**

- Debe tener texto o al menos un recurso multimedia/archivo.
- Los archivos se almacenan en servicios externos y MongoDB solo conserva metadatos y URLs.
- El feed debe filtrar publicaciones según visibilidad, bloqueos, seguimientos y conexiones.
- Las estadísticas son contadores desnormalizados para lectura rápida y se actualizan cuando se crean/eliminan reacciones, comentarios, compartidos o guardados.

**Índices sugeridos:**

- Índice: `autorId`, `createdAt`.
- Índice: `visibilidad`, `estadoModeracion`, `createdAt`.
- Índice multikey: `etiquetas`.
- Índice de texto: `contenido.texto`, `etiquetas`.

---

#### 3.2 stories

**Propósito:** representa contenido efímero, normalmente visible por 24 horas.

```json
{
  "_id": "ObjectId",
  "autorId": "ObjectId",
  "contenido": {
    "texto": "Terminando una nueva funcionalidad de COMUNIDEV.",
    "imagenUrl": "https://...",
    "videoUrl": null,
    "musica": {
      "trackId": "itunes-track-id-123",
      "trackName": "Nombre de canción",
      "artistName": "Artista",
      "coverUrl": "https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/...",
      "previewUrl": "https://audio-ssl.itunes.apple.com/.../preview.m4a",
      "musicMode": "cover",
      "lyricsText": null,
      "lyricsPosX": 50.0,
      "lyricsPosY": 70.0,
      "coverPosX": 50.0,
      "coverPosY": 40.0,
      "lyricsScale": 1.0,
      "coverScale": 1.0
    }
  },
  "visibilidad": "SEGUIDORES",
  "fechaExpiracion": "ISODate",
  "vistasCount": 0,
  "reaccionesCount": 0,
  "estado": "ACTIVA",
  "createdAt": "ISODate"
}
```

**Reglas principales:**

- Una historia debe tener texto, imagen o video.
- La música debe utilizarse únicamente con proveedor autorizado, licencia aplicable o enlace externo permitido; no almacenar música protegida directamente en el sistema.
- La historia deja de estar disponible cuando llega `fechaExpiracion`.
- Se recomienda índice TTL sobre `fechaExpiracion` para limpiar documentos expirados.

---

#### 3.3 story_views

**Propósito:** registra qué usuario visualizó una historia.

```json
{
  "_id": "ObjectId",
  "storyId": "ObjectId",
  "viewerId": "ObjectId",
  "viewedAt": "ISODate"
}
```

**Regla:** índice único compuesto `storyId` + `viewerId` si solo se cuenta una visualización por usuario.

---

#### 3.4 reels

**Propósito:** almacena videos cortos permanentes.

```json
{
  "_id": "ObjectId",
  "autorId": "ObjectId",
  "videoUrl": "https://...",
  "portadaUrl": "https://...",
  "descripcion": "Tip rápido de Spring Boot.",
  "etiquetas": ["SpringBoot", "Java", "Tips"],
  "musica": {
    "titulo": "Nombre de canción",
    "artista": "Artista",
    "proveedor": "PROVEEDOR_AUTORIZADO",
    "referenciaExterna": "id-o-url"
  },
  "duracionSegundos": 30,
  "visibilidad": "PUBLICO",
  "estadoModeracion": "VISIBLE",
  "estadisticas": {
    "vistas": 0,
    "reaccionesCount": 0,
    "comentariosCount": 0,
    "compartidosCount": 0,
    "guardadosCount": 0
  },
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

---

#### 3.5 comments

**Propósito:** almacena comentarios sobre publicaciones, reels, historias o anuncios EcoDev.

```json
{
  "_id": "ObjectId",
  "autorId": "ObjectId",
  "contenidoId": "ObjectId",
  "tipoContenido": "POST",
  "parentCommentId": null,
  "texto": "Muy buen aporte, gracias.",
  "imagenes": [],
  "estadoModeracion": "VISIBLE",
  "reaccionesCount": 0,
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Valores de tipoContenido:** POST, REEL, STORY, ECOTECH_POST.

**Reglas principales:**

- Un comentario debe tener texto o imagen.
- La respuesta a comentario debe pertenecer al mismo contenido del comentario padre.
- No permitir niveles infinitos de anidamiento: se recomienda máximo un nivel de respuesta en el MVP.
- Un comentario oculto por moderación no aparece en la interfaz normal.

**Índices sugeridos:**

- Índice: `contenidoId`, `tipoContenido`, `createdAt`.
- Índice: `parentCommentId`.
- Índice: `autorId`.

---

#### 3.6 reactions

**Propósito:** almacena la reacción individual de un usuario sobre un contenido o comentario.

```json
{
  "_id": "ObjectId",
  "usuarioId": "ObjectId",
  "objetivoId": "ObjectId",
  "tipoObjetivo": "POST",
  "tipoReaccion": "LIKE",
  "createdAt": "ISODate"
}
```

**Valores de tipoObjetivo:** POST, REEL, STORY, COMMENT, TECHNICAL_ANSWER.

**Valores de tipoReaccion (implementado como enum GraphQL `TipoReaccion`):** LIKE, LOVE, CELEBRATE, SUPPORT.

**Nota:** futuras versiones pueden agregar INSIGHTFUL o FIRE al enum.

**Reglas principales:**

- Un usuario solo puede tener una reacción activa por objetivo; puede cambiarla, pero no duplicarla.
- Crear o eliminar una reacción debe actualizar el contador desnormalizado del contenido objetivo.
- El usuario no debe reaccionar sobre contenido que no tiene permiso de visualizar.

**Índice sugerido:** único compuesto `usuarioId` + `objetivoId` + `tipoObjetivo`.

---

#### 3.7 saved_contents

**Propósito:** permite que un usuario guarde publicaciones, reels, preguntas o vacantes para revisarlas después.

```json
{
  "_id": "ObjectId",
  "usuarioId": "ObjectId",
  "contenidoId": "ObjectId",
  "tipoContenido": "POST",
  "createdAt": "ISODate"
}
```

**Reglas principales:**

- Un usuario no puede guardar dos veces el mismo contenido.
- Guardar contenido es privado; otros usuarios no pueden ver esta lista.
- El contenido eliminado u oculto no debe estar disponible aunque permanezca su referencia histórica.

---

#### 3.8 hashtags

**Propósito:** catálogo de etiquetas para normalizar búsquedas, mostrar tendencias y permitir seguimiento de tecnologías o temas.

```json
{
  "_id": "ObjectId",
  "nombre": "springboot",
  "nombreVisible": "SpringBoot",
  "tipo": "TECNOLOGIA",
  "usoCount": 0,
  "activo": true,
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Tipos:** TECNOLOGIA, LENGUAJE, EMPLEO, ECO_DEV, COMUNIDAD, GENERAL.

---

### 4. Asistencia técnica

#### 4.1 technical_questions

**Propósito:** representa una consulta técnica estructurada.

```json
{
  "_id": "ObjectId",
  "autorId": "ObjectId",
  "titulo": "¿Cómo proteger rutas en Angular con JWT?",
  "descripcion": "Tengo una API en Spring Boot y deseo proteger rutas...",
  "categoria": "FRONTEND",
  "etiquetas": ["Angular", "JWT", "AuthGuard"],
  "bloquesCodigo": [
    { "lenguaje": "typescript", "codigo": "canActivate() { ... }" }
  ],
  "archivos": [],
  "nivelDificultad": "INTERMEDIO",
  "estado": "ABIERTA",
  "respuestaAceptadaId": null,
  "votosCount": 0,
  "respuestasCount": 0,
  "vistasCount": 0,
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Valores:**

- `categoria`: FRONTEND, BACKEND, MOVIL, BASE_DE_DATOS, REDES, DEVOPS, CIBERSEGURIDAD, OTRA.
- `estado`: ABIERTA, RESUELTA, CERRADA, ELIMINADA.
- `nivelDificultad`: BASICO, INTERMEDIO, AVANZADO.

**Reglas principales:**

- Debe incluir título, descripción y mínimo una etiqueta.
- Solo el autor puede seleccionar `respuestaAceptadaId`.
- Una pregunta solo puede tener una respuesta aceptada a la vez.
- Al aceptar una respuesta, la pregunta pasa a estado RESUELTA y se asignan puntos según reglas de gamificación.

---

#### 4.2 technical_answers

**Propósito:** almacena las respuestas que otros miembros dan a una pregunta técnica.

```json
{
  "_id": "ObjectId",
  "preguntaId": "ObjectId",
  "autorId": "ObjectId",
  "contenido": "Puedes usar un guard y un interceptor para el token.",
  "bloquesCodigo": [
    { "lenguaje": "typescript", "codigo": "export const authGuard..." }
  ],
  "estado": "PUBLICADA",
  "esAceptada": false,
  "votosCount": 0,
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Reglas principales:**

- Solo se puede responder una pregunta abierta o resuelta si la política permite aportes posteriores.
- La aceptación debe estar sincronizada con `technical_questions.respuestaAceptadaId`.
- Cuando una respuesta se acepta, se actualiza `esAceptada = true` y se acredita puntos al autor.

---

#### 4.3 question_votes

**Propósito:** registra votos de utilidad para preguntas o respuestas técnicas.

```json
{
  "_id": "ObjectId",
  "usuarioId": "ObjectId",
  "objetivoId": "ObjectId",
  "tipoObjetivo": "QUESTION",
  "valor": 1,
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Valores:** `tipoObjetivo` = QUESTION | ANSWER; `valor` = 1 | -1 si se habilitan votos positivos y negativos.

**Reglas principales:**

- Un usuario solo puede tener un voto por cada pregunta o respuesta.
- El autor no puede votar su propio contenido técnico.
- Cambiar un voto debe recalcular el contador del objetivo.

---

### 5. Mensajería, actividad y notificaciones

#### 5.1 conversations

**Propósito:** representa una conversación privada directa o grupal.

```json
{
  "_id": "ObjectId",
  "tipo": "DIRECT",
  "participantes": [
    { "userId": "ObjectId", "leidoHastaMensajeId": "ObjectId", "ultimoVistoEn": "ISODate" }
  ],
  "ultimoMensaje": {
    "mensajeId": "ObjectId",
    "textoPreview": "Hola, vi tu proyecto.",
    "remitenteId": "ObjectId",
    "fecha": "ISODate"
  },
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Reglas principales:**

- Para conversaciones directas, debe existir una conversación única por par de usuarios.
- El inicio de conversación depende de `users.configuracion.privacidad.permiteMensajesDe` del receptor.
- Si la configuración es CONEXIONES, solo usuarios con conexión activa pueden iniciar chat.
- Un bloqueo entre participantes impide crear o continuar la conversación.

---

#### 5.2 messages

**Propósito:** guarda mensajes individuales de una conversación.

```json
{
  "_id": "ObjectId",
  "conversationId": "ObjectId",
  "remitenteId": "ObjectId",
  "contenido": {
    "texto": "Hola, vi tu perfil y me interesa conversar.",
    "imagenes": [],
    "archivos": [],
    "enlaces": [],
    "audio": {
      "url": "https://cloudinary.com/...",
      "duracionSegundos": 12,
      "waveform": [0.1, 0.3, 0.5, 0.8, 0.6, 0.2]
    },
    "video": {
      "url": "https://cloudinary.com/...",
      "thumbnailUrl": "https://cloudinary.com/...",
      "duracionSegundos": 45
    }
  },
  "tipo": "TEXT",
  "estado": "ENVIADO",
  "eliminadoPara": [],
  "reacciones": [
    { "usuarioId": "ObjectId", "tipo": "LIKE", "createdAt": "ISODate" }
  ],
  "leidoPor": [
    { "usuarioId": "ObjectId", "leidoEn": "ISODate" }
  ],
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Tipos:** TEXT, IMAGE, FILE, AUDIO, VIDEO, SYSTEM.

**Estados:** ENVIADO, ENTREGADO, LEIDO, ELIMINADO.

**Reglas principales:**

- Solo un participante activo puede enviar mensajes en una conversación.
- Un mensaje eliminado para todos no debe borrarse físicamente de inmediato si se necesita auditoría.
- Los mensajes se entregan en tiempo real con WebSockets y se almacenan permanentemente en MongoDB.
- Los archivos multimedia (audio, video, imágenes) se suben a Cloudinary y se guardan como URLs en el mensaje.
- El campo `leidoPor` registra qué usuarios han visto el mensaje y cuándo.
- El campo `reacciones` permite reaccionar a mensajes individuales con tipos: LIKE, LOVE, INSIGHTFUL, FIRE, SUPPORT.
- El audio debe incluir duración y waveform para reproducción visual.
- El video debe incluir thumbnail y duración para preview.

**Índices sugeridos:**

- Índice: `conversationId`, `createdAt`.
- Índice: `remitenteId`.
- Índice: `leidoPor.usuarioId`.

---

#### 5.3 activity_status_history

**Propósito:** conserva cambios históricos del estado de actividad si se requiere auditoría.

```json
{
  "_id": "ObjectId",
  "userId": "ObjectId",
  "estado": "ONLINE",
  "mensajePersonalizado": "Disponible",
  "desde": "ISODate",
  "hasta": null
}
```

**Nota:** el estado actual debe mantenerse embebido en `users.estadoActividad` para lectura rápida. Esta colección es opcional para el MVP.

---

#### 5.4 notifications

**Propósito:** registra notificaciones persistentes que el usuario puede visualizar aunque estuviera desconectado cuando ocurrió el evento.

```json
{
  "_id": "ObjectId",
  "destinatarioId": "ObjectId",
  "actorId": "ObjectId",
  "tipo": "NUEVA_RESPUESTA",
  "titulo": "Nueva respuesta a tu pregunta",
  "mensaje": "Ana respondió tu consulta sobre JWT.",
  "referencia": { "tipo": "TECHNICAL_QUESTION", "id": "ObjectId" },
  "leida": false,
  "createdAt": "ISODate"
}
```

**Tipos sugeridos:** NUEVA_REACCION, NUEVO_COMENTARIO, NUEVA_RESPUESTA, RESPUESTA_ACEPTADA, SOLICITUD_CONEXION, CONEXION_ACEPTADA, NUEVO_MENSAJE, NUEVA_VACANTE, NUEVA_POSTULACION, ACTUALIZACION_POSTULACION, REPORTE_RESUELTO.

**Reglas principales:**

- Se persiste en MongoDB y se envía en tiempo real mediante WebSocket cuando el usuario está conectado.
- El usuario puede marcar una o varias notificaciones como leídas.
- Debe respetar la configuración de notificaciones del usuario.

---

### 6. Empleo, empresas y mapa laboral

#### 6.1 vacancies

**Propósito:** representa una oferta laboral publicada por una empresa.

```json
{
  "_id": "ObjectId",
  "companyId": "ObjectId",
  "recruiterId": "ObjectId",
  "titulo": "Desarrollador Full Stack",
  "descripcion": "Buscamos desarrollador con experiencia en Angular y Spring Boot.",
  "responsabilidades": ["Desarrollar APIs", "Participar en revisiones de código"],
  "requisitos": ["Conocimiento de Java", "Conocimiento de Angular"],
  "tecnologias": ["Angular", "Java", "Spring Boot", "MongoDB"],
  "nivelExperiencia": "JUNIOR",
  "modalidad": "HIBRIDO",
  "tipoContrato": "TIEMPO_COMPLETO",
  "ubicacion": {
    "pais": "Perú",
    "ciudad": "Lima",
    "distrito": "San Isidro",
    "coordenadas": { "type": "Point", "coordinates": [-77.036528, -12.097107] }
  },
  "remuneracion": {
    "visible": true,
    "moneda": "PEN",
    "minimo": 3000,
    "maximo": 4500,
    "periodo": "MENSUAL"
  },
  "vacantesDisponibles": 2,
  "estado": "ACTIVA",
  "fechaLimite": "ISODate",
  "destacada": false,
  "postulacionesCount": 0,
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Valores principales:**

- `nivelExperiencia`: PRACTICANTE, JUNIOR, MID, SENIOR, LEAD.
- `modalidad`: REMOTO, HIBRIDO, PRESENCIAL.
- `tipoContrato`: TIEMPO_COMPLETO, MEDIO_TIEMPO, PRACTICAS, FREELANCE, PROYECTO, TEMPORAL.
- `estado`: BORRADOR, PENDIENTE_REVISION, ACTIVA, PAUSADA, CERRADA, VENCIDA, RECHAZADA.

**Reglas principales:**

- Solo reclutadores autorizados de la empresa pueden crear, editar, pausar o cerrar vacantes.
- Una vacante activa requiere título, descripción, empresa, modalidad, tecnologías, fecha límite y ubicación o condición remota.
- La fecha límite no puede ser anterior a la fecha actual.
- Al vencer, debe cambiar automáticamente a VENCIDA y dejar de recibir postulaciones.
- El mapa laboral muestra empresas con una o más vacantes en estado ACTIVA.

**Índices sugeridos:**

- Índice: `companyId`, `estado`.
- Índice multikey: `tecnologias`.
- Índice: `modalidad`, `nivelExperiencia`, `estado`.
- Índice geoespacial: `ubicacion.coordenadas` (2dsphere).
- Índice: `fechaLimite`.

---

#### 6.2 applications

**Propósito:** representa la postulación de un desarrollador a una vacante laboral.

```json
{
  "_id": "ObjectId",
  "vacancyId": "ObjectId",
  "developerId": "ObjectId",
  "perfilSnapshot": {
    "tituloProfesional": "Desarrollador Full Stack",
    "tecnologias": ["Angular", "Spring Boot", "MongoDB"],
    "cvUrl": "https://...",
    "proyectos": [
      { "titulo": "Proyecto ejemplo", "repositorioUrl": "https://github.com/..." }
    ]
  },
  "cartaPresentacion": "Estoy interesado en participar en el proceso.",
  "estado": "ENVIADA",
  "reclutadorAsignadoId": "ObjectId",
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Estados:** ENVIADA, EN_REVISION, PRESELECCIONADA, ENTREVISTA, ACEPTADA, RECHAZADA, RETIRADA.

**Reglas principales:**

- Solo un usuario con rol DEVELOPER puede postular.
- El desarrollador debe tener perfil mínimo completo y CV disponible si la vacante lo exige.
- No puede existir más de una postulación activa por el mismo `developerId` y `vacancyId`.
- `perfilSnapshot` conserva los datos usados al postular, incluso si el usuario actualiza su perfil posteriormente.
- Solo reclutadores autorizados pueden ver postulaciones de vacantes de su empresa.

**Índices sugeridos:**

- Único: `vacancyId`, `developerId`.
- Índice: `developerId`, `estado`.
- Índice: `vacancyId`, `estado`.

---

#### 6.3 application_status_history

**Propósito:** historial auditable de cambios de estado en una postulación.

```json
{
  "_id": "ObjectId",
  "applicationId": "ObjectId",
  "estadoAnterior": "ENVIADA",
  "estadoNuevo": "EN_REVISION",
  "comentario": "Perfil en revisión inicial.",
  "cambiadoPorId": "ObjectId",
  "createdAt": "ISODate"
}
```

**Regla:** cada cambio de estado de `applications` debe generar un registro en esta colección y una notificación para el desarrollador cuando corresponda.

---

### 7. EcoDev y ODS 12

El módulo EcoDev contribuye al ODS 12 al promover consumo responsable de tecnología, Green IT, reparación, reutilización, donación y reciclaje electrónico.

#### 7.1 ecotech_posts

**Propósito:** publicaciones específicas sobre donación, intercambio, reparación, reutilización, campañas de reciclaje o consejos de sostenibilidad tecnológica.

```json
{
  "_id": "ObjectId",
  "autorId": "ObjectId",
  "tipo": "DONACION",
  "titulo": "Laptop funcional para donación",
  "descripcion": "Equipo funcional, requiere cambio de batería.",
  "categoriaEquipo": "LAPTOP",
  "condicion": "FUNCIONAL_CON_REPARACION_MENOR",
  "imagenes": ["https://..."],
  "ubicacionAproximada": {
    "ciudad": "Lima",
    "distrito": "Cercado de Lima",
    "coordenadas": { "type": "Point", "coordinates": [-77.03, -12.04] }
  },
  "etiquetas": ["Donacion", "Reutilizacion", "ODS12"],
  "estado": "ACTIVA",
  "interesadosCount": 0,
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Tipos:** CONSEJO_GREEN_IT, DONACION, INTERCAMBIO, REPARACION, REUTILIZACION, RECICLAJE, CAMPANA.

**Condición de equipo:** FUNCIONAL, FUNCIONAL_CON_REPARACION_MENOR, PARA_REPARAR, PARA_REPUESTOS, NO_FUNCIONAL.

**Reglas principales:**

- Para anuncios de equipos se requiere título, estado/condición, categoría y ubicación aproximada.
- No mostrar dirección exacta públicamente si implica domicilio personal; la coordinación debe realizarse por chat interno.
- Baterías dañadas, componentes peligrosos o residuos riesgosos deben incluir advertencia de seguridad y pueden requerir revisión de moderación.
- El sistema debe priorizar reparación, reutilización o donación antes del descarte de equipos funcionales.

---

#### 7.2 recycling_points

**Propósito:** registra puntos de acopio, reciclaje electrónico o campañas temporales.

```json
{
  "_id": "ObjectId",
  "nombre": "Punto de reciclaje tecnológico",
  "tipo": "PUNTO_PERMANENTE",
  "organizacion": "Organización responsable",
  "descripcion": "Recepción de equipos electrónicos y periféricos.",
  "ubicacion": {
    "pais": "Perú",
    "ciudad": "Lima",
    "distrito": "Miraflores",
    "direccion": "Dirección pública autorizada",
    "coordenadas": { "type": "Point", "coordinates": [-77.02, -12.12] }
  },
  "materialesAceptados": ["LAPTOP", "CELULAR", "CABLE", "PERIFERICO"],
  "horario": "Lunes a viernes de 9:00 a 17:00",
  "contacto": { "telefono": "+51XXXXXXXXX", "email": "contacto@ejemplo.com" },
  "estadoVerificacion": "VERIFICADO",
  "createdById": "ObjectId",
  "validadoPorId": "ObjectId",
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Estados:** PENDIENTE, VERIFICADO, RECHAZADO, INACTIVO.

**Reglas principales:**

- Solo puntos verificados deben aparecer por defecto al público en el mapa EcoDev.
- Debe incluir ubicación, tipos de materiales aceptados y datos de contacto/horario.
- Puede ser un punto permanente o una campaña temporal con fecha de inicio y fin.

**Índice sugerido:** `ubicacion.coordenadas` con 2dsphere.

---

#### 7.3 ecotech_transactions

**Propósito:** registra el resultado de una operación EcoDev.

```json
{
  "_id": "ObjectId",
  "ecotechPostId": "ObjectId",
  "emisorId": "ObjectId",
  "receptorId": "ObjectId",
  "tipo": "DONACION",
  "estado": "PENDIENTE_CONFIRMACION",
  "cantidadEquipos": 1,
  "confirmadoPorEmisor": false,
  "confirmadoPorReceptor": false,
  "validadoPorAdminId": null,
  "fechaCompletado": null,
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Estados:** PENDIENTE_CONFIRMACION, COMPLETADA, CANCELADA, RECHAZADA, EN_DISPUTA.

**Reglas principales:**

- Una transacción se considera COMPLETADA cuando ambas partes la confirman o un administrador la valida.
- Solo transacciones completadas alimentan métricas como equipos donados, reutilizados, reparados o reciclados.
- La transacción puede generar puntos para los participantes según la política de gamificación.

---

#### 7.4 company_sustainability_practices

**Propósito:** permite que empresas registren prácticas sostenibles relacionadas con tecnología y ODS 12.

```json
{
  "_id": "ObjectId",
  "companyId": "ObjectId",
  "tipo": "RECICLAJE_E_WASTE",
  "descripcion": "La empresa cuenta con programa interno de reciclaje electrónico.",
  "evidenciaUrl": "https://...",
  "estadoVerificacion": "PENDIENTE",
  "validadoPorId": null,
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Tipos sugeridos:** RECICLAJE_E_WASTE, EQUIPOS_REACONDICIONADOS, TELETRABAJO, EFICIENCIA_ENERGETICA, REPARACION_EQUIPOS, DONACION_TECNOLOGICA, GREEN_IT.

**Regla:** las prácticas declaradas por empresas deben mostrarse como "pendientes de verificación" hasta que un administrador valide la evidencia.

---

### 8. Puntos, niveles, insignias y planes

> **NOTA:** Esta sección NO está implementada en el MVP. Se预留 para una futura versión con gamificación y planes premium.

Se deben separar dos conceptos:

- **Puntos/reputación:** gamificación por aportes positivos a la comunidad.
- **Planes:** límites y beneficios comerciales o funcionales para desarrolladores, reclutadores o empresas.

#### 8.1 user_points

**Propósito:** mantiene el saldo actual de puntos y nivel de un usuario para consultas rápidas.

```json
{
  "_id": "ObjectId",
  "userId": "ObjectId",
  "puntosTotales": 320,
  "puntosPorCategoria": {
    "COMUNIDAD": 100,
    "ASISTENCIA_TECNICA": 150,
    "ECO_DEV": 40,
    "EMPLEABILIDAD": 30
  },
  "nivel": "GOLD",
  "progresoNivel": 0.2,
  "ultimaActualizacion": "ISODate"
}
```

**Niveles sugeridos:** BRONZE, SILVER, GOLD, PLATINUM.

**Reglas principales:**

- `puntosTotales` es un resumen; el historial verdadero se conserva en `point_transactions`.
- Los puntos no deben otorgarse dos veces por la misma acción y referencia.
- El nivel se recalcula cada vez que cambia el total.

---

#### 8.2 point_transactions

**Propósito:** historial de movimientos de puntos.

```json
{
  "_id": "ObjectId",
  "userId": "ObjectId",
  "tipoAccion": "RESPUESTA_ACEPTADA",
  "puntos": 15,
  "descripcion": "Tu respuesta fue aceptada como solución.",
  "referencia": { "tipo": "TECHNICAL_ANSWER", "id": "ObjectId" },
  "createdAt": "ISODate"
}
```

**Acciones sugeridas:**

REGISTRO_COMPLETADO, PERFIL_COMPLETADO, PRIMERA_PUBLICACION, RESPUESTA_PUBLICADA, RESPUESTA_ACEPTADA, PUBLICACION_DESTACADA, REPORTE_VALIDO, CONEXION_ACEPTADA, DONACION_CONFIRMADA, RECICLAJE_CONFIRMADO, LOGRO_DESBLOQUEADO, PENALIZACION_MODERACION.

**Reglas principales:**

- Debe existir una restricción lógica única por `userId` + `tipoAccion` + `referencia.tipo` + `referencia.id` cuando la acción solo puede recompensarse una vez.
- Puede contener valores negativos para sanciones o reversión de premios.

---

#### 8.3 badges

**Propósito:** catálogo de insignias disponibles en COMUNIDEV.

```json
{
  "_id": "ObjectId",
  "codigo": "MENTOR_COMUNIDAD",
  "nombre": "Mentor de la comunidad",
  "descripcion": "Obtenida al alcanzar cierta cantidad de respuestas aceptadas.",
  "iconoUrl": "https://...",
  "criterio": { "tipo": "RESPUESTAS_ACEPTADAS", "valorMinimo": 10 },
  "activo": true,
  "createdAt": "ISODate"
}
```

---

#### 8.4 user_badges

**Propósito:** relación entre un usuario y las insignias que consiguió.

```json
{
  "_id": "ObjectId",
  "userId": "ObjectId",
  "badgeId": "ObjectId",
  "obtenidoEn": "ISODate",
  "referencia": { "tipo": "RULE", "id": "RESPUESTAS_ACEPTADAS_10" }
}
```

**Regla:** un usuario solo puede obtener una vez una misma insignia.

---

#### 8.5 plans

**Propósito:** catálogo de planes de uso/membresía.

```json
{
  "_id": "ObjectId",
  "codigo": "COMPANY_PRO",
  "nombre": "Company Pro",
  "tipoTitular": "COMPANY",
  "precio": { "monto": 49.0, "moneda": "USD", "periodo": "MENSUAL" },
  "beneficios": [
    "Hasta 20 vacantes activas",
    "Destacar vacantes en el mapa",
    "Métricas avanzadas"
  ],
  "limites": {
    "publicacionesPorDia": null,
    "solicitudesConexionPorDia": null,
    "vacantesActivas": 20,
    "vacantesDestacadas": 5,
    "mensajesNuevosPorDia": null
  },
  "activo": true,
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Tipos de titular:** USER, COMPANY.

**Ejemplos de planes:** DEVELOPER_FREE, DEVELOPER_PRO, RECRUITER_FREE, COMPANY_BASIC, COMPANY_PRO.

**Reglas principales:**

- Los planes no contienen datos de suscripción individual; son catálogo reutilizable.
- Los límites definidos por el plan se validan antes de publicar una vacante, destacar contenido o usar funcionalidades restringidas.
- Para el MVP, los planes pueden existir sin pasarela de pagos; se pueden activar manualmente desde administración y dejar pagos como fase futura.

---

#### 8.6 subscriptions

**Propósito:** registra el plan actualmente contratado o asignado a un usuario o empresa.

```json
{
  "_id": "ObjectId",
  "titularId": "ObjectId",
  "tipoTitular": "COMPANY",
  "planId": "ObjectId",
  "estado": "ACTIVA",
  "fechaInicio": "ISODate",
  "fechaFin": "ISODate",
  "renovacionAutomatica": false,
  "metodoPagoReferencia": "referencia-tokenizada",
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Estados:** ACTIVA, PENDIENTE_PAGO, VENCIDA, CANCELADA, SUSPENDIDA.

**Reglas principales:**

- Un titular solo puede tener una suscripción activa por tipo de plan aplicable.
- Cuando vence o se cancela, el sistema debe aplicar los límites del plan gratuito correspondiente.

---

### 9. Soporte, moderación y auditoría

#### 9.1 reports

**Propósito:** registra denuncias sobre publicaciones, comentarios, reels, historias, usuarios, vacantes u otros recursos.

```json
{
  "_id": "ObjectId",
  "reportanteId": "ObjectId",
  "objetivoId": "ObjectId",
  "tipoObjetivo": "POST",
  "motivo": "SPAM",
  "descripcion": "Contenido repetitivo y no relacionado con la comunidad.",
  "estado": "PENDIENTE",
  "asignadoAId": null,
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Motivos sugeridos:** SPAM, ACOSO, ODIO_DISCRIMINACION, FRAUDE, MALWARE, CONTENIDO_INAPROPIADO, VACANTE_FALSA, OTRO.

**Estados:** PENDIENTE, EN_REVISION, RESUELTO, DESCARTADO.

---

#### 9.2 moderation_actions

**Propósito:** conserva las decisiones aplicadas por moderadores o administradores.

```json
{
  "_id": "ObjectId",
  "moderadorId": "ObjectId",
  "reportId": "ObjectId",
  "objetivoId": "ObjectId",
  "tipoObjetivo": "POST",
  "accion": "OCULTAR_CONTENIDO",
  "motivo": "SPAM confirmado",
  "createdAt": "ISODate"
}
```

**Acciones:** ADVERTIR_USUARIO, OCULTAR_CONTENIDO, ELIMINAR_CONTENIDO, SUSPENDER_USUARIO, BLOQUEAR_USUARIO, RECHAZAR_VACANTE, DESCARTAR_REPORTE.

---

#### 9.3 support_tickets

**Propósito:** registra solicitudes de ayuda o incidencias enviadas por usuarios al equipo de soporte.

```json
{
  "_id": "ObjectId",
  "creadoPorId": "ObjectId",
  "asunto": "No puedo adjuntar mi CV",
  "descripcion": "El sistema muestra un error al intentar subir el archivo.",
  "categoria": "CUENTA",
  "prioridad": "MEDIA",
  "estado": "ABIERTO",
  "asignadoAId": "ObjectId",
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

**Categorías:** CUENTA, PERFIL, CV, PUBLICACIONES, MENSAJERIA, VACANTES, POSTULACIONES, PAGOS, MODERACION, OTRO.

**Estados:** ABIERTO, EN_PROCESO, ESPERANDO_USUARIO, RESUELTO, CERRADO.

Si se necesitan conversaciones extensas dentro del ticket, crear una colección adicional `support_ticket_messages` en vez de embebir un historial ilimitado en el ticket.

---

#### 9.4 audit_logs

**Propósito:** conserva eventos sensibles del sistema para trazabilidad.

```json
{
  "_id": "ObjectId",
  "actorId": "ObjectId",
  "accion": "ROLE_UPDATED",
  "entidadTipo": "USER",
  "entidadId": "ObjectId",
  "datosAntes": { "roles": ["DEVELOPER"] },
  "datosDespues": { "roles": ["DEVELOPER", "MODERATOR"] },
  "createdAt": "ISODate"
}
```

---

#### 9.5 user_bans

**Propósito:** almacena el historial de baneos de usuarios para auditoría y gestión de desbaneos.

```json
{
  "_id": "ObjectId",
  "userId": "ObjectId",
  "tipo": "TEMPORAL_GRAVE",
  "razon": "Acoso a otros usuarios en mensajería privada",
  "evidencia": [
    { "tipo": "MENSAJE", "id": "ObjectId", "preview": "Mensaje ofensivo..." }
  ],
  "moderadorId": "ObjectId",
  "fechaInicio": "ISODate",
  "fechaFin": "ISODate",
  "duracionDias": 7,
  "baneosPrevios": 1,
  "desbaneado": false,
  "fechaDesbaneo": null,
  "desbaneadoPorId": null,
  "motivoDesbaneo": null,
  "createdAt": "ISODate"
}
```

**Campos importantes:**

- `tipo`: TEMPORAL_LEVE, TEMPORAL_GRAVE, PERMANENTE, SHADOW_BAN.
- `evidencia`: array de objetos con referencias al contenido que justifica el baneo.
- `baneosPrevios`: cantidad de baneos anteriores del usuario (para escalar gravedad).
- `desbaneado`: si es `true`, el baneo fue revertido antes de su fecha de expiración.
- `fechaDesbaneo`, `desbaneadoPorId`, `motivoDesbaneo`: datos del desbaneo manual.

**Reglas principales:**

- Un moderador no puede banear a otro moderador o admin; solo ADMIN puede.
- Cada baneo genera un registro en `audit_logs`.
- Los baneos temporales se revisan periódicamente para deshabilitar automáticamente al expirar.
- `baneosPrevios` ayuda a determinar la gravedad: primer baneo = temporal leve; segundo = temporal grave; tercero+ = permanente.
- El shadow ban oculta contenido gradualmente sin notificar al usuario.

**Índices sugeridos:**

- Índice: `userId`, `createdAt`.
- Índice TTL: `fechaFin` (para limpiar baneos expirados de la colección activa).
- Índice: `desbaneado`, `fechaFin` (para desbaneos automáticos).

---

<!-- FUENTE 4/12: doc\04-VISIBILIDAD.md -->

## COMUNIDEV — Reglas de Visibilidad y Relaciones

### Reglas transversales de visibilidad

La privacidad debe evaluarse siempre desde el punto de vista del dueño del contenido o perfil (owner) frente al visitante (viewer).

#### Niveles de visibilidad

| Valor | Significado |
|-------|-------------|
| PUBLICO | Cualquier usuario, o visitante si se habilita, puede visualizar el recurso. |
| SEGUIDORES | Solo usuarios que siguen al dueño del contenido/perfil pueden visualizarlo. |
| CONEXIONES | Solo usuarios con conexión profesional aceptada pueden visualizarlo. |
| PRIVADO | Solo el propietario puede visualizarlo. |

#### Algoritmo funcional simplificado

```
PuedeVer(ownerId, viewerId, nivelPrivacidad):

1. Si ownerId es igual a viewerId, permitir.
2. Si existe bloqueo entre ownerId y viewerId, denegar.
3. Si nivel es PUBLICO, permitir.
4. Si nivel es SEGUIDORES, permitir solo si viewer sigue a owner.
5. Si nivel es CONEXIONES, permitir solo si existe conexión ACTIVA entre ambos.
6. Si nivel es PRIVADO, denegar.
```

#### Perfil parcial y perfil completo

Se recomienda que el perfil público muestre únicamente:

- Foto de perfil.
- Nombre o nombre de usuario.
- Título profesional.
- Biografía corta.
- Tecnologías públicas.
- Proyectos públicos.
- Contadores de seguidores y conexiones, si el usuario lo permite.

Se recomienda que la información restringida pueda incluir:

- Experiencia laboral.
- Educación.
- Certificados.
- Archivo CV.
- Información de contacto.
- Ubicación detallada.
- Disponibilidad y pretensión salarial.

La configuración de cada sección debe evaluarse individualmente. Por ejemplo, un usuario puede tener perfil público, proyectos públicos, pero CV y certificados visibles solo para conexiones aceptadas.

### Relaciones principales

```
User 1 --- 0..1 DeveloperProfile
User 1 --- 0..1 RecruiterProfile
RecruiterProfile N --- N Company
Company 1 --- N Vacancy
DeveloperProfile(User) 1 --- N Application
Vacancy 1 --- N Application

User 1 --- N Post
User 1 --- N Story
User 1 --- N Reel
Post/Reel/Story 1 --- N Comment
User 1 --- N Reaction

User 1 --- N ConnectionRequest (como solicitante)
User 1 --- N ConnectionRequest (como receptor)
User N --- N User mediante Connection
User 1 --- N Follow

Conversation 1 --- N Message
User N --- N Conversation
User 1 --- N Notification

TechnicalQuestion 1 --- N TechnicalAnswer
User 1 --- N TechnicalQuestion
User 1 --- N TechnicalAnswer

User 1 --- 1 UserPoints
User 1 --- N PointTransaction
User N --- N Badge mediante UserBadge
Plan 1 --- N Subscription

User 1 --- N EcoTechPost
EcoTechPost 1 --- N EcoTechTransaction
Company 1 --- N CompanySustainabilityPractice
```

---

<!-- FUENTE 5/12: doc\05-ARQUITECTURA.md -->

## COMUNIDEV — Arquitectura

### Arquitectura hexagonal y vertical slicing

La arquitectura será hexagonal y se organizará por funcionalidades verticales. Cada módulo debe encapsular su dominio, casos de uso, puertos y adaptadores.

#### Capas hexagonales

| Capa | Responsabilidad |
|------|-----------------|
| Dominio | Entidades, value objects, reglas puras, eventos de dominio y excepciones de negocio. |
| Aplicación | Casos de uso, comandos, consultas, DTOs de entrada/salida y puertos. |
| Puertos de entrada | Interfaces de casos de uso invocadas desde REST, GraphQL o WebSocket. |
| Puertos de salida | Interfaces para persistencia, almacenamiento, mensajería, mapas, notificaciones y servicios externos. |
| Adaptadores de entrada | Controladores REST, resolvers GraphQL, controladores WebSocket/STOMP, jobs programados. |
| Adaptadores de salida | Repositorios MongoDB, Cloudinary, email, push notifications, proveedor de pagos, geocoding. |

#### Vertical slicing sugerido

```
com.comunidev
├── auth
├── users
├── developer-profile
├── recruiter-profile
├── companies
├── connections
├── follows
├── posts
├── stories
├── reels
├── comments
├── reactions
├── questions
├── messaging
├── notifications
├── vacancies
├── applications
├── ecotech
├── points
├── plans
├── moderation
├── support
└── shared
    ├── infrastructure/
    │   ├── musica/
    │   │   ├── MusicService.java          # Busqueda iTunes API
    │   │   └── LyricsService.java         # Letras sincronizadas LRCLIB
    │   └── rest/
    │       └── MusicaController.java      # Endpoints REST /api/v1/musica
    └── dto/
        ├── MusicTrackResponse.java
        └── LyricsResponse.java
```

Cada slice puede seguir esta estructura:

```
posts/
├── domain/
│   ├── Post.java
│   ├── PostVisibility.java
│   └── PostStatus.java
├── application/
│   ├── port/in/CreatePostUseCase.java
│   ├── port/out/PostRepositoryPort.java
│   ├── service/CreatePostService.java
│   └── dto/
├── adapter/in/rest/PostRestController.java
├── adapter/in/graphql/PostQueryController.java
├── adapter/out/mongodb/MongoPostRepositoryAdapter.java
└── config/
```

#### Distribución de protocolos

| Tecnología | Uso recomendado |
|------------|-----------------|
| REST + Swagger/OpenAPI | Autenticación, CRUD, carga de archivos, acciones explícitas, administración, operaciones simples y documentación de endpoints. |
| GraphQL | Feed complejo, perfiles con secciones configurables, consulta de vacantes con empresa/mapa, búsqueda y dashboards. |
| WebSockets | Chat, estado de presencia, notificaciones, nueva respuesta, solicitud/aceptación de conexión y actualización de vacantes. |

---

<!-- FUENTE 6/12: doc\06-IMPLEMENTACION.md -->

## COMUNIDEV — Estado de Implementación

> Última actualización:Septiembre 2026

---

### Resumen ejecutivo

| Fase | Módulos | Estado |
|------|---------|--------|
| **Fase 1** — Base y seguridad | Users, Auth, Profiles, Upload | ✅ Completa |
| **Fase 2** — Comunidad técnica | Posts, Comments, Reactions, Stories, Reels | ✅ Completa |
| **Fase 3** — Conexiones | Follows, Connection Requests, Connections, Blocks, Notifications | ✅ Completa |
| **Fase 4** — Empleabilidad | Talent Search | ✅ Completa |
| **Fase 5** — Multimedia y sostenibilidad | — | 🔲 Pendiente |
| **Fase 6** — Gamificación y operación | — | 🔲 Pendiente |

---

### 1. Módulos backend — Estado detallado

#### Fase 1: Base y seguridad

| Módulo | Estado | Entidades | Resolvers / Endpoints |
|--------|--------|-----------|----------------------|
| **Users** | ✅ Completo | `User` | `me`, `user`, `users`, `updateUser`, `deleteUser` |
| **Auth** | ✅ Completo | — | `login` (JWT), `register` (con verificación email) |
| **Recruiter Profiles** | ✅ Completo | `RecruiterProfile` | `recruiterProfile`, `myRecruiterProfile`, `createRecruiterProfile`, `updateRecruiterProfile` |
| **Developer Profiles** | ⚠️ Parcial | `DeveloperProfile` (schema + dominio, sin resolver) | — |
| **Upload (Cloudinary)** | ✅ Completo | — | REST controller con multipart upload |

#### Fase 2: Comunidad técnica

| Módulo | Estado | Entidades | Resolvers GraphQL |
|--------|--------|-----------|-------------------|
| **Posts** | ✅ Completo | `Post` | `posts`, `post`, `feed` (paginado, seguidos primero), `createPost`, `updatePost`, `deletePost` |
| **Comments** | ✅ Completo | `Comment` | `comments`, `comment`, `commentReplies`, `createComment`, `updateComment`, `deleteComment` |
| **Reactions** | ✅ Completo | `Reaction` | `reactions`, `myReaction`, `react`, `unreact`, `changeReaction` |
| **Stories** | ✅ Completo | `Story` | `stories` (24h expiry), `story`, `storyViews`, `createStory`, `viewStory`, `deleteStory` |
| **Reels** | ✅ Completo | `Reel` | `reels`, `reel`, `reelsByUser`, `createReel`, `updateReel`, `deleteReel` |

#### Fase 3: Conexiones y seguridad social

| Módulo | Estado | Entidades | Resolvers GraphQL |
|--------|--------|-----------|-------------------|
| **Follows** | ✅ Completo | `Follow` | `followers`, `following`, `isFollowing`, `follow`, `unfollow` |
| **Connection Requests** | ✅ Completo | `ConnectionRequest` | `connectionRequests`, `connectionStatus`, `sendConnectionRequest`, `acceptConnection`, `rejectConnection` |
| **Connections** | ✅ Completo | `Connection` | `connections`, `removeConnection` |
| **Blocks** | ✅ Completo | `Block` | `isBlocked`, `block`, `unblock` |
| **Notifications** | ✅ Completo | `Notification` | `notifications`, `unreadCount`, `markNotificationAsRead`, `markAllAsRead`, `deleteNotification` |

#### Fase 4: Empleabilidad

| Módulo | Estado | Entidades | Resolvers / Endpoints |
|--------|--------|-----------|----------------------|
| **Talent Search** | ✅ Completo | — | `searchDevelopers`, `savedProfiles`, `isProfileSaved`, `savedSearches` (REST + GraphQL) |

#### Fase 5: Multimedia y sostenibilidad (pendiente)

| Módulo | Estado |
|--------|--------|
| Companies | 🔲 Schema definido, sin implementar |
| EcoDev | 🔲 Schema definido, sin implementar |

#### Fase 6: Gamificación y operación (pendiente)

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

### 2. Enums sociales

| Enum | Valores |
|------|---------|
| `TipoReaccion` | `LIKE`, `LOVE`, `CELEBRATE`, `SUPPORT` |
| `TipoHistoria` | `TEXT`, `IMAGE`, `VIDEO` |
| `VisibilidadPost` | `PUBLICO`, `SEGUIDORES`, `CONEXIONES`, `SOLO_YO` |
| `EstadoSolicitud` | `PENDIENTE`, `ACEPTADA`, `RECHAZADA` |
| `TipoNotificacion` | `SOLICITUD_CONEXION`, `CONEXION_ACEPTADA`, `COMENTARIO`, `REACCION` |

---

### 3. Endpoints

#### GraphQL

| Ruta | Descripción |
|------|-------------|
| `/api/v1/graphql` | Endpoint principal (queries + mutations) |
| `/graphiql` | IDE interactivo para pruebas |

#### REST (Swagger)

| Ruta | Descripción |
|------|-------------|
| `/swagger-ui.html` | Documentación interactiva |
| `/v3/api-docs` | Spec OpenAPI 3.0 |
| `GET /api/v1/musica/search?q={termino}` | Buscar tracks en iTunes (devuelve lista de `MusicTrackResponse`) |
| `GET /api/v1/musica/lyrics?artist={artista}&track={cancion}` | Obtener letras sincronizadas vía LRCLIB (devuelve `LyricsResponse` con `plainLyrics` + `syncedLyrics`) |

---

### 4. Subscriptions GraphQL (WebSocket)

| Subscription | Estado |
|-------------|--------|
| `notificationReceived(userId)` | ✅ Implementado |
| `connectionStatusChanged(userId)` | ✅ Implementado |
| `onNewMessage` | 🔲 No implementado |
| `onMessageUpdated` | 🔲 No implementado |

---

### 5. Índices MongoDB

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

### 6. Variables de entorno

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

### 7. Arquitectura

- **Patrón:** Hexagonal (puertos y adaptadores)
- **Organización:** Vertical slicing por módulo (`domain/` → `ports/` → `data-access/` → `feature/` → `ui/`)
- **Base de datos:** MongoDB Atlas
- **API:** GraphQL (principal) + REST (auth, upload, talent search)
- **Auth:** JWT con roles `DEVELOPER`, `RECRUTADOR`, `MODERADOR`, `ADMIN`

---

<!-- FUENTE 7/12: doc\07-SEGURIDAD.md -->

## COMUNIDEV — Seguridad y Privacidad

### Consideraciones de seguridad

- Las contraseñas deben almacenarse únicamente como hash seguro, por ejemplo BCrypt/Argon2.
- Los JWT deben tener expiración; usar refresh tokens para renovar sesión de forma controlada.
- No exponer CV, certificados, correo, teléfono, ubicación exacta ni datos de contacto sin validar la configuración de privacidad.
- Validar autorización a nivel de caso de uso, no solo en el frontend.
- Verificar que un reclutador realmente pertenezca a la empresa antes de gestionar vacantes o postulaciones.
- Validar tipo, tamaño y contenido permitido de archivos antes de enviarlos a Cloudinary.
- Proteger WebSockets mediante JWT en el handshake o encabezados STOMP.
- Aplicar rate limiting a login, registro, publicación, mensajería y reacciones para mitigar spam.
- Mantener logs de auditoría para acciones administrativas y de moderación.
- No almacenar música con copyright directamente; usar integraciones o referencias permitidas por proveedores autorizados.

---

<!-- FUENTE 8/12: doc\08-GLOSARIO.md -->

## COMUNIDEV — Glosario

| Término | Definición |
|---------|------------|
| Seguimiento | Relación unilateral para ver contenido o recibir novedades de usuarios, empresas o etiquetas. |
| Conexión | Relación profesional bilateral creada después de aceptar una solicitud. |
| Perfil público | Información básica que puede visualizar cualquier persona autorizada. |
| Perfil restringido | Sección de perfil visible solo para seguidores o conexiones, según la configuración del dueño. |
| Feed | Lista personalizada de publicaciones, reels y contenido de usuarios/temas seguidos. |
| Historia | Contenido temporal que expira después de un tiempo definido, normalmente 24 horas. |
| Reel | Video corto permanente o semipermanente para contenido rápido. |
| EcoDev | Módulo de sostenibilidad tecnológica orientado al ODS 12. |
| Vacante | Oferta laboral publicada por una empresa y gestionada por un reclutador autorizado. |
| Postulación | Solicitud de un desarrollador para participar en una vacante. |
| Punto | Unidad de reputación obtenida por contribuciones positivas o retirada por sanciones. |
| Plan | Conjunto de beneficios y límites aplicables a un usuario o empresa. |
| Arquitectura hexagonal | Estilo arquitectónico que separa dominio y casos de uso de detalles externos mediante puertos y adaptadores. |
| Vertical slicing | Organización del código por funcionalidad de negocio, no solo por capas técnicas globales. |

---

<!-- FUENTE 9/12: doc\09-FLUJOS.md -->

## COMUNIDEV — Flujos de Módulos

### 1. Autenticación y registro

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐     ┌──────────────┐
│   Registro   │────▶│ Verificación │────▶│  Selección  │────▶│  Perfil      │
│  (email/     │     │  de email    │     │  de rol     │     │  inicial     │
│  contraseña) │     │              │     │ DEVELOPER/  │     │              │
└─────────────┘     └──────────────┘     │ RECRUITER   │     └──────────────┘
                                         └─────────────┘
                                                │
                                                ▼
                                         ┌──────────────┐
                                         │ Crear empresa│ (solo RECRUITER)
                                         │ + reclutador │
                                         └──────────────┘
```

**Pasos:**
1. Usuario envía email + contraseña.
2. Sistema crea cuenta con `estadoCuenta = PENDIENTE_VERIFICACION`.
3. Se envía código de verificación por email.
4. Al verificar, `emailVerificado = true` y `estadoCuenta = ACTIVA`.
5. Si es DEVELOPER → redirige a completar `developer_profiles`.
6. Si es RECRUITER → redirige a crear empresa + `recruiter_profiles`.

---

### 2. Login y sesión

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐
│   Login     │────▶│  Validar     │────▶│  Generar    │
│  (email +   │     │  credenciales│     │  JWT        │
│  password)  │     │              │     │  (access +  │
└─────────────┘     └──────────────┘     │  refresh)   │
                                         └─────────────┘
                                                │
                                                ▼
                                         ┌──────────────┐
                                         │  Retornar    │
                                         │  usuario +   │
                                         │  token       │
                                         └──────────────┘
```

**Reglas:**
- Si la cuenta está SUSPENDIDA o BLOQUEADA → denegar acceso.
- Si `emailVerificado = false` → permitir login pero mostrar aviso.
- Refresh token permite renovar access token sin re-login.

---

### 3. Publicación de contenido (Posts)

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐     ┌──────────────┐
│  Crear post │────▶│  Subir       │────▶│  Validar    │────▶│  Guardar     │
│  (texto,    │     │  archivos    │     │  contenido  │     │  en MongoDB  │
│  imágenes,  │     │  (Cloudinary)│     │  y visibilidad│    │              │
│  etc.)      │     │              │     │             │     │              │
└─────────────┘     └──────────────┘     └─────────────┘     └──────────────┘
                                                                       │
                                                                       ▼
                                                                ┌──────────────┐
                                                                │  Publicar    │
                                                                │  en feed     │
                                                                │  (notificar  │
                                                                │  seguidores) │
                                                                └──────────────┘
```

**Reglas:**
- Debe tener texto o al menos un recurso multimedia.
- Archivos se suben a Cloudinary; MongoDB guarda URLs.
- La visibilidad determina quién ve el post en su feed.
- Se actualiza `estadisticas.reaccionesCount` y `comentariosCount` dinámicamente.

---

### 4. Feed principal

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐     ┌──────────────┐
│  Solicitar  │────▶│  Consultar   │────▶│  Filtrar    │────▶│  Ordenar     │
│  feed       │     │  posts de    │     │  por        │     │  por         │
│             │     │  seguidos +  │     │  visibilidad│     │  relevancia/ │
│             │     │  conexiones  │     │  y bloqueos │     │  fecha       │
└─────────────┘     └──────────────┘     └─────────────┘     └──────────────┘
                                                                       │
                                                                       ▼
                                                                ┌──────────────┐
                                                                │  Retornar    │
                                                                │  posts +     │
                                                                │  paginación  │
                                                                └──────────────┘
```

**Filtros aplicados:**
1. Excluir posts de usuarios bloqueados.
2. Excluir posts con `estadoModeracion != VISIBLE`.
3. Aplicar regla de visibilidad (PUBLICO → todos, SEGUIDORES → solo seguidores, CONEXIONES → solo conexiones).
4. Incluir posts propios siempre.

---

### 5. Conexiones profesionales

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐     ┌──────────────┐
│  Enviar     │────▶│  Validar     │────▶│  Crear      │────▶│  Notificar   │
│  solicitud  │     │  permisos    │     │  connection │     │  al receptor │
│  conexión   │     │  (no bloqueo,│     │  _request   │     │              │
│             │     │  no duplicado)│    │  (PENDIENTE)│     │              │
└─────────────┘     └──────────────┘     └─────────────┘     └──────────────┘
                                                                       │
                                                                       ▼
                                                                ┌──────────────┐
                                                           ┌───▶│  ACEPTAR     │
                                                           │    │  (crear      │
                                            ┌──────────────┐│    │  connection) │
                                            │  Receptor    │┤    └──────────────┘
                                            │  responde    ││
                                            └──────────────┘│    ┌──────────────┐
                                                           └───▶│  RECHAZAR    │
                                                                │  (eliminar   │
                                                                │  request)    │
                                                                └──────────────┘
```

**Reglas:**
- No puede enviarse solicitud a sí mismo.
- No puede existir solicitud pendiente duplicada.
- No se puede enviar si ya existe conexión activa.
- Un bloqueo impide nuevas solicitudes.
- Al aceptar, se crea `connections` con IDs ordenados para evitar duplicados.

---

### 6. Mensajería directa

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐     ┌──────────────┐
│  Iniciar    │────▶│  Verificar   │────▶│  Buscar o   │────▶│  Enviar      │
│  chat       │     │  permisos    │     │  crear      │     │  mensaje     │
│             │     │  (privacidad │     │  conversación│    │  (texto,     │
│             │     │  + bloqueo)  │     │             │     │  imagen,     │
│             │     │              │     │             │     │  audio,      │
│             │     │              │     │             │     │  video)      │
└─────────────┘     └──────────────┘     └─────────────┘     └──────────────┘
                                                                       │
                                                                       ▼
                                                                ┌──────────────┐
                                                                │  Subir       │
                                                                │  archivos    │
                                                                │  a Cloudinary│
                                                                └──────────────┘
                                                                       │
                                                                       ▼
                                                                ┌──────────────┐
                                                                │  WebSocket   │
                                                                │  (STOMP)     │
                                                                │  entregar    │
                                                                │  en tiempo   │
                                                                │  real        │
                                                                └──────────────┘
                                                                       │
                                                                       ▼
                                                                ┌──────────────┐
                                                                │  Confirmar   │
                                                                │  lectura     │
                                                                │  (leidoPor)  │
                                                                └──────────────┘
```

**Reglas:**
- Si `permiteMensajesDe = CONEXIONES` → solo conexiones activas pueden iniciar chat.
- Si `permiteMensajesDe = TODOS` → cualquier usuario autenticado.
- Bloqueo entre usuarios impide enviar/recibir mensajes.
- Mensajes se almacenan en MongoDB y se entregan via WebSocket.

**Tipos de mensaje:**

| Tipo | Contenido | Almacenamiento |
|------|-----------|----------------|
| TEXT | Solo texto | MongoDB directo |
| IMAGE | Texto + imagen | Cloudinary URL |
| AUDIO | Texto + audio | Cloudinary URL + duración + waveform |
| VIDEO | Texto + video | Cloudinary URL + thumbnail + duración |
| FILE | Texto + archivo | Cloudinary URL + nombre archivo |
| SYSTEM | Mensaje automático | MongoDB directo |

**Funcionalidades de lectura:**

| Función | Descripción |
|---------|-------------|
| `leidoPor` | Array de objetos `{ usuarioId, leidoEn }` que registra quién vio el mensaje y cuándo |
| Indicador visual | ✓ (enviado), ✓✓ (entregado), ✓✓ azul (leído) |
| Último mensaje leído | `participants.leidoHastaMensajeId` indica hasta qué mensaje leyó cada usuario |
| Notificación | Se envía notificación push cuando hay mensaje nuevo si el usuario está offline |

**Funcionalidades de reacciones:**

| Función | Descripción |
|---------|-------------|
| Tipos | LIKE, LOVE, INSIGHTFUL, FIRE, SUPPORT |
| Agregar/quitar | Cada usuario puede tener una reacción por mensaje |
| Conteo | Se pueden contar reacciones por tipo en tiempo real |

---

### 7. Preguntas técnicas

#### Flujo principal

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐
│  Crear      │────▶│  Validar     │────▶│  Publicar   │
│  pregunta   │     │  (título,    │     │  pregunta   │
│  técnica    │     │  descripción,│     │  (ABIERTA)  │
│             │     │  etiquetas,  │     │             │
│             │     │  código)     │     │             │
└─────────────┘     └──────────────┘     └─────────────┘
                                                │
                          ┌─────────────────────┘
                          ▼
                   ┌──────────────┐     ┌─────────────┐
                   │  Respuestas  │────▶│  Respuesta  │
                   │  de otros    │     │  aceptada   │
                   │  usuarios    │     │  (opcional) │
                   └──────────────┘     └─────────────┘
                                                │
                                                ▼
                                         ┌──────────────┐
                                         │  Asignar     │
                                         │  puntos al   │
                                         │  autor de la │
                                         │  respuesta   │
                                         └──────────────┘
```

#### Flujo detallado de respuestas

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐     ┌──────────────┐
│  Responder  │────▶│  Validar     │────▶│  Publicar   │────▶│  Notificar   │
│  pregunta   │     │  contenido   │     │  respuesta  │     │  al autor    │
│             │     │  (texto,     │     │  (PUBLICADA)│     │  de la       │
│             │     │  código)     │     │             │     │  pregunta    │
└─────────────┘     └──────────────┘     └─────────────┘     └──────────────┘
                                                                       │
                                                                       ▼
                                                                ┌──────────────┐
                                                                │  Votos de    │
                                                                │  utilidad    │
                                                                │  (+1 / -1)   │
                                                                └──────────────┘
                                                                       │
                                                                       ▼
                                                                ┌──────────────┐
                                                           ┌───▶│  RESPUESTA   │
                                            ┌──────────────┐│    │  ACEPTADA    │
                                            │  Autor de    │┤    │  (asignar   │
                                            │  pregunta    ││    │  puntos)     │
                                            │  acepta      ││    └──────────────┘
                                            └──────────────┘│
                                                           └───▶ No aceptar
                                                                (respuesta
                                                                sigue
                                                                publicada)
```

#### Funcionalidades de votación

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐
│  Votar      │────▶│  Validar     │────▶│  Actualizar │
│  respuesta  │     │  (no propio, │     │  contador   │
│  (+1 / -1)  │     │  no duplicado)│    │  de votos   │
└─────────────┘     └──────────────┘     └─────────────┘
                                                 │
                                                 ▼
                                          ┌──────────────┐
                                          │  Ranking de  │
                                          │  respuestas  │
                                          │  (mayor votos│
                                          │  primero)    │
                                          └──────────────┘
```

**Reglas de puntos por preguntas:**

| Acción | Puntos | Condición |
|--------|--------|-----------|
| Publicar pregunta | +5 | Si tiene título, descripción y al menos 1 etiqueta |
| Publicar respuesta | +3 | Si tiene contenido válido |
| Respuesta aceptada | +15 | Solo si el autor de la pregunta la acepta |
| Voto positivo recibido | +2 | Por cada voto +1 en respuesta |
| Voto negativo recibido | -1 | Por cada voto -1 en respuesta |

**Límites de moderación:**
- Un moderador puede ocultar respuestas con contenido inapropiado.
- Un moderador puede cerrar preguntas si generan discusiones no constructivas.
- El autor puede eliminar sus propias respuestas (si no fueron aceptadas).

---

### 8. Postulación a vacantes

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐     ┌──────────────┐
│  Ver vacante│────▶│  Verificar   │────▶│  Crear      │────▶│  Notificar   │
│  (mapa o    │     │  requisitos  │     │  postulación│     │  al          │
│  búsqueda)  │     │  (perfil     │     │  (con       │     │  reclutador  │
│             │     │  mínimo, CV) │     │  snapshot)  │     │              │
└─────────────┘     └──────────────┘     └─────────────┘     └──────────────┘
                                                                       │
                                                                       ▼
                                                                ┌──────────────┐
                                                                │  Reclutador  │
                                                                │  revisa y    │
                                                                │  cambia      │
                                                                │  estado      │
                                                                └──────────────┘
```

**Estados de postulación:**
1. ENVIADA → reclutador ve la postulación.
2. EN_REVISION → perfil está siendo evaluado.
3. PRESELECCIONADA → candidato pasó filtro inicial.
4. ENTREVISTA → programar entrevista.
5. ACEPTADA / RECHAZADA → decisión final.
6. RETIRADA → el desarrollador retira su postulación.

**Reglas:**
- Solo DEVELOPER puede postular.
- Debe tener perfil mínimo completo y CV si la vacante lo exige.
- No puede haber más de una postulación activa por `developerId` + `vacancyId`.
- `perfilSnapshot` guarda los datos al momento de postular (no cambia si el perfil se actualiza).

---

### 9. EcoDev (donación/reutilización)

#### Flujo principal

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐     ┌──────────────┐
│  Crear      │────▶│  Subir       │────▶│  Publicar   │────▶│  Otros       │
│  anuncio    │     │  fotos del   │     │  anuncio    │     │  usuarios    │
│  (donación, │     │  equipo      │     │  (ACTIVA)   │     │  interesados │
│  intercambio│     │  (Cloudinary)│     │             │     │  contactan   │
│  etc.)      │     │              │     │             │     │              │
└─────────────┘     └──────────────┘     └─────────────┘     └──────────────┘
                                                                       │
                                                                       ▼
                                                                ┌──────────────┐
                                                                │  Coordinar   │
                                                                │  entrega     │
                                                                │  (chat interno)│
                                                                └──────────────┘
                                                                       │
                                                                       ▼
                                                                ┌──────────────┐
                                                                │  Confirmar   │
                                                                │  transacción │
                                                                │  (ambas      │
                                                                │  partes)     │
                                                                └──────────────┘
                                                                       │
                                                                       ▼
                                                                ┌──────────────┐
                                                                │  Asignar     │
                                                                │  puntos +    │
                                                                │  actualizar  │
                                                                │  métricas    │
                                                                └──────────────┘
```

#### Flujo detallado de transacción

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐
│  Interesado │────▶│  Iniciar     │────▶│  Coordinar  │
│  contacta   │     │  transacción │     │  entrega    │
│  (chat)     │     │  (PENDIENTE) │     │  (fecha,    │
│             │     │              │     │  lugar)     │
└─────────────┘     └──────────────┘     └─────────────┘
                                                 │
                              ┌──────────────────┘
                              ▼
                       ┌──────────────┐     ┌─────────────┐
                       │  Emisor      │────▶│  Confirmar  │
                       │  confirma    │     │  entrega    │
                       │  entrega     │     │  (receptor) │
                       └──────────────┘     └─────────────┘
                                                 │
                              ┌──────────────────┘
                              ▼
                       ┌──────────────┐     ┌─────────────┐
                       │  Admin      │────▶│  Finalizar  │
                       │  valida     │     │  transacción│
                       │  (opcional) │     │  (COMPLETADA│
                       └──────────────┘     └─────────────┘
```

#### Tipos de anuncio EcoDev

| Tipo | Descripción | Ejemplo |
|------|-------------|---------|
| DONACION | Dar equipo sin costo | Laptop funcional usada |
| INTERCAMBIO | trueque de equipos | Mouse por cable |
| REPARACION | Equipo que necesita arreglo | Laptop con batería dañada |
| REUTILIZACION | Reusar componentes | Monitor viejo funcional |
| RECICLAJE | Disposición responsable | Celular no funcional |
| CAMPANA | Evento temporal | Jornada de recolección |

#### Condiciones de equipo

| Condición | Descripción |
|-----------|-------------|
| FUNCIONAL | Opera correctamente |
| FUNCIONAL_CON_REPARACION_MENOR | Funciona pero necesita ajuste |
| PARA_REPARAR | No funciona, pero es reparable |
| PARA_REPUESTOS | Solo sirve para componentes |
| NO_FUNCIONAL | No tiene uso, reciclaje |

#### Reglas de puntos EcoDev

| Acción | Puntos | Condición |
|--------|--------|-----------|
| Publicar anuncio válido | +10 | Con título, condición, categoría y ubicación |
| Completar donación | +25 | Transacción confirmada por ambas partes |
| Completar intercambio | +20 | trueque confirmado |
| Coordinar reciclaje | +15 | Equipo reciclado en punto verificado |

**Reglas:**
- Requiere título, condición, categoría y ubicación aproximada.
- No mostrar dirección exacta si implica domicilio personal.
- Baterías dañadas requieren advertencia de seguridad.
- Transacción COMPLETADA solo cuando ambas partes confirman o admin valida.
- Las fotos se suben a Cloudinary y se guardan como URLs.
- El chat de coordinación se realiza por la mensajería interna del sistema.
- Las métricas de impacto (equipos donados, reutilizados, etc.) se actualizan al completar transacciones.

---

### 10. Moderación, reportes y baneos

#### Flujo de reportes

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐     ┌──────────────┐
│  Usuario    │────▶│  Crear       │────▶│  Asignar a  │────▶│  Moderador   │
│  reporta    │     │  reporte     │     │  moderador  │     │  revisa      │
│  contenido  │     │  (PENDIENTE) │     │             │     │              │
└─────────────┘     └──────────────┘     └─────────────┘     └──────────────┘
                                                                       │
                                                   ┌───────────────────┼───────────────────┐
                                                   ▼                   ▼                   ▼
                                            ┌──────────────┐   ┌──────────────┐   ┌──────────────┐
                                            │  DESCARTAR   │   │  ADVERTIR    │   │  ACCIÓN      │
                                            │  (no hay     │   │  al usuario  │   │  correctiva  │
                                            │  infracción) │   │              │   │  (OCULTAR,   │
                                            └──────────────┘   └──────────────┘   │  ELIMINAR,   │
                                                                                  │  SUSPENDER,  │
                                                                                  │  BANEAR)     │
                                                                                  └──────────────┘
```

#### Flujo de baneo (ban)

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐     ┌──────────────┐
│  Moderador  │────▶│  Evaluar     │────▶│  Seleccionar│────▶│  Aplicar     │
│  decide     │     │  gravedad    │     │  tipo de    │     │  baneo       │
│  banear     │     │  (leve,      │     │  baneo      │     │              │
│             │     │  grave,      │     │             │     │              │
│             │     │  permanente) │     │             │     │              │
└─────────────┘     └──────────────┘     └─────────────┘     └──────────────┘
                                                                       │
                              ┌────────────────────────────────────────┘
                              ▼
                       ┌──────────────┐     ┌─────────────┐
                       │  Registrar   │────▶│  Notificar  │
                       │  en audit    │     │  al usuario │
                       │  _logs       │     │  (email)    │
                       └──────────────┘     └─────────────┘
```

#### Tipos de baneo

| Tipo | Duración | Acceso | Ejemplo |
|------|----------|--------|---------|
| TEMPORAL_LEVE | 24 horas | Bloqueado parcialmente | Spam menor, lenguaje inapropiado |
| TEMPORAL_GRAVE | 7 días | Bloqueado completamente | Acoso, contenido ofensivo |
| PERMANENTE | Indefinido | Bloqueado completamente | Fraude repetido, malware |
| SHADOW_BAN | Variable | No sabe que está baneado | Spam persistente |

#### Efectos del baneo

| Efecto | TEMPORAL | PERMANENTE | SHADOW_BAN |
|--------|----------|------------|------------|
| No puede publicar | ✓ | ✓ | ✓ (no lo sabe) |
| No puede comentar | ✓ | ✓ | ✓ |
| No puede enviar mensajes | ✓ | ✓ | ✓ |
| No puede postular | ✓ | ✓ | ✓ |
| No puede reaccionar | ✓ | ✓ | ✓ |
| Perfil visible | ✓ | ✗ | ✓ (pero sin interacción) |
| Contenido existente | Oculto | Eliminado | Oculto gradualmente |
| Puede crear cuenta nueva | ✓ | ✗ | ✓ |

#### Desbaneo

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐
│  Temporal   │────▶│  Expira      │────▶│  Reactivar  │
│  baneo      │     │  plazo       │     │  cuenta     │
│  (fecha_fin)│     │              │     │  automático │
└─────────────┘     └──────────────┘     └─────────────┘
```

**Reglas:**
- El baneo permanente requiere revisión manual para deshabilitar.
- Los shadow bans ocultan contenido gradualmente sin notificar al usuario.
- Cada baneo genera registro en `audit_logs` con razón y duración.
- El usuario baneado recibe notificación por email con razón y duración.
- Un moderador no puede banear a otro moderador o admin; solo ADMIN puede.

**Acciones de moderación completas:**

| Acción | Descripción |
|--------|-------------|
| ADVERTIR_USUARIO | Notificación de advertencia sin bloqueo |
| OCULTAR_CONTENIDO | El contenido no es visible para otros |
| ELIMINAR_CONTENIDO | Se elimina el contenido permanentemente |
| SUSPENDER_USUARIO | Bloqueo temporal total |
| BANEAR_USUARIO | Bloqueo temporal o permanente |
| DESBANEAR_USUARIO | Reactivar cuenta baneada |
| RECHAZAR_VACANTE | Rechazar publicación de vacante |
| DESCARTAR_REPORTE | Cerrar reporte sin acción |

---

### 11. Mapa laboral

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐
│  Consultar  │────▶│  Filtrar     │────▶│  Retornar   │
│  mapa       │     │  empresas    │     │  empresas + │
│             │     │  (vacantes   │     │  vacantes   │
│             │     │  activas,    │     │  en mapa    │
│             │     │  ubicación,  │     │  (GeoJSON)  │
│             │     │  tecnología) │     │             │
└─────────────┘     └──────────────┘     └─────────────┘
                                                   │
                                                   ▼
                                            ┌──────────────┐
                                            │  Ver detalle │
                                            │  empresa +   │
                                            │  vacantes    │
                                            └──────────────┘
```

**Reglas:**
- Solo empresas con `vacantesActivasCount > 0` aparecen en el mapa.
- Filtrar por ubicación, tecnología, modalidad, nivel de experiencia.
- Las coordenadas deben ser válidas (GeoJSON 2dsphere).

---

### 12. Social Feed — Seguimiento

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐
│  Seguir     │────▶│  Validar     │────▶│  Crear      │
│  usuario    │     │  (no propio, │     │  follow     │
│             │     │  no duplicado)│    │             │
└─────────────┘     └──────────────┘     └─────────────┘
                                                │
                                                ▼
                                         ┌──────────────┐
                                         │  Actualizar  │
                                         │  contadores  │
                                         │  seguidores/ │
                                         │  siguiendo   │
                                         └──────────────┘
```

**Reglas:**
- No puede seguirse a sí mismo.
- No puede existir follow duplicado (`followerId` + `followingId` unique).
- `isFollowing(userId)` retorna boolean para verificar relación.
- El feed prioriza posts de seguidos primero.

---

### 13. Social Feed — Publicaciones

#### Crear post

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐     ┌──────────────┐
│  Crear post │────▶│  Subir       │────▶│  Guardar    │────▶│  Retornar    │
│  (texto +   │     │  imagen a    │     │  post en    │     │  post creado │
│  imagen)    │     │  Cloudinary  │     │  MongoDB    │     │              │
└─────────────┘     └──────────────┘     └─────────────┘     └──────────────┘
```

#### Feed paginado

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐     ┌──────────────┐
│  Solicitar  │────▶│  Obtener     │────▶│  Mezclar    │────▶│  Retornar    │
│  feed       │     │  posts de    │     │  seguidos   │     │  posts +     │
│  (page,     │     │  seguidos +  │     │  primero,   │     │  paginación  │
│  size)      │     │  resto       │     │  resto      │     │              │
└─────────────┘     └──────────────┘     └─────────────┘     └──────────────┘
```

**Estructura de respuesta:**

```json
{
  "posts": [...],
  "page": 0,
  "size": 20,
  "totalElements": 100,
  "totalPages": 5,
  "last": false
}
```

**Reglas:**
- Debe tener texto o al menos una imagen.
- Las imágenes se suben a Cloudinary; MongoDB guarda URLs.
- `estadisticas` se actualiza con contadores denormalizados.
- Feed ordenado por: posts de seguidos (prioridad) → resto, paginado.

---

### 14. Social Feed — Comentarios

#### Comentarios de nivel superior

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐
│  Crear      │────▶│  Validar     │────▶│  Guardar    │
│  comentario │     │  (texto no   │     │  comentario │
│             │     │  vacío)      │     │  (postId,   │
│             │     │              │     │  autorId)   │
└─────────────┘     └──────────────┘     └─────────────┘
                                                │
                                                ▼
                                         ┌──────────────┐
                                         │  Actualizar  │
                                         │  comentarios │
                                         │  Count en    │
                                         │  post        │
                                         └──────────────┘
```

#### Respuestas (comentarios anidados)

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐
│  Responder  │────▶│  Validar     │────▶│  Guardar    │
│  comentario │     │  (parent     │     │  reply      │
│             │     │  comment     │     │  (con       │
│             │     │  existe)     │     │  parent)    │
└─────────────┘     └──────────────┘     └─────────────┘
```

**Reglas:**
- Un comentario pertenece a un `postId` (tipo: POST, REEL, etc.).
- `parentCommentId` es `null` para topLevel, `objectId` para replies.
- `commentReplies(parentCommentId)` retorna solo respuestas directas.
- Se puede editar y eliminar comentarios propios.

---

### 15. Social Feed — Reacciones

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐
│  Reaccionar │────▶│  Verificar   │────▶│  Upsert     │
│  (tipo:     │     │  si ya tiene │     │  reacción   │
│  LIKE/LOVE/ │     │  reacción    │     │  (tipo)     │
│  CELEBRATE/ │     │  previa      │     │             │
│  SUPPORT)   │     │              │     │             │
└─────────────┘     └──────────────┘     └─────────────┘
                                                │
                               ┌────────────────┘
                               ▼
                        ┌──────────────┐     ┌─────────────┐
                        │  Actualizar  │────▶│  Retornar   │
                        │  contadores  │     │  miReacción │
                        │  denorm.     │     │  actualizada│
                        │  (reacciones │     │             │
                        │  Count)      │     │             │
                        └──────────────┘     └─────────────┘
```

**Operaciones:**

| Mutation | Descripción |
|----------|-------------|
| `react` | Crea o cambia reacción (si no existe, crea; si existe, cambia tipo) |
| `unreact` | Elimina reacción del usuario |
| `changeReaction` | Cambia tipo de reacción existente |
| `myReaction` | Retorna la reacción del usuario actual para un contenido |

**Reglas:**
- Una reacción por usuario por contenido (unique: `usuarioId + contenidoId + tipoContenido`).
- `TipoReaccion`: LIKE, LOVE, CELEBRATE, SUPPORT.
- Se aplica a: POST, COMMENT, REEL.
- Contadores `reaccionesCount` se incrementan/decrementan automáticamente.

---

### 16. Stories

#### Crear story

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐     ┌──────────────┐
│  Crear      │────▶│  Subir       │────▶│  Guardar    │────▶│  Retornar    │
│  story      │     │  media a     │     │  story      │     │  story       │
│  (texto,    │     │  Cloudinary  │     │  (expiresAt │     │  creada      │
│  imagen,    │     │              │     │  =now+24h)  │     │              │
│  video)     │     │              │     │             │     │              │
└─────────────┘     └──────────────┘     └─────────────┘     └──────────────┘
```

#### Consultar stories

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐
│  Solicitar  │────▶│  Filtrar     │────▶│  Retornar   │
│  stories    │     │  seguidos +  │     │  stories    │
│             │     │  no expiradas│     │  (24h vigentes)│
│             │     │  (< now)     │     │             │
└─────────────┘     └──────────────┘     └─────────────┘
```

**Reglas:**
- Expiran automáticamente después de 24 horas (`expiresAt = createdAt + 24h`).
- Solo se muestran stories de usuarios seguidos.
- `storyViews` registra quién vio la story.
- `viewStory(storyId, usuarioId)` registra visualización.
- Las stories pueden ser de tipo: TEXT, IMAGE, VIDEO.

---

### 17. Reels

#### Crear reel

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐
│  Crear reel │────▶│  Validar     │────▶│  Guardar    │
│  (videoUrl, │     │  (videoUrl   │     │  reel en    │
│  descripción│     │  requerido)  │     │  MongoDB    │
│  etiquetas) │     │              │     │             │
└─────────────┘     └──────────────┘     └─────────────┘
                                                │
                                                ▼
                                         ┌──────────────┐
                                         │  Retornar    │
                                         │  reel creado │
                                         └──────────────┘
```

#### Consultar reels

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐
│  Solicitar  │────▶│  Obtener     │────▶│  Retornar   │
│  reels      │     │  todos los   │     │  reels[]    │
│             │     │  reels       │     │  con stats  │
│             │     │  (feed)      │     │             │
└─────────────┘     └──────────────┘     └─────────────┘
```

**Campos del reel:**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| videoUrl | String | URL del video (Cloudinary) |
| descripcion | String? | Descripción del reel |
| etiquetas | String[]? | Etiquetas/hashags |
| musicNombre | String? | Nombre de la música |
| musicUrl | String? | URL del audio |
| estadisticas | ReelEstadisticas | reaccionesCount, comentariosCount, vistasCount, compartidosCount |

**Reglas:**
- `videoUrl` es obligatorio.
- Los reels se muestran en orden cronológico (más reciente primero).
- Los comentarios de reels se almacenan como `tipoContenido = REEL`.
- Las reacciones de reels se almacenan como `tipoContenido = REEL`.
- `reelsByUser(autorId)` retorna reels de un usuario específico.
- CRUD completo: crear, editar, eliminar (solo autor).

---

<!-- FUENTE 10/12: doc\10-CAMBIOS-SESION-STORIES-MUSICA.md -->

## 10. Cambios de Sesión: Stories + Música

**Fecha:** 16 de Septiembre 2026
**Rama:** `upgrade: historia`
**Commits:** `720e215`

---

### Resumen de Cambios

Se implementó la integración completa de música en las Stories, incluyendo:
- Búsqueda de música vía iTunes API
- Letras sincronizadas vía LRCLIB API
- 4 modos de visualización (Portada, Solo Audio, Letra, Portada + Letra)
- Posicionamiento y escalado de elementos
- Auto-avance de stories (30s)
- Control de volumen

---

### Archivos Modificados/Creados

#### 1. GraphQL Schema — `stories.graphqls`

**Ruta:** `src/main/resources/graphql/modules/stories.graphqls`

##### Tipo `StoryMusica` (NUEVO)

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

##### Input `StoryMusicaInput` (NUEVO)

Mismos 13 campos que `StoryMusica` para la mutación `createStory`.

---

#### 2. Modelo de Dominio — `Story.java`

**Ruta:** `src/main/java/com/comunidev/comunidevbackend/story/domain/Story.java`

##### Clase interna `StoryMusica` (NUEVA)

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

#### 3. Servicio de Música — `MusicService.java`

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

#### 4. Servicio de Letras — `LyricsService.java`

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

#### 5. Controlador REST — `MusicaController.java`

**Ruta:** `src/main/java/com/comunidev/comunidevbackend/shared/infrastructure/rest/MusicaController.java`

**Base path:** `/api/v1/musica`

**CORS:** `localhost:4200`, `comunidev.pages.dev`, `comunidev-backend.onrender.com`

##### Endpoints

| Método | Path | Parámetros | Respuesta | Descripción |
|--------|------|------------|-----------|-------------|
| `GET` | `/search` | `?q={query}` | `200` → `List<MusicTrackResponse>` | Buscar tracks en iTunes |
| `GET` | `/lyrics` | `?artist={}&track={}` | `200` → `LyricsResponse`, `204` si no encuentra, `400` si faltan params | Obtener letras |

---

#### 6. DTOs (NUEVOS)

##### `MusicTrackResponse.java`

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

##### `LyricsResponse.java`

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

#### 7. CommentGraphQLResolver (MEJORA MENOR)

**Ruta:** `src/main/java/com/comunidev/comunidevbackend/comment/adapter/in/graphql/CommentGraphQLResolver.java`

Se agregó sincronización del contador de comentarios en el post:
- `createComment`: incrementa `comentariosCount` en +1
- `deleteComment`: decrementa `comentariosCount` en -1
- Solo aplica a comentarios de tipo `POST` sin padre (nivel superior)

---

### Stack de Tecnologías Usado

| Capa | Tecnología |
|------|------------|
| API de Música | iTunes Search API (gratuita, sin key) |
| API de Letras | LRCLIB API (gratuita, sin key) |
| HTTP Client | Java 11+ `HttpClient` |
| Serialización | Jackson `ObjectMapper` |
| Persistencia | MongoDB Atlas |
| API | GraphQL (Spring GraphQL) + REST |

---

### Modos de Visualización

| Modo | `musicMode` | Descripción |
|------|-------------|-------------|
| Portada | `cover` | Muestra portada del álbum (arrastrable, escalable) |
| Solo Audio | `audio` | Solo suena la música, sin elemento visual |
| Letra | `lyrics` | Muestra letra sincronizada con la música |
| Portada + Letra | `cover+lyrics` | Muestra ambos: portada y letra |

---

### Formato LRC (Letras Sincronizadas)

LRCLIB devuelve letras en formato LRC:
```
[00:12.34] Soy tu letra sincronizada
[00:18.56] La siguiente línea de la canción
[00:25.78] Y otra línea más
```

El frontend parsea esto y muestra la línea actual según el `currentTime` del audio.

---

### Notas para Futuras Sesiones

1. **iTunes API**: Los previews duran 30 segundos. No hay API key requerida.
2. **LRCLIB API**: No todas las canciones tienen letras sincronizadas. Se usa `plainLyrics` como fallback.
3. **MongoDB**: Los campos de `StoryMusica` se almacenan como subdocumento embebido en `contenido`.
4. **Escalado**: `lyricsScale` y `coverScale` van de 0.3 a 3.0 (30% a 300%).
5. **Posiciones**: Son porcentajes (0-100) relativos al contenedor del preview.

---

<!-- FUENTE 11/12: doc\MONGODB-ATLAS-COLLECTIONS.md -->

## COMUNIDEV — Colecciones MongoDB Atlas

Guia para crear las colecciones en MongoDB Atlas. Copia y pega los documentos JSON directamente desde la interfaz de Atlas (Browse Collection > Insert Document).

> **Base de datos:** `comunidevDB`

---

### 1. Identidad, cuentas y configuracion

#### 1.1 `users`

Cuenta central de todo usuario (desarrolladores, reclutadores, moderadores, admins). Contiene autenticacion, roles, estado de actividad, configuracion de privacidad y datos de baneo.

```json
{
  "nombre": "Carlos Ruiz",
  "nombreUsuario": "carlosdev",
  "email": "carlos@correo.com",
  "passwordHash": "$2a$10$hashdeejemplo",
  "telefono": "+51999888777",
  "fotoPerfilUrl": "https://res.cloudinary.com/demo/image/upload/v1/foto.jpg",
  "bannerUrl": "https://res.cloudinary.com/demo/image/upload/v1/banner.jpg",
  "bio": "Desarrollador orientado a aplicaciones web y moviles.",
  "sitioWeb": "https://miportafolio.pe",
  "ubicacion": {
    "pais": "Peru",
    "departamento": "Lima",
    "provincia": "Lima",
    "ciudad": "Lima",
    "distrito": "Surco",
    "direccion": "Av. Ejemplo 123"
  },
  "roles": ["DEVELOPER"],
  "rolActivo": "DEVELOPER",
  "estadoCuenta": "ACTIVA",
  "emailVerificado": true,
  "estadoActividad": {
    "estado": "ONLINE",
    "mensajePersonalizado": "Disponible para oportunidades",
    "ultimaVez": ISODate("2026-01-15T10:30:00Z")
  },
  "configuracion": {
    "tema": "SYSTEM",
    "idioma": "es",
    "notificaciones": {
      "push": true,
      "email": true,
      "mensajes": true,
      "comentarios": true,
      "reacciones": true,
      "conexiones": true,
      "vacantes": true
    },
    "privacidad": {
      "perfil": "PUBLICO",
      "experiencias": "CONEXIONES",
      "educacion": "CONEXIONES",
      "certificados": "CONEXIONES",
      "cv": "CONEXIONES",
      "proyectos": "PUBLICO",
      "tecnologias": "PUBLICO",
      "permiteMensajesDe": "CONEXIONES",
      "mostrarEmail": false,
      "mostrarUbicacion": false,
      "mostrarEstadoActividad": true
    }
  },
  "seguidoresCount": 0,
  "siguiendoCount": 0,
  "conexionesCount": 0,
  "baneo": {
    "baneado": false,
    "tipo": null,
    "razon": null,
    "fechaInicio": null,
    "fechaFin": null,
    "moderadorId": null,
    "contenidoOcultoGradualmente": false
  },
  "createdAt": ISODate("2026-01-15T10:00:00Z"),
  "updatedAt": ISODate("2026-01-15T10:00:00Z")
}
```

---

#### 1.2 `developer_profiles`

Perfil profesional detallado de un usuario con rol DEVELOPER. Contiene tecnologias, experiencias, educacion, proyectos, CV y disponibilidad laboral.

```json
{
  "userId": {"$oid": "ObjectId_del_usuario"},
  "tituloProfesional": "Desarrollador Full Stack",
  "tecnologias": [
    {"nombre": "Angular", "nivel": "AVANZADO", "aniosExperiencia": 2},
    {"nombre": "Spring Boot", "nivel": "INTERMEDIO", "aniosExperiencia": 1}
  ],
  "habilidadesBlandas": ["Trabajo en equipo", "Comunicacion"],
  "experiencias": [
    {
      "empresa": "Empresa ejemplo",
      "puesto": "Desarrollador Backend",
      "descripcion": "Desarrollo de APIs y servicios.",
      "fechaInicio": "2025-01",
      "fechaFin": null,
      "actual": true
    }
  ],
  "educacion": [
    {
      "institucion": "Universidad ejemplo",
      "grado": "Ingenieria de Sistemas",
      "fechaInicio": "2021-03",
      "fechaFin": null,
      "actual": true
    }
  ],
  "certificados": [
    {
      "titulo": "Curso de Spring Boot",
      "institucion": "Plataforma educativa",
      "fechaObtencion": "2025-06",
      "credencialUrl": "https://credenciales.ejemplo.com/abc123",
      "archivoUrl": "https://res.cloudinary.com/demo/raw/upload/v1/cert.pdf"
    }
  ],
  "proyectos": [
    {
      "titulo": "COMUNIDEV",
      "descripcion": "Red social de desarrolladores.",
      "tecnologias": ["Angular", "Spring Boot", "MongoDB"],
      "repositorioUrl": "https://github.com/usuario/comunidev",
      "demoUrl": "https://comunidev-demo.vercel.app",
      "imagenes": ["https://res.cloudinary.com/demo/image/upload/v1/proyecto.jpg"]
    }
  ],
  "cv": {
    "archivoUrl": "https://res.cloudinary.com/demo/raw/upload/v1/cv.pdf",
    "nombreArchivo": "cv-carlos-ruiz.pdf",
    "actualizadoEn": ISODate( "2026-01-15T10:00:00Z"),
    "visiblePara": "CONEXIONES"
  },
  "disponibilidadLaboral": {
    "buscandoEmpleo": true,
    "modalidades": ["REMOTO", "HIBRIDO"],
    "tiposContrato": ["TIEMPO_COMPLETO", "PRACTICAS"],
    "pretensionSalarial": {"moneda": "PEN", "minimo": 2500, "maximo": 4000}
  },
  "enlaces": {
    "github": "https://github.com/usuario",
    "linkedin": "https://linkedin.com/in/usuario",
    "portafolio": "https://miportafolio.com"
  },
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 1.3 `recruiter_profiles`

Perfil profesional de un usuario con rol RECRUITER. Identifica al representante de una o varias empresas.

```json
{
  "userId": {"$oid": "ObjectId_del_usuario"},
  "cargo": "Talent Acquisition Specialist",
  "ruc": "20123456789",
  "lema": "Conectando talento con oportunidades",
  "empresasDescripcion": "Trabajo con empresas del sector fintech y retail.",
  "fechaCreacion": "2020",
  "modalidadTrabajo": "HIBRIDO",
  "redesSociales": {
    "linkedin": "https://linkedin.com/in/analopez",
    "instagram": "https://instagram.com/analopez",
    "tiktok": "",
    "facebook": ""
  },
  "empresas": [
    {"companyId": {"$oid": "ObjectId_empresa"}, "cargoEnEmpresa": "RECRUITER", "activo": true}
  ],
  "verificado": false,
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

> **Nota:** `anioCreacion` (Int) fue migrado a `fechaCreacion` (String). `empresasDescripcion` es un campo nuevo para la descripcion en texto libre de las empresas del reclutador. El indice unico sobre `userId` evita perfiles duplicados.

---

#### 1.4 `companies`

Empresa que ofrece oportunidades laborales. Aparece en el mapa laboral cuando tiene vacantes activas.

```json
{
  "nombre": "DevSolutions Peru",
  "descripcion": "Empresa de desarrollo de software.",
  "sector": "Tecnologia",
  "sitioWeb": "https://devsolutions.pe",
  "logoUrl": "https://res.cloudinary.com/demo/image/upload/v1/logo.png",
  "bannerUrl": "https://res.cloudinary.com/demo/image/upload/v1/banner.jpg",
  "ubicacion": {
    "pais": "Peru",
    "ciudad": "Lima",
    "distrito": "San Isidro",
    "direccion": "Av. Ejemplo 123",
    "coordenadas": {
      "type": "Point",
      "coordinates": [-77.036528, -12.097107]
    }
  },
  "contacto": {
    "email": "rrhh@devsolutions.pe",
    "telefono": "+51123456789"
  },
  "redes": {
    "linkedin": "https://linkedin.com/company/devsolutions",
    "github": "https://github.com/devsolutions"
  },
  "estadoVerificacion": "PENDIENTE",
  "vacantesActivasCount": 0,
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

### 2. Seguimientos, conexiones y privacidad

#### 2.1 `connection_requests`

Solicitudes de conexion pendientes, aceptadas, rechazadas o canceladas.

```json
{
  "solicitanteId": {"$oid": "ObjectId_usuario1"},
  "receptorId": {"$oid": "ObjectId_usuario2"},
  "mensaje": "Hola, me gustaria conectar contigo.",
  "estado": "PENDIENTE",
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "respondidoEn": null,
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 2.2 `connections`

Relacion profesional aceptada entre dos usuarios. La conexion es simetrica.

```json
{
  "usuarioMenorId": {"$oid": "ObjectId_usuario1"},
  "usuarioMayorId": {"$oid": "ObjectId_usuario2"},
  "iniciadorId": {"$oid": "ObjectId_usuario1"},
  "estado": "ACTIVA",
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

**Nota:** Los IDs se guardan ordenados para evitar duplicados (`usuarioMenorId < usuarioMayorId`).

---

#### 2.3 `follows`

Seguimiento unilateral de usuarios, empresas o etiquetas.

```json
{
  "seguidorId": {"$oid": "ObjectId_usuario"},
  "tipoSeguido": "USER",
  "seguidoId": {"$oid": "ObjectId_objetivo"},
  "createdAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 2.4 `blocked_users`

Bloqueos entre usuarios para proteger la experiencia.

```json
{
  "bloqueadorId": {"$oid": "ObjectId_usuario1"},
  "bloqueadoId": {"$oid": "ObjectId_usuario2"},
  "motivo": "ACOSO",
  "createdAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

### 3. Contenido social

#### 3.1 `posts`

Publicaciones permanentes del feed: textos, imagenes, archivos, codigo, videos y enlaces.

```json
{
  "autorId": {"$oid": "ObjectId_usuario"},
  "tipo": "POST",
  "contenido": {
    "texto": "Comparto una guia para implementar JWT.",
    "imagenes": ["https://res.cloudinary.com/demo/image/upload/v1/img.jpg"],
    "videos": [],
    "archivos": [
      {"nombre": "guia-jwt.pdf", "url": "https://res.cloudinary.com/demo/raw/upload/v1/guia.pdf", "mimeType": "application/pdf"}
    ],
    "bloquesCodigo": [
      {"lenguaje": "java", "codigo": "public class JwtFilter implements Filter { ... }"}
    ],
    "enlaces": ["https://spring.io/guides/gs/securing-web"]
  },
  "etiquetas": ["SpringBoot", "JWT", "Backend"],
  "categoria": "TECNOLOGIA",
  "visibilidad": "PUBLICO",
  "estadoModeracion": "VISIBLE",
  "estadisticas": {
    "vistas": 0,
    "reaccionesCount": 0,
    "comentariosCount": 0,
    "compartidosCount": 0,
    "guardadosCount": 0
  },
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 3.2 `stories`

Contenido efimero, visible por 24 horas.

```json
{
  "autorId": {"$oid": "ObjectId_usuario"},
  "contenido": {
    "texto": "Terminando una nueva funcionalidad de COMUNIDEV.",
    "imagenUrl": "https://res.cloudinary.com/demo/image/upload/v1/story.jpg",
    "videoUrl": null,
    "musica": {
      "trackId": "itunes-track-id-123",
      "trackName": "Nombre de cancion",
      "artistName": "Artista",
      "coverUrl": "https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/...",
      "previewUrl": "https://audio-ssl.itunes.apple.com/.../preview.m4a",
      "musicMode": "cover",
      "lyricsText": null,
      "lyricsPosX": 50.0,
      "lyricsPosY": 70.0,
      "coverPosX": 50.0,
      "coverPosY": 40.0,
      "lyricsScale": 1.0,
      "coverScale": 1.0
    }
  },
  "visibilidad": "SEGUIDORES",
  "fechaExpiracion": ISODate( "2026-01-16T10:00:00Z"),
  "vistasCount": 0,
  "reaccionesCount": 0,
  "estado": "ACTIVA",
  "createdAt": ISODate( "2026-01-15T10:00:00Z")
}
```

> **Tip:** Crear indice TTL sobre `fechaExpiracion` para limpiar automaticamente las historias expiradas.

---

#### 3.3 `story_views`

Registra que usuario visualizo una historia.

```json
{
  "storyId": {"$oid": "ObjectId_historia"},
  "viewerId": {"$oid": "ObjectId_usuario"},
  "viewedAt": ISODate( "2026-01-15T11:00:00Z")
}
```

---

#### 3.4 `reels`

Videos cortos permanentes.

```json
{
  "autorId": {"$oid": "ObjectId_usuario"},
  "videoUrl": "https://res.cloudinary.com/demo/video/upload/v1/reel.mp4",
  "portadaUrl": "https://res.cloudinary.com/demo/image/upload/v1/portada.jpg",
  "descripcion": "Tip rapido de Spring Boot.",
  "etiquetas": ["SpringBoot", "Java", "Tips"],
  "musica": {
    "titulo": "Nombre de cancion",
    "artista": "Artista",
    "proveedor": "SPOTIFY",
    "referenciaExterna": "track-id-456"
  },
  "duracionSegundos": 30,
  "visibilidad": "PUBLICO",
  "estadoModeracion": "VISIBLE",
  "estadisticas": {
    "vistas": 0,
    "reaccionesCount": 0,
    "comentariosCount": 0,
    "compartidosCount": 0,
    "guardadosCount": 0
  },
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 3.5 `comments`

Comentarios sobre publicaciones, reels, historias o anuncios EcoDev.

```json
{
  "autorId": {"$oid": "ObjectId_usuario"},
  "contenidoId": {"$oid": "ObjectId_contenido"},
  "tipoContenido": "POST",
  "parentCommentId": null,
  "texto": "Muy buen aporte, gracias.",
  "imagenes": [],
  "estadoModeracion": "VISIBLE",
  "reaccionesCount": 0,
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 3.6 `reactions`

Reaccion individual de un usuario sobre un contenido o comentario.

```json
{
  "usuarioId": {"$oid": "ObjectId_usuario"},
  "objetivoId": {"$oid": "ObjectId_contenido"},
  "tipoObjetivo": "POST",
  "tipoReaccion": "LIKE",
  "createdAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 3.7 `saved_contents`

Permite que un usuario guarde publicaciones, reels, preguntas o vacantes para revisarlas despues.

```json
{
  "usuarioId": {"$oid": "ObjectId_usuario"},
  "contenidoId": {"$oid": "ObjectId_contenido"},
  "tipoContenido": "POST",
  "createdAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 3.8 `hashtag`

Catalogo de etiquetas para normalizar busquedas, mostrar tendencias y permitir seguimiento.

```json
{
  "nombre": "springboot",
  "nombreVisible": "SpringBoot",
  "tipo": "TECNOLOGIA",
  "usoCount": 0,
  "activo": true,
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

### 4. Asistencia tecnica

#### 4.1 `technical_questions`

Consulta tecnica estructurada con titulo, descripcion, categorias y etiquetas.

```json
{
  "autorId": {"$oid": "ObjectId_usuario"},
  "titulo": "Como proteger rutas en Angular con JWT?",
  "descripcion": "Tengo una API en Spring Boot y deseo proteger rutas del frontend Angular...",
  "categoria": "FRONTEND",
  "etiquetas": ["Angular", "JWT", "AuthGuard"],
  "bloquesCodigo": [
    {"lenguaje": "typescript", "codigo": "canActivate() { ... }"}
  ],
  "archivos": [],
  "nivelDificultad": "INTERMEDIO",
  "estado": "ABIERTA",
  "respuestaAceptadaId": null,
  "votosCount": 0,
  "respuestasCount": 0,
  "vistasCount": 0,
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 4.2 `technical_answers`

Respuestas que otros miembros dan a una pregunta tecnica.

```json
{
  "preguntaId": {"$oid": "ObjectId_pregunta"},
  "autorId": {"$oid": "ObjectId_usuario"},
  "contenido": "Puedes usar un guard y un interceptor para el token.",
  "bloquesCodigo": [
    {"lenguaje": "typescript", "codigo": "export const authGuard: CanActivateFn = (route, state) => { ... }"}
  ],
  "estado": "PUBLICADA",
  "esAceptada": false,
  "votosCount": 0,
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 4.3 `question_votes`

Votos de utilidad para preguntas o respuestas tecnicas.

```json
{
  "usuarioId": {"$oid": "ObjectId_usuario"},
  "objetivoId": {"$oid": "ObjectId_pregunta_o_respuesta"},
  "tipoObjetivo": "QUESTION",
  "valor": 1,
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

### 5. Mensajeria, actividad y notificaciones

#### 5.1 `conversations`

Conversacion privada directa o grupal.

```json
{
  "tipo": "DIRECT",
  "participantes": [
    {"userId": {"$oid": "ObjectId_usuario1"}, "leidoHastaMensajeId": null, "ultimoVistoEn": ISODate( "2026-01-15T10:00:00Z")},
    {"userId": {"$oid": "ObjectId_usuario2"}, "leidoHastaMensajeId": null, "ultimoVistoEn": ISODate( "2026-01-15T10:00:00Z")}
  ],
  "ultimoMensaje": {
    "mensajeId": {"$oid": "ObjectId_mensaje"},
    "textoPreview": "Hola, vi tu proyecto.",
    "remitenteId": {"$oid": "ObjectId_usuario1"},
    "fecha": ISODate( "2026-01-15T10:00:00Z")
  },
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 5.2 `messages`

Mensajes individuales de una conversacion. Soporta texto, imagenes, archivos, audio y video.

```json
{
  "conversationId": {"$oid": "ObjectId_conversacion"},
  "remitenteId": {"$oid": "ObjectId_usuario"},
  "contenido": {
    "texto": "Hola, vi tu perfil y me interesa conversar.",
    "imagenes": [],
    "archivos": [],
    "enlaces": [],
    "audio": {
      "url": "https://res.cloudinary.com/demo/video/upload/v1/audio.mp3",
      "duracionSegundos": 12,
      "waveform": [0.1, 0.3, 0.5, 0.8, 0.6, 0.2]
    },
    "video": {
      "url": "https://res.cloudinary.com/demo/video/upload/v1/video.mp4",
      "thumbnailUrl": "https://res.cloudinary.com/demo/image/upload/v1/thumb.jpg",
      "duracionSegundos": 45
    }
  },
  "tipo": "TEXT",
  "estado": "ENVIADO",
  "eliminadoPara": [],
  "reacciones": [
    {"usuarioId": {"$oid": "ObjectId_usuario"}, "tipo": "LIKE", "createdAt": ISODate( "2026-01-15T10:00:00Z")}
  ],
  "leidoPor": [
    {"usuarioId": {"$oid": "ObjectId_usuario"}, "leidoEn": ISODate( "2026-01-15T10:00:00Z")}
  ],
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 5.3 `activity_status_history`

Historial de cambios del estado de actividad (opcional para MVP).

```json
{
  "userId": {"$oid": "ObjectId_usuario"},
  "estado": "ONLINE",
  "mensajePersonalizado": "Disponible",
  "desde": ISODate( "2026-01-15T10:00:00Z"),
  "hasta": null
}
```

---

#### 5.4 `notifications`

Notificaciones persistentes para el usuario.

```json
{
  "destinatarioId": {"$oid": "ObjectId_usuario"},
  "actorId": {"$oid": "ObjectId_actor"},
  "tipo": "NUEVA_RESPUESTA",
  "titulo": "Nueva respuesta a tu pregunta",
  "mensaje": "Ana respondio tu consulta sobre JWT.",
  "referencia": {"tipo": "TECHNICAL_QUESTION", "id": {"$oid": "ObjectId_pregunta"}},
  "leida": false,
  "createdAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

### 6. Empleo, empresas y mapa laboral

#### 6.1 `vacancies`

Oferta laboral publicada por una empresa.

```json
{
  "companyId": {"$oid": "ObjectId_empresa"},
  "recruiterId": {"$oid": "ObjectId_reclutador"},
  "titulo": "Desarrollador Full Stack",
  "descripcion": "Buscamos desarrollador con experiencia en Angular y Spring Boot.",
  "responsabilidades": ["Desarrollar APIs REST", "Participar en revisiones de codigo"],
  "requisitos": ["Conocimiento de Java", "Conocimiento de Angular"],
  "tecnologias": ["Angular", "Java", "Spring Boot", "MongoDB"],
  "nivelExperiencia": "JUNIOR",
  "modalidad": "HIBRIDO",
  "tipoContrato": "TIEMPO_COMPLETO",
  "ubicacion": {
    "pais": "Peru",
    "ciudad": "Lima",
    "distrito": "San Isidro",
    "coordenadas": {
      "type": "Point",
      "coordinates": [-77.036528, -12.097107]
    }
  },
  "remuneracion": {
    "visible": true,
    "moneda": "PEN",
    "minimo": 3000,
    "maximo": 4500,
    "periodo": "MENSUAL"
  },
  "vacantesDisponibles": 2,
  "estado": "ACTIVA",
  "fechaLimite": ISODate( "2026-02-15T23:59:59Z"),
  "destacada": false,
  "postulacionesCount": 0,
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 6.2 `applications`

Postulacion de un desarrollador a una vacante laboral.

```json
{
  "vacancyId": {"$oid": "ObjectId_vacante"},
  "developerId": {"$oid": "ObjectId_desarrollador"},
  "perfilSnapshot": {
    "tituloProfesional": "Desarrollador Full Stack",
    "tecnologias": ["Angular", "Spring Boot", "MongoDB"],
    "cvUrl": "https://res.cloudinary.com/demo/raw/upload/v1/cv.pdf",
    "proyectos": [
      {"titulo": "Proyecto ejemplo", "repositorioUrl": "https://github.com/usuario/proyecto"}
    ]
  },
  "cartaPresentacion": "Estoy interesado en participar en el proceso.",
  "estado": "ENVIADA",
  "reclutadorAsignadoId": {"$oid": "ObjectId_reclutador"},
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 6.3 `application_status_history`

Historial auditable de cambios de estado en una postulacion.

```json
{
  "applicationId": {"$oid": "ObjectId_postulacion"},
  "estadoAnterior": "ENVIADA",
  "estadoNuevo": "EN_REVISION",
  "comentario": "Perfil en revision inicial.",
  "cambiadoPorId": {"$oid": "ObjectId_reclutador"},
  "createdAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

### 7. EcoDev y ODS 12

#### 7.1 `ecotech_posts`

Publicaciones sobre donacion, intercambio, reparacion, reutilizacion, reciclaje o sostenibilidad tecnologica.

```json
{
  "autorId": {"$oid": "ObjectId_usuario"},
  "tipo": "DONACION",
  "titulo": "Laptop funcional para donacion",
  "descripcion": "Equipo funcional, requiere cambio de bateria.",
  "categoriaEquipo": "LAPTOP",
  "condicion": "FUNCIONAL_CON_REPARACION_MENOR",
  "imagenes": ["https://res.cloudinary.com/demo/image/upload/v1/laptop.jpg"],
  "ubicacionAproximada": {
    "ciudad": "Lima",
    "distrito": "Cercado de Lima",
    "coordenadas": {
      "type": "Point",
      "coordinates": [-77.03, -12.04]
    }
  },
  "etiquetas": ["Donacion", "Reutilizacion", "ODS12"],
  "estado": "ACTIVA",
  "interesadosCount": 0,
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 7.2 `recycling_points`

Puntos de acopio, reciclaje electronico o campanas temporales.

```json
{
  "nombre": "Punto de reciclaje tecnologico",
  "tipo": "PUNTO_PERMANENTE",
  "organizacion": "Municipalidad de Lima",
  "descripcion": "Recepcion de equipos electronicos y perifericos.",
  "ubicacion": {
    "pais": "Peru",
    "ciudad": "Lima",
    "distrito": "Miraflores",
    "direccion": "Av. Larco 123",
    "coordenadas": {
      "type": "Point",
      "coordinates": [-77.02, -12.12]
    }
  },
  "materialesAceptados": ["LAPTOP", "CELULAR", "CABLE", "PERIFERICO"],
  "horario": "Lunes a viernes de 9:00 a 17:00",
  "contacto": {
    "telefono": "+51123456789",
    "email": "reciclaje@munimiraflores.gob.pe"
  },
  "estadoVerificacion": "PENDIENTE",
  "createdById": {"$oid": "ObjectId_usuario"},
  "validadoPorId": null,
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 7.3 `ecotech_transactions`

Resultado de una operacion EcoDev (donacion, intercambio, etc.).

```json
{
  "ecotechPostId": {"$oid": "ObjectId_publicacion"},
  "emisorId": {"$oid": "ObjectId_usuario_emisor"},
  "receptorId": {"$oid": "ObjectId_usuario_receptor"},
  "tipo": "DONACION",
  "estado": "PENDIENTE_CONFIRMACION",
  "cantidadEquipos": 1,
  "confirmadoPorEmisor": false,
  "confirmadoPorReceptor": false,
  "validadoPorAdminId": null,
  "fechaCompletado": null,
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 7.4 `company_sustainability_practices`

Practicas sostenibles registradas por empresas relacionadas con ODS 12.

```json
{
  "companyId": {"$oid": "ObjectId_empresa"},
  "tipo": "RECICLAJE_E_WASTE",
  "descripcion": "La empresa cuenta con programa interno de recicla je electronico.",
  "evidenciaUrl": "https://res.cloudinary.com/demo/raw/upload/v1/evidencia.pdf",
  "estadoVerificacion": "PENDIENTE",
  "validadoPorId": null,
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

### 8. Puntos, niveles, insignias y planes

> **NOTA:** Esta sección NO está implementada en el MVP. Se预留 para una futura versión con gamificación y planes premium.

#### 8.1 `user_points`

Saldo actual de puntos y nivel de un usuario para consultas rapidas.

```json
{
  "userId": {"$oid": "ObjectId_usuario"},
  "puntosTotales": 320,
  "puntosPorCategoria": {
    "COMUNIDAD": 100,
    "ASISTENCIA_TECNICA": 150,
    "ECO_DEV": 40,
    "EMPLEABILIDAD": 30
  },
  "nivel": "GOLD",
  "progresoNivel": 0.2,
  "ultimaActualizacion": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 8.2 `point_transactions`

Historial de movimientos de puntos.

```json
{
  "userId": {"$oid": "ObjectId_usuario"},
  "tipoAccion": "RESPUESTA_ACEPTADA",
  "puntos": 15,
  "descripcion": "Tu respuesta fue aceptada como solucion.",
  "referencia": {"tipo": "TECHNICAL_ANSWER", "id": {"$oid": "ObjectId_respuesta"}},
  "createdAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 8.3 `badges`

Catalogo de insignias disponibles en COMUNIDEV.

```json
{
  "codigo": "MENTOR_COMUNIDAD",
  "nombre": "Mentor de la comunidad",
  "descripcion": "Obtenida al alcanzar 10 respuestas aceptadas.",
  "iconoUrl": "https://res.cloudinary.com/demo/image/upload/v1/mentor.png",
  "criterio": {"tipo": "RESPUESTAS_ACEPTADAS", "valorMinimo": 10},
  "activo": true,
  "createdAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 8.4 `user_badges`

Relacion entre un usuario y las insignias que consiguio.

```json
{
  "userId": {"$oid": "ObjectId_usuario"},
  "badgeId": {"$oid": "ObjectId_insignia"},
  "obtenidoEn": ISODate( "2026-01-15T10:00:00Z"),
  "referencia": {"tipo": "RULE", "id": "RESPUESTAS_ACEPTADAS_10"}
}
```

---

#### 8.5 `plans`

Catalogo de planes de uso/membresia.

```json
{
  "codigo": "DEVELOPER_FREE",
  "nombre": "Developer Free",
  "tipoTitular": "USER",
  "precio": {"monto": 0.0, "moneda": "USD", "periodo": "MENSUAL"},
  "beneficios": [
    "Perfil publico basico",
    "Hasta 5 postulaciones al mes",
    "Publicaciones ilimitadas"
  ],
  "limites": {
    "publicacionesPorDia": 3,
    "solicitudesConexionPorDia": 10,
    "vacantesActivas": null,
    "vacantesDestacadas": 0,
    "mensajesNuevosPorDia": 20,
    "postulacionesPorMes": 5
  },
  "activo": true,
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

**Ejemplos de codigos:** DEVELOPER_FREE, DEVELOPER_PRO, RECRUITER_FREE, RECRUITER_PRO, COMPANY_BASIC, COMPANY_PRO

---

#### 8.6 `subscriptions`

Plan actualmente contratado o asignado a un usuario o empresa.

```json
{
  "titularId": {"$oid": "ObjectId_usuario_o_empresa"},
  "tipoTitular": "USER",
  "planId": {"$oid": "ObjectId_plan"},
  "estado": "ACTIVA",
  "fechaInicio": ISODate( "2026-01-15T10:00:00Z"),
  "fechaFin": ISODate( "2026-02-15T10:00:00Z"),
  "renovacionAutomatica": false,
  "metodoPagoReferencia": "tok_visa_1234",
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

### 9. Soporte, moderacion y auditoria

#### 9.1 `reports`

Denuncias sobre publicaciones, comentarios, reels, historias, usuarios, vacantes u otros recursos.

```json
{
  "reportanteId": {"$oid": "ObjectId_usuario_reporta"},
  "objetivoId": {"$oid": "ObjectId_objetivo"},
  "tipoObjetivo": "POST",
  "motivo": "SPAM",
  "descripcion": "Contenido repetitivo y no relacionado con la comunidad.",
  "estado": "PENDIENTE",
  "asignadoAId": null,
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 9.2 `moderation_actions`

Decisiones aplicadas por moderadores o administradores.

```json
{
  "moderadorId": {"$oid": "ObjectId_moderador"},
  "reportId": {"$oid": "ObjectId_reporte"},
  "objetivoId": {"$oid": "ObjectId_objetivo"},
  "tipoObjetivo": "POST",
  "accion": "OCULTAR_CONTENIDO",
  "motivo": "SPAM confirmado",
  "createdAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 9.3 `support_tickets`

Solicitudes de ayuda o incidencias enviadas por usuarios al equipo de soporte.

```json
{
  "creadoPorId": {"$oid": "ObjectId_usuario"},
  "asunto": "No puedo adjuntar mi CV",
  "descripcion": "El sistema muestra un error al intentar subir el archivo.",
  "categoria": "CUENTA",
  "prioridad": "MEDIA",
  "estado": "ABIERTO",
  "asignadoAId": {"$oid": "ObjectId_soporte"},
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 9.4 `audit_logs`

Eventos sensibles del sistema para trazabilidad.

```json
{
  "actorId": {"$oid": "ObjectId_actor"},
  "accion": "ROLE_UPDATED",
  "entidadTipo": "USER",
  "entidadId": {"$oid": "ObjectId_usuario"},
  "datosAntes": {"roles": ["DEVELOPER"]},
  "datosDespues": {"roles": ["DEVELOPER", "MODERATOR"]},
  "createdAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 9.5 `user_bans`

Historial de baneos de usuarios para auditoria y gestion de desbaneos.

```json
{
  "userId": {"$oid": "ObjectId_usuario"},
  "tipo": "TEMPORAL_GRAVE",
  "razon": "Acoso a otros usuarios en mensajeria privada",
  "evidencia": [
    {"tipo": "MENSAJE", "id": {"$oid": "ObjectId_mensaje"}, "preview": "Mensaje ofensivo contenido en el mensaje..."}
  ],
  "moderadorId": {"$oid": "ObjectId_moderador"},
  "fechaInicio": ISODate( "2026-01-15T10:00:00Z"),
  "fechaFin": ISODate( "2026-01-22T10:00:00Z"),
  "duracionDias": 7,
  "baneosPrevios": 1,
  "desbaneado": false,
  "fechaDesbaneo": null,
  "desbaneadoPorId": null,
  "motivoDesbaneo": null,
  "createdAt": ISODate( "2026-01-15T10:00:00Z")
}
```

> **Tip:** Crear indice TTL sobre `fechaFin` para limpiar automaticamente baneos expirados.

---

### 10. Busqueda de talento (ya implementadas)

#### 10.1 `saved_searches`

Filtros de busqueda guardados por un reclutador.

```json
{
  "recruiterId": {"$oid": "ObjectId_reclutador"},
  "name": "Desarrolladores Angular Senior en Lima",
  "technologies": ["Angular", "TypeScript"],
  "experienceLevel": "SENIOR",
  "locationCountry": "Peru",
  "locationCity": "Lima",
  "remoteAvailable": true,
  "availability": "INMEDIATA",
  "previousExperience": ["Banca", "Fintech"],
  "createdAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

#### 10.2 `saved_profiles`

Perfiles de desarrolladores guardados por un reclutador.

```json
{
  "recruiterId": {"$oid": "ObjectId_reclutador"},
  "developerId": {"$oid": "ObjectId_desarrollador"},
  "notes": "Buen perfil, experiencia en Angular y Spring Boot.",
  "savedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

### 11. Test (ya implementada)

#### 11.1 `test`

Entidad de prueba para verificar la conexion a MongoDB.

```json
{
  "nombre": "Test Document"
}
```

---

### 12. Modulos ya implementados (colecciones creadas por el backend)

Estas colecciones son creadas y gestionadas automaticamente por Spring Data MongoDB al ejecutar el backend. No es necesario crearlas manualmente en Atlas:

| Coleccion | Modulo Java | Operaciones GraphQL disponibles |
|-----------|-------------|--------------------------------|
| `follows` | `follow` | `follow`, `unfollow`, `followers`, `following`, `isFollowing` |
| `posts` | `post` | `posts`, `post`, `feed` (paginado, con prioridad a personas seguidas), `createPost`, `updatePost`, `deletePost` |
| `comments` | `comment` | `comments`, `commentReplies`, `comment`, `createComment`, `updateComment`, `deleteComment` |
| `reactions` | `reaction` | `reactions`, `myReaction`, `react`, `unreact`, `changeReaction` |
| `stories` | `story` | `stories` (activas de seguidos, 24h), `story`, `createStory`, `deleteStory` |
| REST `/api/v1/musica` | `shared` | `GET /search?q=...` (iTunes), `GET /lyrics?artist=...&track=...` (LRCLIB) |
| `story_views` | `story` | `storyViews`, `viewStory` |
| `recruiter_profiles` | `recruiter_profile` | `recruiterProfile`, `createRecruiterProfile` (upsert), `updateRecruiterProfile` |
| `saved_searches` | `talent_search` | `savedSearches`, `saveSearch`, `deleteSavedSearch` |
| `saved_profiles` | `talent_search` | `savedProfiles`, `saveDeveloperProfile`, `unsaveDeveloperProfile`, `isProfileSaved` |

> **Nota sobre el feed:** `feed(userId, page, size)` devuelve primero las publicaciones de usuarios que el `userId` sigue y luego el resto, ordenadas por fecha descendente.

> **Nota sobre reacciones:** `tipoReaccion` usa el enum GraphQL `TipoReaccion` con valores LIKE, LOVE, CELEBRATE, SUPPORT. Crear o eliminar una reaccion actualiza automaticamente el contador desnormalizado del post o comentario objetivo.

---

### Indices recomendados en MongoDB Atlas

Despues de crear las colecciones, configurar los siguientes indices desde la interfaz de Atlas:

#### `users`
- Único: `email`
- Único: `nombreUsuario`
- Indice: `roles`
- Indice: `estadoCuenta`

#### `developer_profiles`
- Único: `userId`
- Indice multikey: `tecnologias.nombre`
- Indice geoespacial: `ubicacion.coordenadas` (2dsphere)

#### `recruiter_profiles`
- Único: `userId` (ya aplicado con `@Indexed(unique = true)` en el dominio)

#### `companies`
- Indice de texto: `nombre`, `descripcion`, `sector`
- Indice geoespacial: `ubicacion.coordenadas` (2dsphere)
- Indice: `estadoVerificacion`

#### `connection_requests`
- Único parcial: `solicitanteId`, `receptorId`, `estado`
- Indice: `receptorId`, `estado`

#### `connections`
- Único: `usuarioMenorId`, `usuarioMayorId`

#### `follows`
- Único: `seguidorId`, `tipoSeguido`, `seguidoId`

#### `posts`
- Indice: `autorId`, `createdAt`
- Indice: `visibilidad`, `estadoModeracion`, `createdAt`
- Indice multikey: `etiquetas`
- Indice de texto: `contenido.texto`, `etiquetas`

#### `stories`
- Indice TTL: `fechaExpiracion`

#### `comments`
- Indice: `contenidoId`, `tipoContenido`, `createdAt`
- Indice: `parentCommentId`

#### `reactions`
- Único: `usuarioId`, `objetivoId`, `tipoObjetivo`

#### `messages`
- Indice: `conversationId`, `createdAt`
- Indice: `remitenteId`

#### `vacancies`
- Indice: `companyId`, `estado`
- Indice multikey: `tecnologias`
- Indice: `modalidad`, `nivelExperiencia`, `estado`
- Indice geoespacial: `ubicacion.coordenadas` (2dsphere)
- Indice: `fechaLimite`

#### `applications`
- Único: `vacancyId`, `developerId`
- Indice: `developerId`, `estado`

#### `recycling_points`
- Indice geoespacial: `ubicacion.coordenadas` (2dsphere)

#### `user_bans`
- Indice: `userId`, `createdAt`
- Indice TTL: `fechaFin`

---

<!-- FUENTE 12/12: HELP.md -->

## Read Me First
The following was discovered as part of building this project:

* The original package name 'com.comunidev.comunidev-backend' is invalid and this project uses 'com.comunidev.comunidev_backend' instead.

## Getting Started

#### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/4.0.5/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/4.0.5/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/4.0.5/reference/web/servlet.html)
* [MongoDB](https://docs.spring.io/spring-boot/4.0.5/reference/data/nosql.html#data.nosql.mongodb)
* [Spring for GraphQL](https://docs.spring.io/spring-boot/4.0.5/reference/web/spring-graphql.html)
* [WebSocket](https://docs.spring.io/spring-boot/4.0.5/reference/messaging/websockets.html)
* [Validation](https://docs.spring.io/spring-boot/4.0.5/reference/io/validation.html)

#### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Building a GraphQL service](https://spring.io/guides/gs/graphql-server/)
* [Using WebSocket to build an interactive web application](https://spring.io/guides/gs/messaging-stomp-websocket/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)

#### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

