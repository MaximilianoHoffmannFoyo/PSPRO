package actfinaleshilos;

import java.util.ArrayList;

/**
 *
 * @author maxi 
 * 1 Escribir un programa para el problema clásico de los procesos
 * productores y consumidores, pero utilizando un contenedor. Un proceso
 * productor almacena datos en un contenedor, y un proceso consumidor obtiene
 * los datos del mismo contenedor. Los datos deben consumirse en el mismo orden
 * en que se producen, según el modelo de cola FIFO (first in, first out). Los
 * datos producidos deben ser números secuenciales, empezando por 1, e
 * incrementando de uno en uno. El contenedor debe ser un ArrayList. Debe
 * protegerse el acceso al contenedor, dado que la clase ArrayList no es
 * thread-safe. Debe crearse una clase ColaSincronizada con métodos get y put.
 *
 * Todos los mecanismos de sincronización deben estar implementados en los
 * métodos de la clase, por lo que los productores y consumidores sencillamente
 * llaman a los métodos get y put. Para mayor variabilidad, debe ponerse un
 * tiempo de espera aleatorio después de que el proceso productor genere un
 * valor y antes de generar el siguiente. Lo mismo para el proceso consumidor,
 * pero en término medio este tiempo de espera debe ser menor que para el
 * proceso productor. Por ejemplo: se puede hacer que el proceso productor tarde
 * un tiempo al azar entre 0 y 1000ms, y el proceso consumidor entre 0 y 500ms
 */
class ColaSincronizada {

    private ArrayList<Integer> contenedor;
//    private static int contadorGet = -1;
    private static int contadorPut = 1;

    public ColaSincronizada() {
        this.contenedor = new ArrayList<>();
    }

    public ArrayList<Integer> getContenedor() {
        return contenedor;
    }

    public synchronized Integer get() {
        while (this.contenedor.size() < (1)) {
            try {
                wait();
            } catch (InterruptedException iex) {
            }
        }
//        contadorGet++;
        return this.contenedor.get(0);
    }

    public synchronized void put() {
        this.contenedor.add(contadorPut);
        contadorPut++;
        notifyAll();
    }

}

class Productor extends Thread{
    
    private ColaSincronizada contenedor;

    public Productor(ColaSincronizada contenedor) {
        this.contenedor = contenedor;
    }
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            contenedor.put();
            System.out.println("Productor pone el "+contenedor.getContenedor().size());
        }
    }
    
}

class Consumidor extends Thread{
    
    private ColaSincronizada contenedor;

    public Consumidor(ColaSincronizada contenedor) {
        this.contenedor = contenedor;
    }
    @Override
    public void run() {
        
    }
}

public class ActFinalesHilos {

    public static void main(String[] args) {
        // TODO code application logic here
    }

}
