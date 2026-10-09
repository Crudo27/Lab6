package vista;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import modelo.Pedido;

public class PedidoVista {
	private Scanner scanner;

	public PedidoVista()
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

	public String solicitarNombrePlato()
	{
		return solicitarTexto("Introduce el nombre del plato: ");
	}

	public String solicitarTipoPlato()
	{
		return solicitarTexto("Introduce el tipo del plato: ");
	}

	public int solicitarNumero(String mensaje)
	{
		String dato = solicitarTexto(mensaje);
		if(dato == null)
		{
			throw new IllegalArgumentException("No se recibió un número.");
		}
		try
		{
			return Integer.parseInt(dato.trim());
		}
		catch(NumberFormatException e)
		{
			throw new IllegalArgumentException("Debes ingresar un número entero.");
		}
	}

	public void mostrarPedidos(List<Pedido> pedidos)
	{
		if(pedidos.isEmpty())
		{
			mostrarMensaje("No hay pedidos en la lista.");
			return;
		}
		mostrarMensaje("ID | Plato | Tipo | Estado");
		for(Pedido pedido: pedidos)
		{
			mostrarMensaje(pedido.getId() + " | " + pedido.getNombrePlato() + " | "
					+ pedido.getTipo() + " | " + pedido.getEstado());
		}
	}

	public void mostrarConteo(int total, Map<String, Integer> conteo)
	{
		mostrarMensaje("Total de pedidos actuales: " + total);
		for(Map.Entry<String, Integer> entrada: conteo.entrySet())
		{
			mostrarMensaje(entrada.getKey() + ": " + entrada.getValue());
		}
	}

	public void mostrarMenu()
	{
		mostrarMensaje("\nGESTIÓN DE PEDIDOS");
		mostrarMensaje("1. Agregar pedido");
		mostrarMensaje("2. Mostrar pedidos");
		mostrarMensaje("3. Eliminar pedido");
		mostrarMensaje("4. Buscar pedido");
		mostrarMensaje("5. Actualizar nombre");
		mostrarMensaje("6. Contar pedidos");
		mostrarMensaje("7. Marcar como completo");
		mostrarMensaje("8. Mostrar pedidos por estado");
		mostrarMensaje("9. Contar pendientes");
		mostrarMensaje("10. Historial");
		mostrarMensaje("0. Salir");
	}

	public void mostrarMenuBuscar()
	{
		mostrarMensaje("1. Nombre");
		mostrarMensaje("2. Tipo");
	}

	public String solicitarOpcion()
	{
		return solicitarTexto("Opción: ");
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
