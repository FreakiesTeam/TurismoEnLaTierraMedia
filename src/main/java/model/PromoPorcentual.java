package model;

import java.util.List;

public class PromoPorcentual extends Promocion {
    private int porcentaje;

    public PromoPorcentual(int id, String nombre, TipoAtraccion tipoAtraccion, List<Atraccion> atracciones, int porcentaje) {
        super(id, nombre, tipoAtraccion, atracciones);
        this.porcentaje = porcentaje;
    }

    @Override
    public Integer getCosto() {
        return (int) Math.ceil(super.getCosto() * (1 - this.porcentaje/100.0));
    }

    @Override
    public boolean esPromocion() {
        return true;
    }

    @Override
    public boolean esAtraccion() {
        return false;
    }

	public int getPorcentaje() {
		return porcentaje;
	}

	@Override
    public boolean esPromoAxB() {
        return false;
    }

	@Override
	public boolean esPorcentual() {
		return true;
	}

	@Override
	public boolean esAbsoluta() {
		return false;
	}
}
