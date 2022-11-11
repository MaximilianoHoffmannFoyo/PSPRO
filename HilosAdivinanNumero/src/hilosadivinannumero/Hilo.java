package hilosadivinannumero;

import java.util.Random;

/**
 *
 * @author maxi
 */
public class Hilo implements Runnable {

    static final NumeroSecreto NUMERO = new NumeroSecreto();

    private final String NOMBRE;

    public Hilo(String nombre) {
        this.NOMBRE = nombre;
    }

    public Hilo() {
        this.NOMBRE = null;
    }

    @Override
    public void run() {
        System.out.println("Comienza el hilo " + NOMBRE + " \n");
        do {
            execute();
        } while (false);

    }

    public void execute() {

        Random r = new Random();
        int num = r.nextInt(1, 100);

        switch (NUMERO.propuestaNumero(num)) {
            case 0 ->
                System.out.println("·····Hilo: " + NOMBRE + " intento fallido con el numero " + num);
            case 1 -> {
                System.out.println("#####Hilo: " + NOMBRE + " ha ganado con el numero " + num);
                System.out.println("Termina el hilo " + NOMBRE + " \n");
            }
            case -1 -> {
                System.out.println("#####Hilo: " + NOMBRE + " otro hilo ya ha ganado");
                System.out.println("Termina el hilo " + NOMBRE + " \n");
            }
            default -> {
            }

        }

    }
}
