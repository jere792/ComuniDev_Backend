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
| `companies` | `companies`, `company` | `createCompany`, `updateCompany` |
| `vacancies` | `vacancies`, `vacancy`, `vacanciesByCompany`, `applications`, `myApplications` | `createVacancy`, `updateVacancy`, `applyToVacancy`, `updateApplicationStatus`, `withdrawApplication` |
| `messaging` | `conversations`, `messages` | `createConversation`, `sendMessage`, `markMessageAsRead` |
| `questions` | `questions`, `question`, `answers` | `createQuestion`, `createAnswer`, `acceptAnswer`, `voteQuestion`, `voteAnswer` |
| `moderation` | `reports`, `moderationActions`, `userBans` | `createReport`, `assignReport`, `takeModerationAction`, `banUser`, `unbanUser` |
| `support` | `supportTickets`, `myTickets`, `ticket` | `createTicket`, `updateTicketStatus`, `assignTicket` |
| `points` | `userPoints`, `pointHistory`, `badges` | — |
| `plans` | `plans`, `mySubscription` | `subscribe`, `cancelSubscription` |
| `ecotech` | `ecotechPosts`, `ecotechPost`, `recyclingPoints`, `ecotechTransactions` | `createEcotechPost`, `initTransaction`, `confirmTransaction` |

## Orden sugerido

1. **messaging** — el frontend ya tiene `messenger.ts`/`messenger.html` con la UI lista.
2. **vacancies** / **companies** — es el módulo de reclutamiento, que es el otro rol del producto.
3. **questions** — comunidad técnica.
4. **moderation** — necesario antes de abrir al público.
5. **points**, **plans**, **support**, **ecotech** — pendientes de priorizar.

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
