package controlador;

import java.util.List;

import modelo.Pedido;
import modelo.PedidoModelo;
import vista.PedidoVista;

public class PedidoControlador {
    private PedidoModelo modelo;
    private PedidoVista vista;

    public PedidoControlador(PedidoModelo modelo, PedidoVista vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void agregarPedido(String nombrePlato, String tipoPlato) {
        if (!nombrePlato.isEmpty()) {
            modelo.agregarPedido(new Pedido(nombrePlato, tipoPlato));
            vista.mostrarMensaje("Pedido agregado: " + nombrePlato);
        } else {
            vista.mostrarMensaje("El nombre del plato no puede estar vacío.");
        }
    }

    public void mostrarPedidos() {
        List<Pedido> pedidos = modelo.getPedidos();
        vista.mostrarPedidos(pedidos);
    }
    
    public void eliminarPedido(int op)
    {
    	if(op < 1 || op > modelo.getPedidos().size())
    	{
    		vista.mostrarMensaje("Número inválido");
    	}
    	modelo.eliminarPedido(op-1);
    	vista.mostrarMensaje("Pedido eliminado");
    }
    
    public void buscarPedidos(String nombre)
    {
    	List<Pedido> pedidosBuscados = modelo.buscarPedido(nombre);
    	if(pedidosBuscados == null)
    	{
    		return;
    	}
    	
    	vista.mostrarPedidos(pedidosBuscados);
    }
    
    public void buscarPedidosTipo(String tipo)
    {
    	List<Pedido> pedidosBuscados = modelo.buscarPedidoTipo(tipo);
    	
    	if(pedidosBuscados == null)
    	{
    		return;
    	}
    	
    	vista.mostrarPedidos(pedidosBuscados);
    }

    public void iniciar() {
        String opcion;

        do {
            vista.mostrarMenu();
            opcion = vista.solicitarOpcion();

            switch (opcion) {
                case "1":
                    String nombrePlato = vista.solicitarNombrePlato();
                    String tipoPlato = vista.solicitarTipoPlato();
                    agregarPedido(nombrePlato, tipoPlato);
                    break;

                case "2":
                    mostrarPedidos();
                    break;
                    
                case "3":
                	mostrarPedidos();
                	String numPlatoEliminar = vista.solicitarOpcion();
                	
                	eliminarPedido(Integer.parseInt(numPlatoEliminar));
                	
                	break;
                	
                case "4":
                	vista.mostrarMenuBuscar();
                	String opcionBuscar = vista.solicitarOpcion();
                	
                	switch(opcionBuscar)
                	{
                	case "1":
                		buscarPedidos(vista.solicitarNombrePlato());
                		break;
                	case "2":
                		buscarPedidosTipo(vista.solicitarTipoPlato());
                		break;
                	}

                case "6":
                    vista.mostrarMensaje("Saliendo...");
                    break;

                default:
                    vista.mostrarMensaje("Opción no válida. Inténtalo de nuevo.");
            }
        } while (!opcion.equals("3"));

        vista.cerrarScanner();
    }
}