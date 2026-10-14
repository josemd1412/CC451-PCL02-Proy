# SafeMine Hands-Free

Aplicación de alerta temprana para trabajadores de minería y construcción: reporte de riesgos
por voz y semáforo de alto contraste, sin quitarse el EPP.

## Estructura

Proyecto Android único (Kotlin + Jetpack Compose), separado por capas dentro de
`app/src/main/java/com/example/cc451_pcl02_proy/`:

| Paquete | Contenido |
| :--- | :--- |
| `ui/` | Frontend: pantallas, componentes, tema y navegación |
| `domain/` | Modelos y reglas (severidad, reportes) |
| `data/` | Repositorios y datos simulados (luego Supabase y LLM) |

## Estado

PCL02: solo diseño de interfaz con datos simulados.

## Licencia

MIT. Ver `LICENSE`.
