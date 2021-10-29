package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Atraccion;

public interface AtraccionDAO {

    public abstract Atraccion findByName(String nombre);

    public abstract List<Atraccion> obtenerTodos();

    public int actualizar(Atraccion atraccion);

    public Atraccion toAtraccion(ResultSet resultados) throws SQLException;

    public Atraccion findById(int id);
}
