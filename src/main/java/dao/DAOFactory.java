package dao;

import config.Config;

public class DAOFactory {

	public static UsuarioDAO getUsuarioDAO() {
		if (Config.usarBD)
			return new UsuarioDAOImpl();
		else
			return new UsuarioDAOArchivo();
	}

	public static AtraccionDAO getAtraccionDAO() {
		if (Config.usarBD)
			return new AtraccionDAOImpl();
		else
			return new AtraccionDAOArchivo();
	}

	public static PromocionDAO getPromocionDAO() {
		if (Config.usarBD)
			return new PromocionDAOImpl();
		else
			return new PromocionDAOArchivo();
	}

	public static ItinerarioDAO getItinerarioDAO() {
		return new ItinerarioDAOImpl();
	}

	public static TipoDAO getTipoDAO() {
		return new TipoDAOImpl();
	}

}
