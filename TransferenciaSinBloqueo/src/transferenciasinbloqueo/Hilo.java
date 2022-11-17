
package transferenciasinbloqueo;

/**
 *
 * @author maxi
 */
public class Hilo implements Runnable{

    private String nombre;

    public Hilo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 0; i < 200; i++) {
            
        }
    }

    @Override
    public String toString() {
        return "Hilo{" + "nombre=" + nombre + '}';
    }

    
    
}
