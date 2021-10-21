package dao;

import java.sql.*;
import jdbc.ConnectionProvider;
import model.*;

public class AtraccionDAOImpl implements AtraccionDAO{

	@Override
	public int insert(Atraccion atraccion) {
		try {
			String sql2 = "SELECT tipo_atraccion_id FROM tipo_atraccion WHERE tipo_atraccion.nombre = ?";
			String sql = "INSERT INTO atraccion (nombre, precio, tiempo, cupo, tipo_atraccion_id) VALUES (?, ?, ?, ?, ?, ?)";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement2 = conn.prepareStatement(sql2);
			PreparedStatement statement = conn.prepareStatement(sql);
			statement2.setString(1, atraccion.getTipo().toString());
			ResultSet resultadoId = statement2.executeQuery();
			
			statement.setString(1, atraccion.getNombre());
			statement.setInt(2, atraccion.getCosto());
			statement.setDouble(3, atraccion.getTiempo());
			statement.setInt(4, atraccion.getCupoDisponible());
			statement.setInt(5, resultadoId.getInt(1));
			
			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public int update(Atraccion atraccion) {
		try {
			String sql = "UPDATE atraccion SET cupo = ? WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, atraccion.getCupoDisponible());
			statement.setString(2, atraccion.getNombre());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public Atraccion findByName(String atraccionNombre) {
		try {
			String sql = "SELECT * FROM atraccion WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, atraccionNombre);
			ResultSet resultados = statement.executeQuery();

			Atraccion atraccion = null;

			if (resultados.next()) {
				atraccion = toAtraccion(resultados);
			}

			return atraccion;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	private Atraccion toAtraccion(ResultSet resultados) throws SQLException {
		return new Atraccion(resultados.getInt(1), resultados.getString(2), resultados.getInt(3), resultados.getDouble(4), resultados.getInt(5), resultados.getString(6));
	}

}
