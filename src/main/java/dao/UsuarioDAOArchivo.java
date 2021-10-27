package dao;

import model.Producto;
import model.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	public void actualizarItinerario(List<Producto> itinerario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int actualizarItinerario(Usuario usuario) {
		// TODO Auto-generated method stub
		return 0;
	}
}
