package dao;

import config.Config;
import model.Atraccion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AtraccionDAOArchivo implements AtraccionDAO {

    public List<Atraccion> obtenerTodos(String url) {

        FileReader fr = null;
        BufferedReader br ;
        List<Atraccion> atracciones = new ArrayList<>();

        try {
            fr = new FileReader(Config.leerPropiedad("path_atracciones"));
            br = new BufferedReader(fr);

            String linea = br.readLine();
            while ((linea != null)) {
                Atraccion atraccion = toAtraccion(linea);
                atracciones.add(atraccion);

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
        }
        return atracciones;
    }

    @Override
    public Atraccion toAtraccion(Object objeto) {
        String linea = (String) objeto;
        String[] datos = linea.split(";");
        int id = Integer.parseInt(datos[0]);
        String nombre = datos[1];
        int costo = Integer.parseInt(datos[2]);
        double tiempo = Double.parseDouble(datos[3]);
        int cupoDiario = Integer.parseInt(datos[4]);
        String tipo = datos[5];

        return new Atraccion(id, nombre, costo, tiempo, cupoDiario, tipo);
    }

    @Override
    public int actualizar(Atraccion atraccion) {
        return 0;
    }

    @Override
    public Atraccion obtenerPorId(int id) {
        return null;
    }

    @Override
    public Atraccion obtenerPorNombre(String nombre) {
        return null;
    }

}
