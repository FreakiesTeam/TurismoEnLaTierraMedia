package dao;

import model.*;

public interface PromocionDAO extends GenericDAO<Promocion>{
	
	public abstract Promocion findByName(String nombre);
}
