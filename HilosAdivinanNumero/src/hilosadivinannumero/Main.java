package hilosadivinannumero;

/**
 *
 * @author maxi
 */
public class Main {

    public static void main(String[] args) {

        Thread h1 = new Thread(new Hilo("h1"));
        Thread h2 = new Thread(new Hilo("h2"));
        Thread h3 = new Thread(new Hilo("h3"));
        
        
        h1.start();
        h2.start();
        h3.start();

        try {
            h1.join();//se termina antes que el hilo pricipal(main)
            h2.join();//se termina antes que el hilo pricipal(main)
            h3.join();//se termina antes que el hilo pricipal(main)
        } catch (InterruptedException ex) {
        }

        System.out.println("Hilo principal termina \n");

    }
}
