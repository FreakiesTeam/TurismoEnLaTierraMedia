package tierraMedia;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UsuarioTest {
    Usuario usuario;
    PromoPorcentual porcentual;
    PromoAbsoluta absoluta;
    PromoAxB axb;
    Atraccion atraccionGratis;
    List<Atraccion> atracciones;
    Atraccion atraccion1,atraccion2;
    List<Promocion> promociones;

    @Before
    public void setup() {
        usuario = new Usuario(1,"Marcos", "PAISAJE",10,10);

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

    }

    @Test
    public void noSeVisitoAtraccionTest(){
        Atraccion atraccion3 = new Atraccion(4,"atraccion3", 60, 2, 2, "AVENTURA");
        usuario.setAtraccionesCompradas(atracciones);
        Assert.assertTrue(usuario.noSeVisito(atraccion3));
    }

    @Test
    public void seVisitoAtraccionEnPromocionTest() {
        atracciones.add(atraccionGratis);
        usuario.setAtraccionesCompradas(atracciones);
        Assert.assertFalse(usuario.noSeVisito(axb));
    }

    @Test
    public void escribirArchivoTest() throws IOException {
        //Comparamos el archivo que se genera en el test con uno con los valores esperados

        List<Producto> itinerario = new ArrayList<>();
        List<Atraccion> atracciones = new ArrayList<>();
        Atraccion minasTirith = new Atraccion (1,"Minas Tirith",5,2.5,25,"PAISAJE");
        Atraccion abismo = new Atraccion (2,"Abismo de Helm",5,2,15,"PAISAJE");
        Atraccion erebor = new Atraccion (3,"Erebor",12,3,32,"PAISAJE");
        atracciones.add(minasTirith);
        atracciones.add(abismo);
        Promocion promo = new PromoAxB("Pack paisajes", TipoAtraccion.valueOf("PAISAJE"), atracciones, erebor);
        Usuario sole = new Usuario(1,"Sole", "PAISAJE", 100, 100);
        itinerario.add(promo);
        sole.setItinerario(itinerario);
        List<Atraccion> compradas = new ArrayList<>();
        compradas.add(minasTirith);
        compradas.add(abismo);
        compradas.add(erebor);
        sole.setAtraccionesCompradas(compradas);
        sole.escribirArchivo();

        FileReader fr1 = null;
        FileReader fr2 = null;
        BufferedReader br1 = null;
        BufferedReader br2 = null;

        try {
            fr1 = new FileReader("salida/Sole.out");
            fr2 = new FileReader("salida/SoleTest.out");
            br1 = new BufferedReader(fr1);
            br2 = new BufferedReader(fr2);

            String lineaOriginal = br1.readLine();
            String lineaTest = br2.readLine();
            while ((lineaOriginal != null && lineaTest != null)) {

                Assert.assertEquals(lineaTest,lineaOriginal);

                lineaOriginal = br1.readLine();
                lineaTest = br2.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr1 != null && fr2 != null) {
                    fr1.close();
                    fr2.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


}
