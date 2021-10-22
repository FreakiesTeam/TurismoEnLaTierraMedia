package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jdbc.ConnectionProvider;
import model.Atraccion;
import model.PromoAbsoluta;
import model.PromoAxB;
import model.PromoPorcentual;
import model.Promocion;

public class PromocionDAOImpl implements PromocionDAO {

	
	public ArrayList<Promocion> cargarPromociones(){
		ArrayList<Promocion> promociones = new ArrayList<Promocion>();
        Connection con = null;
		try {
			con = ConnectionProvider.getConnection();
		} catch (SQLException e1) {			
			e1.printStackTrace();
		}
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM promocion";
        try{
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                //promociones.add(toPromocion(rs)
              //  );
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                con.close();
                ps.close();
                rs.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return promociones;
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
	

	private Atraccion toPromocion(ResultSet resultados) throws SQLException {
		String sql2 = "SELECT nombre FROM tipo_atraccion WHERE tipo_atraccion.tipo_atraccion_id = ?";
		Connection conn = ConnectionProvider.getConnection();
		PreparedStatement statement = conn.prepareStatement(sql2);
		statement.setInt(1, resultados.getInt(3));
		ResultSet resultadoNombre = statement.executeQuery();
		return null;
		
		//if(resultados.getInt(4) == 1) {
		//	return new PromoAxB(resultados.getInt(1), resultadoNombre.getInt(1), )
	 //}
	}

}
