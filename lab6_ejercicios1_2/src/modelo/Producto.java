package modelo;

import java.math.BigDecimal;

public class Producto {
	private int id;
	private String nombre;
	private BigDecimal precio;

	public Producto(int id, String nombre, BigDecimal precio)
	{
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public BigDecimal getPrecio() {
		return precio;
	}
}
