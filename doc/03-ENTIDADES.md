# COMUNIDEV — Entidades de Negocio

## 1. Identidad, cuentas y configuración

### 1.1 users

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
  "fotoPerfilUrl": "https://...",
  "bannerUrl": "https://...",
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

### 1.2 developer_profiles

**Propósito:** almacena el perfil profesional detallado de un usuario con rol DEVELOPER. Se mantiene separado de users porque contiene información extensa, laboral y configurable por privacidad.

**Relación:** un user con rol DEVELOPER tiene como máximo un `developer_profile`.

Campos recomendados:

```json
{
  "_id": "ObjectId",
  "userId": "ObjectId",
  "tituloProfesional": "Desarrollador Full Stack",
  "bannerUrl": "https://...",
  "bio": "Desarrollador orientado a aplicaciones web y móviles.",
  "ubicacion": {
    "pais": "Perú",
    "ciudad": "Lima",
    "distrito": "Surco",
    "coordenadas": { "type": "Point", "coordinates": [-76.99, -12.12] }
  },
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

### 1.3 recruiter_profiles

**Propósito:** contiene información profesional de un usuario con rol RECRUITER; permite identificar al representante de una o varias empresas.

```json
{
  "_id": "ObjectId",
  "userId": "ObjectId",
  "nombres": "Ana",
  "apellidos": "López",
  "cargo": "Talent Acquisition Specialist",
  "bannerUrl": "https://...",
  "telefono": "+51XXXXXXXXX",
  "linkedinUrl": "https://linkedin.com/in/...",
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

---

### 1.4 companies

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

## 2. Seguimientos, conexiones y privacidad

COMUNIDEV usa dos relaciones sociales distintas:

- **Seguimiento (follow):** relación unilateral. A puede seguir a B para recibir contenido de B; B no necesita aceptar. Es útil para construir el feed.
- **Conexión (connection):** relación profesional con solicitud y aceptación. A envía solicitud a B; B la acepta o rechaza. Una conexión es simétrica y puede habilitar vista de información restringida, mensajería y contactos profesionales.

Esta separación es importante porque una persona puede querer permitir que otros vean sus publicaciones públicas sin que todos tengan acceso a su CV, certificados o mensajería privada.

### 2.1 connection_requests

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

### 2.2 connections

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

### 2.3 follows

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

### 2.4 blocked_users

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

## 3. Contenido social

### 3.1 posts

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

### 3.2 stories

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
      "titulo": "Nombre de canción",
      "artista": "Artista",
      "proveedor": "PROVEEDOR_AUTORIZADO",
      "referenciaExterna": "id-o-url",
      "inicioSegundos": 0,
      "duracionSegundos": 15
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

### 3.3 story_views

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

### 3.4 reels

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

### 3.5 comments

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

### 3.6 reactions

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

**Valores de tipoReaccion:** LIKE, LOVE, INSIGHTFUL, CELEBRATE, FIRE, SUPPORT.

**Reglas principales:**

- Un usuario solo puede tener una reacción activa por objetivo; puede cambiarla, pero no duplicarla.
- Crear o eliminar una reacción debe actualizar el contador desnormalizado del contenido objetivo.
- El usuario no debe reaccionar sobre contenido que no tiene permiso de visualizar.

**Índice sugerido:** único compuesto `usuarioId` + `objetivoId` + `tipoObjetivo`.

---

### 3.7 saved_contents

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

### 3.8 hashtags

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

## 4. Asistencia técnica

### 4.1 technical_questions

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

### 4.2 technical_answers

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

### 4.3 question_votes

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

## 5. Mensajería, actividad y notificaciones

### 5.1 conversations

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

### 5.2 messages

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

### 5.3 activity_status_history

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

### 5.4 notifications

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

## 6. Empleo, empresas y mapa laboral

### 6.1 vacancies

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

### 6.2 applications

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

### 6.3 application_status_history

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

## 7. EcoDev y ODS 12

El módulo EcoDev contribuye al ODS 12 al promover consumo responsable de tecnología, Green IT, reparación, reutilización, donación y reciclaje electrónico.

### 7.1 ecotech_posts

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

### 7.2 recycling_points

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

### 7.3 ecotech_transactions

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

### 7.4 company_sustainability_practices

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

## 8. Puntos, niveles, insignias y planes

> **NOTA:** Esta sección NO está implementada en el MVP. Se预留 para una futura versión con gamificación y planes premium.

Se deben separar dos conceptos:

- **Puntos/reputación:** gamificación por aportes positivos a la comunidad.
- **Planes:** límites y beneficios comerciales o funcionales para desarrolladores, reclutadores o empresas.

### 8.1 user_points

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

### 8.2 point_transactions

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

### 8.3 badges

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

### 8.4 user_badges

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

### 8.5 plans

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

### 8.6 subscriptions

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

## 9. Soporte, moderación y auditoría

### 9.1 reports

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

### 9.2 moderation_actions

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

### 9.3 support_tickets

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

### 9.4 audit_logs

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

### 9.5 user_bans

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
