package model;

public class Tipo {
	private String nombre;
	private int id;
	private String imagen;
	private Boolean activo;
	
	
	public Tipo(int id, String nombre, String imagen, Boolean activo) {
		this.nombre = nombre;
		this.id = id;
		this.imagen = imagen;
		this.activo = activo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	
	
}
