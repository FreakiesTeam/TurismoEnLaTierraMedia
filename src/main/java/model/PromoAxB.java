package model;

import java.util.ArrayList;
import java.util.List;

public class PromoAxB extends Promocion {
    private Atraccion atraccionGratis;

    public PromoAxB(int id, String nombre, TipoAtraccion tipoAtraccion, List<Atraccion> atracciones, Atraccion atraccionGratis) {
        super(id, nombre, tipoAtraccion, atracciones);
        this.atraccionGratis = atraccionGratis;
        this.atraccionGratis.setCosto(0);
    }

    @Override
    public Double getTiempo() {
        double tiempo = super.getTiempo();

        return tiempo + this.atraccionGratis.getTiempo();
    }

    public Atraccion getAtraccionGratis(){
        return this.atraccionGratis;
    }

    public List<Atraccion> getAtraccionesTotales(){
        List<Atraccion> todas = new ArrayList<>();
        todas.addAll(super.atracciones);
        todas.add(this.atraccionGratis);
        return todas;
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
        return true;
    }

    @Override
    public boolean tieneCupo() {
        return super.tieneCupo() && this.atraccionGratis.tieneCupo();
    }

    @Override
    public void actualizarCupo() {
        super.actualizarCupo();
        this.atraccionGratis.actualizarCupo();
    }

	@Override
	public boolean esPorcentual() {
		return false;
	}

	@Override
	public boolean esAbsoluta() {
		return false;
	}

}
