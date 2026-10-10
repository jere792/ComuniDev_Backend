# COMUNIDEV — Criterios de Aceptación (Gherkin)

> Formato Gherkin: **Dado / Cuando / Entonces** (DWT). Cada historia de usuario del backlog (`doc\11-BACKLOG-HU.md`) tiene al menos un escenario feliz y un escenario de validación o error.
> Los IDs de escenario siguen el patrón `CA-HU-NN-01`.

---

## Épica A — Autenticación y acceso

### CA-HU-01 — Registro de usuario

```gherkin
Característica: Registro de usuarios

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

  Escenario: Contraseña no cumple requisitos
    Dado que el visitante no tiene una cuenta
    Cuando ingresa una contraseña con menos de 8 caracteres o sin letras y números
    Entonces el sistema no crea la cuenta
    Y muestra el mensaje "La contraseña debe tener mínimo 8 caracteres e incluir una letra y un número"
```

### CA-HU-02 — Inicio de sesión

```gherkin
Característica: Login y sesión

  Escenario: Login exitoso
    Dado que existe un usuario registrado con correo y contraseña válidos
    Cuando ingresa sus credenciales correctas y selecciona "Iniciar sesión"
    Entonces el sistema devuelve un token de acceso JWT y un refresh token
    Y el token contiene el identificador del usuario y su rol activo

  Escenario: Credenciales incorrectas
    Dado que existe un usuario registrado con correo "usuario@correo.com"
    Cuando ingresa una contraseña incorrecta
    Entonces el sistema rechaza el inicio de sesión
    Y muestra el mensaje "Credenciales inválidas"
    Y no revela si el correo existe en el sistema
```

### CA-HU-03 — Refresh token

```gherkin
  Escenario: Renovar token expirado
    Dado que el usuario tiene un refresh token vigente
    Cuando su token de acceso expira e invoca la operación de renovación
    Entonces el sistema emite un nuevo token de acceso válido
    Y el refresh token anterior queda rotado (no reutilizable)

  Escenario: Refresh token inválido
    Dado que el usuario envía un refresh token expirado o manipulado
    Cuando invoca la operación de renovación
    Entonces el sistema rechaza la renovación
    Y responde con error de autenticación
```

### CA-HU-04 — Cierre de sesión

```gherkin
  Escenario: Cerrar sesión
    Dado que el usuario tiene una sesión activa con token válido
    Cuando selecciona "Cerrar sesión"
    Entonces el sistema invalida el refresh token asociado
    Y una petición posterior con el token anterior es rechazada
```

### CA-HU-05 — Rol activo y multi-rol

```gherkin
  Escenario: Cambiar rol activo
    Dado que el usuario tiene los roles DEVELOPER y RECRUITER
    Cuando selecciona el rol RECRUITER como rol activo
    Entonces el sistema aplica los permisos de reclutador en la sesión
    Y el usuario accede a las operaciones exclusivas de reclutador

  Escenario: Intento de rol no asignado
    Dado que el usuario solo tiene el rol DEVELOPER
    Cuando intenta ejecutar una operación exclusiva de ADMIN
    Entonces el sistema rechaza la operación con error de autorización
```

---

## Épica B — Usuarios y perfiles

### CA-HU-06 — Perfil básico

```gherkin
  Escenario: Editar perfil básico
    Dado que el usuario tiene una sesión activa
    Cuando actualiza su nombre, biografía y ubicación
    Entonces el sistema guarda los cambios
    Y la consulta posterior del perfil refleja la información actualizada

  Escenario: Editar perfil de otro usuario
    Dado que el usuario tiene sesión activa como "usuarioA"
    Cuando intenta editar el perfil de "usuarioB"
    Entonces el sistema rechaza la operación con error de autorización
```

### CA-HU-07 — Perfil profesional de desarrollador

