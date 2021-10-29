package model.servicios;

import config.Config;
import dao.AtraccionDAO;
import dao.DAOFactory;
import dao.PromocionDAO;
import dao.UsuarioDAO;
import model.Atraccion;
import model.Promocion;
import model.Usuario;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class ArchivosDAOS {
    UsuarioDAO usuarioDAO;
    PromocionDAO promocionDAO;
    AtraccionDAO atraccionDAO;
    List<Usuario> usuarios;
    List<Atraccion> atracciones;
    List<Promocion> promociones;

    @Before
    public void setup() {
        Config.usarBD = false;
        usuarioDAO = DAOFactory.getUsuarioDAO();
        promocionDAO = DAOFactory.getPromocionDAO();
        atraccionDAO = DAOFactory.getAtraccionDAO();
        usuarios = usuarioDAO.obtenerTodos(Config.leerPropiedad("path_usuarios"));
        atracciones = atraccionDAO.obtenerTodos(Config.leerPropiedad("path_atracciones"));
        promociones = promocionDAO.obtenerTodos(Config.leerPropiedad("path_promociones"));

    }

    @Test
    public void leerUsuariosTest() {

        System.out.println("---Usuarios---");

        for (Usuario usuario : usuarios) {
            System.out.println("Usuario: " + usuario.getNombre());
            System.out.println("Monedas: " + usuario.getMonedas());
            System.out.println("Tipo preferido: " + usuario.getTipoPreferido());
            System.out.println("Tiempo disponible: " + usuario.getTiempoDisponible());
            System.out.println();
        }
        System.out.println("---Fin usuarios---");
    }

    @Test
    public void leerAtraccionesTest() {
        System.out.println("---Atracciones---");
        for (Atraccion atraccion : atracciones) {
            System.out.println("Nombre: " + atraccion.getNombre());
            System.out.println("Costo: " + atraccion.getCosto());
            System.out.println("Tipo: " + atraccion.getTipo());
            System.out.println("Duracion: " + atraccion.getTiempo());
            System.out.println("Cupo diario: " + atraccion.getCupoDisponible());

            System.out.println();
        }
        System.out.println("---Fin Atracciones---");
    }

    @Test
    public void leerPromociones() {
        System.out.println("---Promociones---");

        for (Promocion promocion : promociones) {
            System.out.println("Nombre: " + promocion.getNombre());
            System.out.println("Tipo: " + promocion.getTipo());
            System.out.println("Tipo promo: " + promocion.getTipoPromo());
            for (Atraccion atraccion: atracciones) {
                System.out.println(">Atraccion:");
                System.out.println("Nombre: " + atraccion.getNombre());
                System.out.println("Tiempo: " + atraccion.getTiempo());
                System.out.println("Costo: " + atraccion.getCosto());
                System.out.println("Cupo diario: " + atraccion.getCupoDisponible());
            }
            System.out.println("---------------");

        }
        System.out.println("---Fin Promociones---");
    }

}
