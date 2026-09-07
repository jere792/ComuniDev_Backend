# COMUNIDEV — Reglas de Visibilidad y Relaciones

## Reglas transversales de visibilidad

La privacidad debe evaluarse siempre desde el punto de vista del dueño del contenido o perfil (owner) frente al visitante (viewer).

### Niveles de visibilidad

| Valor | Significado |
|-------|-------------|
| PUBLICO | Cualquier usuario, o visitante si se habilita, puede visualizar el recurso. |
| SEGUIDORES | Solo usuarios que siguen al dueño del contenido/perfil pueden visualizarlo. |
| CONEXIONES | Solo usuarios con conexión profesional aceptada pueden visualizarlo. |
| PRIVADO | Solo el propietario puede visualizarlo. |

### Algoritmo funcional simplificado

```
PuedeVer(ownerId, viewerId, nivelPrivacidad):

1. Si ownerId es igual a viewerId, permitir.
2. Si existe bloqueo entre ownerId y viewerId, denegar.
3. Si nivel es PUBLICO, permitir.
4. Si nivel es SEGUIDORES, permitir solo si viewer sigue a owner.
5. Si nivel es CONEXIONES, permitir solo si existe conexión ACTIVA entre ambos.
6. Si nivel es PRIVADO, denegar.
```

### Perfil parcial y perfil completo

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

## Relaciones principales

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
