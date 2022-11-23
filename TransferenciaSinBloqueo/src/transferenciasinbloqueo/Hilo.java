package transferenciasinbloqueo;

import java.util.ArrayList;

/**
 *
 * @author maxi
 */
public class Hilo implements Runnable {

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
        boolean m = true;
        int dinero = 0;
        ArrayList<Cuenta> cuentas = new ArrayList<>();
        
        for (int i = 0; i < 200; i++) {
            if(m){
                dinero = GestorTransferencias.dineroATransferir();
                cuentas = GestorTransferencias.azarOrigenDestino(TransferenciaSinBloqueo.listaCuentas);
                System.out.println("ejecucion "+ (i+1)+" de "+this.nombre);
            }else{
                i--;
            }
            m = GestorTransferencias.transferencia(cuentas.get(0), cuentas.get(1), dinero);
            
                
            
            
        }
        System.out.println("Ha terminado el hilo "+nombre);
    }

    @Override
    public String toString() {
        return "Hilo{" + "nombre=" + nombre + '}';
    }

}
