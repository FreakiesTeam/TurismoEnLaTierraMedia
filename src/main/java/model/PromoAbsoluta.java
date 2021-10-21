package model;

import java.util.List;

public class PromoAbsoluta extends Promocion {
    private int descuento;

    public PromoAbsoluta(String nombre, TipoAtraccion tipoAtraccion, List<Atraccion> atracciones, int monedas) {
        super(nombre, tipoAtraccion, atracciones);
        this.descuento = monedas;
    }

    @Override
    public Integer getCosto() {
        return super.getCosto() - this.descuento;
    }

    @Override
    public boolean esPromocion() {
        return true;
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

}
