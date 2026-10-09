package vista;

import java.util.List;
import java.util.Scanner;

import modelo.Pedido;

public class PedidoVista {
	private Scanner scanner;
	
	public PedidoVista()
	{
		scanner = new Scanner(System.in);
	}
	
	public String solicitarNombrePlato()
	{
		System.out.print("Introduce el nombre del plato: ");
		return scanner.nextLine();
	}
	
	public String solicitarTipoPlato()
	{
		System.out.print("Introduce el tipo del plato: ");
		return scanner.nextLine();
	}
	
	public void mostrarPedidos(List<Pedido> pedidos)
	{
		if(pedidos.isEmpty())
		{
			System.out.println("No hay pedidos en la lista.");
			
		} else
		{
			int i = 1;
			System.out.println("Lista de Pedidos:");
			for(Pedido pedido: pedidos)
			{
				System.out.println(i+"- "+pedido.getNombrePlato());
				System.out.println("- "+pedido.getTipo());
			}
		}
	}
	
	public void mostrarMenu()
	{
		System.out.println("\nOpciones:");
		System.out.println("1. Agregar Pedido");
		System.out.println("2. Mostrar Pedidos");
		System.out.println("3. Eliminar un Pedido");
		System.out.println("4. Buscar un Pedido");
		System.out.println("6. Salir");
	}
	
	public void mostrarMenuBuscar()
	{
		System.out.println("1. Nombre");
		System.out.println("2. Tipo");
	}
	
	public String solicitarOpcion()
	{
		System.out.print("Selecciona una opción: ");
		return scanner.nextLine();
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
