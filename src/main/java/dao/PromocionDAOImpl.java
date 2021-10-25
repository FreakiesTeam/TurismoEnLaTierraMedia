package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.ConnectionProvider;
import model.*;

import javax.xml.stream.FactoryConfigurationError;

public class PromocionDAOImpl implements PromocionDAO {


    public ArrayList<Promocion> cargarPromociones() {

        try {
            String query = "SELECT * FROM promocion";
            Connection con = ConnectionProvider.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            ArrayList<Promocion> promociones = new ArrayList<>();

            while (rs.next()) {
                promociones.add(toPromocion(rs));
            }
            con.close();
            return promociones;

        } catch (Exception e) {
            throw new MissingDataException(e);
        }
    }


    @Override
    public int insert(Promocion promocion) {
        try {
            String sql = "INSERT INTO promocion (nombre,  tipo_atraccion_id, tipo_promocion_id, descuento) VALUES (?, ?, ?, ?)";
            Connection conn = ConnectionProvider.getConnection();

            String sql2 = "SELECT tipo_atraccion_id FROM tipo_atraccion WHERE tipo_atraccion.nombre = ?";
            PreparedStatement statement2 = conn.prepareStatement(sql2);
            statement2.setString(1, promocion.getTipo().toString().toLowerCase());
            ResultSet resultadoId = statement2.executeQuery();

            String sql3 = "SELECT id FROM tipo_promocion WHERE tipo_promocion.nombre = ?";
            PreparedStatement statement3 = conn.prepareStatement(sql3);
            statement3.setString(1, promocion.getTipoPromo());
            ResultSet resultado2 = statement3.executeQuery();

            String sql4 = "SELECT id FROM atraccion WHERE atraccion.nombre = ?";
            PreparedStatement statement4 = conn.prepareStatement(sql4);
            statement4.setString(1, promocion.getTipoPromo());
            ResultSet resultado3 = statement4.executeQuery();

            String descuento = null;
            if (promocion.esPromoAxB()) {
                PromoAxB promoAxB = (PromoAxB) promocion;
                descuento = promoAxB.getAtraccionGratis().getNombre();
            } else if (promocion.esPorcentual()) {
                PromoPorcentual promoPorcentual = (PromoPorcentual) promocion;
                descuento = Integer.toString(promoPorcentual.getPorcentaje());
            } else if (promocion.esAbsoluta()) {
                PromoAbsoluta promoAbsolut = (PromoAbsoluta) promocion;
                descuento = Integer.toString(promoAbsolut.getDescuento());
            }

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, promocion.getNombre());
            statement.setInt(2, resultadoId.getInt(1));
            statement.setInt(3, resultado2.getInt(1));
            statement.setInt(4, resultado3.getInt(1));
            statement.setString(5, descuento);
            int rows = statement.executeUpdate();
            return rows;
        } catch (Exception e) {
            throw new MissingDataException(e);
        }
    }

    @Override
    public int update(Promocion promocion) {

        return 0;
    }

    @Override
    public Promocion findByName(String nombre) {

        return null;
    }

    private Promocion toPromocion(ResultSet resultados) throws SQLException {

        int idPromo = resultados.getInt(1);
        String nombre = resultados.getString(2);
        int idTipoAtraccion = resultados.getInt(3);
        int idTipoPromo = resultados.getInt(4);
        String descuento = resultados.getString(5);


        AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
        TipoAtraccion tipo_atraccion = TipoAtraccion.valueOf(atraccionDAO.obtenerTipoNombre(idTipoAtraccion));

        String tipo_promocion = obtenerTipoNombre(idTipoPromo);

        List<Atraccion> atracciones = this.obtenerAtraccionesDePromocion(idPromo);


        if (tipo_promocion.equals("AXB")) {
            Atraccion atraccionGratis = atraccionDAO.findByName(descuento);

            return new PromoAxB(nombre, tipo_atraccion, atracciones, atraccionGratis);

        } else if (tipo_promocion.equals("ABSOLUTA")) {

            return new PromoAbsoluta(nombre, tipo_atraccion, atracciones, Integer.parseInt(descuento));

        } else if (tipo_promocion.equals("PORCENTUAL")) {

            return new PromoPorcentual(nombre, tipo_atraccion, atracciones, Integer.parseInt(descuento));
        } else {
            throw new RuntimeException("Tipo de promoción inexistente.");
        }

    }

    private List<Atraccion> obtenerAtraccionesDePromocion(int idPromocion) throws SQLException {
        //language=SQL
        String sql = "SELECT * FROM atraccion_x_promocion WHERE promocion_id = ?";
        Connection conn = ConnectionProvider.getConnection();
        PreparedStatement statement2 = conn.prepareStatement(sql);
        statement2.setInt(1, idPromocion);
        ResultSet resultados = statement2.executeQuery();
        //resultado id promo / id atraccion

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
