
package lanzahilos;

/**
 *
 * @author maxi
 */
public class Hilo implements Runnable{

    private final String nombre;
    private final Contador c;

    public Hilo(String nombre, Contador c) {
        this.nombre = nombre;
        this.c = c;
    }

    public Hilo() {
        this.nombre = null;
        this.c = null;
    }
    
    @Override
    public void run() {
        System.out.println("Comienza el hilo "+nombre+" \n");
        this.c.setValor(c.getValor());
        System.out.println("Termina el hilo "+nombre+" \n");
    }
    
}
