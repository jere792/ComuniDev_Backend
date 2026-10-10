# COMUNIDEV — Requerimientos No Funcionales (RNF)

> Los RNF definen **cómo** el sistema debe operar (calidades), no qué hace. Se codifican RNF-NN y se agrupan por categoría. Cada RNF es verificable.

---

## 1. Seguridad

| Código | Requerimiento | Criterio de verificación |
|--------|---------------|--------------------------|
| RNF-01 | Autenticación basada en JWT con token de acceso y refresh token con rotación. | Login devuelve ambos tokens; usar un refresh token reutilizado lo rechaza el servidor. |
| RNF-02 | Contraseñas almacenadas con hash (BCrypt) — nunca en texto plano. | Inspección de la colección `users` no muestra contraseñas legibles. |
| RNF-03 | Endpoints GraphQL y REST protegidos por Spring Security; las operaciones públicas (registro, login, contenido público) son la excepción explícita. | Una petición sin token a `me` retorna error 401/GraphQL error. |
| RNF-04 | Autorización por rol (DEVELOPER, RECRUITER, MODERATOR, ADMIN) aplicada en servidor; el cliente no es fuente de verdad de permisos. | Un DEVELOPER invocando una mutación ADMIN recibe error de autorización. |
| RNF-05 | Las reglas de privacidad y visibilidad (perfil, CV, publicaciones por conexiones/seguidores) se validan en el backend incluso si el cliente consulta directamente por GraphQL. | Consulta directa a campos restringidos devuelve null o error. |
| RNF-06 | Rate limiting en operaciones de autenticación (login, registro, refresh) para mitigar fuerza bruta. | Más de N intentos fallidos en ventana de tiempo bloquea temporalmente. |
| RNF-07 | Tokens y secretos (JWT_SECRET, credenciales de Cloudinary y MongoDB) solo en variables de entorno, nunca en el código fuente. | El repositorio no contiene secretos; existen `env.local.example` y `.gitignore` adecuados. |
| RNF-08 | Validación de entrada en todas las mutaciones/métodos (tamaños, formatos, enums, coordenadas geográficas). | Enviar enums inválidos o coordenadas fuera de rango retorna error de validación. |
| RNF-09 | Sanitización/limitación de contenido subido: solo tipos MIME permitidos, tamaño máximo configurable, almacenamiento en Cloudinary (no en disco del servidor). | Subir un `.exe` o un archivo mayor al límite es rechazado. |
| RNF-10 | Un usuario suspendido o baneado no puede ejecutar operaciones de escritura (publicar, comentar, responder, postular, enviar mensajes). | Cuenta suspendida recibe error al intentar crear un post. |

---

## 2. Rendimiento

| Código | Requerimiento | Criterio de verificación |
|--------|---------------|--------------------------|
| RNF-11 | Tiempo de respuesta de queries GraphQL y REST simples (perfil, feed página 1, lista de vacantes) menor a 500 ms en condiciones normales. | Medición con herramienta de carga; percentil 95 bajo el umbral. |
| RNF-12 | Uso de índices MongoDB en campos de consulta frecuentes (email, `autorId+createdAt`, `postId+parentCommentId`, `usuarioId+contenidoId+tipoContenido` unique, coordenadas para mapa). | `explain()` de las consultas calientes muestra índice utilizado. |
| RNF-13 | Paginación obligatoria en listados de gran volumen (feed, preguntas, postulaciones, notificaciones, vacantes); prohibido devolver colecciones completas sin límite. | Feed responde máximo N ítems por petición (cursor o número de página). |
| RNF-14 | El mapa laboral debe responder agregando empresas/vacantes activas de forma eficiente (consulta agregada, no N+1 por empresa). | La carga del mapa con >100 empresas no genera más de un round-trip de agregación. |
| RNF-15 | Operaciones de escritura de un solo usuario deben ser no bloqueantes para el resto del sistema (sin locks globales). | Publicaciones concurrentes de distintos usuarios no se serializan. |

---

## 3. Disponibilidad y despliegue

| Código | Requerimiento | Criterio de verificación |
|--------|---------------|--------------------------|
| RNF-16 | El backend debe estar desplegado en Railway con despliegue continuo desde el repositorio principal. | Un push a la rama principal dispara el build y despliegue. |
| RNF-17 | Build reproducible con Docker (Dockerfile incluido) y Maven Wrapper (`mvnw`) sin requerir Maven global. | `docker build` y `./mvnw package` funcionan en máquina limpia. |
| RNF-18 | Health check disponible para monitoreo del servicio. | Endpoint de salud responde 200 cuando la app está arriba. |
| RNF-19 | La base de datos (MongoDB Atlas) debe usar conexión TLS y credenciales rotadas, con backup automático de Atlas. | URI de conexión usa `mongodb+srv://` con TLS; backups configurados en Atlas. |
| RNF-20 | Configuración por entorno (local, CI, producción) mediante variables de entorno, sin editar código entre entornos. | Mismo artefacto corre local y en producción con distintas variables. |

---

## 4. Escalabilidad

