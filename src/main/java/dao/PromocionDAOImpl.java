package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jdbc.ConnectionProvider;
import model.*;

public class PromocionDAOImpl implements PromocionDAO{

	@Override
	public int insert(Promocion promocion) {
		return 0;
	/*	try {
			String sql = "INSERT INTO promocion (nombre,  tipo_atraccion_id, tipo_promocion_id, atraccion_gratis, descuento_absoluto, descuento_porcentual) VALUES (?, ?, ?, ?, ?, ?)";
			Connection conn = ConnectionProvider.getConnection();
			
			String sql2 = "SELECT tipo_atraccion_id FROM tipo_atraccion WHERE tipo_atraccion.nombre = ?";
			PreparedStatement statement2 = conn.prepareStatement(sql2);
			statement2.setString(1, promocion.getTipo().toString());
			ResultSet resultadoId = statement2.executeQuery();
			
			String sql3 = "SELECT id FROM tipo_promocion WHERE tipo_promocion.nombre = ?";
			PreparedStatement statement3 = conn.prepareStatement(sql3);
			statement3.setString(1, promocion.getTipoPromo());			
			ResultSet resultado2 = statement3.executeQuery();
			
			
			String sql4 = "SELECT id FROM atraccion WHERE atraccion.nombre = ?";
			PreparedStatement statement4 = conn.prepareStatement(sql4);
			statement2.setString(1, promocion.getTipoPromo());
			ResultSet resultado3 = statement3.executeQuery();
			
			
			
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, promocion.getNombre());
			statement.setInt(2, resultadoId.getInt(1));
			statement.setInt(3, resultado2.getInt(1));
			statement.setInt(4, resultado3.getInt(1));
			statement.setInt(5, promocion);
			statement.setInt(6, promocion.getTipo().toString());
			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}*/
	}

	@Override
	public int update(Promocion t) {
		
		return 0;
	}

	@Override
	public Promocion findByName(String nombre) {
		
		return null;
	}

}
