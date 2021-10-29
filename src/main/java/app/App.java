package app;

import java.io.IOException;
import java.sql.SQLException;

import config.Config;
import model.GestorDeSugerencias;

public class App {
    public static void main(String[] args) throws IOException, SQLException {
        Config.usarBD = true;
        GestorDeSugerencias gestor = GestorDeSugerencias.getInstancia();
        gestor.cargarUsuarios();
        gestor.cargarProductos();
        gestor.generarSugerenciasParaUsuarios();
    }
}
