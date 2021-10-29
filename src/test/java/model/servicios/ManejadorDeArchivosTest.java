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


public class ManejadorDeArchivosTest {
    UsuarioDAO usuarioDAO;
    PromocionDAO promocionDAO;
    AtraccionDAO atraccionDAO;

    @Before
    public void setup() {
        Config.usarBD = false;
        usuarioDAO = DAOFactory.getUsuarioDAO();
        promocionDAO = DAOFactory.getPromocionDAO();
        atraccionDAO = DAOFactory.getAtraccionDAO();

    }

    @Test
    public void leerUsuariosTest() {

        System.out.println("---Usuarios---");
        List<Usuario> usuarios = usuarioDAO.obtenerTodos();
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
        List<Atraccion> atracciones = atraccionDAO.obtenerTodos();
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
        List<Atraccion> atracciones = atraccionDAO.obtenerTodos();
        List<Promocion> promociones = promocionDAO.obtenerTodos();

        for (Promocion promocion : promociones) {
            System.out.println("Nombre: " + promocion.getNombre());
            System.out.println("Tipo: " + promocion.getTipo());
            System.out.println("Tipo promo: " + promocion.getClass());
            for (int i = 0; i < promocion.getAtracciones().size(); i++) {
                System.out.println(">Atraccion:");
                System.out.println("Nombre: " + promocion.getAtracciones().get(i).getNombre());
                System.out.println("Tiempo: " + promocion.getAtracciones().get(i).getTiempo());
                System.out.println("Costo: " + promocion.getAtracciones().get(i).getCosto());
                System.out.println("Cupo diario: " + promocion.getAtracciones().get(i).getCupoDisponible());
            }
            System.out.println("---------------");

        }
        System.out.println("---Fin Promociones---");
    }

}
