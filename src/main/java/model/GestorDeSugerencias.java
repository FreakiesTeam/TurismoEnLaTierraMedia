package model;

import java.sql.SQLException;
import java.util.*;

import dao.DAOFactory;

public class GestorDeSugerencias {
    private static GestorDeSugerencias instancia;
    private List<Usuario> usuarios;
    private List<Atraccion> atracciones;
    private List<Promocion> promociones;
    private List<Tipo> tiposAtraccion;

    public static GestorDeSugerencias getInstancia() {
        if (instancia == null) {
            instancia = new GestorDeSugerencias();
        }
        return instancia;
    }

    private GestorDeSugerencias() {
        this.usuarios = new ArrayList<>();
        this.atracciones = new ArrayList<>();
        this.promociones = new ArrayList<>();
        this.tiposAtraccion = new ArrayList<>();
    }

    public void cargarUsuarios(String url) {
            this.usuarios = DAOFactory.getUsuarioDAO().obtenerTodos(url);
    }

    public void cargarPromociones(String url) {
            this.promociones = DAOFactory.getPromocionDAO().obtenerTodos(url);
    }

    public void cargarAtracciones(String  url) {
        this.atracciones = DAOFactory.getAtraccionDAO().obtenerTodos(url);
    }
    
    public void cargarTipos(String  url) {
        this.tiposAtraccion = DAOFactory.getTipoDAO().obtenerTodos(url);
    }


    public List<Atraccion> getAtracciones() {
        return this.atracciones;
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
        for (Promocion promociones : promociones) {
            if (promociones.getTipo().equals(tipo)) {
                sugerencias.add(promociones);
            } else
                otroTipo.add(promociones);
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

        for (Atraccion atraccione : atracciones) {
            if (atraccione.getTipo().equals(tipo)) {
                sugerencias.add(atraccione);
            } else {
                otrasAtracciones.add(atraccione);
            }
        }

        Collections.sort(sugerencias);
        Collections.sort(otrasAtracciones);

        sugerencias.addAll(otrasAtracciones);

        return sugerencias;
    }

    public void generarSugerenciasParaUsuarios() throws SQLException {
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
