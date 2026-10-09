package modelo;

public class Pedido {
	private int id;
	private String nombrePlato;
	private String tipo;
	private EstadoPedido estado;

	public Pedido(int id, String nombrePlato, String tipo)
	{
		this.id = id;
		this.nombrePlato = nombrePlato.trim();
		this.tipo = tipo.trim();
		estado = EstadoPedido.PENDIENTE;
	}

	public int getId() {
		return id;
	}

	public String getNombrePlato() {
		return nombrePlato;
	}

	public void setNombrePlato(String nombrePlato) {
		this.nombrePlato = nombrePlato.trim();
	}

	public String getTipo() {
		return tipo;
	}

	public EstadoPedido getEstado() {
		return estado;
	}

	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}
}
