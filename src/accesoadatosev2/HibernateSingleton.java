/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesoadatosev2;

import java.io.File;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/** Patron Singleton para la clase SessionFactory
 *
 * @author Jborregb
 */
public class HibernateSingleton {

    private static SessionFactory INSTANCE = null;

    private HibernateSingleton() {}

    //Metodo sincronizado para crear instancia de la clase session factory (solo una por ejecucion)
    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            synchronized (HibernateSingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Configuration().configure(new File("src\\hibernate.cfg.xml")).buildSessionFactory();
                }
            }
        }
    }

    //Metodo para asegurar que solo se crea una sola clase de Session factory
    public static SessionFactory getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }

        return INSTANCE;
    }
}
