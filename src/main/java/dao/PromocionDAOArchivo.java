package dao;

import config.Config;
import model.*;
import model.GestorDeSugerencias;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PromocionDAOArchivo implements PromocionDAO {

    @Override
    public List<Promocion> obtenerTodos() {
        FileReader fr = null;
        BufferedReader br = null;
        List<Promocion> promociones = new ArrayList<>();

        List<Atraccion> atracciones = GestorDeSugerencias.getInstancia().getAtracciones();

        try {
            fr = new FileReader(Config.leerPropiedad("path_promociones"));
            br = new BufferedReader(fr);

            String linea = br.readLine();
            while ((linea != null)) {
                Promocion promocion = toPromocion(linea);
                promociones.add(promocion);

                linea = br.readLine();
            }
            for (Promocion promo:promociones) {
                System.out.println(promo.getNombre());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return promociones;
        }
    }

    public Promocion toPromocion(Object objeto) {
        String linea = (String) objeto;
        Promocion promocion = null;
        List<Atraccion> atracciones = GestorDeSugerencias.getInstancia().getAtracciones();

        String[] datos = linea.split(";");
        int id = Integer.parseInt(datos[0]);
        String nombre = datos[1];
        TipoAtraccion tipoAtraccion = TipoAtraccion.valueOf(datos[2].toUpperCase());
        String atr = datos[3];
        String[] atraccionesStr = atr.split(",");
        List<Atraccion> atraccionesPromo = this.atraccionesStrToList(atraccionesStr, atracciones);
        String tipoPromo = datos[4];


        if (tipoPromo.equals("absoluta")) {
            int monedas = Integer.parseInt(datos[5]);
            promocion = new PromoAbsoluta(id, nombre, tipoAtraccion, atraccionesPromo, monedas);
        } else if (tipoPromo.equals("porcentual")) {
            int porcentaje = Integer.parseInt(datos[5]);
            promocion = new PromoPorcentual(id, nombre, tipoAtraccion, atraccionesPromo, porcentaje);
        } else if (tipoPromo.equals("AxB")) {
            Atraccion atraccion = this.obtenerAtraccionDesdeNombre(datos[5], atracciones);
            promocion = new PromoAxB(id, nombre, tipoAtraccion, atraccionesPromo, atraccion);
        } else {
            throw new Error("Tipo de promoción incorrecto.");
        }

        return promocion;
    }

    @Override
    public int actualizar(Promocion promocion) {
        return 0;
    }

    public static List<Atraccion> atraccionesStrToList(String[] atraccionesStrings, List<Atraccion> atracciones) {
        List<Atraccion> atraccionesObj = new ArrayList<>();

        for (int i = 0; i < atraccionesStrings.length; i++) {
            atraccionesObj.add(obtenerAtraccionDesdeNombre(atraccionesStrings[i], atracciones));
        }
        return atraccionesObj;
    }

    public static Atraccion obtenerAtraccionDesdeNombre(String nombre, List<Atraccion> atracciones) {
        for (int i = 0; i < atracciones.size(); i++) {
            if (atracciones.get(i).getNombre().equals(nombre)) {
                return atracciones.get(i);
            }
        }
        return null;
    }

}
