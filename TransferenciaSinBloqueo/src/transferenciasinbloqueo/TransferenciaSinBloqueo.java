package transferenciasinbloqueo;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author maxi
 */
public class TransferenciaSinBloqueo {

    /**
     * Crear un programa que realice al azar transferencias entre seis cuentas, 
     * cada una con un saldo inicial de 50000€, y por un importe para cada 
     * transferencia al azar de entre 20 y 250€. Las transferencias deben 
     * realizarse en 15 hilos distintos, y cada uno debe realizar 200 transferencias, 
     * seleccionando al azar, para cada una de ellas, una cuenta de origen y 
     * una de destino, por supuesto distintas, entre todas las disponibles. 
     * Si una transferencia no se puede realizar porque no hay saldo suficiente 
     * en la cuenta de origen, se debe desistir sin más, liberando ambas cuentas, 
     * y se vuelve a intentar. Es decir, no cuenta como transferencia realizada, 
     * y en el siguiente intento se vuelve a intentar la misma transferencia, 
     * en lugar de seleccionar al azar dos cuentas y una cantidad. Las cuentas 
     * deben ser objetos de una clase Cuenta y deben almacenarse en un objeto 
     * compartido de clase Cuentas, que contenga las cinco cuentas en un array. 
     * Cada cuenta estará identificada por un código de 24 caracteres, que empieza 
     * por ES, a lo que le siguen 22 dígitos cualesquiera. Antes de hacer una 
     * transferencia hay que conseguir el bloqueo o el acceso exclusivo a las 
     * dos cuentas, la de origen y la de destino, y de manera que no se puedan 
     * producir interbloqueos.
     */
    public static void main(String[] args) {
        
        ArrayList<Cuenta> listaCuentas = new ArrayList<>();
        ArrayList<Hilo> listaHilos = new ArrayList<>();
        
        for (int i = 0; i < 6; i++) {
            listaCuentas.add(new Cuenta());
        }
        for (int i = 1; i < 16; i++) {
            listaHilos.add(new Hilo("Hilo"+i));
        }
        
        for (Cuenta listaCuenta : listaCuentas) {
            System.out.println(listaCuenta.toString());
        }
        for (Hilo listaCuenta : listaHilos) {
            System.out.println(listaCuenta.toString());
        }
    }
    
}
