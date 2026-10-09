package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UsuarioModelo {
	private List<Usuario> usuarios;
	private Usuario usuarioActual;

	public UsuarioModelo()
	{
		usuarios = new ArrayList<>();
	}

	public void registrar(String nombre, String correo, String clave)
	{
		nombre = nombre.trim();
		correo = correo.trim().toLowerCase(Locale.ROOT);
		if(nombre.isEmpty() || !correo.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+"))
		{
			throw new IllegalArgumentException("Nombre o correo inválido.");
		}
		if(clave.trim().length() < 8)
		{
			throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
		}
		for(Usuario usuario: usuarios)
		{
			if(usuario.getCorreo().equals(correo))
			{
				throw new IllegalArgumentException("El correo ya está registrado.");
			}
		}
		usuarios.add(new Usuario(usuarios.size() + 1, nombre, correo, clave));
	}

	public void iniciarSesion(String correo, String clave)
	{
		correo = correo.trim().toLowerCase(Locale.ROOT);
		for(Usuario usuario: usuarios)
		{
			if(usuario.getCorreo().equals(correo) && usuario.verificarClave(clave))
			{
				usuarioActual = usuario;
				return;
			}
		}
		throw new IllegalArgumentException("Correo o contraseña incorrectos.");
	}

	public void cerrarSesion()
	{
		usuarioActual = null;
	}

	public Usuario getUsuarioActual()
	{
		return usuarioActual;
	}

	public Usuario exigirSesion()
	{
		if(usuarioActual == null)
		{
			throw new IllegalArgumentException("Primero debes iniciar sesión.");
		}
		return usuarioActual;
	}
}
