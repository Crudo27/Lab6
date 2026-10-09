# Actualización del Laboratorio 6 - Solo ejercicios

Fecha de revisión: 08/10/2026.

La carpeta `lab6_actividad1` se mantiene sin modificaciones. Incluye las actividades 1, 2 y 3 y su conteo original con `TreeMap`.

## Ejercicio 1: carrito de compras

- `Producto`, `ItemCarrito`, `Compra`, `TiendaModelo`, `TiendaVista` y `TiendaControlador`.
- Registro de productos, adición de cantidades, eliminación del carrito, descuento, envío, total y confirmación de compra.
- Se usa `double` y operaciones aritméticas sencillas en lugar de `BigDecimal`; los importes se muestran con dos decimales.

## Ejercicio 2: usuarios y reseñas

- Registro, inicio/cierre de sesión, historial de compras por usuario, calificación de 1 a 5 y restricción de una reseña por usuario/producto comprado.
- El menú, la entrada de datos y la coordinación de usuarios se integran en una sola vista y un controlador.
- La verificación de contraseña con `String.equals` es únicamente para **una simulación académica local**. No es apta para una plataforma real.

## Código y pruebas

- `src/modelo`: objetos y datos de la tienda.
- `src/vista`: lectura con Scanner y presentación en consola.
- `src/controlador`: manejo del menú con `switch`.
- `src/principal`: instanciación de MVC.
- El antiguo archivo `module-info.java` de ejercicios se retiró porque no se necesita modularidad en este proyecto didáctico.
- `pruebas/Verificacion.java` verifica las actividades intactas y los ejercicios nuevos; resultado local: **32 verificaciones correctas** con Java 17.

El informe Word y PDF corregidos se generan por separado; los documentos binarios antiguos de `docs/` no se sustituyen en este commit.
