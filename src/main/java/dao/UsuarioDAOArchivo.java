package dao;

import config.Config;
import model.Atraccion;
import model.Producto;
import model.Usuario;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOArchivo implements UsuarioDAO {

    public List<Usuario> cargarUsuarios() {
        FileReader fr = null;
        BufferedReader br = null;
        List<Usuario> usuarios = new ArrayList<>();

        try {
            fr = new FileReader(Config.leerPropiedad("path_usuarios"));
            br = new BufferedReader(fr);

            String linea = br.readLine();
            while ((linea != null)) {
                String[] datos = linea.split(";");
                String nombre = datos[0];
                String preferencia = datos[1];
                int monedas = Integer.parseInt(datos[2]);
                int tiempo = Integer.parseInt(datos[3]);

                usuarios.add(new Usuario(1, nombre, preferencia, monedas, tiempo));
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
            return usuarios;
        }
    }

    @Override
    public int insert(Usuario usuario) {
        return 0;
    }

    @Override
    public int update(Usuario usuario) {
        return 0;
    }

    @Override
    public int delete(Usuario usuario) {
        return 0;
    }

    @Override
    public Usuario findByUsername(String username) {
        return null;
    }


    @Override
    public int actualizarItinerario(Usuario usuario) {
        String path = "salida/" + usuario.getNombre() + ".out";
        try {
            PrintWriter salida = new PrintWriter(new FileWriter(path));

            salida.println("<<< Itinerario de " + usuario.getNombre() + ">>>");

            int costoTotal = 0;
            double tiempoTotal = 0;
            for (Producto producto : usuario.getItinerario()) {
                salida.println();
                salida.println("Nombre del producto: " + producto.getNombre());
                salida.println("Tipo de atracción: " + producto.getTipo());
                salida.print("Costo: " + producto.getCosto());
                salida.println(" Tiempo: " + producto.getTiempo());
                costoTotal += producto.getCosto();
                tiempoTotal += producto.getTiempo();
                salida.println("---------------------------------");
            }
            salida.println("Atracciones a visitar: ");
            for (Atraccion atraccion : usuario.getAtraccionesCompradas()) {
                salida.println(atraccion.getNombre());
            }
            salida.println("Costo total: " + costoTotal);
            salida.println("Tiempo total: " + tiempoTotal);
            salida.close();
        } catch (Exception e) {
            throw new RuntimeException("Error");
        }
        return 0;
    }
}

