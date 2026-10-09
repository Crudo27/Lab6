package vista;

import java.util.Scanner;
import modelo.Usuario;

public class UsuarioVista {
	private Scanner scanner;

	public UsuarioVista(Scanner scanner)
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

	public void mostrarMensaje(String mensaje)
	{
		System.out.println(mensaje);
	}
}
