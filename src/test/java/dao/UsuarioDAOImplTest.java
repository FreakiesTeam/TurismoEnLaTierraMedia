package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.Config;
import org.junit.Assert;
import org.junit.Test;

import model.Usuario;

public class UsuarioDAOImplTest {

    @Test
    public void obtenerTodosLosUsuariosTest() {
        Usuario eowyn2 = new Usuario(1, "Eowyn", "AVENTURA", 1000000, 76.0);
        Usuario gandalf2 = new Usuario(2, "Gandalf", "PAISAJE", 0, 76.0);
        Usuario sam2 = new Usuario(3, "Sam", "DEGUSTACION", 55, 76.0);
        Usuario galadriel2 = new Usuario(4, "Galadriel", "PAISAJE", 55, 76.0);
        List<Usuario> usuariosTest = new ArrayList<>();
        usuariosTest.add(eowyn2);
        usuariosTest.add(gandalf2);
        usuariosTest.add(sam2);
        usuariosTest.add(galadriel2);
        UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.obtenerTodos(Config.leerPropiedad("db_test"));
        for (int i = 0; i < usuariosTest.size(); i++) {
            Assert.assertEquals(usuariosTest.get(i), usuarios.get(i));
        }
    }
}
