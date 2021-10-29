package model;

import java.sql.SQLException;
import java.util.*;

import dao.*;

public class Usuario {
    private int id;
    private String nombre;
    private int monedas;
    private TipoAtraccion tipoPreferido;
    private double tiempoDisponible;
    private List<Producto> itinerario;
    private List<Atraccion> atraccionesCompradas;

    public Usuario(int id, String nombre, String preferencia, int monedas, double tiempo) {
        this.id = id;
        this.nombre = nombre;
        this.tipoPreferido = TipoAtraccion.valueOf(preferencia.toUpperCase());
        this.monedas = monedas;
        this.tiempoDisponible = tiempo;
        this.itinerario = new ArrayList<>();
        this.atraccionesCompradas = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getMonedas() {
        return monedas;
    }

    public TipoAtraccion getTipoPreferido() {
        return tipoPreferido;
    }

    public void setAtraccionesCompradas(List<Atraccion> atraccionesCompradas) {
        this.atraccionesCompradas = atraccionesCompradas;
    }

    public double getTiempoDisponible() {
        return tiempoDisponible;
    }

    public void analizarSugerencias(List<Producto> sugerencias) throws SQLException {
        Scanner in = new Scanner(System.in);
        String respuesta;

        mostrarPresentacion();
        productosSinCupo(sugerencias);
        System.out.println("---Armá tu itinerario---");

        for (Producto sugerencia : sugerencias) {
            if (this.monedas == 0) {
                System.out.println("Te quedaste sin monedas :(");
                break;
            }
            if (this.tiempoDisponible == 0) {
                System.out.println("Te quedaste sin tiempo :(");
                break;
            }

            if (puedeComprar(sugerencia)) {
                mostrarProducto(sugerencia);
                respuesta = in.nextLine().toUpperCase();

                while (!respuesta.equals("SI") && !respuesta.equals("NO")) {
                    System.out.println("Por favor, ingresá Si o No.");
                    respuesta = in.nextLine().toUpperCase();
                }

                if (respuesta.equals("SI")) {
                    adquirirProducto(sugerencia);
                }
                System.out.println("---------------------------------");
            }
        }

        System.out.println("¡Listo! Itinerario generado.");
        System.out.println("---------------------------------");

    }

    private void adquirirProducto(Producto sugerencia) throws SQLException {
        this.itinerario.add(sugerencia);
        this.atraccionesCompradas.addAll(sugerencia.getAtraccionesTotales());
        this.actualizarUsuario(sugerencia);
        sugerencia.actualizarCupo();
        System.out.println("Tiempo restante: " + this.tiempoDisponible);
        System.out.println("Monedas restantes: " + this.monedas);
    }

    private void mostrarProducto(Producto sugerencia) {
        System.out.println("Nombre: " + sugerencia.getNombre());
        System.out.println("Precio: " + sugerencia.getCosto() + " monedas");
        System.out.println("Tiempo: " + sugerencia.getTiempo() + " horas");
        System.out.println("Tipo: " + sugerencia.getTipo());
        System.out.println("Querés comprarlo?(Si/No)");
    }

    private boolean puedeComprar(Producto sugerencia) {
        return this.monedas >= sugerencia.getCosto() && this.tiempoDisponible >= sugerencia.getTiempo()
                && this.noSeVisito(sugerencia) && sugerencia.tieneCupo();
    }

    private void mostrarPresentacion() {
        System.out.println("¡Hola, " + this.getNombre() + "!");
        System.out.println("Tus monedas: " + this.monedas);
        System.out.println("Tu tiempo disponible: " + this.tiempoDisponible + " horas");
        System.out.println("Tu preferencia: " + this.tipoPreferido);
    }

    private void productosSinCupo(List<Producto> sugerencias) {
        boolean hayAtraccionSinCupo = false;
        for (Producto producto : sugerencias) {

            if (!producto.tieneCupo() && !hayAtraccionSinCupo) {
                hayAtraccionSinCupo = true;
                System.out.println("Los siguientes productos ya no tienen cupo:");
            }

            if (!producto.tieneCupo()) {
                System.out.println("-" + producto.getNombre());
            }

        }
    }

    private void actualizarUsuario(Producto sugerencia) throws SQLException {
        this.monedas -= sugerencia.getCosto();
        this.tiempoDisponible -= sugerencia.getTiempo();
        UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();

        usuarioDAO.actualizar(this);
    }

    public List<Atraccion> getAtraccionesCompradas() {
        return this.atraccionesCompradas;
    }

    public boolean noSeVisito(Producto sugerencia) {
        if (sugerencia.esAtraccion()) {
            return !atraccionesCompradas.contains((Atraccion) sugerencia);
        }

        if (sugerencia.esPromocion()) {
            List<Atraccion> atraccionesPromo = ((Promocion) sugerencia).getAtracciones();
            boolean noContieneGratis = true;
            if (sugerencia.esPromoAxB()) {
                PromoAxB axb = (PromoAxB) sugerencia;
                noContieneGratis = !atraccionesCompradas.contains(axb.getAtraccionGratis());
            }
            return Collections.disjoint(atraccionesCompradas, atraccionesPromo) && noContieneGratis;
        }
        return false;
    }

    public void setItinerario(List<Producto> itinerario) {
        this.itinerario = itinerario;
    }

    public List<Producto> getItinerario() {
        return itinerario;
    }

    public void actualizarItinerario() {
        UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();
        usuarioDAO.actualizarItinerario(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        return id == other.id && Objects.equals(nombre, other.nombre);
    }

}
