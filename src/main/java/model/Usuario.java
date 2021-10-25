package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Usuario {
	private int id;
    private String nombre;
    private int monedas;
    private TipoAtraccion tipoPreferido;
    private double tiempoDisponible;
    private List<Producto> itinerario;
    private List<Atraccion> atraccionesCompradas;

    public Usuario(int id ,String nombre, String preferencia, int monedas, double tiempo) {
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

    public void analizarSugerencias(List<Producto> sugerencias) {
        Scanner in = new Scanner(System.in);
        String respuesta;

        System.out.println("¡Hola," + this.getNombre() + "!");
        System.out.println("Tus monedas:" + this.monedas);
        System.out.println("Tu tiempo disponible:" + this.tiempoDisponible + " horas");
        System.out.println("Tu preferencia: " + this.tipoPreferido);
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

            if (this.monedas >= sugerencia.getCosto() && this.tiempoDisponible >= sugerencia.getTiempo() &&
                    this.noSeVisito(sugerencia) && sugerencia.tieneCupo()) {

                System.out.println("Nombre: " + sugerencia.getNombre());
                System.out.println("Precio: " + sugerencia.getCosto() + " monedas");
                System.out.println("Tiempo: " + sugerencia.getTiempo() + " horas");
                System.out.println("Tipo: " + sugerencia.getTipo());
                System.out.println("Querés comprarlo?(Si/No)");
                respuesta = in.nextLine().toUpperCase();

                while (!respuesta.equals("SI") && !respuesta.equals("NO")) {
                    System.out.println("Por favor, ingresá Si o No.");
                    respuesta = in.nextLine().toUpperCase();
                }
                if (respuesta.equals("SI")) {
                    this.itinerario.add(sugerencia);
                    this.monedas -= sugerencia.getCosto();
                    this.tiempoDisponible -= sugerencia.getTiempo();
                    //TODO Actualizar tiempo y monedas en la base aca o al finalizar?
                    this.atraccionesCompradas.addAll(sugerencia.getAtraccionesTotales());
                    sugerencia.actualizarCupo();
                    //TODO Actualizar el cupo en la base

                    System.out.println("Tiempo restante: " + this.tiempoDisponible);
                    System.out.println("Monedas restantes: " + this.monedas);
                    System.out.println("--------------------------");
                }
            }
        }

        System.out.println("¡Listo! Itinerario generado.");
        System.out.println("--------------------------");

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

    public boolean noSeVisito2(Producto sugerencia) {
        String tipo = sugerencia.getClass().getSimpleName();
        if (tipo.equals("Atraccion")) {
            return !atraccionesCompradas.contains((Atraccion) sugerencia);
        }

        if (tipo.equals("PromoAbsoluta") || tipo.equals("PromoAxB") ||
                tipo.equals("PromoPorcentual")) {

            List<Atraccion> atraccionesPromo = ((Promocion) sugerencia).getAtracciones();
            boolean noContieneGratis = true;
            if (tipo.equals("PromoAxB")) {
                PromoAxB axb = (PromoAxB) sugerencia;
                noContieneGratis = !atraccionesCompradas.contains(axb.getAtraccionGratis());
            }
            return Collections.disjoint(atraccionesCompradas, atraccionesPromo) && noContieneGratis;
        }
        return false;
    }

    public void escribirArchivo() throws IOException {
        String path = "salida/" + this.nombre + ".out";
        PrintWriter salida = new PrintWriter(new FileWriter(path));

        salida.println("<<< Itinerario de " + this.getNombre() + ">>>");

        int costoTotal = 0;
        double tiempoTotal = 0;
        for (Producto producto : itinerario) {
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
        for (Atraccion atraccion : atraccionesCompradas) {
            salida.println(atraccion.getNombre());
        }
        salida.println("Costo total: " + costoTotal);
        salida.println("Tiempo total: " + tiempoTotal);
        salida.close();
    }

    public void setItinerario(List<Producto> itinerario) {
        this.itinerario = itinerario;
    }
}
