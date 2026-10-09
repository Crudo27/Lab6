package controlador;

import modelo.Compra;
import modelo.TiendaModelo;
import modelo.UsuarioModelo;
import vista.TiendaVista;

public class TiendaControlador {
    private TiendaModelo modelo;
    private UsuarioModelo usuarios;
    private TiendaVista vista;

    public TiendaControlador(TiendaModelo modelo, UsuarioModelo usuarios, TiendaVista vista)
    {
        this.modelo = modelo;
        this.usuarios = usuarios;
        this.vista = vista;
    }

    public void mostrarCarrito()
    {
        vista.mostrarItems(modelo.getCarrito());
        vista.mostrarTotales(modelo.calcularSubtotal(), modelo.calcularDescuento(),
                modelo.calcularEnvio(), modelo.calcularTotal());
    }

    public void comprar()
    {
        usuarios.exigirSesion();
        if(modelo.getCarrito().isEmpty())
        {
            throw new IllegalArgumentException("El carrito está vacío.");
        }
        mostrarCarrito();
        String respuesta = vista.solicitarTexto("Confirmar compra (S/N): ");
        if(!"S".equalsIgnoreCase(respuesta))
        {
            vista.mostrarMensaje("Compra cancelada.");
            return;
        }
        Compra compra = modelo.realizarCompra(usuarios.exigirSesion());
        vista.mostrarMensaje("Compra realizada.");
        vista.mostrarCompra(compra);
    }

    public void iniciar()
    {
        String opcion;
        do
        {
            vista.mostrarMenu();
            vista.mostrarUsuario(usuarios.getUsuarioActual());
            opcion = vista.solicitarTexto("Opción: ");
            if(opcion == null)
            {
                break;
            }
            try
            {
                switch(opcion)
                {
                    case "1":
                        String nombre = vista.solicitarTexto("Nombre del producto: ");
                        modelo.agregarProducto(nombre, vista.solicitarPrecio());
                        vista.mostrarMensaje("Producto agregado.");
                        break;
                    case "2":
                        vista.mostrarProductos(modelo.getProductos());
                        break;
                    case "3":
                        usuarios.exigirSesion();
                        int id = vista.solicitarNumero("ID del producto: ");
                        modelo.agregarAlCarrito(id, vista.solicitarNumero("Cantidad: "));
                        vista.mostrarMensaje("Producto agregado al carrito.");
                        break;
                    case "4":
                        usuarios.exigirSesion();
                        mostrarCarrito();
                        break;
                    case "5":
                        usuarios.exigirSesion();
                        modelo.eliminarDelCarrito(vista.solicitarNumero("ID del producto: "));
                        vista.mostrarMensaje("Producto eliminado.");
                        break;
                    case "6":
                        usuarios.exigirSesion();
                        modelo.aplicarDescuento(vista.solicitarNumero("Descuento (%): "));
                        mostrarCarrito();
                        break;
                    case "7":
                        usuarios.exigirSesion();
                        modelo.seleccionarEnvio(vista.solicitarTexto("RECOJO/LOCAL/NACIONAL: "));
                        mostrarCarrito();
                        break;
                    case "8":
                        comprar();
                        break;
                    case "9":
                        vista.mostrarHistorial(modelo.historialUsuario(usuarios.exigirSesion()));
                        break;
                    case "10":
                        String usuarioNombre = vista.solicitarTexto("Nombre: ");
                        String correo = vista.solicitarTexto("Correo: ");
                        String clave = vista.solicitarTexto("Contraseña: ");
                        usuarios.registrar(usuarioNombre, correo, clave);
                        vista.mostrarMensaje("Usuario registrado.");
                        break;
                    case "11":
                        if(usuarios.getUsuarioActual() != null)
                        {
                            throw new IllegalArgumentException("Cierra la sesión anterior.");
                        }
                        String correoAcceso = vista.solicitarTexto("Correo: ");
                        String claveAcceso = vista.solicitarTexto("Contraseña: ");
                        usuarios.iniciarSesion(correoAcceso, claveAcceso);
                        modelo.vaciarCarrito();
                        vista.mostrarMensaje("Sesión iniciada.");
                        break;
                    case "12":
                        usuarios.cerrarSesion();
                        modelo.vaciarCarrito();
                        vista.mostrarMensaje("Sesión cerrada.");
                        break;
                    case "13":
                        usuarios.exigirSesion();
                        int productoId = vista.solicitarNumero("ID a reseñar: ");
                        int calificacion = vista.solicitarNumero("Calificación (1 a 5): ");
                        String comentario = vista.solicitarTexto("Comentario: ");
                        modelo.agregarResena(usuarios.exigirSesion(), productoId, calificacion, comentario);
                        vista.mostrarMensaje("Reseña registrada.");
                        break;
                    case "14":
                        int producto = vista.solicitarNumero("ID del producto: ");
                        vista.mostrarResenas(modelo.resenasProducto(producto), modelo.promedioProducto(producto));
                        break;
                    case "0":
                        vista.mostrarMensaje("Saliendo...");
                        break;
                    default:
                        vista.mostrarMensaje("Opción incorrecta.");
                }
            }
            catch(IllegalArgumentException e)
            {
                vista.mostrarMensaje("Error: " + e.getMessage());
            }
        } while(!opcion.equals("0"));
        vista.cerrarScanner();
    }
}
