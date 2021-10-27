package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Atraccion;

public interface AtraccionDAO extends GenericDAO<Atraccion>{
	
	public abstract Atraccion findByName(String nombre);
	
	public abstract List<Atraccion> cargarAtracciones() throws SQLException;

	public abstract int obtenerTipoId(String nombreTipo) throws SQLException;

	public abstract String obtenerTipoNombre(int idTipo) throws SQLException;

	public Atraccion toAtraccion(ResultSet resultados) throws SQLException;

	public Atraccion findById(int id);
}
