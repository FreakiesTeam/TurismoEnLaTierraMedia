package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public final class Config {

    public static String leerPropiedad(String propiedad){
        String valor = null;

        try {
            Properties propiedades = new Properties();
            propiedades.load(new FileInputStream("src/main/resources/config.properties"));
            valor = propiedades.getProperty(propiedad);
        } catch (FileNotFoundException e) {
            System.out.println("Error: El archivo no existe.");
        } catch (IOException e) {
            System.out.println("Error: No se puede leer el archivo.");
        }
        return valor;
    }
}
