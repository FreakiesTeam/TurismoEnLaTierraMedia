package model.atracciones;

import model.Atraccion;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AtraccionTest {
    Atraccion atraccion;

    @Before
    public void setUp() {
        atraccion = new Atraccion(1,"atraccion1", 40, 1, 1, "AVENTURA");
    }

    @Test
    public void costoAtraccionTest() {
        Assert.assertEquals(40, (int) atraccion.getCosto());
    }

    @Test
    public void tiempoAtraccionTest() {
        Assert.assertEquals(1,atraccion.getTiempo(),0);
    }
    @Test
    public void tieneCupoTest() {
        Assert.assertTrue(atraccion.tieneCupo());
        atraccion.actualizarCupo();
        Assert.assertFalse(atraccion.tieneCupo());
    }
}
