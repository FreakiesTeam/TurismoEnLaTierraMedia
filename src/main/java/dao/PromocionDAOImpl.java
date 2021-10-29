package dao;

import java.sql.*;
import java.util.*;

import jdbc.ConnectionProvider;
import model.*;

public class PromocionDAOImpl implements PromocionDAO {

    @Override
    public ArrayList<Promocion> obtenerTodos() {

        try {
            String query = "SELECT * FROM promocion";
            Connection con = ConnectionProvider.getConnection();
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
        int idPromo = 0;

        try {
            idPromo = resultados.getInt(1);
        String nombre = resultados.getString(2);
        int idTipoAtraccion = resultados.getInt(3);
        int idTipoPromo = resultados.getInt(4);
        String descuento = resultados.getString(5);


        AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
        TipoAtraccion tipo_atraccion = TipoAtraccion.valueOf(((AtraccionDAOImpl) atraccionDAO).obtenerTipoNombre(idTipoAtraccion));

        String tipo_promocion = obtenerTipoNombre(idTipoPromo);

        List<Atraccion> atracciones = this.obtenerAtraccionesDePromocion(idPromo);

        if (tipo_promocion.equals("AXB")) {
            Atraccion atraccionGratis = atraccionDAO.findByName(descuento);

            promocion = new PromoAxB(idPromo, nombre, tipo_atraccion, atracciones, atraccionGratis);

        } else if (tipo_promocion.equals("ABSOLUTA")) {

            promocion =  new PromoAbsoluta(idPromo, nombre, tipo_atraccion, atracciones, Integer.parseInt(descuento));

        } else if (tipo_promocion.equals("PORCENTUAL")) {

            promocion = new PromoPorcentual(idPromo, nombre, tipo_atraccion, atracciones, Integer.parseInt(descuento));

        } else {
            throw new RuntimeException("Tipo de promoción inexistente.");
        }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return promocion;
    }

    private List<Atraccion> obtenerAtraccionesDePromocion(int idPromocion) throws SQLException {

        String sql = "SELECT * FROM atraccion_x_promocion WHERE promocion_id = ?";
        Connection conn = ConnectionProvider.getConnection();
        PreparedStatement statement2 = conn.prepareStatement(sql);
        statement2.setInt(1, idPromocion);
        ResultSet resultados = statement2.executeQuery();

        List<Atraccion> atracciones = new ArrayList<>();
        AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();

        while (resultados.next()) {
            Atraccion atraccion = atraccionDAO.findById(resultados.getInt(2));
            atracciones.add(atraccion);
        }

        return atracciones;
    }

    public String obtenerTipoNombre(int idTipo) throws SQLException {
        Connection conn = ConnectionProvider.getConnection();
        String sql2 = "SELECT nombre FROM tipo_promocion WHERE id = ?";
        PreparedStatement statement2 = conn.prepareStatement(sql2);
        statement2.setInt(1, idTipo);

        ResultSet resultadoId = statement2.executeQuery();

        return resultadoId.getString(1).toUpperCase();
    }

}