```gherkin
  Escenario: Completar perfil profesional
    Dado que el usuario tiene sesión activa como desarrollador
    Cuando registra tecnologías, experiencia, educación, proyectos, certificados, disponibilidad y pretensiones salariales
    Entonces el sistema guarda el perfil profesional completo
    Y el perfil puede ser consultado según las reglas de visibilidad

  Escenario: Postular con perfil incompleto
    Dado que el desarrollador no tiene ubicación ni al menos una tecnología registrada
    Cuando intenta postular a una vacante
    Entonces el sistema bloquea la postulación
    Y muestra el mensaje "Complete nombre, ubicación y al menos una tecnología antes de postular"
```

### CA-HU-08 — Perfil de reclutador

```gherkin
  Escenario: Crear perfil de reclutador
    Dado que el usuario tiene sesión activa con rol RECRUITER
    Cuando completa empresa, cargo y redes sociales
    Entonces el sistema crea el perfil de reclutador asociado a su cuenta
```

### CA-HU-09 — Subida de archivos a Cloudinary

```gherkin
  Escenario: Subir foto de perfil
    Dado que el usuario tiene sesión activa
    Cuando envía una imagen válida al endpoint de upload
    Entonces el sistema la sube a Cloudinary
    Y devuelve la URL segura para usar en el perfil

  Escenario: Archivo no permitido
    Dado que el usuario tiene sesión activa
    Cuando envía un archivo de tipo ejecutable
    Entonces el sistema rechaza la subida
    Y muestra el mensaje "Tipo de archivo no permitido"
```

### CA-HU-10 — Privacidad configurable

```gherkin
  Escenario: Configurar visibilidad del CV
    Dado que el usuario tiene perfil profesional completo
    Cuando configura la visibilidad del CV como "Solo conexiones"
    Entonces un usuario sin conexión no puede consultar su CV
    Y un usuario conectado sí puede consultarlo

  Escenario: Aplicar privacidad aunque el cliente intente acceder directo
    Dado que un usuario externo no autorizado consulta el GraphQL
    Cuando solicita directamente el campo CV de otro usuario restringido
    Entonces el sistema devuelve null o error de autorización
```

### CA-HU-11 — Eliminar cuenta

```gherkin
  Escenario: Eliminar cuenta propia
    Dado que el usuario tiene sesión activa
    Cuando confirma la eliminación de su cuenta
    Entonces el sistema desactiva o elimina sus datos personales
    Y su token deja de ser válido
```

---

## Épica C — Publicaciones y feed

### CA-HU-12 — Crear publicación

```gherkin
Característica: Creación de publicaciones

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

  Escenario: Publicación solo para conexiones
    Dado que el desarrollador creó una publicación con visibilidad "CONEXIONES"
    Cuando un usuario que no es su conexión consulta el feed
    Entonces la publicación no aparece en su feed
    Y un usuario conectado sí la ve
```

### CA-HU-13 — Editar o eliminar publicación

```gherkin
  Escenario: Eliminar publicación propia
    Dado que el autor visualiza su publicación
    Cuando selecciona "Eliminar publicación" y confirma
    Entonces el sistema elimina la publicación del feed

  Escenario: Editar publicación de otro usuario
    Dado que el usuario visualiza una publicación de otro autor
    Cuando intenta editarla
    Entonces el sistema rechaza la operación con error de autorización
```

### CA-HU-14 — Feed paginado

```gherkin
  Escenario: Feed con paginación
    Dado que el usuario tiene una sesión activa
    Cuando consulta el feed con una página de tamaño 10
    Entonces el sistema devuelve las publicaciones visibles para él ordenadas por fecha
    Y devuelve el cursor o página siguiente

  Escenario: Feed respeta bloqueos
    Dado que el usuario bloqueó a otro usuario
    Cuando consulta el feed
    Entonces no aparecen publicaciones del usuario bloqueado
```

### CA-HU-15 — Guardar publicaciones

