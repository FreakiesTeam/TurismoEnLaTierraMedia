package dao;

import java.sql.*;
import java.util.*;

import config.Config;
import jdbc.ConnectionProvider;
import model.*;

public class PromocionDAOImpl implements PromocionDAO {

    @Override
    public ArrayList<Promocion> obtenerTodos(String url) {

        try {
            String query = "SELECT * FROM promocion";
            Connection con = ConnectionProvider.getConnection(url);
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            ArrayList<Promocion> promociones = new ArrayList<>();

            while (rs.next()) {
                promociones.add(toPromocion(rs));
            }

            return promociones;

        } catch (Exception e) {
            throw new MissingDataException(e);
        }
    }

    @Override
    public int actualizar(Promocion promocion) {
        AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
        for (Atraccion atraccion : promocion.getAtraccionesTotales()) {
            atraccionDAO.actualizar(atraccion);
        }
        return 0;
    }

    @Override
    public Promocion toPromocion(Object objeto) {
        ResultSet resultados = (ResultSet) objeto;
        Promocion promocion = null;
        int idPromo;

        try {
            idPromo = resultados.getInt(1);
            String nombre = resultados.getString(2);
            int idTipoAtraccion = resultados.getInt(3);
            int idTipoPromo = resultados.getInt(4);
            String descuento = resultados.getString(5);
            String descripcion = resultados.getString(6);
            String imagen = resultados.getString(7);


            AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
            TipoAtraccion tipo_atraccion = TipoAtraccion.valueOf(((AtraccionDAOImpl) atraccionDAO).obtenerTipoNombre(idTipoAtraccion));

            String tipo_promocion = obtenerTipoNombre(idTipoPromo);

            List<Atraccion> atracciones = this.obtenerAtraccionesDePromocion(idPromo);

            switch (tipo_promocion) {
                case "AXB":
                    Atraccion atraccionGratis = atraccionDAO.obtenerPorNombre(descuento);

                    promocion = new PromoAxB(idPromo, nombre, tipo_atraccion, atracciones, atraccionGratis);
                    promocion.setDescripcion(descripcion);
                    promocion.setImagen(imagen);

                    break;
                case "ABSOLUTA":

                    promocion = new PromoAbsoluta(idPromo, nombre, tipo_atraccion, atracciones, Integer.parseInt(descuento));
                    promocion.setDescripcion(descripcion);
                    promocion.setImagen(imagen);

                    break;
                case "PORCENTUAL":

                    promocion = new PromoPorcentual(idPromo, nombre, tipo_atraccion, atracciones, Integer.parseInt(descuento));
                    promocion.setDescripcion(descripcion);
                    promocion.setImagen(imagen);

                    break;
                default:
                    throw new RuntimeException("Tipo de promoción inexistente.");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return promocion;
    }

    public List<Atraccion> obtenerAtraccionesDePromocion(int idPromocion) throws SQLException {

        String sql = "SELECT * FROM atraccion_x_promocion WHERE promocion_id = ?";
        Connection conn = ConnectionProvider.getConnection(Config.leerPropiedad("db"));
        PreparedStatement statement2 = conn.prepareStatement(sql);
        statement2.setInt(1, idPromocion);
        ResultSet resultados = statement2.executeQuery();

        List<Atraccion> atracciones = new ArrayList<>();
        AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();

        while (resultados.next()) {
            Atraccion atraccion = atraccionDAO.obtenerPorId(resultados.getInt(2));
            atracciones.add(atraccion);
        }

        return atracciones;
    }

    public String obtenerTipoNombre(int idTipo) throws SQLException {
        Connection conn = ConnectionProvider.getConnection(Config.leerPropiedad("db"));
        String sql2 = "SELECT nombre FROM tipo_promocion WHERE id = ?";
        PreparedStatement statement2 = conn.prepareStatement(sql2);
        statement2.setInt(1, idTipo);

        ResultSet resultadoId = statement2.executeQuery();

        return resultadoId.getString(1).toUpperCase();
    }

}
