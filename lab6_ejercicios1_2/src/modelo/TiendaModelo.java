package modelo;

import java.util.ArrayList;

public class TiendaModelo {
    private ArrayList<Producto> productos;
    private ArrayList<ItemCarrito> carrito;
    private ArrayList<Compra> compras;
    private ArrayList<Resena> resenas;
    private int porcentajeDescuento;
    private String zonaEnvio;

    public TiendaModelo()
    {
        productos = new ArrayList<>();
        carrito = new ArrayList<>();
        compras = new ArrayList<>();
        resenas = new ArrayList<>();
        zonaEnvio = "RECOJO";
    }

    public void agregarProducto(String nombre, double precio)
    {
        if(nombre.trim().isEmpty() || precio <= 0)
        {
            throw new IllegalArgumentException("Nombre o precio incorrecto.");
        }
        productos.add(new Producto(productos.size() + 1, nombre.trim(), precio));
    }

    public Producto buscarProducto(int id)
    {
        for(Producto producto : productos)
        {
            if(producto.getId() == id)
            {
                return producto;
            }
        }
        throw new IllegalArgumentException("Producto no encontrado.");
    }

    public void agregarAlCarrito(int id, int cantidad)
    {
        Producto producto = buscarProducto(id);
        if(cantidad < 1 || cantidad > 1000)
        {
            throw new IllegalArgumentException("La cantidad debe estar entre 1 y 1000.");
        }
        for(ItemCarrito item : carrito)
        {
            if(item.getProducto().getId() == id)
            {
                if(item.getCantidad() + cantidad > 1000)
                {
                    throw new IllegalArgumentException("Máximo 1000 unidades por producto.");
                }
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        carrito.add(new ItemCarrito(producto, cantidad));
    }

    public void eliminarDelCarrito(int id)
    {
        for(int i = 0; i < carrito.size(); i++)
        {
            if(carrito.get(i).getProducto().getId() == id)
            {
                carrito.remove(i);
                return;
            }
        }
        throw new IllegalArgumentException("Ese producto no está en el carrito.");
    }

    public void aplicarDescuento(int porcentaje)
    {
        if(porcentaje < 0 || porcentaje > 100)
        {
            throw new IllegalArgumentException("Descuento incorrecto.");
        }
        porcentajeDescuento = porcentaje;
    }

    public void seleccionarEnvio(String zona)
    {
        zona = zona.trim().toUpperCase();
        if(!zona.equals("RECOJO") && !zona.equals("LOCAL") && !zona.equals("NACIONAL"))
        {
            throw new IllegalArgumentException("Elige RECOJO, LOCAL o NACIONAL.");
        }
        zonaEnvio = zona;
    }

    public double calcularSubtotal()
    {
        double subtotal = 0;
        for(ItemCarrito item : carrito)
        {
            subtotal += item.getSubtotal();
        }
        return subtotal;
    }

    public double calcularDescuento()
    {
        return Math.round(calcularSubtotal() * porcentajeDescuento) / 100.0;
    }

    public double calcularEnvio()
    {
        if(carrito.isEmpty() || zonaEnvio.equals("RECOJO"))
        {
            return 0;
        }
        if(zonaEnvio.equals("LOCAL"))
        {
            return 10;
        }
        return 25;
    }

    public double calcularTotal()
    {
        return calcularSubtotal() - calcularDescuento() + calcularEnvio();
    }

    public Compra realizarCompra(Usuario usuario)
    {
        if(usuario == null)
        {
            throw new IllegalArgumentException("Primero debes iniciar sesión.");
        }
        if(carrito.isEmpty())
        {
            throw new IllegalArgumentException("El carrito está vacío.");
        }
        Compra compra = new Compra(compras.size() + 1, usuario.getId(), carrito,
                calcularSubtotal(), calcularDescuento(), calcularEnvio());
        compras.add(compra);
        vaciarCarrito();
        return compra;
    }

    public void vaciarCarrito()
    {
        carrito.clear();
        porcentajeDescuento = 0;
        zonaEnvio = "RECOJO";
    }

    public ArrayList<Compra> historialUsuario(Usuario usuario)
    {
        ArrayList<Compra> historial = new ArrayList<>();
        for(Compra compra : compras)
        {
            if(compra.getUsuarioId() == usuario.getId())
            {
                historial.add(compra);
            }
        }
        return historial;
    }

    public boolean productoComprado(Usuario usuario, int id)
    {
        for(Compra compra : historialUsuario(usuario))
        {
            for(ItemCarrito item : compra.getProductos())
            {
                if(item.getProducto().getId() == id)
                {
                    return true;
                }
            }
        }
        return false;
    }

    public void agregarResena(Usuario usuario, int id, int calificacion, String comentario)
    {
        buscarProducto(id);
        if(usuario == null || !productoComprado(usuario, id))
        {
            throw new IllegalArgumentException("Solo puedes reseñar un producto comprado.");
        }
        if(calificacion < 1 || calificacion > 5 || comentario.trim().isEmpty())
        {
            throw new IllegalArgumentException("Ingresa una calificación de 1 a 5 y un comentario.");
        }
        for(Resena resena : resenas)
        {
            if(resena.getProductoId() == id && resena.getUsuarioId() == usuario.getId())
            {
                throw new IllegalArgumentException("Ya reseñaste ese producto.");
            }
        }
        resenas.add(new Resena(id, usuario, calificacion, comentario.trim()));
    }

    public ArrayList<Resena> resenasProducto(int id)
    {
        buscarProducto(id);
        ArrayList<Resena> resultado = new ArrayList<>();
        for(Resena resena : resenas)
        {
            if(resena.getProductoId() == id)
            {
                resultado.add(resena);
            }
        }
        return resultado;
    }

    public double promedioProducto(int id)
    {
        ArrayList<Resena> lista = resenasProducto(id);
        if(lista.isEmpty())
        {
            return 0;
        }
        int suma = 0;
        for(Resena resena : lista)
        {
            suma += resena.getCalificacion();
        }
        return (double) suma / lista.size();
    }

    public ArrayList<Producto> getProductos() { return new ArrayList<>(productos); }
    public ArrayList<ItemCarrito> getCarrito() { return new ArrayList<>(carrito); }
}
