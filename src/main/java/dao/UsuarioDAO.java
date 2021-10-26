package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Producto;
import model.Usuario;

public interface UsuarioDAO extends GenericDAO<Usuario> {

	public abstract Usuario findByUsername(String username);
	
	public abstract ArrayList<Usuario> cargarUsuarios() throws SQLException;

	public abstract int actualizarItinerario(Usuario usuario);
}
