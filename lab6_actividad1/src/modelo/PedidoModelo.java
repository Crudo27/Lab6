package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class PedidoModelo {
	private List<Pedido> pedidos;
	private List<Pedido> historial;
	private int siguienteId;

	public PedidoModelo()
	{
		pedidos = new ArrayList<>();
		historial = new ArrayList<>();
		siguienteId = 1;
	}

	public void agregarPedido(String nombre, String tipo)
	{
		if(nombre.trim().isEmpty() || tipo.trim().isEmpty())
		{
			throw new IllegalArgumentException("El nombre y el tipo no pueden estar vacíos.");
		}
		pedidos.add(new Pedido(siguienteId, nombre, tipo));
		siguienteId++;
	}

	public Pedido buscarPorId(int id)
	{
		for(Pedido pedido: pedidos)
		{
			if(pedido.getId() == id && pedido.getEstado() != EstadoPedido.ELIMINADO)
			{
				return pedido;
			}
		}
		throw new IllegalArgumentException("Número de pedido inválido.");
	}

	public void eliminarPedido(int id)
	{
		Pedido pedido = buscarPorId(id);
		pedido.setEstado(EstadoPedido.ELIMINADO);
		registrarHistorial(pedido);
	}

	private void registrarHistorial(Pedido pedido)
	{
		Pedido registro = new Pedido(pedido.getId(), pedido.getNombrePlato(), pedido.getTipo());
		registro.setEstado(pedido.getEstado());
		historial.add(registro);
	}

	public void actualizarPedido(int id, String nombre)
	{
		Pedido pedido = buscarPorId(id);
		if(pedido.getEstado() != EstadoPedido.PENDIENTE)
		{
			throw new IllegalArgumentException("Solo se pueden actualizar pedidos pendientes.");
		}
		if(nombre.trim().isEmpty())
		{
			throw new IllegalArgumentException("El nombre no puede estar vacío.");
		}
		pedido.setNombrePlato(nombre);
	}

	public List<Pedido> buscarPedido(String nombre)
	{
		List<Pedido> pedidosBuscados = new ArrayList<>();
		for(Pedido pedido: getPedidos())
		{
			if(pedido.getNombrePlato().equalsIgnoreCase(nombre.trim()))
			{
				pedidosBuscados.add(pedido);
			}
		}
		return pedidosBuscados;
	}

	public List<Pedido> buscarPedidoTipo(String tipo)
	{
		List<Pedido> pedidosBuscados = new ArrayList<>();
		for(Pedido pedido: getPedidos())
		{
			if(pedido.getTipo().equalsIgnoreCase(tipo.trim()))
			{
				pedidosBuscados.add(pedido);
			}
		}
		return pedidosBuscados;
	}

	public TreeMap<String, Integer> contarPedidos()
	{
		TreeMap<String, Integer> pedidosContados = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		for(Pedido pedido: getPedidos())
		{
			String tipo = pedido.getTipo();
			if(pedidosContados.containsKey(tipo))
			{
				pedidosContados.put(tipo, pedidosContados.get(tipo) + 1);
			}
			else
			{
				pedidosContados.put(tipo, 1);
			}
		}
		return pedidosContados;
	}

	public void completarPedido(int id)
	{
		Pedido pedido = buscarPorId(id);
		if(pedido.getEstado() != EstadoPedido.PENDIENTE)
		{
			throw new IllegalArgumentException("El pedido ya está completo.");
		}
		pedido.setEstado(EstadoPedido.COMPLETO);
		registrarHistorial(pedido);
	}

	public List<Pedido> pedidosPorEstado(EstadoPedido estado)
	{
		List<Pedido> resultado = new ArrayList<>();
		for(Pedido pedido: pedidos)
		{
			if(pedido.getEstado() == estado)
			{
				resultado.add(pedido);
			}
		}
		return resultado;
	}

	public int contarPendientes()
	{
		return pedidosPorEstado(EstadoPedido.PENDIENTE).size();
	}

	public List<Pedido> getHistorial()
	{
		return new ArrayList<>(historial);
	}

	public List<Pedido> getPedidos()
	{
		List<Pedido> actuales = new ArrayList<>();
		for(Pedido pedido: pedidos)
		{
			if(pedido.getEstado() != EstadoPedido.ELIMINADO)
			{
				actuales.add(pedido);
			}
		}
		return actuales;
	}
}
