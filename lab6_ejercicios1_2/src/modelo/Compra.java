package modelo;

import java.util.ArrayList;

public class Compra {
    private int id;
    private int usuarioId;
    private ArrayList<ItemCarrito> productos;
    private double subtotal;
    private double descuento;
    private double envio;

    public Compra(int id, int usuarioId, ArrayList<ItemCarrito> carrito,
                  double subtotal, double descuento, double envio)
    {
        this.id = id;
        this.usuarioId = usuarioId;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.envio = envio;
        productos = new ArrayList<>();
        for(ItemCarrito item : carrito)
        {
            productos.add(new ItemCarrito(item.getProducto(), item.getCantidad()));
        }
    }

    public int getId() { return id; }
    public int getUsuarioId() { return usuarioId; }
    public double getSubtotal() { return subtotal; }
    public double getDescuento() { return descuento; }
    public double getEnvio() { return envio; }
    public double getTotal() { return subtotal - descuento + envio; }
    public ArrayList<ItemCarrito> getProductos() { return new ArrayList<>(productos); }
}
