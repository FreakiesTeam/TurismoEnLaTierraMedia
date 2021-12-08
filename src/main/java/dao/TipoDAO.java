package dao;

import java.sql.SQLException;
import java.util.List;

import model.Tipo;

public interface TipoDAO {
	
	 public abstract int actualizarTipo(Tipo tipoAtraccion);

	 public int actualizar(Tipo tipoAtraccion);

	 Tipo toTipo(Object objeto) throws SQLException;

	 public List<Tipo> obtenerTodos(String url);
}
