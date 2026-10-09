package modelo;

public class Pedido {
	private String nombrePlato;
	private String tipo;
	
	public Pedido(String nombrePlato, String tipo)
	{
		this.setNombrePlato(nombrePlato);
		this.setTipo(tipo);
	}

	public String getNombrePlato() {
		return nombrePlato;
	}

	public void setNombrePlato(String nombrePlato) {
		this.nombrePlato = nombrePlato;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
