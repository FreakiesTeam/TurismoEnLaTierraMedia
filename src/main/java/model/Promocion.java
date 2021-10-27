package model;

import java.util.List;
import java.util.Objects;

public abstract class Promocion implements Producto, Comparable<Promocion> {
	protected String nombre;
	protected List<Atraccion> atracciones;
	protected TipoAtraccion tipoAtraccion;
	protected int id;

	public Promocion(int id, String nombre, TipoAtraccion tipoAtraccion, List<Atraccion> atracciones) {
		this.id = id;
		this.nombre = nombre;
		this.tipoAtraccion = tipoAtraccion;
		this.atracciones = atracciones;
	}

	public int getId() {
		return this.id;
	}
	public String getNombre() {
		return nombre;
	}

	public List<Atraccion> getAtracciones() {
		return atracciones;
	}

	public List<Atraccion> getAtraccionesTotales() {
		return atracciones;
	}

	public TipoAtraccion getTipo() {
		return tipoAtraccion;
	}

	public int compareTo(Promocion otra) {
		if (this.getCosto().compareTo(otra.getCosto()) == 0) {
			return -this.getTiempo().compareTo(otra.getTiempo());
		}
		return -this.getCosto().compareTo(otra.getCosto());
	}

	public Double getTiempo() {
		double total = 0;
		for (int i = 0; i < this.atracciones.size(); i++) {
			total += this.atracciones.get(i).getTiempo();
		}
		return total;
	}

	public Integer getCosto() {
		int total = 0;

		for (int i = 0; i < this.atracciones.size(); i++) {
			total += this.atracciones.get(i).getCosto();
		}
		return total;
	}

	public boolean tieneCupo() {
		boolean hay = true;
		for (Atraccion atraccion : atracciones) {
			hay &= atraccion.tieneCupo();
		}
		return hay;
	}

	public void actualizarCupo() {
		for (Atraccion atraccion : atracciones) {
			atraccion.actualizarCupo();
		}
	}

	public String getTipoPromo() {
    	if(this.esPromoAxB()) {
    		return "AxB";
    	}else if(this.esAbsoluta()) {
    		return "Absoluta";
    	}else if(this.esPorcentual()) {
    		return "Porcentual";
    	}return "No es PROMO";
    }
	
	public boolean esPromocion() {
		return true;
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
		Promocion other = (Promocion) obj;
		return id == other.id && Objects.equals(nombre, other.nombre);
	}
	
	
}
