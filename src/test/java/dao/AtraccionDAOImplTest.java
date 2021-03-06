package dao;

import java.util.List;

import config.Config;
import org.junit.Assert;
import org.junit.Test;

import model.Atraccion;

public class AtraccionDAOImplTest {

	@Test
	public void obtenerTodasLasAtraccionesTest() {
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		List<Atraccion> atracciones = atraccionDAO.obtenerTodos(Config.leerPropiedad("db_test"));
		Atraccion atraccionTest = new Atraccion(1, "Moria", 6, 6, 6, "AVENTURA");
		Assert.assertTrue(atracciones.contains(atraccionTest));
	}
}
