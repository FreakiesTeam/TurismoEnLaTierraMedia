package dao;

import java.util.ArrayList;
import model.Atraccion;

public interface AtraccionDAO extends GenericDAO<Atraccion>{
	
	public abstract Atraccion findByName(String nombre);
	
	public abstract ArrayList<Atraccion> cargarAtracciones();
}
