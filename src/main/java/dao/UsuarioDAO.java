package dao;

import java.sql.SQLException;
import java.util.List;

import model.Usuario;

public interface UsuarioDAO {

    public abstract int actualizarItinerario(Usuario usuario);

    public int actualizar(Usuario usuario);

    Usuario toUsuario(Object objeto) throws SQLException;

    public List<Usuario> obtenerTodos(String url);
}