| Código | Requerimiento | Criterio de verificación |
|--------|---------------|--------------------------|
| RNF-21 | Arquitectura sin estado (stateless) en el backend: los tokens JWT sostienen la sesión, no la memoria del servidor. | Reiniciar el contenedor no corta sesiones válidas. |
| RNF-22 | Capacidad de horizontal scaling del backend detrás del balanceador de Railway (múltiples instancias sin coordinación local). | Escalar a 2 instancias no rompe operaciones. |
| RNF-23 | El modelo de datos MongoDB permite crecimiento de usuarios/publicaciones sin migraciones monolíticas (documentos autodescriptivos, versionados cuando aplique). | Agregar un campo opcional no requiere migración de datos existentes. |
| RNF-24 | Subida de archivos delegada a Cloudinary (almacenamiento externo), no al filesystem del servidor. | El contenedor no acumula archivos de usuario en disco. |

---

## 5. Privacidad y protección de datos

| Código | Requerimiento | Criterio de verificación |
|--------|---------------|--------------------------|
| RNF-25 | La ubicación exacta de un desarrollador no es pública sin consentimiento explícito; solo se expone lo que el usuario autoriza en su configuración de privacidad. | Perfil ajeno no expone coordenadas personales por defecto. |
| RNF-26 | Solo se muestran direcciones corporativas o áreas aproximadas de empresas en el mapa; nunca domicilios particulares. | Revisión del payload del mapa no contiene domicilios privados. |
| RNF-27 | Los datos de contacto internos de anuncios EcoDev se gestionan como mensaje interno de la plataforma, sin exponer correos/teléfonos personales sin consentimiento. | El anuncio no filtra el correo real del autor en la respuesta pública. |
| RNF-28 | Los reportes y acciones de moderación quedan registrados en auditoría (`audit_logs`) con actor, acción y fecha. | Cada acción de moderación genera un registro de auditoría. |
| RNF-29 | Un usuario puede eliminar su cuenta y sus datos personales dejan de ser consultables públicamente. | Tras la eliminación, su perfil no responde públicamente. |

---

## 6. Usabilidad de la API

| Código | Requerimiento | Criterio de verificación |
|--------|---------------|--------------------------|
| RNF-30 | API GraphQL principal en `/api/v1/graphql` con esquema versionado en módulos (`graphql/modules/*.graphqls`) y GraphiQL habilitado para desarrollo. | GraphiQL carga y ejecuta queries de prueba. |
| RNF-31 | API REST complementaria documentada con Swagger/OpenAPI (`/swagger-ui.html`, `/v3/api-docs`). | Swagger muestra las operaciones REST con sus DTOs. |
| RNF-32 | Respuestas de error consistentes: GraphQL errors con mensaje claro y código; REST con `ApiResponse` estandarizado. | Todos los errores devuelven estructura uniforme, no HTML crudo. |
| RNF-33 | Paginación consistente en REST (objeto `PaginationResponse`) y GraphQL (cursor o página + total). | Feed, vacantes y notificaciones usan el mismo patrón de paginación. |
| RNF-34 | Suscripciones GraphQL por WebSocket para eventos en tiempo real (notificaciones, estado de conexión). | Suscripción activa recibe eventos sin polling. |

---

## 7. Mantenibilidad y calidad

| Código | Requerimiento | Criterio de verificación |
|--------|---------------|--------------------------|
| RNF-35 | Arquitectura hexagonal (puertos y adaptadores) con separación domain / application / adapter; las dependencias apuntan hacia adentro. | El módulo `domain` no importa clases de Spring ni MongoDB. |
| RNF-36 | Organización en vertical slicing por módulo (auth, users, post, connection, story, reel, comment, reaction, notification, talent_search, etc.). | Cada funcionalidad vive en su paquete de módulo, no en un "services" global. |
| RNF-37 | Cobertura de pruebas automáticas: al menos pruebas unitarias de servicios de dominio crítico (auth, visibilidad, postulaciones, moderación) y una prueba de integración de arranque de la aplicación. | `./mvnw test` pasa en CI. |
| RNF-38 | Integración continua: build + tests automáticos en cada push/PR (GitHub Actions). | El workflow de CI se ejecuta y falla si los tests fallan. |
| RNF-39 | Código sin secretos, con `.gitignore` adecuado (logs, target, .env) y variables de entorno documentadas en `env.local.example`. | El repositorio no contiene `.env` real ni logs versionados. |
| RNF-40 | Documentación técnica actualizada (docs en `doc/`) coherente con el estado real del código. | Estado de implementación en `06-IMPLEMENTACION.md` refleja los módulos existentes. |

---

## 8. Tiempo real

| Código | Requerimiento | Criterio de verificación |
|--------|---------------|--------------------------|
| RNF-41 | Los eventos de tiempo real (notificación recibida, cambio de estado de conexión) se emiten solo al usuario destino autorizado. | Un usuario no recibe eventos de otro usuario. |
| RNF-42 | Las suscripciones de WebSocket requieren autenticación (token) al conectarse. | Conexión WS sin token válido es rechazada. |
| RNF-43 | El cierre de sesión o expiración del token invalida las suscripciones abiertas del usuario. | Tras logout, la suscripción no emite más eventos. |

---

## Resumen de cobertura

| Categoría | Cantidad | Rangos |
|-----------|----------|--------|
| Seguridad | 10 | RNF-01…10 |
| Rendimiento | 5 | RNF-11…15 |
| Disponibilidad y despliegue | 5 | RNF-16…20 |
| Escalabilidad | 4 | RNF-21…24 |
| Privacidad y datos | 5 | RNF-25…29 |
| Usabilidad de la API | 5 | RNF-30…34 |
| Mantenibilidad y calidad | 6 | RNF-35…40 |
| Tiempo real | 3 | RNF-41…43 |
| **Total** | **43** | |
