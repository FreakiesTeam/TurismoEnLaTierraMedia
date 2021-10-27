package dao;

import java.util.ArrayList;

import model.Promocion;

public interface PromocionDAO extends GenericDAO<Promocion>{
	
	public abstract Promocion findByName(String nombre);
	
	public abstract ArrayList<Promocion> findAll();
}
