package modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class TiendaModelo {
	private List<Producto> productos;
	private List<ItemCarrito> carrito;
	private List<Compra> compras;
	private List<Resena> resenas;
	private int porcentajeDescuento;
	private String zonaEnvio;

	public TiendaModelo()
	{
		productos = new ArrayList<>();
		carrito = new ArrayList<>();
		compras = new ArrayList<>();
		resenas = new ArrayList<>();
		zonaEnvio = "RECOJO";
	}

	public void agregarProducto(String nombre, BigDecimal precio)
	{
		if(nombre.trim().isEmpty() || precio.compareTo(BigDecimal.ZERO) <= 0
				|| precio.compareTo(new BigDecimal("1000000")) > 0)
		{
			throw new IllegalArgumentException("Nombre vacío o precio fuera de rango.");
		}
		precio = precio.setScale(2, RoundingMode.UNNECESSARY);
		productos.add(new Producto(productos.size() + 1, nombre.trim(), precio));
	}

	public Producto buscarProducto(int id)
	{
		for(Producto producto: productos)
		{
			if(producto.getId() == id)
			{
				return producto;
			}
		}
		throw new IllegalArgumentException("Producto no encontrado.");
	}

	public void agregarAlCarrito(int id, int cantidad)
	{
		Producto producto = buscarProducto(id);
		if(cantidad < 1 || cantidad > 1000)
		{
			throw new IllegalArgumentException("La cantidad debe estar entre 1 y 1000.");
		}
		for(ItemCarrito item: carrito)
		{
			if(item.getProducto().getId() == id)
			{
				if(item.getCantidad() + cantidad > 1000)
				{
					throw new IllegalArgumentException("Máximo 1000 unidades por producto.");
				}
				item.setCantidad(item.getCantidad() + cantidad);
				return;
			}
		}
		carrito.add(new ItemCarrito(producto, cantidad));
	}

	public void eliminarDelCarrito(int id)
	{
		for(int i = 0; i < carrito.size(); i++)
		{
			if(carrito.get(i).getProducto().getId() == id)
			{
				carrito.remove(i);
				return;
			}
		}
		throw new IllegalArgumentException("El producto no está en el carrito.");
	}

	public void aplicarDescuento(int porcentaje)
	{
		if(porcentaje < 0 || porcentaje > 100)
		{
			throw new IllegalArgumentException("El descuento debe estar entre 0 y 100.");
		}
		porcentajeDescuento = porcentaje;
	}

	public void seleccionarEnvio(String zona)
	{
		zona = zona.trim().toUpperCase(java.util.Locale.ROOT);
		if(!zona.equals("RECOJO") && !zona.equals("LOCAL") && !zona.equals("NACIONAL"))
		{
			throw new IllegalArgumentException("Elige RECOJO, LOCAL o NACIONAL.");
		}
		zonaEnvio = zona;
	}

	public BigDecimal calcularSubtotal()
	{
		BigDecimal subtotal = BigDecimal.ZERO;
		for(ItemCarrito item: carrito)
		{
			subtotal = subtotal.add(item.getSubtotal());
		}
		return subtotal.setScale(2);
	}

	public BigDecimal calcularDescuento()
	{
		return calcularSubtotal().multiply(BigDecimal.valueOf(porcentajeDescuento))
				.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
	}

	public BigDecimal calcularEnvio()
	{
		if(carrito.isEmpty() || zonaEnvio.equals("RECOJO"))
		{
			return new BigDecimal("0.00");
		}
		if(zonaEnvio.equals("LOCAL"))
		{
			return new BigDecimal("10.00");
		}
		return new BigDecimal("25.00");
	}

	public BigDecimal calcularTotal()
	{
		return calcularSubtotal().subtract(calcularDescuento()).add(calcularEnvio());
	}

	public Compra realizarCompra(Usuario usuario)
	{
		if(usuario == null)
		{
			throw new IllegalArgumentException("Primero debes iniciar sesión.");
		}
		if(carrito.isEmpty())
		{
			throw new IllegalArgumentException("El carrito está vacío.");
		}
		Compra compra = new Compra(compras.size() + 1, usuario.getId(), carrito,
				calcularSubtotal(), calcularDescuento(), calcularEnvio());
		compras.add(compra);
		vaciarCarrito();
		return compra;
	}

	public void vaciarCarrito()
	{
		carrito.clear();
		porcentajeDescuento = 0;
		zonaEnvio = "RECOJO";
	}

	public List<Compra> historialUsuario(Usuario usuario)
	{
		List<Compra> historial = new ArrayList<>();
		for(Compra compra: compras)
		{
			if(compra.getUsuarioId() == usuario.getId())
			{
				historial.add(compra);
			}
		}
		return historial;
	}

	public boolean productoComprado(Usuario usuario, int id)
	{
		for(Compra compra: historialUsuario(usuario))
		{
			for(ItemCarrito item: compra.getProductos())
			{
				if(item.getProducto().getId() == id)
				{
					return true;
				}
			}
		}
		return false;
	}

	public void agregarResena(Usuario usuario, int id, int calificacion, String comentario)
	{
		buscarProducto(id);
		if(usuario == null || !productoComprado(usuario, id))
		{
			throw new IllegalArgumentException("Solo puedes reseñar productos que hayas comprado.");
		}
		if(calificacion < 1 || calificacion > 5 || comentario.trim().isEmpty())
		{
			throw new IllegalArgumentException("Ingresa una calificación de 1 a 5 y una reseña.");
		}
		for(Resena resena: resenas)
		{
			if(resena.getProductoId() == id && resena.getUsuarioId() == usuario.getId())
			{
				throw new IllegalArgumentException("Ya reseñaste este producto.");
			}
		}
		resenas.add(new Resena(id, usuario, calificacion, comentario.trim()));
	}

	public List<Resena> resenasProducto(int id)
	{
		buscarProducto(id);
		List<Resena> resultado = new ArrayList<>();
		for(Resena resena: resenas)
		{
			if(resena.getProductoId() == id)
			{
				resultado.add(resena);
			}
		}
		return resultado;
	}

	public double promedioProducto(int id)
	{
		List<Resena> resultado = resenasProducto(id);
		int suma = 0;
		for(Resena resena: resultado)
		{
			suma += resena.getCalificacion();
		}
		if(resultado.isEmpty())
		{
			return 0;
		}
		return (double) suma / resultado.size();
	}

	public List<Producto> getProductos() {
		return new ArrayList<>(productos);
	}

	public List<ItemCarrito> getCarrito() {
		return new ArrayList<>(carrito);
	}
}
