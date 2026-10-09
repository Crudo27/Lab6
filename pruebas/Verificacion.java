import java.math.BigDecimal;
import modelo.EstadoPedido;
import modelo.PedidoModelo;
import modelo.TiendaModelo;
import modelo.UsuarioModelo;
import modelo.Compra;
import modelo.Usuario;

public class Verificacion {
	private static int cantidad;

	private static void comprobar(boolean resultado, String nombre)
	{
		if(!resultado)
		{
			throw new AssertionError(nombre);
		}
		cantidad++;
		System.out.println("OK " + cantidad + " - " + nombre);
	}

	private static void rechazar(Runnable accion, String nombre)
	{
		try
		{
			accion.run();
		}
		catch(IllegalArgumentException | ArithmeticException e)
		{
			comprobar(true, nombre);
			return;
		}
		throw new AssertionError(nombre);
	}

	public static void main(String[] args)
	{
		PedidoModelo pedidos = new PedidoModelo();
		comprobar(pedidos.getPedidos().isEmpty(), "Lista inicial vacía");
		rechazar(() -> pedidos.agregarPedido(" ", "Fondo"), "Nombre vacío rechazado");
		rechazar(() -> pedidos.agregarPedido("Sopa", " "), "Tipo vacío rechazado");
		pedidos.agregarPedido("Sopa", "Entrada");
		pedidos.agregarPedido("Pollo", "Fondo");
		comprobar(pedidos.buscarPedido("sopa").size() == 1, "Búsqueda por nombre");
		comprobar(pedidos.buscarPedidoTipo("fondo").size() == 1, "Búsqueda por tipo");
		comprobar(pedidos.buscarPedido("Otro").isEmpty(), "Búsqueda sin resultados");
		pedidos.actualizarPedido(2, "Pollo al horno");
		comprobar(pedidos.buscarPorId(2).getNombrePlato().equals("Pollo al horno"), "Actualización");
		rechazar(() -> pedidos.eliminarPedido(99), "ID inválido rechazado");
		comprobar(pedidos.getPedidos().size() == 2, "El error conserva los pedidos");
		pedidos.completarPedido(1);
		comprobar(pedidos.contarPendientes() == 1, "Contador de pendientes");
		rechazar(() -> pedidos.completarPedido(1), "No se completa dos veces");
		rechazar(() -> pedidos.actualizarPedido(1, "Otra sopa"), "No se modifica un pedido servido");
		pedidos.eliminarPedido(1);
		comprobar(pedidos.getPedidos().size() == 1, "Eliminación lógica");
		comprobar(pedidos.getHistorial().size() == 2, "Historial conserva ambas transiciones");
		comprobar(pedidos.getHistorial().get(0).getEstado() == EstadoPedido.COMPLETO, "Copia del estado completo");
		comprobar(pedidos.getHistorial().get(1).getEstado() == EstadoPedido.ELIMINADO, "Copia del estado eliminado");
		comprobar(pedidos.contarPedidos().get("FONDO") == 1, "Conteo por tipo");
		pedidos.agregarPedido("Jugo", "Bebida");
		comprobar(pedidos.buscarPorId(3).getNombrePlato().equals("Jugo"), "ID estable sin reutilización");

		UsuarioModelo usuarios = new UsuarioModelo();
		rechazar(() -> usuarios.exigirSesion(), "Acceso sin sesión rechazado");
		rechazar(() -> usuarios.registrar("Piero", "incorrecto", "clave123"), "Correo inválido");
		rechazar(() -> usuarios.registrar("Piero", "piero@example.com", "123"), "Contraseña corta");
		usuarios.registrar("Piero", "piero@example.com", "clave123");
		rechazar(() -> usuarios.registrar("Otro", "PIERO@example.com", "clave123"), "Correo duplicado");
		rechazar(() -> usuarios.iniciarSesion("piero@example.com", "otra1234"), "Clave incorrecta");
		comprobar(usuarios.getUsuarioActual() == null, "Fallo de acceso no abre sesión");
		usuarios.iniciarSesion("PIERO@example.com", "clave123");
		Usuario primero = usuarios.exigirSesion();
		comprobar(primero.getNombre().equals("Piero"), "Inicio de sesión correcto");

		TiendaModelo tienda = new TiendaModelo();
		rechazar(() -> tienda.agregarProducto("Teclado", new BigDecimal("-1")), "Precio negativo");
		rechazar(() -> tienda.agregarProducto("Teclado", new BigDecimal("0.001")), "Precio con más de dos decimales");
		tienda.agregarProducto("Teclado", new BigDecimal("120"));
		tienda.agregarProducto("Mouse", new BigDecimal("50"));
		comprobar(tienda.getProductos().size() == 2, "Catálogo de productos");
		rechazar(() -> tienda.agregarAlCarrito(99, 1), "Producto inexistente");
		rechazar(() -> tienda.agregarAlCarrito(1, 0), "Cantidad cero");
		tienda.agregarAlCarrito(1, 1);
		tienda.agregarAlCarrito(1, 1);
		tienda.agregarAlCarrito(2, 1);
		comprobar(tienda.getCarrito().size() == 2 && tienda.getCarrito().get(0).getCantidad() == 2,
				"Acumulación de unidades");
		tienda.eliminarDelCarrito(2);
		comprobar(tienda.calcularSubtotal().compareTo(new BigDecimal("240")) == 0, "Eliminación del carrito");
		tienda.agregarAlCarrito(2, 1);
		rechazar(() -> tienda.aplicarDescuento(101), "Descuento inválido");
		tienda.aplicarDescuento(10);
		tienda.seleccionarEnvio("local");
		comprobar(tienda.calcularDescuento().compareTo(new BigDecimal("29")) == 0, "Descuento exacto");
		comprobar(tienda.calcularTotal().compareTo(new BigDecimal("271")) == 0, "Total con envío local");
		tienda.seleccionarEnvio("nacional");
		comprobar(tienda.calcularTotal().compareTo(new BigDecimal("286")) == 0, "Envío nacional");
		rechazar(() -> tienda.seleccionarEnvio("otro"), "Zona inválida");
		rechazar(() -> tienda.realizarCompra(null), "Compra sin sesión");
		rechazar(() -> tienda.agregarResena(primero, 1, 5, "Bien"), "Reseña antes de comprar");
		tienda.seleccionarEnvio("local");
		Compra compra = tienda.realizarCompra(primero);
		comprobar(compra.getTotal().compareTo(new BigDecimal("271")) == 0, "Compra registrada");
		comprobar(tienda.getCarrito().isEmpty(), "Carrito vaciado después de comprar");
		rechazar(() -> tienda.realizarCompra(primero), "Compra vacía rechazada");
		tienda.agregarAlCarrito(1, 1);
		comprobar(compra.getProductos().get(0).getCantidad() == 2, "Historial independiente del carrito");
		comprobar(tienda.calcularDescuento().compareTo(BigDecimal.ZERO) == 0, "Descuento reiniciado");
		rechazar(() -> tienda.agregarResena(primero, 1, 6, "Bien"), "Calificación inválida");
		rechazar(() -> tienda.agregarResena(primero, 1, 5, " "), "Reseña vacía");
		tienda.agregarResena(primero, 1, 5, "Buen teclado");
		comprobar(tienda.promedioProducto(1) == 5, "Promedio de calificaciones");
		rechazar(() -> tienda.agregarResena(primero, 1, 4, "Otra"), "Reseña duplicada");
		usuarios.cerrarSesion();
		tienda.vaciarCarrito();
		comprobar(usuarios.getUsuarioActual() == null, "Cierre de sesión");
		usuarios.registrar("Ana", "ana@example.com", "clave456");
		usuarios.iniciarSesion("ana@example.com", "clave456");
		Usuario segundo = usuarios.exigirSesion();
		comprobar(tienda.historialUsuario(segundo).isEmpty(), "Historial separado por usuario");
		rechazar(() -> tienda.agregarResena(segundo, 1, 5, "Bien"), "Compra ajena no autoriza reseña");
		comprobar(tienda.getCarrito().isEmpty(), "Carrito limpio al cambiar de usuario");
		comprobar(tienda.historialUsuario(primero).size() == 1, "Historial del comprador conservado");
		System.out.println("Resultado: " + cantidad + " verificaciones correctas.");
	}
}
