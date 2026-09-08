# COMUNIDEV — Colecciones MongoDB Atlas

Guia para crear las colecciones en MongoDB Atlas. Copia y pega los documentos JSON directamente desde la interfaz de Atlas (Browse Collection > Insert Document).

> **Base de datos:** `comunidevDB`

---

## 1. Identidad, cuentas y configuracion

### 1.1 `users`

Cuenta central de todo usuario (desarrolladores, reclutadores, moderadores, admins). Contiene autenticacion, roles, estado de actividad, configuracion de privacidad y datos de baneo.

```json
{
  "nombre": "Carlos Ruiz",
  "nombreUsuario": "carlosdev",
  "email": "carlos@correo.com",
  "passwordHash": "$2a$10$hashdeejemplo",
  "fotoPerfilUrl": "https://res.cloudinary.com/demo/image/upload/v1/foto.jpg",
  "bannerUrl": "https://res.cloudinary.com/demo/image/upload/v1/banner.jpg",
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

### 1.2 `developer_profiles`

Perfil profesional detallado de un usuario con rol DEVELOPER. Contiene tecnologias, experiencias, educacion, proyectos, CV y disponibilidad laboral.

```json
{
  "userId": {"$oid": "ObjectId_del_usuario"},
  "tituloProfesional": "Desarrollador Full Stack",
  "bannerUrl": "https://res.cloudinary.com/demo/image/upload/v1/banner.jpg",
  "bio": "Desarrollador orientado a aplicaciones web y moviles.",
  "ubicacion": {
    "pais": "Peru",
    "ciudad": "Lima",
    "distrito": "Surco",
    "coordenadas": {
      "type": "Point",
      "coordinates": [-76.99, -12.12]
    }
  },
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

### 1.3 `recruiter_profiles`

Perfil profesional de un usuario con rol RECRUITER. Identifica al representante de una o varias empresas.

```json
{
  "userId": {"$oid": "ObjectId_del_usuario"},
  "nombres": "Ana",
  "apellidos": "Lopez",
  "cargo": "Talent Acquisition Specialist",
  "bannerUrl": "https://res.cloudinary.com/demo/image/upload/v1/banner.jpg",
  "telefono": "+51999888777",
  "linkedinUrl": "https://linkedin.com/in/analopez",
  "empresas": [
    {"companyId": {"$oid": "ObjectId_empresa"}, "cargoEnEmpresa": "RECRUITER", "activo": true}
  ],
  "verificado": false,
  "createdAt": ISODate( "2026-01-15T10:00:00Z"),
  "updatedAt": ISODate( "2026-01-15T10:00:00Z")
}
```

---

### 1.4 `companies`

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

## 2. Seguimientos, conexiones y privacidad

### 2.1 `connection_requests`

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

### 2.2 `connections`

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

### 2.3 `follows`

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

### 2.4 `blocked_users`

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

## 3. Contenido social

### 3.1 `posts`

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

### 3.2 `stories`

Contenido efimero, visible por 24 horas.

```json
{
  "autorId": {"$oid": "ObjectId_usuario"},
  "contenido": {
    "texto": "Terminando una nueva funcionalidad de COMUNIDEV.",
    "imagenUrl": "https://res.cloudinary.com/demo/image/upload/v1/story.jpg",
    "videoUrl": null,
    "musica": {
      "titulo": "Nombre de cancion",
      "artista": "Artista",
      "proveedor": "SPOTIFY",
      "referenciaExterna": "track-id-123",
      "inicioSegundos": 0,
      "duracionSegundos": 15
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

### 3.3 `story_views`

Registra que usuario visualizo una historia.

```json
{
  "storyId": {"$oid": "ObjectId_historia"},
  "viewerId": {"$oid": "ObjectId_usuario"},
  "viewedAt": ISODate( "2026-01-15T11:00:00Z")
}
```

---

### 3.4 `reels`

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

### 3.5 `comments`

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

### 3.6 `reactions`

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

### 3.7 `saved_contents`

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

### 3.8 `hashtag`

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

## 4. Asistencia tecnica

### 4.1 `technical_questions`

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

### 4.2 `technical_answers`

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

### 4.3 `question_votes`

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

## 5. Mensajeria, actividad y notificaciones

### 5.1 `conversations`

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

### 5.2 `messages`

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

### 5.3 `activity_status_history`

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

### 5.4 `notifications`

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

## 6. Empleo, empresas y mapa laboral

### 6.1 `vacancies`

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

### 6.2 `applications`

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

### 6.3 `application_status_history`

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

## 7. EcoDev y ODS 12

### 7.1 `ecotech_posts`

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

### 7.2 `recycling_points`

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

### 7.3 `ecotech_transactions`

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

### 7.4 `company_sustainability_practices`

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

## 8. Puntos, niveles, insignias y planes

> **NOTA:** Esta sección NO está implementada en el MVP. Se预留 para una futura versión con gamificación y planes premium.

### 8.1 `user_points`

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

### 8.2 `point_transactions`

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

### 8.3 `badges`

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

### 8.4 `user_badges`

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

### 8.5 `plans`

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

### 8.6 `subscriptions`

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

## 9. Soporte, moderacion y auditoria

### 9.1 `reports`

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

### 9.2 `moderation_actions`

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

### 9.3 `support_tickets`

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

### 9.4 `audit_logs`

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

### 9.5 `user_bans`

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

## 10. Busqueda de talento (ya implementadas)

### 10.1 `saved_searches`

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

### 10.2 `saved_profiles`

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

## 11. Test (ya implementada)

### 11.1 `test`

Entidad de prueba para verificar la conexion a MongoDB.

```json
{
  "nombre": "Test Document"
}
```

---

## Indices recomendados en MongoDB Atlas

Despues de crear las colecciones, configurar los siguientes indices desde la interfaz de Atlas:

### `users`
- Único: `email`
- Único: `nombreUsuario`
- Indice: `roles`
- Indice: `estadoCuenta`

### `developer_profiles`
- Único: `userId`
- Indice multikey: `tecnologias.nombre`
- Indice geoespacial: `ubicacion.coordenadas` (2dsphere)

### `companies`
- Indice de texto: `nombre`, `descripcion`, `sector`
- Indice geoespacial: `ubicacion.coordenadas` (2dsphere)
- Indice: `estadoVerificacion`

### `connection_requests`
- Único parcial: `solicitanteId`, `receptorId`, `estado`
- Indice: `receptorId`, `estado`

### `connections`
- Único: `usuarioMenorId`, `usuarioMayorId`

### `follows`
- Único: `seguidorId`, `tipoSeguido`, `seguidoId`

### `posts`
- Indice: `autorId`, `createdAt`
- Indice: `visibilidad`, `estadoModeracion`, `createdAt`
- Indice multikey: `etiquetas`
- Indice de texto: `contenido.texto`, `etiquetas`

### `stories`
- Indice TTL: `fechaExpiracion`

### `comments`
- Indice: `contenidoId`, `tipoContenido`, `createdAt`
- Indice: `parentCommentId`

### `reactions`
- Único: `usuarioId`, `objetivoId`, `tipoObjetivo`

### `messages`
- Indice: `conversationId`, `createdAt`
- Indice: `remitenteId`

### `vacancies`
- Indice: `companyId`, `estado`
- Indice multikey: `tecnologias`
- Indice: `modalidad`, `nivelExperiencia`, `estado`
- Indice geoespacial: `ubicacion.coordenadas` (2dsphere)
- Indice: `fechaLimite`

### `applications`
- Único: `vacancyId`, `developerId`
- Indice: `developerId`, `estado`

### `recycling_points`
- Indice geoespacial: `ubicacion.coordenadas` (2dsphere)

### `user_bans`
- Indice: `userId`, `createdAt`
- Indice TTL: `fechaFin`
