package controlador;

import modelo.Compra;
import modelo.TiendaModelo;
import modelo.UsuarioModelo;
import vista.TiendaVista;

public class TiendaControlador {
	private TiendaModelo modelo;
	private TiendaVista vista;
	private UsuarioModelo usuarios;
	private UsuarioControlador usuarioControlador;

	public TiendaControlador(TiendaModelo modelo, TiendaVista vista,
			UsuarioModelo usuarios, UsuarioControlador usuarioControlador)
	{
		this.modelo = modelo;
		this.vista = vista;
		this.usuarios = usuarios;
		this.usuarioControlador = usuarioControlador;
	}

	public void mostrarCarrito()
	{
		vista.mostrarItems(modelo.getCarrito());
		vista.mostrarTotales(modelo.calcularSubtotal(), modelo.calcularDescuento(),
				modelo.calcularEnvio(), modelo.calcularTotal());
	}

	public void comprar()
	{
		usuarios.exigirSesion();
		if(modelo.getCarrito().isEmpty())
		{
			throw new IllegalArgumentException("El carrito está vacío.");
		}
		mostrarCarrito();
		String confirmacion = vista.solicitarTexto("Confirmar compra (S/N): ");
		if(!confirmacion.trim().equalsIgnoreCase("S"))
		{
			vista.mostrarMensaje("Compra cancelada.");
			return;
		}
		Compra compra = modelo.realizarCompra(usuarios.exigirSesion());
		vista.mostrarMensaje("Compra realizada.");
		vista.mostrarCompra(compra);
	}

	public void iniciar()
	{
		String opcion;
		do
		{
			vista.mostrarMenu();
			usuarioControlador.mostrarUsuario();
			opcion = vista.solicitarOpcion();
			try
			{
				switch(opcion)
				{
				case "1":
					String nombre = vista.solicitarTexto("Nombre del producto: ");
					modelo.agregarProducto(nombre, vista.solicitarPrecio());
					vista.mostrarMensaje("Producto agregado.");
					break;
				case "2":
					vista.mostrarProductos(modelo.getProductos());
					break;
				case "3":
					usuarios.exigirSesion();
					int id = vista.solicitarNumero("ID del producto: ");
					modelo.agregarAlCarrito(id, vista.solicitarNumero("Cantidad: "));
					vista.mostrarMensaje("Producto agregado al carrito.");
					break;
				case "4":
					usuarios.exigirSesion();
					mostrarCarrito();
					break;
				case "5":
					usuarios.exigirSesion();
					modelo.eliminarDelCarrito(vista.solicitarNumero("ID a eliminar: "));
					vista.mostrarMensaje("Producto eliminado del carrito.");
					break;
				case "6":
					usuarios.exigirSesion();
					modelo.aplicarDescuento(vista.solicitarNumero("Descuento en porcentaje: "));
					mostrarCarrito();
					break;
				case "7":
					usuarios.exigirSesion();
					modelo.seleccionarEnvio(vista.solicitarTexto("Zona (RECOJO/LOCAL/NACIONAL): "));
					mostrarCarrito();
					break;
				case "8":
					comprar();
					break;
				case "9":
					vista.mostrarHistorial(modelo.historialUsuario(usuarios.exigirSesion()));
					break;
				case "10":
					usuarioControlador.registrar();
					break;
				case "11":
					if(usuarios.getUsuarioActual() != null)
					{
						throw new IllegalArgumentException("Cierra la sesión actual primero.");
					}
					usuarioControlador.iniciarSesion();
					modelo.vaciarCarrito();
					break;
				case "12":
					usuarioControlador.cerrarSesion();
					modelo.vaciarCarrito();
					break;
				case "13":
					usuarios.exigirSesion();
					int productoId = vista.solicitarNumero("ID a reseñar: ");
					int calificacion = vista.solicitarNumero("Calificación (1 a 5): ");
					String comentario = vista.solicitarTexto("Reseña: ");
					modelo.agregarResena(usuarios.exigirSesion(), productoId, calificacion, comentario);
					vista.mostrarMensaje("Reseña registrada.");
					break;
				case "14":
					int producto = vista.solicitarNumero("ID del producto: ");
					vista.mostrarResenas(modelo.resenasProducto(producto), modelo.promedioProducto(producto));
					break;
				case "0":
					vista.mostrarMensaje("Saliendo...");
					break;
				default:
					vista.mostrarMensaje("Opción no válida.");
				}
			}
			catch(IllegalArgumentException e)
			{
				vista.mostrarMensaje(e.getMessage());
			}
			catch(ArithmeticException e)
			{
				vista.mostrarMensaje("Usa un precio con máximo dos decimales.");
			}
		} while(!opcion.equals("0"));
	}
}
