package model;

import java.util.List;

public class PromoAbsoluta extends Promocion {
    private int descuento;

    public PromoAbsoluta(int id, String nombre, TipoAtraccion tipoAtraccion, List<Atraccion> atracciones, int monedas) {
        super(id, nombre, tipoAtraccion, atracciones);
        this.descuento = monedas;
    }

    @Override
    public Integer getCosto() {
        return super.getCosto() - this.descuento;
    }

    @Override
    public boolean esAtraccion() {
        return false;
    }

    @Override
    public boolean esPromoAxB() {
        return false;
    }

	@Override
	public boolean esPorcentual() {
		return false;
	}

	@Override
	public boolean esAbsoluta() {
		return true;
	}

	public int getDescuento() {
		return descuento;
	}
}
