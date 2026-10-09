package modelo;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.GeneralSecurityException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Usuario {
	private int id;
	private String nombre;
	private String correo;
	private byte[] sal;
	private byte[] claveHash;

	public Usuario(int id, String nombre, String correo, String clave)
	{
		this.id = id;
		this.nombre = nombre;
		this.correo = correo;
		sal = new byte[16];
		new SecureRandom().nextBytes(sal);
		claveHash = generarHash(clave);
	}

	private byte[] generarHash(String clave)
	{
		PBEKeySpec datos = new PBEKeySpec(clave.toCharArray(), sal, 120000, 256);
		try
		{
			return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(datos).getEncoded();
		}
		catch(GeneralSecurityException e)
		{
			throw new IllegalStateException("No se pudo procesar la contraseña.", e);
		}
		finally
		{
			datos.clearPassword();
		}
	}

	public boolean verificarClave(String clave)
	{
		return MessageDigest.isEqual(claveHash, generarHash(clave));
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getCorreo() {
		return correo;
	}
}
