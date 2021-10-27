package dao;

import java.sql.*;
import java.util.ArrayList;

import jdbc.ConnectionProvider;
import model.Atraccion;

public class AtraccionDAOImpl implements AtraccionDAO {

	
    public ArrayList<Atraccion> findAll() {

        try {
            String query = "SELECT * FROM atraccion";
            Connection con = ConnectionProvider.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            ArrayList<Atraccion> atracciones = new ArrayList<>();
            while (rs.next()) {
                atracciones.add(toAtraccion(rs)
                );
            }

            return atracciones;

        } catch (Exception e) {
            throw new MissingDataException(e);
        }
    }

    @Override
    public int insert(Atraccion atraccion) {
        try {
            Connection conn = ConnectionProvider.getConnection();

            String sql2 = "SELECT tipo_atraccion_id FROM tipo_atraccion WHERE tipo_atraccion.nombre = ?";
            PreparedStatement statement2 = conn.prepareStatement(sql2);
            statement2.setString(1, atraccion.getTipo().toString());
            ResultSet resultadoId = statement2.executeQuery();

            String sql = "INSERT INTO atraccion (nombre, precio, tiempo, cupo, tipo_atraccion_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, atraccion.getNombre());
            statement.setInt(2, atraccion.getCosto());
            statement.setDouble(3, atraccion.getTiempo());
            statement.setInt(4, atraccion.getCupoDisponible());
            statement.setInt(5, resultadoId.getInt(1));

            int rows = statement.executeUpdate();
            conn.close();
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

    public Atraccion findById(int id) {
        try {
            String sql = "SELECT * FROM atraccion WHERE id = ?";
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
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

    public Atraccion toAtraccion(ResultSet resultados) throws SQLException {

        int id = resultados.getInt(1);
        String nombre = resultados.getString(2);
        int costo = resultados.getInt(3);
        Double tiempo = resultados.getDouble(4);
        int cupo = resultados.getInt(5);
        int idTipoAtraccion = resultados.getInt(6);
        String nombreTipoAtraccion = obtenerTipoNombre(idTipoAtraccion).toUpperCase();

        return new Atraccion(id, nombre, costo, tiempo, cupo, nombreTipoAtraccion);
    }

    public int obtenerTipoId(String nombreTipo) throws SQLException {
        Connection conn = ConnectionProvider.getConnection();
        String sql2 = "SELECT tipo_atraccion_id FROM tipo_atraccion WHERE tipo_atraccion.nombre = ?";
        PreparedStatement statement2 = conn.prepareStatement(sql2);
        statement2.setString(1, nombreTipo);

        ResultSet resultadoId = statement2.executeQuery();
        conn.close();
        return resultadoId.getInt(1);
    }

    public String obtenerTipoNombre(int idTipo) throws SQLException {
        //language=SQL
        String sql = "SELECT nombre FROM tipo_atraccion WHERE tipo_atraccion_id = ?";
        Connection conn = ConnectionProvider.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, idTipo);
        ResultSet resultadoId = statement.executeQuery();

        String nombre = "";
        if(resultadoId.next()){
            nombre = resultadoId.getString(1).toUpperCase();
        }

        return nombre;
    }

}
