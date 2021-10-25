package app;

import java.io.IOException;
import java.sql.SQLException;

import tierraMedia.servicios.GestorDeSugerencias;

public class App {
	public static void main(String[] args) throws IOException, SQLException {
		GestorDeSugerencias gestor = new GestorDeSugerencias();
		gestor.cargarUsuarios();
		gestor.cargarProductos();
		gestor.generarSugerenciasParaUsuarios();
	}
}
