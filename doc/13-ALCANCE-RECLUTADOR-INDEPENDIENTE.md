# Decisión: reclutador independiente (sin empresa)

**Fecha:** 2026-10-12
**Estado:** aplicada
**Contradice:** `03-ENTIDADES.md` (`:232`, `:233`, `:1016`), HU-38, HU-39

## Contexto

El modelo documentado asume que **toda vacante pertenece a una empresa**:

- `03-ENTIDADES.md:233` — "Solo un reclutador vinculado y autorizado puede crear
  o gestionar vacantes de una empresa".
- `03-ENTIDADES.md:1016` — "Solo reclutadores autorizados de la empresa pueden
  crear, editar, pausar o cerrar vacantes".
- HU-38 / HU-39 — el reclutador registra su empresa y un administrador la valida
  antes de poder publicar.

Eso excluye a quien contrata desarrolladores sin tener una organización detrás:
consultores independientes, freelancers que arman un equipo, personas que necesitan
un developer para un proyecto puntual.

## Decisión

`companyId` en la vacante **es opcional**. Un reclutador independiente publica a su
nombre y la oferta queda atribuida únicamente a `recruiterId`.

Dato a favor: el esquema GraphQL original (`vacancies.graphqls`) ya declaraba
`companyId: String` **sin** `!`. La obligatoriedad la introdujo la validación de
servicio al implementar el módulo, no el diseño.

## Qué cambió

**Backend**

- `VacancyService.createVacancy` ya no exige `companyId`; si viene, debe existir.
- `validarParaActiva` ya no exige empresa entre los requisitos de una vacante activa.
- `createVacancy` ahora **sí** exige `recruiterId`: con la empresa opcional es la
  única atribución que queda, y sin ella la vacante sería huérfana.
- `recalcularVacantesActivas` ya ignoraba `null`, así que el contador de la empresa
  simplemente no se toca en vacantes independientes.

**Frontend**

- `/recruiter/offers/new` — casilla "Soy independiente: publicar sin empresa". Al
  marcarla se omite el selector; la tarjeta muestra el nombre de la persona.
- `/register` — los campos de empresa pasaron a ser **opcionales**, tras la casilla
  "Tengo una empresa". Se desmarcada por defecto, así que el registro es más corto.
- Tarjeta de oferta: `offer.companyName ?? userName` (el `userName` de localStorage,
  que se actualiza al editar el perfil).

## Consecuencias conocidas

- **No aparece en el mapa (HU-46).** El mapa muestra empresas con vacantes activas;
  un independiente no tiene empresa, así que queda fuera. Es coherente, pero conviene
  decidir algún día si el mapa debe mostrar también personas.
- **Sin distintivo de empresa.** El desarrollador no puede distinguir a simple vista
  si detrás hay una organización validada o una persona. HU-39 existe precisamente
  para dar esa confianza, y aquí no aplica.
- **`Company.recruiterId` sigue en 1:1.** Ver nota abajo.

## Limitación de seguridad conocida (no resuelta)

**Sin JWT no hay identidad confiable en el servidor.** El `recruiterId` lo manda el
cliente desde `localStorage`, así que cualquiera puede suplantar a otro reclutador
cambiando ese valor en la consola:

- `myCompany(recruiterId)` → lee y devuelve la empresa de quien diga ser.
- `updateCompany(id, ...)` → edita cualquier empresa por su id.
- `Company.recruiterId` con índice único solo impide dos empresas para el **mismo
  id reclamado**, no impide la suplantación.

Toda validación basada en un id enviado por el cliente es decorativa. La única
solución real es autenticar en el servidor (JWT), que quedó fuera de alcance a
petición explícita.

**Hueco sí cerrado en esta iteración:** el formulario de "Mis ofertas" listaba
todas las empresas (`companies` → `findAll()`), lo que permitía publicar una
vacante a nombre de la empresa de otro reclutador. Ahora solo se ofrece **la
empresa propia** (`myCompany`), o la opción de publicar como independiente.
Sigue siendo cliente, así que con JWT de por medio debería reforzarse en el
servidor.

## Pendiente relacionado

`03-ENTIDADES.md:232` describe la relación reclutador–empresa como **muchos a
muchos** ("un reclutador puede pertenecer a una o varias empresas", vía
`recruiter_profiles.empresas`). La implementación actual usa `Company.recruiterId`
con índice único: **una empresa por reclutador, un dueño por empresa**.

Es una simplificación respecto al diseño. Si se necesita que un reclutador trabaje
para varias empresas, hay que migrar a la relación many-to-many del documento.