```gherkin
  Escenario: Guardar y listar favoritos
    Dado que el usuario visualiza una publicación
    Cuando selecciona "Guardar"
    Entonces la publicación se agrega a sus guardados
    Y al consultar sus guardados aparece en la lista
```

---

## Épica D — Comentarios y reacciones

### CA-HU-16 — Comentar publicaciones

```gherkin
  Escenario: Comentario válido
    Dado que el usuario inició sesión y visualiza una publicación
    Cuando escribe un comentario y lo envía
    Entonces el sistema registra el comentario con autor y fecha
    Y el autor de la publicación recibe una notificación

  Escenario: Comentar sin contenido
    Dado que el usuario visualiza una publicación
    Cuando envía un comentario vacío
    Entonces el sistema rechaza el comentario
```

### CA-HU-17 — Respuestas a comentarios

```gherkin
  Escenario: Responder un comentario
    Dado que el usuario visualiza un comentario
    Cuando envía una respuesta a ese comentario
    Entonces el sistema la registra como respuesta anidada (hijo del comentario original)

  Escenario: Consultar respuestas de un comentario
    Dado que un comentario tiene 3 respuestas
    Cuando el usuario consulta las respuestas del comentario
    Entonces el sistema devuelve las 3 respuestas ordenadas por fecha
```

### CA-HU-18 — Reacciones

```gherkin
  Escenario: Reaccionar por primera vez
    Dado que el usuario inició sesión y visualiza una publicación
    Cuando selecciona la reacción "LIKE"
    Entonces el sistema registra la reacción y actualiza el contador

  Escenario: Cambiar tipo de reacción
    Dado que el usuario ya reaccionó con "LIKE" a una publicación
    Cuando cambia su reacción a "LOVE"
    Entonces el sistema actualiza la reacción existente
    Y no crea una segunda reacción del mismo usuario

  Escenario: Reacción duplicada rechazada
    Dado que el usuario ya reaccionó con "LIKE" a una publicación
    Cuando intenta registrar nuevamente "LIKE"
    Entonces el sistema no duplica la reacción
```

---

## Épica E — Stories y reels

### CA-HU-19 — Crear story

```gherkin
  Escenario: Crear story de texto
    Dado que el usuario inició sesión
    Cuando crea una story de tipo TEXT con contenido
    Entonces el sistema la registra con fecha de creación
    Y expira automáticamente a las 24 horas

  Escenario: Consultar stories activas
    Dado que existen stories creadas hace menos de 24 horas
    Cuando un usuario consulta las stories de sus seguidos
    Entonces solo aparecen las no expiradas
```

### CA-HU-20 — Vistas de story

```gherkin
  Escenario: Registrar vista de story
    Dado que un usuario visualiza una story ajena
    Cuando la story termina de reproducirse
    Entonces el sistema registra la vista
    Y el autor puede consultar quién la vio
```

### CA-HU-21 — Reels

```gherkin
  Escenario: Crear reel con música
    Dado que el usuario inició sesión
    Cuando crea un reel con video y metadatos de música asociada
    Entonces el sistema registra el reel
    Y puede consultarse en el listado de reels y por autor
```

---

## Épica F — Asistencia técnica

### CA-HU-22 — Crear pregunta técnica

```gherkin
Característica: Resolución de preguntas técnicas

  Escenario: Crear una pregunta técnica
    Dado que el desarrollador inició sesión
    Cuando registra título, descripción, código de ejemplo, al menos una etiqueta y una categoría
    Y selecciona "Publicar pregunta"
    Entonces el sistema publica la pregunta en la sección de asistencia técnica

  Escenario: Pregunta sin etiquetas
    Dado que el desarrollador inició sesión
    Cuando intenta publicar una pregunta sin etiquetas
    Entonces el sistema rechaza la pregunta
    Y muestra el mensaje "Una pregunta debe incluir al menos una etiqueta tecnológica"

  Escenario: Pregunta sin categoría
    Dado que el desarrollador inició sesión
    Cuando intenta publicar una pregunta sin seleccionar categoría
    Entonces el sistema rechaza la pregunta
```

