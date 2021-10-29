package dao;

import java.sql.*;
import java.util.*;

import config.Config;
import jdbc.ConnectionProvider;
import model.Producto;
import model.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {

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
            String sql = "UPDATE usuario SET monedas = ?, tiempo = ? WHERE nombre = ?";
            Connection conn = ConnectionProvider.getConnection(Config.leerPropiedad("db"));
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
        String tipoPreferido = ((AtraccionDAOImpl) atraccionDAO).obtenerTipoNombre(idTipoPreferido);

        return new Usuario(id, nombre, tipoPreferido, monedas, tiempo);
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
