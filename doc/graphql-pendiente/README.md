# Módulos GraphQL pendientes de implementar

Estos módulos estaban declarados en `src/main/resources/graphql/modules/` pero **no
tenían resolvers en el backend**. Al estar en el esquema activo, cualquier consulta
sobre ellos fallaba en runtime con:

```
NullValueInNonNullableField ... 'companies' ... data: null
```

Se movieron aquí para que el esquema que sirve el backend refleje lo que realmente
existe, y no se pierda el diseño ya escrito.

**No los borres:** son la especificación de lo que falta construir. Cuando se
implemente un módulo, devuélvelo a `src/main/resources/graphql/modules/` junto con
su resolver, y verifique que compile con `./mvnw compile`.

## Qué falta en cada módulo

| Módulo | Queries sin resolver | Mutations sin resolver |
|---|---|---|
| `applications` | `applications`, `myApplications` | `applyToVacancy`, `updateApplicationStatus`, `withdrawApplication` |
| `messaging` | `conversations`, `messages` | `createConversation`, `sendMessage`, `markMessageAsRead` |
| `questions` | `questions`, `question`, `answers` | `createQuestion`, `createAnswer`, `acceptAnswer`, `voteQuestion`, `voteAnswer` |
| `moderation` | `reports`, `moderationActions`, `userBans` | `createReport`, `assignReport`, `takeModerationAction`, `banUser`, `unbanUser` |
| `support` | `supportTickets`, `myTickets`, `ticket` | `createTicket`, `updateTicketStatus`, `assignTicket` |
| `points` | `userPoints`, `pointHistory`, `badges` | — |
| `plans` | `plans`, `mySubscription` | `subscribe`, `cancelSubscription` |
| `ecotech` | `ecotechPosts`, `ecotechPost`, `recyclingPoints`, `ecotechTransactions` | `createEcotechPost`, `initTransaction`, `confirmTransaction` |

## Orden sugerido

1. **applications** — postulaciones (HU-43/HU-44). Ya existe `vacancies`; es su continuación directa.
2. **messaging** — el frontend ya tiene `messenger.ts`/`messenger.html` con la UI lista.
3. **questions** — comunidad técnica.
4. **moderation** — necesario antes de abrir al público.
5. **points**, **plans**, **support**, **ecotech** — pendientes de priorizar.

## Ya implementados

| Módulo | Estado | Notas |
|---|---|---|
| `companies` | ✅ | `CompanyGraphQLResolver`. `estadoVerificacion` se guarda pero **no se valida** al publicar: HU-39 exige que el admin verifique la empresa y aún no existe ese flujo (ni JWT). |
| `vacancies` | ✅ | `VacancyGraphQLResolver`. RN-21 (auto-vencer), RN-22 (fecha no pasada), CA-HU-40 y CA-HU-42 están implementados. HU-41 manual se agregó como argumento `estado` a `updateVacancy`. |

**Diferencias con la especificación original:**

- `vacancies.ubicacion` quedó como `String` (texto libre), no como objeto con
  coordenadas. El esquema original ya lo exponía así. El GeoJSON que pide el
  mapa (HU-46) queda pendiente.
- La validación de empresa verificada (HU-39) no se aplica todavía.
- La exclusión de vacantes vencidas se normaliza **al leer** (en `VacancyService`),
  no con un `@Scheduled`. Suficiente mientras no existan postulaciones.

## Al implementar

Cada módulo necesita, siguiendo la arquitectura hexagonal del proyecto:

- `domain/` — la entidad (`@Document`), con los índices que corresponda.
- `application/port/out/` — el repositorio (puerto).
- `application/port/in/` + `application/service/` — los casos de uso.
- `adapter/out/mongodb/` — `MongoXRepository` (Spring Data) + `MongoXAdapter` (implementa el puerto).
- `adapter/in/graphql/XGraphQLResolver` — `@Controller` con `@QueryMapping` / `@MutationMapping`.

Consultas relevantes del frontend que hoy no tienen backend:

- `developer-profile-graphql.service.ts` → **ya implementado** (`developerProfileByUserId`, `createDeveloperProfile`, `updateDeveloperProfile`).

## También pendiente (requiere autenticación)

Quedan en `modules/` pero sin resolver porque necesitan leer el `userId` del JWT:

- `users.myProfile`
- `developer-profile.myProfile`
- `recruiter-profile.myRecruiterProfile`