### CA-HU-23 — Responder preguntas

```gherkin
  Escenario: Responder una pregunta
    Dado que el desarrollador visualiza una pregunta
    Cuando escribe una respuesta y la envía
    Entonces el sistema registra la respuesta
    Y el autor de la pregunta recibe una notificación
```

### CA-HU-24 — Listar y filtrar preguntas

```gherkin
  Escenario: Filtrar por categoría y estado
    Dado que existen preguntas en varias categorías
    Cuando el usuario filtra por categoría "backend" y estado "abierta"
    Entonces el sistema muestra solo preguntas de backend sin respuesta aceptada
```

---

## Épica G — Votos, respuesta aceptada y reputación

### CA-HU-25 — Marcar respuesta aceptada

```gherkin
  Escenario: Aceptar una respuesta
    Dado que el autor visualiza las respuestas de su pregunta
    Cuando selecciona "Marcar como solución" en una respuesta
    Entonces el sistema identifica la respuesta como aceptada

  Escenario: Segunda respuesta aceptada rechazada
    Dado que la pregunta ya tiene una respuesta aceptada
    Cuando el autor intenta aceptar otra respuesta
    Entonces el sistema no permite aceptar una segunda respuesta
    Y muestra el mensaje "La pregunta ya tiene una respuesta aceptada"

  Escenario: Solo el autor acepta
    Dado que un usuario no es el autor de la pregunta
    Cuando intenta marcar una respuesta como aceptada
    Entonces el sistema rechaza la operación con error de autorización
```

### CA-HU-26 — Votar respuestas

```gherkin
  Escenario: Votar una respuesta
    Dado que el usuario visualiza una respuesta útil
    Cuando registra un voto positivo
    Entonces el sistema incrementa el puntaje de la respuesta

  Escenario: Segundo voto del mismo usuario
    Dado que el usuario ya votó la respuesta
    Cuando intenta votar nuevamente la misma respuesta
    Entonces el sistema rechaza el voto duplicado
```

### CA-HU-27 — Reputación

```gherkin
  Escenario: Puntos por respuesta aceptada
    Dado que la respuesta de un desarrollador fue aceptada
    Cuando el sistema procesa la aceptación
    Entonces incrementa la reputación del autor de la respuesta
```

---

## Épica H — Conexiones, seguimientos y privacidad social

### CA-HU-28 — Seguir usuarios

```gherkin
  Escenario: Seguir a un usuario
    Dado que el usuario tiene sesión activa
    Cuando selecciona "Seguir" en el perfil de otro usuario
    Entonces el sistema registra el follow
    Y el usuario seguido recibe una notificación

  Escenario: No seguirse a sí mismo
    Dado que el usuario visualiza su propio perfil
    Cuando intenta seguirse
    Entonces el sistema rechaza la operación
```

### CA-HU-29 — Enviar solicitud de conexión

```gherkin
  Escenario: Enviar solicitud de conexión
    Dado que el usuario quiere conectarse con otro usuario
    Cuando envía la solicitud de conexión
    Entonces el sistema registra la solicitud con estado PENDIENTE
    Y el receptor recibe una notificación

  Escenario: Solicitud a usuario bloqueado
    Dado que el receptor bloqueó al solicitante
    Cuando el solicitante envía una solicitud de conexión
    Entonces el sistema rechaza la solicitud
```

### CA-HU-30 — Aceptar o rechazar solicitud

```gherkin
  Escenario: Aceptar solicitud
    Dado que el receptor tiene una solicitud PENDIENTE
    Cuando acepta la solicitud
    Entonces el sistema cambia el estado a ACEPTADA
    Y crea la conexión entre ambos usuarios
    Y notifica al solicitante

  Escenario: Rechazar solicitud
    Dado que el receptor tiene una solicitud PENDIENTE
    Cuando rechaza la solicitud
    Entonces el sistema cambia el estado a RECHAZADA
    Y no se crea conexión
```

