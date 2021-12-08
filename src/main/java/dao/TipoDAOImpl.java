package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.Config;
import jdbc.ConnectionProvider;
import model.Tipo;

public class TipoDAOImpl implements TipoDAO{

	@Override
	public int actualizarTipo(Tipo tipoAtraccion) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int actualizar(Tipo tipoAtraccion) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Tipo toTipo(Object objeto) throws SQLException {
		ResultSet resultados = (ResultSet) objeto;

        int id = resultados.getInt(1);
        String nombre = resultados.getString(2);
        String imagen = resultados.getString(3);
        Boolean activo = Boolean.valueOf(resultados.getString(4));

        Tipo tipo = new Tipo(id, nombre, imagen, activo);

        return tipo;
	}

	@Override
	public List<Tipo> obtenerTodos(String url) {
		try {
            String query = "SELECT * FROM tipo_atraccion";
            Connection con = ConnectionProvider.getConnection(Config.leerPropiedad("db"));

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            List<Tipo> tipos = new ArrayList<>();

            while (rs.next()) {
                tipos.add(toTipo(rs));
            }

            return tipos;

        } catch (Exception e) {
            throw new MissingDataException(e);
        }
	}
	
	public int insertar(Tipo tipoAtraccion) {
		//TODO probar
		int rows = 0;
		String sql = "INSERT INTO tipo_atraccion (nombre, imagen, activo) VALUES (?, ?, ?)";
        Connection conn;
        try {
            conn = ConnectionProvider.getConnection(Config.leerPropiedad("db"));
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, tipoAtraccion.getNombre());
            statement.setString(2,tipoAtraccion.getImagen());
            statement.setBoolean(3, tipoAtraccion.getActivo());
            
            rows = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
	}
	
	public int borrar(Tipo tipoAtraccion) {
		try {
			//TODO probar
			String sql = "UPDATE tipo_atraccion SET activo = ? WHERE id = ?";
			Connection conn = ConnectionProvider.getConnection(Config.leerPropiedad("db"));

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, "false");
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

}
