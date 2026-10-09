# Laboratorio 6 - MVC en Java

**Curso:** Lenguajes de Programación III  
**Universidad:** Universidad Católica de Santa María

## Contenido

El repositorio contiene dos proyectos de consola desarrollados en Java:

- `lab6_actividad1`: registro y gestión de pedidos, búsqueda, actualización, estados e historial.
- `lab6_ejercicios1_2`: catálogo de productos, carrito de compras, descuentos, envíos, usuarios, compras y reseñas.
- `pruebas`: verificaciones del funcionamiento de ambos proyectos.
- `docs`: archivos del informe y capturas.

Los proyectos están organizados en los paquetes `modelo`, `vista`, `controlador` y `principal`.

## Requisitos

- JDK 17 o superior.
- Eclipse IDE (opcional).

## Ejecutar en Eclipse

1. Importar `lab6_actividad1` y `lab6_ejercicios1_2` como proyectos Java.
2. Seleccionar el proyecto que se desea ejecutar.
3. Ejecutar `principal.Main` con **Run As > Java Application**.

## Ejecutar desde terminal

Entrar a la carpeta de uno de los proyectos y ejecutar:

```bash
javac -encoding UTF-8 --release 17 -d bin @fuentes.txt
java -cp bin principal.Main
```

Repetir los comandos en la carpeta del otro proyecto para ejecutarlo.

## Pruebas

Después de compilar ambos proyectos, desde la raíz del repositorio:

```bash
mkdir -p pruebas/bin
javac -encoding UTF-8 --release 17 -cp lab6_actividad1/bin:lab6_ejercicios1_2/bin -d pruebas/bin pruebas/Verificacion.java
java -cp lab6_actividad1/bin:lab6_ejercicios1_2/bin:pruebas/bin Verificacion
```

En Windows, reemplazar los dos puntos del classpath por punto y coma.

## Observaciones

Los datos se mantienen en memoria durante la ejecución. La aplicación no utiliza base de datos ni procesa pagos reales. El registro y acceso de usuarios son una demostración local; las contraseñas no se almacenan de forma segura para un uso en producción.