### CA-HU-31 — Conexiones

```gherkin
  Escenario: Listar y eliminar conexión
    Dado que el usuario tiene conexiones activas
    Cuando consulta su lista de conexiones y elimina una
    Entonces la conexión se elimina de ambos usuarios
```

### CA-HU-32 — Bloquear usuarios

```gherkin
  Escenario: Bloquear usuario
    Dado que el usuario quiere bloquear a otro usuario
    Cuando confirma el bloqueo
    Entonces el sistema registra el bloqueo
    Y el usuario bloqueado no puede enviarle solicitudes, comentarios ni mensajes

  Escenario: Desbloquear usuario
    Dado que existe un bloqueo registrado
    Cuando el usuario lo desbloquea
    Entonces el bloqueo se elimina
```

---

## Épica I — Notificaciones y tiempo real

### CA-HU-33 — Notificaciones

```gherkin
  Escenario: Recibir notificación por conexión
    Dado que un usuario envió una solicitud de conexión
    Cuando el receptor consulta sus notificaciones
    Entonces aparece la notificación de tipo SOLICITUD_CONEXION no leída

  Escenario: Marcar todas como leídas
    Dado que el usuario tiene varias notificaciones no leídas
    Cuando selecciona "Marcar todas como leídas"
    Entonces el contador de no leídas queda en cero
```

### CA-HU-34 — Tiempo real (WebSocket)

```gherkin
  Escenario: Notificación en tiempo real
    Dado que un usuario tiene abierta una suscripción de notificaciones
    Cuando otro usuario comenta su publicación
    Entonces la suscripción emite el evento de notificación sin recargar

  Escenario: Cambio de estado de conexión en tiempo real
    Dado que el usuario tiene abierta la suscripción de estado de conexión
    Cuando su solicitud de conexión es aceptada
    Entonces la suscripción emite el evento con el nuevo estado
```

---

## Épica J — Búsqueda y búsqueda de talento

### CA-HU-35 — Búsqueda general

```gherkin
  Escenario: Buscar por palabra clave
    Dado que existen publicaciones con el término "JWT"
    Cuando el usuario busca "JWT"
    Entonces el sistema devuelve publicaciones y preguntas que contienen el término
```

### CA-HU-36 — Búsqueda de talento

```gherkin
  Escenario: Filtrar desarrolladores por tecnología
    Dado que existen desarrolladores con distintas tecnologías
    Cuando el reclutador filtra por tecnología "Angular" y disponibilidad "inmediata"
    Entonces el sistema devuelve solo perfiles que cumplen ambos criterios

  Escenario: Sin resultados
    Dado que no hay perfiles con la tecnología "COBOL"
    Cuando el reclutador filtra por esa tecnología
    Entonces el sistema devuelve una lista vacía sin error
```

### CA-HU-37 — Guardar búsquedas y perfiles

```gherkin
  Escenario: Guardar perfil de candidato
    Dado que el reclutador visualiza un perfil de desarrollador
    Cuando selecciona "Guardar perfil"
    Entonces el perfil se agrega a sus perfiles guardados
```

---

## Épica K — Empresas

### CA-HU-38 — Registrar empresa

```gherkin
  Escenario: Registrar empresa válida
    Dado que el reclutador tiene perfil de reclutador activo
    Cuando registra nombre, descripción, correo de contacto, ubicación, latitud y longitud válidas
    Entonces el sistema crea la empresa en estado pendiente de validación

  Escenario: Empresa sin coordenadas
    Dado que el reclutador registra una empresa
    Cuando no proporciona latitud y longitud válidas
    Entonces el sistema rechaza el registro
    Y muestra el mensaje "La empresa debe tener coordenadas válidas para aparecer en el mapa"
```

