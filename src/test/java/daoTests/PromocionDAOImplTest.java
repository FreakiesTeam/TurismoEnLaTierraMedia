package daoTests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import dao.DAOFactory;
import dao.PromocionDAO;
import model.PromoPorcentual;
import model.Promocion;
import model.TipoAtraccion;

public class PromocionDAOImplTest {

	@Test
	public void promocionFindAll() {
		PromocionDAO promocionDAO = DAOFactory.getPromocionDAO();
		ArrayList<Promocion> promociones = promocionDAO.findAll();
		Promocion promocionTest = new PromoPorcentual(3, "Pack Aventura",TipoAtraccion.valueOf("AVENTURA"), new ArrayList<>(), 5);
		Assert.assertTrue(promociones.contains(promocionTest));
	}

}
