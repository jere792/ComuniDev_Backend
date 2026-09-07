# COMUNIDEV — Flujos de Módulos

## 1. Autenticación y registro

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

## 2. Login y sesión

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

## 3. Publicación de contenido (Posts)

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

## 4. Feed principal

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

## 5. Conexiones profesionales

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

## 6. Mensajería directa

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

## 7. Preguntas técnicas

### Flujo principal

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

### Flujo detallado de respuestas

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

### Funcionalidades de votación

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

## 8. Postulación a vacantes

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

## 9. EcoDev (donación/reutilización)

### Flujo principal

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

### Flujo detallado de transacción

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

### Tipos de anuncio EcoDev

| Tipo | Descripción | Ejemplo |
|------|-------------|---------|
| DONACION | Dar equipo sin costo | Laptop funcional usada |
| INTERCAMBIO | trueque de equipos | Mouse por cable |
| REPARACION | Equipo que necesita arreglo | Laptop con batería dañada |
| REUTILIZACION | Reusar componentes | Monitor viejo funcional |
| RECICLAJE | Disposición responsable | Celular no funcional |
| CAMPANA | Evento temporal | Jornada de recolección |

### Condiciones de equipo

| Condición | Descripción |
|-----------|-------------|
| FUNCIONAL | Opera correctamente |
| FUNCIONAL_CON_REPARACION_MENOR | Funciona pero necesita ajuste |
| PARA_REPARAR | No funciona, pero es reparable |
| PARA_REPUESTOS | Solo sirve para componentes |
| NO_FUNCIONAL | No tiene uso, reciclaje |

### Reglas de puntos EcoDev

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

## 10. Moderación, reportes y baneos

### Flujo de reportes

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

### Flujo de baneo (ban)

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

### Tipos de baneo

| Tipo | Duración | Acceso | Ejemplo |
|------|----------|--------|---------|
| TEMPORAL_LEVE | 24 horas | Bloqueado parcialmente | Spam menor, lenguaje inapropiado |
| TEMPORAL_GRAVE | 7 días | Bloqueado completamente | Acoso, contenido ofensivo |
| PERMANENTE | Indefinido | Bloqueado completamente | Fraude repetido, malware |
| SHADOW_BAN | Variable | No sabe que está baneado | Spam persistente |

### Efectos del baneo

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

### Desbaneo

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

## 11. Mapa laboral

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
