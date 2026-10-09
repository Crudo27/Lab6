package vista;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import modelo.Compra;
import modelo.ItemCarrito;
import modelo.Producto;
import modelo.Resena;

public class TiendaVista {
	private Scanner scanner;

	public TiendaVista(Scanner scanner)
	{
		this.scanner = scanner;
	}

	public String solicitarTexto(String mensaje)
	{
		System.out.print(mensaje);
		if(!scanner.hasNextLine())
		{
			throw new IllegalArgumentException("Entrada incompleta.");
		}
		return scanner.nextLine();
	}

	public String solicitarOpcion()
	{
		System.out.print("Opción: ");
		if(!scanner.hasNextLine())
		{
			return "0";
		}
		return scanner.nextLine();
	}

	public int solicitarNumero(String mensaje)
	{
		try
		{
			return Integer.parseInt(solicitarTexto(mensaje).trim());
		}
		catch(NumberFormatException e)
		{
			throw new IllegalArgumentException("Debes ingresar un número entero.");
		}
	}

	public BigDecimal solicitarPrecio()
	{
		try
		{
			return new BigDecimal(solicitarTexto("Precio S/: ").trim().replace(',', '.'));
		}
		catch(NumberFormatException e)
		{
			throw new IllegalArgumentException("Precio inválido.");
		}
	}

	public void mostrarMenu()
	{
		mostrarMensaje("\nCARRITO DE COMPRAS");
		mostrarMensaje("1. Agregar producto");
		mostrarMensaje("2. Listar productos");
		mostrarMensaje("3. Agregar al carrito");
		mostrarMensaje("4. Ver carrito");
		mostrarMensaje("5. Eliminar del carrito");
		mostrarMensaje("6. Aplicar descuento");
		mostrarMensaje("7. Calcular envío");
		mostrarMensaje("8. Realizar compra");
		mostrarMensaje("9. Historial de compras");
		mostrarMensaje("10. Registrarse");
		mostrarMensaje("11. Iniciar sesión");
		mostrarMensaje("12. Cerrar sesión");
		mostrarMensaje("13. Agregar reseña y calificación");
		mostrarMensaje("14. Ver reseñas");
		mostrarMensaje("0. Salir");
	}

	public void mostrarProductos(List<Producto> productos)
	{
		if(productos.isEmpty())
		{
			mostrarMensaje("No hay productos.");
		}
		for(Producto producto: productos)
		{
			mostrarMensaje(producto.getId() + " | " + producto.getNombre()
					+ " | S/ " + producto.getPrecio().toPlainString());
		}
	}

	public void mostrarItems(List<ItemCarrito> items)
	{
		if(items.isEmpty())
		{
			mostrarMensaje("El carrito está vacío.");
		}
		for(ItemCarrito item: items)
		{
			mostrarMensaje(item.getProducto().getId() + " | " + item.getProducto().getNombre()
					+ " | Cantidad: " + item.getCantidad()
					+ " | S/ " + item.getSubtotal().toPlainString());
		}
	}

	public void mostrarTotales(BigDecimal subtotal, BigDecimal descuento, BigDecimal envio, BigDecimal total)
	{
		mostrarMensaje("Subtotal: S/ " + subtotal.toPlainString());
		mostrarMensaje("Descuento: S/ " + descuento.toPlainString());
		mostrarMensaje("Envío: S/ " + envio.toPlainString());
		mostrarMensaje("Total: S/ " + total.toPlainString());
	}

	public void mostrarCompra(Compra compra)
	{
		mostrarMensaje("Compra #" + compra.getId() + " | "
				+ compra.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
		mostrarItems(compra.getProductos());
		mostrarTotales(compra.getSubtotal(), compra.getDescuento(), compra.getEnvio(), compra.getTotal());
	}

	public void mostrarHistorial(List<Compra> compras)
	{
		if(compras.isEmpty())
		{
			mostrarMensaje("No tienes compras registradas.");
		}
		for(Compra compra: compras)
		{
			mostrarCompra(compra);
		}
	}

	public void mostrarResenas(List<Resena> resenas, double promedio)
	{
		if(resenas.isEmpty())
		{
			mostrarMensaje("El producto no tiene reseñas.");
			return;
		}
		for(Resena resena: resenas)
		{
			mostrarMensaje(resena.getNombreUsuario() + " | " + resena.getCalificacion()
					+ "/5 | " + resena.getComentario());
		}
		mostrarMensaje(String.format(Locale.ROOT, "Promedio: %.2f/5", promedio));
	}

	public void mostrarMensaje(String mensaje)
	{
		System.out.println(mensaje);
	}
}
