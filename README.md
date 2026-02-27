# ReqTrace-Java
## Requirements Traceability & Engineering Validation Bridge

**ReqTrace-Java** er et verktøy designet for å bygge bro mellom ustrukturerte krav i regneark og formelle systemmodeller i et Model-Based Systems Engineering (MBSE) miljø. Prosjektet demonstrerer hvordan man bruker bransjestandarden ReqIF (Requirements Interchange Format) for å sikre sporbarhet og dataintegritet i komplekse ingeniørprosjekter.

## Problemstilling & Løsning
I store prosjekter (som hos Kongsberg) oppstår det ofte et gap mellom ingeniører som skriver krav i Excel og systemarkitekter som modellerer i verktøy som Cameo eller MagicDraw.

**Dette verktøyet løser dette ved å:**

- 1. Validere rådata via en VBA-basert "Linter" i Excel.

- 2. Konvertere og parse data til standardisert ReqIF/XML-format.

- 3. Analysere sporbarhet i Java for å identifisere "Orphan Requirements" (krav uten tilhørende systemkomponent).