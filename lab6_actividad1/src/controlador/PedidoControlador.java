package controlador;

import modelo.EstadoPedido;
import modelo.PedidoModelo;
import vista.PedidoVista;

public class PedidoControlador {
	private PedidoModelo modelo;
	private PedidoVista vista;

	public PedidoControlador(PedidoModelo modelo, PedidoVista vista)
	{
		this.modelo = modelo;
		this.vista = vista;
	}

	private String solicitarTexto(String mensaje)
	{
		String dato = vista.solicitarTexto(mensaje);
		if(dato == null)
		{
			throw new IllegalArgumentException("Entrada incompleta.");
		}
		return dato;
	}

	public void buscarPedidos()
	{
		vista.mostrarMenuBuscar();
		String opcion = vista.solicitarOpcion();
		if("1".equals(opcion))
		{
			vista.mostrarPedidos(modelo.buscarPedido(solicitarTexto("Nombre: ")));
		}
		else if("2".equals(opcion))
		{
			vista.mostrarPedidos(modelo.buscarPedidoTipo(solicitarTexto("Tipo: ")));
		}
		else
		{
			vista.mostrarMensaje("Opción de búsqueda inválida.");
		}
	}

	public void mostrarPorEstado()
	{
		vista.mostrarMensaje("1. Pendientes");
		vista.mostrarMensaje("2. Completos");
		String opcion = vista.solicitarOpcion();
		if("1".equals(opcion))
		{
			vista.mostrarPedidos(modelo.pedidosPorEstado(EstadoPedido.PENDIENTE));
		}
		else if("2".equals(opcion))
		{
			vista.mostrarPedidos(modelo.pedidosPorEstado(EstadoPedido.COMPLETO));
		}
		else
		{
			vista.mostrarMensaje("Estado inválido.");
		}
	}

	public void iniciar()
	{
		String opcion;
		do
		{
			vista.mostrarMenu();
			opcion = vista.solicitarOpcion();
			if(opcion == null)
			{
				break;
			}
			try
			{
				switch(opcion)
				{
				case "1":
					String nombre = solicitarTexto("Introduce el nombre del plato: ");
					String tipo = solicitarTexto("Introduce el tipo del plato: ");
					modelo.agregarPedido(nombre, tipo);
					vista.mostrarMensaje("Pedido agregado: " + nombre.trim());
					break;
				case "2":
					vista.mostrarPedidos(modelo.getPedidos());
					break;
				case "3":
					vista.mostrarPedidos(modelo.getPedidos());
					modelo.eliminarPedido(vista.solicitarNumero("ID a eliminar: "));
					vista.mostrarMensaje("Pedido eliminado.");
					break;
				case "4":
					buscarPedidos();
					break;
				case "5":
					int id = vista.solicitarNumero("ID a actualizar: ");
					modelo.actualizarPedido(id, solicitarTexto("Nuevo nombre: "));
					vista.mostrarMensaje("Pedido actualizado.");
					break;
				case "6":
					vista.mostrarConteo(modelo.getPedidos().size(), modelo.contarPedidos());
					break;
				case "7":
					modelo.completarPedido(vista.solicitarNumero("ID a completar: "));
					vista.mostrarMensaje("Pedido completo.");
					break;
				case "8":
					mostrarPorEstado();
					break;
				case "9":
					vista.mostrarMensaje("Pedidos pendientes: " + modelo.contarPendientes());
					break;
				case "10":
					vista.mostrarPedidos(modelo.getHistorial());
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
		} while(!opcion.equals("0"));
		vista.cerrarScanner();
	}
}