### CA-HU-39 — Validación de empresa

```gherkin
  Escenario: Validar empresa
    Dado que un administrador visualiza empresas pendientes
    Cuando valida una empresa
    Entonces el sistema cambia su estado a activa
    Y sus reclutadores vinculados pueden publicar vacantes

  Escenario: Publicar vacante sin empresa validada
    Dado que el reclutador no está vinculado a una empresa activa
    Cuando intenta crear una vacante
    Entonces el sistema rechaza la operación
```

---

## Épica L — Vacantes

### CA-HU-40 — Crear vacante

```gherkin
Característica: Vacantes laborales

  Escenario: Publicar una vacante válida
    Dado que el reclutador está vinculado a una empresa registrada y validada
    Cuando completa título, tecnologías, modalidad, ubicación, número de vacantes y fecha límite válida
    Y selecciona "Publicar vacante"
    Entonces el sistema registra la vacante con estado ACTIVA
    Y la muestra en la lista de empleos y en el mapa laboral

  Escenario: Fecha límite inválida
    Dado que el reclutador está creando una vacante
    Cuando registra una fecha límite anterior a la fecha actual
    Entonces el sistema no guarda la vacante
    Y muestra el mensaje "La fecha límite no puede ser anterior a la fecha actual"

  Escenario: Modalidad no permitida
    Dado que el reclutador está creando una vacante
    Cuando selecciona una modalidad fuera de (PRESENCIAL, REMOTA, HIBRIDA)
    Entonces el sistema rechaza la vacante
```

### CA-HU-41 — Ciclo de vida de la vacante

```gherkin
  Escenario: Vencimiento automático
    Dado que una vacante tiene fecha límite ayer y estado ACTIVA
    Cuando el sistema ejecuta el proceso de vencimiento
    Entonces la vacante cambia a estado CERRADA
    Y deja de aceptar postulaciones

  Escenario: Cierre manual
    Dado que el reclutador tiene una vacante activa
    Cuando selecciona "Cerrar vacante"
    Entonces el sistema cambia su estado a CERRADA
```

### CA-HU-42 — Listado y filtros de vacantes

```gherkin
  Escenario: Filtrar vacantes
    Dado que existen vacantes con distintas modalidades y tecnologías
    Cuando el desarrollador filtra por tecnología "Kotlin" y modalidad "REMOTA"
    Entonces el sistema devuelve solo vacantes activas que cumplen el filtro

  Escenario: No incluir vacantes cerradas
    Dado que existen vacantes cerradas
    Cuando el desarrollador consulta el listado general
    Entonces las vacantes cerradas no aparecen
```

---

## Épica M — Postulaciones

### CA-HU-43 — Postular a vacante

```gherkin
Característica: Postulación a vacantes

  Escenario: Postulación exitosa
    Dado que el desarrollador tiene el perfil obligatorio completo
    Y existe una vacante activa
    Cuando selecciona "Postular"
    Entonces el sistema registra una postulación con estado ENVIADA
    Y notifica al reclutador responsable

  Escenario: Postulación duplicada
    Dado que el desarrollador ya tiene una postulación activa a una vacante
    Cuando intenta postular nuevamente a la misma vacante
    Entonces el sistema bloquea la acción
    Y muestra el mensaje "Ya existe una postulación activa para esta vacante"

  Escenario: Postular a vacante cerrada
    Dado que la vacante tiene estado CERRADA
    Cuando el desarrollador intenta postular
    Entonces el sistema rechaza la postulación
```

### CA-HU-44 — Gestión de postulaciones

```gherkin
  Escenario: Cambiar estado de postulación
    Dado que el reclutador autorizado visualiza postulaciones de su vacante
    Cuando cambia una postulación a PRESELECCIONADA
    Entonces el sistema actualiza el estado
    Y el postulante recibe una notificación con el nuevo estado

  Escenario: Acceso no autorizado a postulaciones
    Dado que un reclutador no está vinculado a la empresa de la vacante
    Cuando intenta consultar sus postulaciones
    Entonces el sistema rechaza la operación con error de autorización
```

