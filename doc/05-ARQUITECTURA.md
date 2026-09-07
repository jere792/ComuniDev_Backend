# COMUNIDEV — Arquitectura

## Arquitectura hexagonal y vertical slicing

La arquitectura será hexagonal y se organizará por funcionalidades verticales. Cada módulo debe encapsular su dominio, casos de uso, puertos y adaptadores.

### Capas hexagonales

| Capa | Responsabilidad |
|------|-----------------|
| Dominio | Entidades, value objects, reglas puras, eventos de dominio y excepciones de negocio. |
| Aplicación | Casos de uso, comandos, consultas, DTOs de entrada/salida y puertos. |
| Puertos de entrada | Interfaces de casos de uso invocadas desde REST, GraphQL o WebSocket. |
| Puertos de salida | Interfaces para persistencia, almacenamiento, mensajería, mapas, notificaciones y servicios externos. |
| Adaptadores de entrada | Controladores REST, resolvers GraphQL, controladores WebSocket/STOMP, jobs programados. |
| Adaptadores de salida | Repositorios MongoDB, Cloudinary, email, push notifications, proveedor de pagos, geocoding. |

### Vertical slicing sugerido

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

### Distribución de protocolos

| Tecnología | Uso recomendado |
|------------|-----------------|
| REST + Swagger/OpenAPI | Autenticación, CRUD, carga de archivos, acciones explícitas, administración, operaciones simples y documentación de endpoints. |
| GraphQL | Feed complejo, perfiles con secciones configurables, consulta de vacantes con empresa/mapa, búsqueda y dashboards. |
| WebSockets | Chat, estado de presencia, notificaciones, nueva respuesta, solicitud/aceptación de conexión y actualización de vacantes. |
