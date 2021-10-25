package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Usuario;

public interface UsuarioDAO extends GenericDAO<Usuario> {

	public abstract Usuario findByUsername(String username);
	
	public abstract ArrayList<Usuario> cargarUsuarios() throws SQLException;
}
