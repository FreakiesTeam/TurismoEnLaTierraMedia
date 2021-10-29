package dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import model.PromoPorcentual;
import model.Promocion;
import model.TipoAtraccion;

public class PromocionDAOImplTest {

	@Test
	public void obtenerTodasLasPromocionesTest() {
		PromocionDAO promocionDAO = DAOFactory.getPromocionDAO();
		List<Promocion> promociones = promocionDAO.obtenerTodos();
		Promocion promocionTest = new PromoPorcentual(3, "Pack Aventura",TipoAtraccion.valueOf("AVENTURA"), null, 5);
		Assert.assertTrue(promociones.contains(promocionTest));
	}

}
