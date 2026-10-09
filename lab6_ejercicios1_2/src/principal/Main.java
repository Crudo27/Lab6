package principal;

import java.util.Scanner;
import modelo.TiendaModelo;
import modelo.UsuarioModelo;
import vista.TiendaVista;
import vista.UsuarioVista;
import controlador.TiendaControlador;
import controlador.UsuarioControlador;

public class Main {
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		TiendaModelo modelo = new TiendaModelo();
		UsuarioModelo usuarios = new UsuarioModelo();
		TiendaVista vista = new TiendaVista(scanner);
		UsuarioVista usuarioVista = new UsuarioVista(scanner);
		UsuarioControlador usuarioControlador = new UsuarioControlador(usuarios, usuarioVista);
		TiendaControlador controlador = new TiendaControlador(modelo, vista, usuarios, usuarioControlador);
		controlador.iniciar();
		scanner.close();
	}
}
