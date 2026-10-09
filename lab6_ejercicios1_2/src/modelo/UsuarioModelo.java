package modelo;

import java.util.ArrayList;

public class UsuarioModelo {
    private ArrayList<Usuario> usuarios;
    private Usuario usuarioActual;

    public UsuarioModelo()
    {
        usuarios = new ArrayList<>();
    }

    public void registrar(String nombre, String correo, String clave)
    {
        nombre = nombre.trim();
        correo = correo.trim().toLowerCase();
        if(nombre.isEmpty() || !correo.contains("@") || !correo.contains("."))
        {
            throw new IllegalArgumentException("Nombre o correo incorrecto.");
        }
        if(clave.length() < 8)
        {
            throw new IllegalArgumentException("La contraseña debe tener 8 caracteres como mínimo.");
        }
        for(Usuario usuario : usuarios)
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
        correo = correo.trim().toLowerCase();
        for(Usuario usuario : usuarios)
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
