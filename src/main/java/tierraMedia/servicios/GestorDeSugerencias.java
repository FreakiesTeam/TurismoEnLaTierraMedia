package tierraMedia.servicios;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import dao.DAOFactory;
import model.*;

public class GestorDeSugerencias {
    private static GestorDeSugerencias instancia;
    private List<Usuario> usuarios;
    private List<Atraccion> atracciones;
    private List<Promocion> promociones;

    public static GestorDeSugerencias getInstancia() {
        if (instancia == null) {
            instancia = new GestorDeSugerencias();
        }
        return instancia;
    }
    public GestorDeSugerencias() {
        this.usuarios = new ArrayList<>();
        this.atracciones = new ArrayList<>();
        this.promociones = new ArrayList<>();
    }

    public void cargarUsuarios() throws SQLException {
            this.usuarios = DAOFactory.getUsuarioDAO().cargarUsuarios();
    }

    public void cargarProductos() throws SQLException {
            this.atracciones = DAOFactory.getAtraccionDAO().cargarAtracciones();
            this.promociones = DAOFactory.getPromocionDAO().cargarPromociones();
    }

    public List<Atraccion> getAtracciones() {
        return this.atracciones;
    }

    public List<Promocion> getPromociones() {
        return promociones;
    }

    public void setAtracciones(List<Atraccion> atracciones) {
        this.atracciones = atracciones;
    }

    public void setPromociones(List<Promocion> promociones) {
        this.promociones = promociones;
    }

    private List<Promocion> agregarPromocionesOrdenadas(TipoAtraccion tipo) {
        List<Promocion> sugerencias = new ArrayList<>();
        List<Promocion> otroTipo = new ArrayList<>();

        //Separo las promociones del tipo preferido de los otros
        for (int i = 0; i < promociones.size(); i++) {
            if (promociones.get(i).getTipo().equals(tipo)) {
                sugerencias.add(promociones.get(i));
            } else
                otroTipo.add(promociones.get(i));
        }

        //Ordeno por costo y si hay empate por tiempo
        Collections.sort(sugerencias);
        Collections.sort(otroTipo);

        sugerencias.addAll(otroTipo);

        return sugerencias;
    }

    private List<Atraccion> agregarAtraccionesOrdenadas(TipoAtraccion tipo) {
        List<Atraccion> sugerencias = new ArrayList<>();
        List<Atraccion> otrasAtracciones = new ArrayList<>();

        for (int i = 0; i < atracciones.size(); i++) {
            if (atracciones.get(i).getTipo().equals(tipo)) {
                sugerencias.add(atracciones.get(i));
            } else {
                otrasAtracciones.add(atracciones.get(i));
            }
        }

        Collections.sort(sugerencias);
        Collections.sort(otrasAtracciones);

        sugerencias.addAll(otrasAtracciones);

        return sugerencias;
    }

    public void generarSugerenciasParaUsuarios() throws IOException, SQLException {
        for (Usuario usuario : this.usuarios) {
            usuario.analizarSugerencias(this.generarSugerenciasPara(usuario));
            usuario.actualizarItinerario();
        }
    }

    public List<Producto> generarSugerenciasPara(Usuario usuario) {
        List<Producto> sugerencias = new ArrayList<>();
        sugerencias.addAll(agregarPromocionesOrdenadas(usuario.getTipoPreferido()));
        sugerencias.addAll(agregarAtraccionesOrdenadas(usuario.getTipoPreferido()));
        return sugerencias;
    }

}
