/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesoadatosev2;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author jborregb
 */
public class AccesoADatosEv2 {

    //Instanciamos la clase scanner
    static Scanner sc = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Instanciamos un sessionFactory instancia con el patron singleton
        SessionFactory instancia = HibernateSingleton.getInstance();
        //Abrimos la sesion
        Session session = instancia.openSession();

        //Menu inicial
        int n;
        do {
            System.out.println("\nIntroduzca la operación que desea realizar: ");
            System.out.println("1 - Mostrar 3 peliculas aleatorias.");
            System.out.println("2 - Guarda, Elimina o Actualiza un actor.");
            System.out.println("3 - Ver tabla Clientes.");
            System.out.println("4 - Modificar un actor.");
            System.out.println("5 - Finalizar.");

            //Scaneamos el numero que seleccione el usuario
            n = Integer.parseInt(sc.nextLine());

            switch (n) {
                case 1:
                    //Realizamos 3 veces la operacion
                    for (int i = 0; i < 3; i++) {
                        //Instanciamos la clase random
                        Random rand = new Random();
                        //Generamos un entero aleatorio entre 0 y 300
                        int m = rand.nextInt(301);
                        /*Usamos el metodo get ya que el load nos da excepcion si no la encuentra
                          Y get en su lugar daria null ahorrandonos posibles fallos */
                        Film film = (Film) session.get(Film.class, (short) m);
                        //Lo imprimimos por pantalla
                        System.out.println(film.getFilmId() + ". " + film.getTitle());
                    }
                    break;

                case 2:
                    //Submenu para este ejercicio
                    System.out.println("\nIntroduzca la operación que desea realizar: ");
                    System.out.println("1 - Guarda un nuevo actor.");
                    System.out.println("2 - Elimina un actor.");
                    System.out.println("3 - Actualiza un actor.");
                    int n2 = Integer.parseInt(sc.nextLine());
                    //Abrimos la transaccion
                    Transaction tx = session.beginTransaction();
                    switch (n2) {
                        case 1:
                            //Pedimos los datos del nuevo actor
                            System.out.println("Introduce nombre del actor");
                            String fName = sc.nextLine();
                            System.out.println("Introduce apellido del actor");
                            String lName = sc.nextLine();
                            Actor act = new Actor(fName, lName, new Date());
                            //Le damos el id siguiente respecto al ultimo de la tabla
                            act.setActorId((short) 0);
                            //Lo hacemos persistente en la tabla (Guardamos)
                            session.save(act);
                            System.out.println("\nGuardado con exito!");
                            break;

                        case 2:
                            //Solicitamos que id queremos borrar
                            System.out.println("Introduce el id del actor a eliminar (entre 1 y 200):");
                            int actId = Integer.parseInt(sc.nextLine());
                            //Usamos el metodo get para seleccionar el actor
                            Actor actE = (Actor) session.get(Actor.class, (short) actId);
                            //Lo borramos de la tabla
                            session.delete(actE);
                            System.out.println("\nEliminado con exito!");
                            break;
                            
                        case 3:
                            //Solicitamos al usuario el id que queremos actualizar
                            System.out.println("Introduce el id del actor a actualizar (entre 1 y 200):");
                            int actId2 = Integer.parseInt(sc.nextLine());
                            //Usamos el metodo get para seleccionar el actor
                            Actor actU = (Actor) session.get(Actor.class, (short) actId2);
                            //Solicitamos nuevo nombre
                            System.out.println("Introduce nuevo nombre del actor");
                            String newName = sc.nextLine();
                            actU.setFirstName(newName);
                            //Actualizamos el registro
                            session.update(actU);
                            System.out.println("\nNombre cambiado con exito!");
                            break;
                        default:
                            break;
                    }
                    tx.commit();
                    break;

                case 3:
                    //Creamos la query
                    Query q = session.createQuery("from Customer");
                    //Hacemos una lista de los objetos buscados
                    List<Customer> lista = q.list();
                    //Obtenemos un Iterador y recorremos la lista.
                    Iterator<Customer> iter = lista.iterator();
                    //Iteramos
                    while (iter.hasNext()) {
                        //Extraemos el objeto
                        Customer customer = (Customer) iter.next();
                        //Lo imprimimos
                        System.out.println(customer.getCustomerId() + ". " + customer.getFirstName());
                    }
                    break;

                case 4:
                    //Creamos transaccion
                    Transaction tx1 = session.beginTransaction();
                    //Solicitamos el id
                    System.out.println("Selecciona un actor por su id (entre 1 y 200):");
                    int m = Integer.parseInt(sc.nextLine());
                    //Recuperamos el actor con el metodo get por el id antes introducido
                    Actor actor = (Actor) session.get(Actor.class, (short) m);
                    //Le modificamos el nombre
                    System.out.println("Introduce nuevo nombre del actor: ");
                    String name = sc.nextLine();
                    actor.setFirstName(name);
                    tx1.commit();
                    System.out.println("\nNombre cambiado con exito!");
                    break;

                default:
                    session.close();
                    instancia.close();
                    break;
            }

        } while (n != 5);

        System.out.println("Finalizado");
    }

}
