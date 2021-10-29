package dao;

import config.Config;
import model.Atraccion;
import model.Producto;
import model.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOArchivo implements UsuarioDAO {


    public List<Usuario> obtenerTodos(String url) {
        FileReader fr = null;
        BufferedReader br = null;
        List<Usuario> usuarios = new ArrayList<>();

        try {
            fr = new FileReader(Config.leerPropiedad("path_usuarios"));
            br = new BufferedReader(fr);

            String linea = br.readLine();
            while ((linea != null)) {
                Usuario usuario = toUsuario(linea);
                usuarios.add(usuario);
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

    private Usuario toUsuario(String linea) {
        String[] datos = linea.split(";");
        int id = Integer.parseInt(datos[0]);
        String nombre = datos[1];
        String preferencia = datos[2];
        int monedas = Integer.parseInt(datos[3]);
        int tiempo = Integer.parseInt(datos[4]);

        return new Usuario(id, nombre, preferencia, monedas, tiempo);
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

    @Override
    public int actualizar(Usuario usuario) {
        return 0;
    }

    @Override
    public Usuario toUsuario(Object objeto) {

        return null;
    }
}

