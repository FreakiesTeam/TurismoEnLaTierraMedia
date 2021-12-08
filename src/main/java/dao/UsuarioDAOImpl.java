package dao;

import java.sql.*;
import java.util.*;

import config.Config;
import jdbc.ConnectionProvider;
import model.Atraccion;
import model.Producto;
import model.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {

	public int insert(Usuario usuario) {
		try {
			String sql = "INSERT INTO usuario (nombre, tipo_preferido_id,monedas,tiempo,imagen,hash_contrasenia,activo) VALUES (?, ?,?,?,?,?,?)";
			Connection conn = ConnectionProvider.getConnection(Config.leerPropiedad("db"));

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, usuario.getNombre());
			statement.setString(2, String.valueOf(usuario.getTipoPreferido()));
			statement.setInt(3,usuario.getMonedas());
			statement.setDouble(4,usuario.getTiempoDisponible());
			statement.setString(5, usuario.getImagenPerfil());
			statement.setString(6, usuario.getHashContrasenia());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public List<Usuario> obtenerTodos(String url) {
		try {
			String query = "SELECT * FROM usuario";
			Connection con = ConnectionProvider.getConnection(Config.leerPropiedad("db"));

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

	public int actualizar(Usuario usuario) {
		try {
			String sql = "UPDATE usuario SET monedas = ?, tiempo = ? WHERE id = ?";
			Connection conn = ConnectionProvider.getConnection(Config.leerPropiedad("db"));
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, usuario.getMonedas());
			statement.setDouble(2, usuario.getTiempoDisponible());
			statement.setInt(3, usuario.getId());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public Usuario findByUsername(String username) {
		try {
			String sql = "SELECT * FROM usuario WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection(Config.leerPropiedad("db"));
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

	@Override
	public Usuario toUsuario(Object objeto) throws SQLException {
		ResultSet resultados = (ResultSet) objeto;
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();

		int id = resultados.getInt(1);
		String nombre = resultados.getString(2);
		int idTipoPreferido = resultados.getInt(3);
		int monedas = resultados.getInt(4);
		Double tiempo = resultados.getDouble(5);
		String imagenPerfil = resultados.getString(6);

		String tipoPreferido = ((AtraccionDAOImpl) atraccionDAO).obtenerTipoNombre(idTipoPreferido);
		Usuario usuario = new Usuario(id, nombre, tipoPreferido, monedas, tiempo);
		usuario.setImagenPerfil(imagenPerfil);
		ItinerarioDAO itinerarioDAO = DAOFactory.getItinerarioDAO();
		List<Atraccion> atraccionesCompradas = itinerarioDAO.obtenerAtraccionesCompradas(id);

		usuario.setAtraccionesCompradas(atraccionesCompradas);

		return usuario;
	}

	@Override
	public int actualizarItinerario(Usuario usuario) {
		int rows = 0;
		for (Producto producto : usuario.getItinerario()) {
			String sql = "INSERT INTO itinerario (usuario_id, atraccion_id, promocion_id) VALUES (?, ?, ?)";
			Connection conn;
			try {
				conn = ConnectionProvider.getConnection(Config.leerPropiedad("db"));
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setInt(1, usuario.getId());
				if (producto.esPromocion()) {
					statement.setString(2, null);
					statement.setInt(3, producto.getId());
				} else if (!producto.esPromocion()) {
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
