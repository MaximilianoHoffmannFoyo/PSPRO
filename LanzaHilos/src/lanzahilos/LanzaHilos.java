package lanzahilos;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maxi
 */
public class LanzaHilos {

    public static void main(String[] args) {
        
        Contador c = new Contador();
        
        
        Thread h1 = new Thread(new Hilo("h1",c));
        h1.start();
        Thread h2 = new Thread(new Hilo("h2",c));
        h2.start();
        Thread h3 = new Thread(new Hilo("h3",c));
        h3.start();
        
        try {
            h1.join();//se termina antes que el hilo pricipal(main)
        } catch (InterruptedException ex) {
        }
        
        System.out.println("Hilo principal termina \n");
        
    }
    
}
