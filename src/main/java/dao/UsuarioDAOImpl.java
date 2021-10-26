package dao;

import java.sql.*;
import java.util.*;

import jdbc.ConnectionProvider;
import model.Producto;
import model.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {

	public ArrayList<Usuario> cargarUsuarios() {
		try {
			String query = "SELECT * FROM usuario";
			Connection con = ConnectionProvider.getConnection();

			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			ArrayList<Usuario> usuarios = new ArrayList<>();

			while (rs.next()) {
				usuarios.add(toUsuario(rs));
			}

			return usuarios;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}

	}

	public int insert(Usuario usuario) {
		try {
			String sql2 = "SELECT tipo_atraccion_id FROM tipo_atraccion WHERE tipo_atraccion.nombre = ?";
			String sql = "INSERT INTO usuarios (nombre, tipo_preferido_id, monedas, tiempo) VALUES (?, ?, ?, ?)";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement2 = conn.prepareStatement(sql2);
			PreparedStatement statement = conn.prepareStatement(sql);
			statement2.setString(1, usuario.getTipoPreferido().toString());
			ResultSet resultadoId = statement2.executeQuery();
			statement.setString(1, usuario.getNombre());
			statement.setInt(2, resultadoId.getInt(1));
			statement.setInt(3, usuario.getMonedas());
			statement.setDouble(4, usuario.getTiempoDisponible());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int update(Usuario usuario) {
		try {
			String sql = "UPDATE usuario SET monedas = ?, tiempo = ? WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, usuario.getMonedas());
			statement.setDouble(2, usuario.getTiempoDisponible());
			statement.setString(3, usuario.getNombre());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public Usuario findByUsername(String username) {
		try {
			String sql = "SELECT * FROM usuario WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet resultados = statement.executeQuery();

			Usuario user = null;

			if (resultados.next()) {
				user = toUsuario(resultados);
			}

			return user;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	private Usuario toUsuario(ResultSet resultados) throws SQLException {
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();

		int id = resultados.getInt(1);
		String nombre = resultados.getString(2);
		int idTipoPreferido = resultados.getInt(3);
		int monedas = resultados.getInt(4);
		Double tiempo = resultados.getDouble(5);
		String tipoPreferido = atraccionDAO.obtenerTipoNombre(idTipoPreferido);

		return new Usuario(id, nombre, tipoPreferido, monedas, tiempo);
	}

	@Override
	public int actualizarItinerario(Usuario usuario) {
		int rows = 0;
		for (Producto producto : usuario.getItinerario()) {
			String sql = "INSERT INTO itinerario (usuario_id, atraccion_id, promocion_id) VALUES (?, ?, ?)";
			Connection conn;
			try {
				conn = ConnectionProvider.getConnection();
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setInt(1, usuario.getId());
				if (producto.esPromocion()) {
					System.out.println("compraste esta promo: " + producto.getNombre());
					statement.setString(2, null);
					statement.setInt(3, producto.getId());
				} else if(!producto.esPromocion()){
					System.out.println("compraste esta atraccion: " + producto.getNombre());
					statement.setInt(2, producto.getId());
					statement.setString(3, null);
				}
				rows = statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return rows;
	}

}
