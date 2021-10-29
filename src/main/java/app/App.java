package app;

import java.sql.SQLException;

import config.Config;
import model.GestorDeSugerencias;

public class App {
    public static void main(String[] args) throws SQLException {
        Config.usarBD = true;
        GestorDeSugerencias gestor = GestorDeSugerencias.getInstancia();
        gestor.cargarUsuarios(Config.leerPropiedad("db"));
        gestor.cargarAtracciones(Config.leerPropiedad("db"));
        gestor.cargarPromociones(Config.leerPropiedad("db"));
        gestor.generarSugerenciasParaUsuarios();
    }
}
