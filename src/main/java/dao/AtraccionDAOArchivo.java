package dao;

import config.Config;
import model.Atraccion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtraccionDAOArchivo implements AtraccionDAO{

    @Override
    public Atraccion findByName(String nombre) {
        return null;
    }

    public ArrayList<Atraccion> findAll() {
        FileReader fr = null;
        BufferedReader br = null;
        List<Atraccion> atracciones = new ArrayList<>();

        try {
            fr = new FileReader(Config.leerPropiedad("path_atracciones"));
            br = new BufferedReader(fr);

            String linea = br.readLine();
            while ((linea != null)) {
                String[] datos = linea.split(";");
                String nombre = datos[0];
                int costo = Integer.parseInt(datos[1]);
                double tiempo = Double.parseDouble(datos[2]);
                int cupoDiario = Integer.parseInt(datos[3]);
                String tipo = datos[4];

                atracciones.add(new Atraccion(1, nombre, costo, tiempo, cupoDiario, tipo));
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
            return (ArrayList<Atraccion>) atracciones;
        }
    }

    @Override
    public int obtenerTipoId(String nombreTipo) throws SQLException {
        return 0;
    }

    @Override
    public String obtenerTipoNombre(int idTipo) throws SQLException {
        return null;
    }

    @Override
    public Atraccion toAtraccion(ResultSet resultados) throws SQLException {
        return null;
    }

    @Override
    public Atraccion findById(int id) {
        return null;
    }

    public List<Atraccion> atraccionesStrToList(String[] atraccionesStrings, List<Atraccion> atracciones) {
        List<Atraccion> atraccionesObj = new ArrayList<>();

        for (int i = 0; i < atraccionesStrings.length; i++) {
            atraccionesObj.add(this.obtenerAtraccionDesdeNombre(atraccionesStrings[i], atracciones));
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
    public int insert(Atraccion atraccion) {
        return 0;
    }

    @Override
    public int update(Atraccion atraccion) {
        return 0;
    }
}
