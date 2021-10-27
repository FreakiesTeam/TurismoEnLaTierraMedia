package daoTests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import dao.AtraccionDAO;
import dao.DAOFactory;
import model.Atraccion;

public class AtraccionDAOImplTest {

	@Test
	public void findAllAtraccion() {
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		ArrayList<Atraccion> atracciones = atraccionDAO.findAll();
		Atraccion atraccionTest = new Atraccion(1, "Moria", 6, 6, 6, "AVENTURA");
		Assert.assertTrue(atracciones.contains(atraccionTest));
	}
}
