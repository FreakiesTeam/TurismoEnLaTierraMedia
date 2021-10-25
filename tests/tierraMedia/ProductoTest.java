package tierraMedia;

import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tierraMedia.servicios.GestorDeSugerencias;

import java.util.ArrayList;
import java.util.List;

public class ProductoTest {
    List<Atraccion> atracciones;
    PromoPorcentual porcentual;
    PromoAbsoluta absoluta;
    PromoAxB axb;
    Atraccion atraccionGratis;
    List<Promocion> promociones;
    GestorDeSugerencias gestor;
    Usuario usuario;
    Atraccion atraccion1, atraccion2;

    @Before
    public void setUp() {
        atracciones = new ArrayList<>();
        atraccion1 = new Atraccion(1,"atraccion1", 40, 1, 1, "AVENTURA");
        atraccion2 = new Atraccion(2,"atraccion2", 60, 2, 2, "AVENTURA");
        atracciones.add(atraccion1);
        atracciones.add(atraccion2);
        atraccionGratis = new Atraccion(3,"atraccionGratis", 10, 3, 3, "AVENTURA");

        axb = new PromoAxB("PROMO AXB", TipoAtraccion.valueOf("AVENTURA"), atracciones, atraccionGratis);

        absoluta = new PromoAbsoluta("PROMO absoluta", TipoAtraccion.valueOf("AVENTURA"), atracciones, 5);

        porcentual = new PromoPorcentual("PROMO Porcentual", TipoAtraccion.valueOf("AVENTURA"), atracciones, 10);

        promociones = new ArrayList<>();
        promociones.add(axb);
        promociones.add(absoluta);
        promociones.add(porcentual);

        usuario = new Usuario(1,"Maria", "AVENTURA", 150, 120);
        gestor = new GestorDeSugerencias();
    }

    @Test
    public void getAtraccionesTotalesDePromocionTest() {
        List<Atraccion> esperado = new ArrayList<>();
        esperado.addAll(atracciones);
        esperado.add(atraccionGratis);

        Assert.assertEquals(esperado, axb.getAtraccionesTotales());
    }

    @Test
    public void getAtraccionesTotalesDeAtraccionTest() {
        List<Atraccion> esperado = new ArrayList<>();
        esperado.add(atraccionGratis);

        Assert.assertEquals(esperado, atraccionGratis.getAtraccionesTotales());
    }

}