### CA-HU-45 — Historial de postulación

```gherkin
  Escenario: Consultar historial
    Dado que el desarrollador tiene una postulación con cambios de estado
    Cuando consulta el detalle de su postulación
    Entonces el sistema muestra el historial completo de estados con fechas
```

---

## Épica N — Mapa laboral

### CA-HU-46 — Mapa de empresas

```gherkin
Característica: Mapa laboral

  Escenario: Mostrar empresas en el mapa
    Dado que existen empresas con vacantes activas y coordenadas válidas
    Cuando el usuario ingresa al mapa laboral
    Entonces el sistema muestra un marcador por cada empresa o grupo de empresas cercanas
    Y cada marcador muestra la cantidad de vacantes activas

  Escenario: Empresa sin vacantes activas
    Dado que una empresa no tiene vacantes activas
    Cuando el usuario consulta el mapa
    Entonces la empresa no aparece en el mapa
```

### CA-HU-47 — Filtros del mapa

```gherkin
  Escenario: Filtrar vacantes por tecnología
    Dado que el usuario se encuentra en el mapa laboral
    Cuando selecciona la tecnología "Angular" y aplica los filtros
    Entonces el sistema muestra únicamente empresas con vacantes que requieren Angular
```

### CA-HU-48 — Detalle desde marcador

```gherkin
  Escenario: Ver detalle de empresa
    Dado que el usuario selecciona un marcador del mapa
    Cuando el sistema muestra el detalle
    Entonces aparece la empresa, vacantes activas, tecnologías y modalidad
    Y existe un enlace al listado de vacantes de esa empresa
```

---

## Épica O — EcoDev y ODS 12

### CA-HU-49 — Publicación sostenible

```gherkin
Característica: EcoDev y ODS 12

  Escenario: Publicar contenido sostenible
    Dado que el usuario inició sesión
    Cuando crea una publicación con etiquetas de sostenibilidad (Green IT, reciclaje, reutilización, reparación, donación, eficiencia energética u ODS 12)
    Entonces el sistema la clasifica como contenido ODS 12
    Y es visible en la sección EcoDev

  Escenario: Publicación sostenible sin etiqueta
    Dado que el usuario intenta publicar en EcoDev sin etiquetas de sostenibilidad
    Cuando envía la publicación
    Entonces el sistema la rechaza
```

### CA-HU-50 — Anuncios de equipos

```gherkin
  Escenario: Publicar equipo en donación
    Dado que el usuario inició sesión
    Cuando registra una publicación con categoría, estado del equipo, ubicación aproximada, medio de contacto interno y etiqueta "Donación"
    Y selecciona "Publicar"
    Entonces el sistema registra el anuncio en la sección EcoDev
    Y lo clasifica como contenido relacionado con ODS 12

  Escenario: Anuncio incompleto
    Dado que el usuario intenta publicar un equipo
    Cuando omite el estado del equipo o la ubicación aproximada
    Entonces el sistema rechaza el anuncio
```

### CA-HU-51 — Puntos de reciclaje

```gherkin
  Escenario: Punto de reciclaje validado
    Dado que un administrador validó un punto de reciclaje electrónico
    Cuando un usuario consulta el mapa EcoDev
    Entonces el sistema muestra el punto de reciclaje
    Y muestra su nombre, ubicación y tipos de residuos aceptados

  Escenario: Punto pendiente de validación
    Dado que un punto de reciclaje fue registrado y aún no validado
    Cuando un usuario consulta el mapa EcoDev
    Entonces el punto no aparece públicamente
```

### CA-HU-52 — Indicadores de impacto

