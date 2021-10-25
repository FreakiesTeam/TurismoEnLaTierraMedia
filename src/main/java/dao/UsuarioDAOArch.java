package dao;

import model.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDAOArch implements UsuarioDAO {

    @Override
    public int insert(Usuario usuario) {
        return 0;
    }

    @Override
    public int update(Usuario usuario) {
        return 0;
    }

    @Override
    public Usuario findByUsername(String username) {
        return null;
    }

    @Override
    public ArrayList<Usuario> cargarUsuarios() throws SQLException {
        return null;
    }
}
