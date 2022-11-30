package ejerciciosdehilos;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author maxi
 */
public class Act1 {

    /**
     * 1. Existen 10 procesos que necesitan obtener una cantidad ente 10 y 25 de
     * un recurso para realizar una actividad. El recurso es compartido y lo
     * proporciona un proceso suministrador. Una vez que un proceso obtiene la
     * cantidad de producto que necesita, tarda un tiempo aleatorio entre 100 y
     * 500 ms en realizar una actividad con él. Si no obtiene la cantidad que
     * necesita, debe esperar hasta que esté disponible. El proceso
     * suministrador repone cada segundo una cantidad de producto entre 50 y
     * 120, pero nunca puede haber más de 600, por limitación de espacio de
     * almacenamiento. Realizar una simulación de estos procesos utilizando
     * hilos en Java.
     */
    public static void main(String[] args) {

        //procesos extractores
        ArrayList<Thread> listaHilos = new ArrayList<>();
        for (int i = 1; i < 16; i++) {
            listaHilos.add(new Thread(new Hilo("Hilo extractor " + i, "E")));
        }
        for (Thread hilo : listaHilos) {
            hilo.start();
        }

        //proceso reponedor
        Thread Reponedor = new Thread(new Hilo("Reponedor", "R"));
        Reponedor.start();
    }

    static class Hilo implements Runnable {
        private final String NOMBRE;
        private char actividad;
        private static Recursos recursos;

        public Hilo(String nombre, String actividad) {
            this.NOMBRE = nombre;
        }
        public Hilo() {
            this.NOMBRE = null;
        }

        public char getActividad() {
            return actividad;
        }
        public void setActividad(char actividad) {
            this.actividad = actividad;
        }
        public String getNOMBRE() {
            return NOMBRE;
        }
        

        @Override
        public void run() {
            System.out.println("Comienza el hilo " + NOMBRE + " \n");
            do {
                execute();
            } while (false);

        }

        public void execute() {
            if(this.actividad == 'R'){
                
            }else if (this.actividad == 'E'){
                
            }
        }

    }
    
    static class Recursos {
        static final int MAX_RECURSOS = 600;
        int numeroDeRecursos;

        public Recursos() {
            this.numeroDeRecursos = 100;
        }

        public synchronized boolean agregarRecursos(int nuevosRecursos) {
            this.numeroDeRecursos += nuevosRecursos;
            return;
        }
        
        public synchronized boolean extraerRecursos(int nuevosRecursos) {
            this.numeroDeRecursos -= nuevosRecursos;
            return ;
        }
        
        
        
    }
}