```gherkin
  Escenario: Contabilizar impacto sostenible
    Dado que una publicación de donación fue confirmada por ambas partes
    Cuando el sistema actualiza el estado a "completada"
    Entonces incrementa el indicador de equipos donados
    Y muestra el resultado actualizado en el dashboard EcoDev
```

---

## Épica P — Moderación, reportes y administración

### CA-HU-53 — Reportar contenido

```gherkin
Característica: Moderación de contenido

  Escenario: Reportar una publicación
    Dado que un usuario autenticado visualiza una publicación inapropiada
    Cuando selecciona "Reportar" y registra una razón válida
    Entonces el sistema crea un reporte con estado EN_REVISION
    Y notifica a los moderadores

  Escenario: Reportar sin razón
    Dado que un usuario selecciona "Reportar"
    Cuando intenta enviar el reporte sin seleccionar una razón
    Entonces el sistema rechaza el reporte
```

### CA-HU-54 — Acciones de moderación

```gherkin
  Escenario: Moderar contenido reportado
    Dado que un moderador visualiza un reporte pendiente
    Cuando decide ocultar la publicación por incumplir las normas
    Entonces el sistema cambia el estado de la publicación a OCULTA
    Y notifica al autor sobre la acción tomada
```

### CA-HU-55 — Baneos y suspensiones

```gherkin
  Escenario: Suspender usuario
    Dado que un administrador selecciona un usuario
    Cuando aplica una suspensión
    Entonces el usuario suspendido no puede publicar, comentar, responder, postular ni enviar mensajes

  Escenario: Usuario suspendido intenta publicar
    Dado que el usuario tiene estado SUSPENDIDO
    Cuando intenta crear una publicación
    Entonces el sistema rechaza la operación
    Y muestra el mensaje "Tu cuenta está suspendida"
```

### CA-HU-56 — Administración de usuarios y categorías

```gherkin
  Escenario: Asignar rol de moderador
    Dado que un administrador gestiona un usuario
    Cuando le asigna el rol MODERATOR
    Entonces el usuario adquiere los permisos de moderador

  Escenario: Asignar rol por usuario no admin
    Dado que un usuario con rol DEVELOPER
    Cuando intenta asignar el rol ADMIN a otra cuenta
    Entonces el sistema rechaza la operación con error de autorización
```

### CA-HU-57 — Tickets de soporte

```gherkin
  Escenario: Crear ticket de soporte
    Dado que el usuario tiene un problema con la plataforma
    Cuando crea un ticket con asunto y descripción
    Entonces el sistema registra el ticket en estado ABIERTO
    Y el equipo de soporte lo recibe
```

---

## Épica Q — Gamificación y analítica (backlog continuo)

### CA-HU-58 — Puntos, niveles e insignias

```gherkin
  Escenario: Obtener insignia por respuesta aceptada
    Dado que un desarrollador acumula 10 respuestas aceptadas
    Cuando el sistema evalúa sus logros
    Entonces le otorga la insignia correspondiente
    Y notifica al usuario
```

### CA-HU-59 — Planes y suscripciones

```gherkin
  Escenario: Suscribirse a plan premium
    Dado que el usuario consulta los planes disponibles
    Cuando selecciona el plan premium y confirma la suscripción
    Entonces el sistema registra la suscripción
    Y habilita las funcionalidades premium de su cuenta
```

### CA-HU-60 — Panel de métricas

```gherkin
  Escenario: Ver métricas de vacantes por tecnología
    Dado que un administrador ingresa al panel de métricas
    Cuando consulta el reporte de tecnologías demandadas
    Entonces el sistema muestra el ranking de tecnologías con más vacantes activas
```

### CA-HU-61 — Cierre: pruebas y despliegue

```gherkin
  Escenario: Despliegue de producción
    Dado que el equipo ejecuta el pipeline de CI/CD
    Cuando todas las pruebas automáticas pasan
    Entonces el sistema despliega la versión en Railway
    Y el health check responde correctamente
```
