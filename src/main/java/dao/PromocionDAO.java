package dao;

import java.util.List;

import model.Promocion;

public interface PromocionDAO extends GenericDAO<Promocion>{
	
	public abstract Promocion findByName(String nombre);
	
	public abstract List<Promocion> cargarPromociones();
}
