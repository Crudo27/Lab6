package principal;

import modelo.TiendaModelo;
import modelo.UsuarioModelo;
import vista.TiendaVista;
import controlador.TiendaControlador;

public class Main {
    public static void main(String[] args)
    {
        TiendaModelo modelo = new TiendaModelo();
        UsuarioModelo usuarios = new UsuarioModelo();
        TiendaVista vista = new TiendaVista();
        TiendaControlador controlador = new TiendaControlador(modelo, usuarios, vista);
        controlador.iniciar();
    }
}
