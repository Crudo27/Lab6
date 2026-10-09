package controlador;

import modelo.UsuarioModelo;
import vista.UsuarioVista;

public class UsuarioControlador {
	private UsuarioModelo modelo;
	private UsuarioVista vista;

	public UsuarioControlador(UsuarioModelo modelo, UsuarioVista vista)
	{
		this.modelo = modelo;
		this.vista = vista;
	}

	public void registrar()
	{
		String nombre = vista.solicitarTexto("Nombre: ");
		String correo = vista.solicitarTexto("Correo: ");
		String clave = vista.solicitarTexto("Contraseña: ");
		modelo.registrar(nombre, correo, clave);
		vista.mostrarMensaje("Usuario registrado.");
	}

	public void iniciarSesion()
	{
		String correo = vista.solicitarTexto("Correo: ");
		String clave = vista.solicitarTexto("Contraseña: ");
		modelo.iniciarSesion(correo, clave);
		vista.mostrarMensaje("Sesión iniciada.");
	}

	public void cerrarSesion()
	{
		modelo.cerrarSesion();
		vista.mostrarMensaje("Sesión cerrada.");
	}

	public void mostrarUsuario()
	{
		vista.mostrarUsuario(modelo.getUsuarioActual());
	}
}
