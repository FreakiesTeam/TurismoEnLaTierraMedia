package dao;

import config.Config;
import model.*;
import tierraMedia.servicios.GestorDeSugerencias;
import tierraMedia.servicios.ManejadorDeArchivos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PromocionDAOArchivo implements PromocionDAO {

    @Override
    public List<Promocion> cargarPromociones() {
        FileReader fr = null;
        BufferedReader br = null;
        List<Promocion> promociones = new ArrayList<>();

        List<Atraccion> atracciones = GestorDeSugerencias.getInstancia().getAtracciones();

        try {
            fr = new FileReader(Config.leerPropiedad("path_promociones"));
            br = new BufferedReader(fr);

            String linea = br.readLine();
            while ((linea != null)) {
                String[] datos = linea.split(";");
                String nombre = datos[0];
                TipoAtraccion tipoAtraccion = TipoAtraccion.valueOf(datos[1].toUpperCase());
                String atr = datos[2];
                String[] atraccionesStr = atr.split(",");
                List<Atraccion> atraccionesPromo = this.atraccionesStrToList(atraccionesStr, atracciones);
                String tipoPromo = datos[3];


                if (datos[3].equals("absoluta")) {
                    int monedas = Integer.parseInt(datos[4]);
                    promociones.add(new PromoAbsoluta(0, nombre, tipoAtraccion, atraccionesPromo, monedas));
                } else if (tipoPromo.equals("porcentual")) {
                    int porcentaje = Integer.parseInt(datos[4]);
                    promociones.add(new PromoPorcentual(0, nombre, tipoAtraccion, atraccionesPromo, porcentaje));
                } else if (tipoPromo.equals("AxB")) {
                    Atraccion atraccion = this.obtenerAtraccionDesdeNombre(datos[4], atracciones);
                    promociones.add(new PromoAxB(0, nombre, tipoAtraccion, atraccionesPromo, atraccion));
                } else {
                    throw new Error("Tipo de promoción incorrecto.");
                }

                linea = br.readLine();
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

    public static List<Atraccion> atraccionesStrToList(String[] atraccionesStrings, List<Atraccion> atracciones) {
        List<Atraccion> atraccionesObj = new ArrayList<>();

        for (int i = 0; i < atraccionesStrings.length; i++) {
            atraccionesObj.add(ManejadorDeArchivos.obtenerAtraccionDesdeNombre(atraccionesStrings[i], atracciones));
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

    @Override
    public int insert(Promocion promocion) {
        return 0;
    }

    @Override
    public int update(Promocion promocion) {
        return 0;
    }

    @Override
    public int delete(Promocion promocion) {
        return 0;
    }

    @Override
    public Promocion findByName(String nombre) {
        return null;
    }


}
