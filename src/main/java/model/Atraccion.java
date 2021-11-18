package model;

import java.util.*;

import dao.AtraccionDAO;
import dao.DAOFactory;

public class Atraccion implements Producto, Comparable<Atraccion> {

    private int id;
    private String nombre;
    private TipoAtraccion tipo;
    private Integer costo;
    private Double tiempo;
    private int cupoDisponible;
    private String descripcion;
    private String imagen;

    public Atraccion(int id, String nombre, int costo, double tiempo, int cupoDiario, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = TipoAtraccion.valueOf(tipo.toUpperCase());
        this.costo = costo;
        this.tiempo = tiempo;
        this.cupoDisponible = cupoDiario;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoAtraccion getTipo() {
        return this.tipo;
    }

    public boolean tieneCupo() {
        return cupoDisponible > 0;
    }

    public void actualizarCupo() {
        this.cupoDisponible--;
        AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
        atraccionDAO.actualizar(this);

    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Double getTiempo() {
        return tiempo;
    }

    public int getCupoDisponible() {
        return cupoDisponible;
    }

    public Integer getCosto() {
        return this.costo;
    }

    public void setCosto(Integer costo) {
        this.costo = costo;
    }

    public List<Atraccion> getAtraccionesTotales() {
        List<Atraccion> atraccion = new ArrayList<>();
        atraccion.add(this);
        return atraccion;
    }

    @Override
    public boolean esPromocion() {
        return false;
    }

    @Override
    public boolean esAtraccion() {
        return true;
    }

    @Override
    public boolean esPromoAxB() {
        return false;
    }

    @Override
    public int compareTo(Atraccion otra) {
        // ordenar por costo y si es el mismo ordena por tiempo
        if (this.getCosto().compareTo(otra.getCosto()) == 0) {
            return -this.getTiempo().compareTo(otra.getTiempo());
        }
        return -this.getCosto().compareTo(otra.getCosto());
    }

    @Override
    public boolean esPorcentual() {
        return false;
    }

    @Override
    public boolean esAbsoluta() {
        return false;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atraccion atraccion = (Atraccion) o;
        return id == atraccion.id && Objects.equals(nombre, atraccion.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }
}
