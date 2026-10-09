import modelo.PedidoModelo;
import modelo.EstadoPedido;
import modelo.TiendaModelo;
import modelo.UsuarioModelo;
import modelo.Usuario;
import modelo.Compra;

public class Verificacion {
    private static int correctas = 0;

    public static void comprobar(boolean condicion, String descripcion)
    {
        if(!condicion)
        {
            throw new AssertionError("Falló: " + descripcion);
        }
        correctas++;
        System.out.println("OK " + correctas + " - " + descripcion);
    }

    public static void main(String[] args)
    {
        PedidoModelo pedidos = new PedidoModelo();
        comprobar(pedidos.getPedidos().isEmpty(), "Pedidos vacíos al comenzar");
        pedidos.agregarPedido("Sopa", "Entrada");
        pedidos.agregarPedido("Pollo", "Fondo");
        comprobar(pedidos.getPedidos().size() == 2, "Registrar pedidos");
        comprobar(pedidos.buscarPedido("sopa").size() == 1, "Buscar por nombre");
        comprobar(pedidos.buscarPedidoTipo("fondo").size() == 1, "Buscar por tipo");
        comprobar(pedidos.contarPedidos().get("Entrada") == 1, "Contar por tipo");
        pedidos.actualizarPedido(2, "Pollo al horno");
        comprobar(pedidos.buscarPorId(2).getNombrePlato().equals("Pollo al horno"), "Actualizar pedido");
        pedidos.completarPedido(1);
        comprobar(pedidos.contarPendientes() == 1, "Contar pendientes");
        comprobar(pedidos.pedidosPorEstado(EstadoPedido.COMPLETO).size() == 1, "Filtrar completos");
        pedidos.eliminarPedido(1);
        comprobar(pedidos.getPedidos().size() == 1, "Eliminar de pedidos actuales");
        comprobar(pedidos.getHistorial().size() == 2, "Guardar historial");
        comprobar(pedidos.getHistorial().get(0).getEstado() == EstadoPedido.COMPLETO, "Conservar historial de completo");
        pedidos.agregarPedido("Jugo", "Bebida");
        comprobar(pedidos.buscarPorId(3).getNombrePlato().equals("Jugo"), "Mantener IDs");
        boolean errorPedido = false;
        try
        {
            pedidos.eliminarPedido(99);
        }
        catch(IllegalArgumentException e)
        {
            errorPedido = true;
        }
        comprobar(errorPedido, "Rechazar ID de pedido inexistente");

        UsuarioModelo usuarios = new UsuarioModelo();
        usuarios.registrar("Piero", "PIERO@example.com", "clave123");
        usuarios.iniciarSesion("piero@example.com", "clave123");
        Usuario primero = usuarios.exigirSesion();
        comprobar(primero.getNombre().equals("Piero"), "Inicio de sesión");
        boolean errorClave = false;
        try
        {
            usuarios.iniciarSesion("piero@example.com", "incorrecta");
        }
        catch(IllegalArgumentException e)
        {
            errorClave = true;
        }
        comprobar(errorClave, "Rechazar clave incorrecta");

        TiendaModelo tienda = new TiendaModelo();
        tienda.agregarProducto("Teclado", 120);
        tienda.agregarProducto("Mouse", 50);
        comprobar(tienda.getProductos().size() == 2, "Agregar productos");
        tienda.agregarAlCarrito(1, 1);
        tienda.agregarAlCarrito(1, 1);
        tienda.agregarAlCarrito(2, 1);
        comprobar(tienda.getCarrito().size() == 2, "Acumular productos iguales");
        comprobar(tienda.getCarrito().get(0).getCantidad() == 2, "Sumar cantidades");
        comprobar(tienda.calcularSubtotal() == 290, "Calcular subtotal");
        tienda.aplicarDescuento(10);
        tienda.seleccionarEnvio("local");
        comprobar(tienda.calcularDescuento() == 29, "Aplicar descuento");
        comprobar(tienda.calcularEnvio() == 10, "Calcular envío local");
        comprobar(tienda.calcularTotal() == 271, "Calcular total");
        Compra compra = tienda.realizarCompra(primero);
        comprobar(compra.getTotal() == 271, "Guardar importe de compra");
        comprobar(tienda.getCarrito().isEmpty(), "Vaciar carrito después de comprar");
        tienda.agregarAlCarrito(1, 1);
        comprobar(compra.getProductos().get(0).getCantidad() == 2, "Conservar copia de compra");
        comprobar(tienda.calcularDescuento() == 0, "Reiniciar descuento");
        comprobar(tienda.productoComprado(primero, 1), "Comprobar producto comprado");
        tienda.agregarResena(primero, 1, 5, "Buen teclado");
        comprobar(tienda.promedioProducto(1) == 5, "Promedio de reseñas");
        boolean resenaRepetida = false;
        try
        {
            tienda.agregarResena(primero, 1, 3, "Otra");
        }
        catch(IllegalArgumentException e)
        {
            resenaRepetida = true;
        }
        comprobar(resenaRepetida, "Rechazar reseña duplicada");
        usuarios.cerrarSesion();
        tienda.vaciarCarrito();
        comprobar(usuarios.getUsuarioActual() == null, "Cerrar sesión");
        usuarios.registrar("Ana", "ana@example.com", "clave456");
        usuarios.iniciarSesion("ana@example.com", "clave456");
        comprobar(tienda.historialUsuario(usuarios.exigirSesion()).isEmpty(), "Separar compras por usuario");
        comprobar(tienda.getCarrito().isEmpty(), "Limpiar carrito al cambiar de cuenta");
        System.out.println("Resultado: " + correctas + " verificaciones correctas.");
    }
}
