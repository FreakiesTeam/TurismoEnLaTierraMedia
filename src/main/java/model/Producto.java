package model;

import java.util.List;

public interface Producto{

    Integer getCosto();

    Double getTiempo();

    String getNombre();

    TipoAtraccion getTipo();

    boolean tieneCupo();

    void actualizarCupo();

    List<Atraccion> getAtraccionesTotales();

    boolean esPromocion();

    boolean esAtraccion();

    boolean esPromoAxB();
    
    boolean esPorcentual();
    
    boolean esAbsoluta();
}
