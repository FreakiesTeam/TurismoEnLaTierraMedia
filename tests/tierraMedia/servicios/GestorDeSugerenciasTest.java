package tierraMedia.servicios;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import model.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestorDeSugerenciasTest {
    GestorDeSugerencias gestor;
    List<Producto> sugerencias = new ArrayList<>();
    Usuario usuario;
    List<Atraccion> atracciones;
    PromoPorcentual porcentual;
    PromoAbsoluta absoluta;
    PromoAxB axb;
    Atraccion atraccionGratis;
    List<Promocion> promociones;
    Atraccion atraccion1, atraccion2;

    @Before
    public void setUp() throws IOException {
        gestor = new GestorDeSugerencias();
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

    }

    @Test
    public void sugerirPreferidoMasCaroPrimeroTest(){
        gestor.setAtracciones(atracciones);
        gestor.setPromociones(promociones);
        sugerencias = gestor.generarSugerenciasPara(usuario);

        Assert.assertEquals("PROMO AXB",sugerencias.get(0).getNombre());
    }

    @Test
    public void ordenarPromocionesTest() {
        gestor.setPromociones(promociones);
        List<Producto> sugerencia = gestor.generarSugerenciasPara(usuario);

        List<Promocion> esperado = new ArrayList<>();

        esperado.add(axb);
        esperado.add(absoluta);
        esperado.add(porcentual);

        Assert.assertEquals(esperado, sugerencia);

    }

    @Test
    public void ordenarAtraccionesTest() {
        atracciones.add(atraccionGratis);

        gestor.setAtracciones(atracciones);
        List<Producto> sugerencia = gestor.generarSugerenciasPara(usuario);

        List<Atraccion> esperado = new ArrayList<>();
        esperado.add(atraccion2);
        esperado.add(atraccion1);
        esperado.add(atraccionGratis);

        Assert.assertEquals(esperado, sugerencia);
    }


}
