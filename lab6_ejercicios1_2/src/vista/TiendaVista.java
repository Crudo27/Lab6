package vista;

import java.util.ArrayList;
import java.util.Scanner;
import modelo.Compra;
import modelo.ItemCarrito;
import modelo.Producto;
import modelo.Resena;
import modelo.Usuario;

public class TiendaVista {
    private Scanner scanner;

    public TiendaVista()
    {
        scanner = new Scanner(System.in);
    }

    public String solicitarTexto(String mensaje)
    {
        System.out.print(mensaje);
        if(!scanner.hasNextLine())
        {
            return null;
        }
        return scanner.nextLine();
    }

    public int solicitarNumero(String mensaje)
    {
        String texto = solicitarTexto(mensaje);
        if(texto == null)
        {
            throw new IllegalArgumentException("Entrada incompleta.");
        }
        return Integer.parseInt(texto.trim());
    }

    public double solicitarPrecio()
    {
        String texto = solicitarTexto("Precio S/: ");
        if(texto == null)
        {
            throw new IllegalArgumentException("Entrada incompleta.");
        }
        return Double.parseDouble(texto.trim().replace(',', '.'));
    }

    public void mostrarMenu()
    {
        System.out.println("\nCARRITO DE COMPRAS");
        System.out.println("1. Agregar producto");
        System.out.println("2. Listar productos");
        System.out.println("3. Agregar al carrito");
        System.out.println("4. Ver carrito");
        System.out.println("5. Eliminar del carrito");
        System.out.println("6. Aplicar descuento");
        System.out.println("7. Seleccionar envío");
        System.out.println("8. Realizar compra");
        System.out.println("9. Historial de compras");
        System.out.println("10. Registrarse");
        System.out.println("11. Iniciar sesión");
        System.out.println("12. Cerrar sesión");
        System.out.println("13. Agregar reseña");
        System.out.println("14. Ver reseñas");
        System.out.println("0. Salir");
    }

    public void mostrarUsuario(Usuario usuario)
    {
        if(usuario == null)
        {
            mostrarMensaje("Usuario: sin sesión");
        }
        else
        {
            mostrarMensaje("Usuario: " + usuario.getNombre());
        }
    }

    public void mostrarProductos(ArrayList<Producto> productos)
    {
        if(productos.isEmpty())
        {
            mostrarMensaje("No hay productos.");
        }
        for(Producto producto : productos)
        {
            System.out.printf("%d | %s | S/ %.2f%n", producto.getId(), producto.getNombre(), producto.getPrecio());
        }
    }

    public void mostrarItems(ArrayList<ItemCarrito> carrito)
    {
        if(carrito.isEmpty())
        {
            mostrarMensaje("El carrito está vacío.");
        }
        for(ItemCarrito item : carrito)
        {
            System.out.printf("%d | %s | Cantidad: %d | S/ %.2f%n",
                    item.getProducto().getId(), item.getProducto().getNombre(),
                    item.getCantidad(), item.getSubtotal());
        }
    }

    public void mostrarTotales(double subtotal, double descuento, double envio, double total)
    {
        System.out.printf("Subtotal: S/ %.2f%n", subtotal);
        System.out.printf("Descuento: S/ %.2f%n", descuento);
        System.out.printf("Envío: S/ %.2f%n", envio);
        System.out.printf("Total: S/ %.2f%n", total);
    }

    public void mostrarCompra(Compra compra)
    {
        mostrarMensaje("Compra #" + compra.getId());
        mostrarItems(compra.getProductos());
        mostrarTotales(compra.getSubtotal(), compra.getDescuento(), compra.getEnvio(), compra.getTotal());
    }

    public void mostrarHistorial(ArrayList<Compra> compras)
    {
        if(compras.isEmpty())
        {
            mostrarMensaje("No tienes compras registradas.");
        }
        for(Compra compra : compras)
        {
            mostrarCompra(compra);
        }
    }

    public void mostrarResenas(ArrayList<Resena> resenas, double promedio)
    {
        if(resenas.isEmpty())
        {
            mostrarMensaje("El producto no tiene reseñas.");
            return;
        }
        for(Resena resena : resenas)
        {
            mostrarMensaje(resena.getNombreUsuario() + " | " + resena.getCalificacion()
                    + "/5 | " + resena.getComentario());
        }
        System.out.printf("Promedio: %.2f/5%n", promedio);
    }

    public void mostrarMensaje(String mensaje)
    {
        System.out.println(mensaje);
    }

    public void cerrarScanner()
    {
        scanner.close();
    }
}
