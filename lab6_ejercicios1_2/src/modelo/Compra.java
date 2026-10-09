package modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Compra {
	private int id;
	private int usuarioId;
	private LocalDateTime fecha;
	private List<ItemCarrito> productos;
	private BigDecimal subtotal;
	private BigDecimal descuento;
	private BigDecimal envio;

	public Compra(int id, int usuarioId, List<ItemCarrito> carrito,
			BigDecimal subtotal, BigDecimal descuento, BigDecimal envio)
	{
		this.id = id;
		this.usuarioId = usuarioId;
		fecha = LocalDateTime.now();
		productos = new ArrayList<>();
		for(ItemCarrito item: carrito)
		{
			productos.add(new ItemCarrito(item.getProducto(), item.getCantidad()));
		}
		this.subtotal = subtotal;
		this.descuento = descuento;
		this.envio = envio;
	}

	public int getId() {
		return id;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public List<ItemCarrito> getProductos() {
		return new ArrayList<>(productos);
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public BigDecimal getEnvio() {
		return envio;
	}

	public BigDecimal getTotal()
	{
		return subtotal.subtract(descuento).add(envio);
	}
}
