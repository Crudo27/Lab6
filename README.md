# Laboratorio 6

Lenguajes de Programación III - Universidad Católica de Santa María.

Se completaron las actividades 1, 2 y 3 de pedidos y se eligieron únicamente los ejercicios 1 y 2, integrados en un carrito con usuarios y reseñas.

## Proyectos

- [Actividad de pedidos](lab6_actividad1/src): registro, búsqueda, actualización, eliminación lógica, conteos, estados e historial.
- [Ejercicios 1 y 2](lab6_ejercicios1_2/src): productos, carrito, descuentos, envío, compras, registro, inicio de sesión y reseñas de compradores.
- [Informe PDF](docs/Laboratorio_06_MVC_UCSM.pdf).
- [Informe Word](docs/Laboratorio_06_MVC_UCSM.docx).
- [Imágenes de ejecución](docs/capturas).
- [Verificación de los modelos](pruebas/Verificacion.java).
- [Resultados de las pruebas](pruebas/resultados.txt).

## Ejecutar en Eclipse

Se necesita un JDK 17 o superior. Importar cada carpeta mediante File > Import > Existing Projects into Workspace. Configurar el JDK del proyecto si Eclipse no encuentra JavaSE-17. Ejecutar principal.Main mediante Run As > Java Application. Ambos proyectos utilizan solamente la biblioteca estándar de Java.

## Ejecutar desde la terminal

Desde lab6_actividad1:

```bash
javac -encoding UTF-8 --release 17 -d bin @fuentes.txt
java -cp bin principal.Main
```

Desde lab6_ejercicios1_2:

```bash
javac -encoding UTF-8 --release 17 -d bin @fuentes.txt
java -cp bin principal.Main
```

## Verificar los modelos

Primero compilar los dos proyectos. Desde la raíz del repositorio, en Linux o macOS:

```bash
javac -encoding UTF-8 --release 17 -cp lab6_actividad1/bin:lab6_ejercicios1_2/bin -d pruebas/bin pruebas/Verificacion.java
java -cp lab6_actividad1/bin:lab6_ejercicios1_2/bin:pruebas/bin Verificacion
```

En Windows se cambia el separador del classpath de : a ; y se encierra el classpath entre comillas. El resultado esperado es: Resultado: 53 verificaciones correctas.

## Probar el carrito

1. Agregar Teclado a S/ 120.00 y Mouse a S/ 50.00.
2. Registrar un usuario con correo válido y contraseña de al menos ocho caracteres.
3. Iniciar sesión y agregar dos teclados y un mouse.
4. Aplicar 10 % de descuento y seleccionar envío LOCAL.
5. Confirmar la compra con S. El total debe ser S/ 271.00.
6. Revisar el historial y calificar un producto comprado con un valor de 1 a 5.

## Reglas utilizadas

Los pedidos tienen un ID estable. Se eliminan cambiando su estado y se guarda una copia de cada transición a completo o eliminado. Los pedidos actuales excluyen a los eliminados.

El envío cuesta S/ 0.00 para RECOJO, S/ 10.00 para LOCAL y S/ 25.00 para NACIONAL. El descuento se aplica al subtotal, sin descontar el envío. Los importes se calculan con BigDecimal.

El carrito requiere iniciar sesión. Al cerrar sesión se vacían el carrito, el descuento y la selección de envío. El historial se filtra por usuario. Solo se permite una reseña por usuario y producto, después de una compra confirmada. Las contraseñas se guardan como hash PBKDF2 con sal aleatoria.

Los datos permanecen en memoria mientras se ejecuta el programa. No se solicita una base de datos ni persistencia entre ejecuciones. La compra es un registro local de la práctica y no procesa pagos.

Las trece imágenes muestran salidas registradas al compilar y ejecutar los programas con Java 17. Las imágenes de resultados omiten la repetición del menú para facilitar la lectura. Las transcripciones completas de compilación y ejecución se encuentran en pruebas/evidencias. Los resultados de Verificacion se conservan en pruebas/resultados.txt.

El código fuente se mantiene sin comentarios. El informe utiliza la plantilla simple UCSM del Laboratorio 5: encabezado institucional, tabla de Código, Apellidos y Nombres y Fecha, y las secciones Actividades, Ejercicios, Cuestionario y Bibliografía. Incluye el código completo de cada archivo Java y de configuración, seguido de su explicación, las evidencias de ejecución y el cuestionario con referencias científicas y DOI.
