package dao;

import java.sql.ResultSet;
import java.util.List;

import model.Promocion;

public interface PromocionDAO {

    public abstract List<Promocion> obtenerTodos(String url);

    public int actualizar(Promocion promocion);

    public Promocion toPromocion(Object objeto);

}
