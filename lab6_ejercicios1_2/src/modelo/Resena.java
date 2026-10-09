package modelo;

public class Resena {
    private int productoId;
    private int usuarioId;
    private String nombreUsuario;
    private int calificacion;
    private String comentario;

    public Resena(int productoId, Usuario usuario, int calificacion, String comentario)
    {
        this.productoId = productoId;
        usuarioId = usuario.getId();
        nombreUsuario = usuario.getNombre();
        this.calificacion = calificacion;
        this.comentario = comentario;
    }

    public int getProductoId() { return productoId; }
    public int getUsuarioId() { return usuarioId; }
    public String getNombreUsuario() { return nombreUsuario; }
    public int getCalificacion() { return calificacion; }
    public String getComentario() { return comentario; }
}
