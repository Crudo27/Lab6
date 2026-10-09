package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class PedidoModelo {
	private List<Pedido> pedidos;
	
	public PedidoModelo()
	{
		pedidos = new ArrayList<>();
	}
	
	public void agregarPedido(Pedido pedido)
	{
		pedidos.add(pedido);
	}
	
	public void eliminarPedido(int index)
	{
		pedidos.remove(index);
	}
	
	public List<Pedido> buscarPedido(String nombre)
	{
		List<Pedido> pedidosBuscados = new ArrayList<>();
		for(Pedido pedido: pedidos)
		{
			if(pedido.getNombrePlato().equals(nombre))
			{
				pedidosBuscados.add(pedido);
			}
		}
		
		if(pedidosBuscados.isEmpty())
		{
			System.out.println("El pedido no se encuentra en la lista");
			return null;
		}
		
		return pedidosBuscados;
	}
	
	public List<Pedido> buscarPedidoTipo(String tipo)
	{
		List<Pedido> pedidosBuscados = new ArrayList<>();
		for(Pedido pedido: pedidos)
		{
			if(pedido.getNombrePlato().equals(tipo))
			{
				pedidosBuscados.add(pedido);
			}
		}
		
		if(pedidosBuscados.isEmpty())
		{
			System.out.println("El tipo no se ecuentra en la lista");
			return null;
		}
		
		return pedidosBuscados;
	}
	
	public TreeMap<String, Integer> contarPedidos()
	{
		TreeMap<String, Integer> pedidosContados = new TreeMap<>();
		int cont = 0;
		for(Pedido pedido: pedidos)
		{
			cont ++;
			if(pedidosContados.containsKey(pedido.getTipo()))
			{
				pedidosContados.put(pedido.getTipo(), pedidosContados.get(pedido.getTipo()) +1);
			}
			else
			{
				pedidosContados.put(pedido.getTipo(), 1);
			}
		}
		
		return pedidosContados;
		
	}
	
	public List<Pedido> getPedidos()
	{
		return pedidos;
	}
}
