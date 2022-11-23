package transferenciasinbloqueo;

import java.util.ArrayList;
import java.util.Random;

public class GestorTransferencias {

    public static boolean transferencia(Cuenta c1, Cuenta c2, int cantidad) {
        Cuenta cuentaMenor, cuentaMayor;
        if (c1.getNumCuenta().compareTo(c2.getNumCuenta()) < 0) {
            cuentaMenor = c1;
            cuentaMayor = c2;
        } else {
            cuentaMenor = c2;
            cuentaMayor = c1;
        }
        boolean result = false;
        synchronized (cuentaMenor) {
            synchronized (cuentaMayor) {
                if (c1.getSaldo() >= cantidad) {
                    c1.sacar(cantidad);
                    c2.ingresar(cantidad);
                    result = true;
                }
            }
        }
        return result;
    }

    public static ArrayList<Cuenta> azarOrigenDestino(ArrayList<Cuenta> cuentas) {
        Random r = new Random();
        int n1, n2;
        ArrayList<Cuenta> origenDestino = new ArrayList<>();

        do {
            n1 = r.nextInt(cuentas.size());
            n2 = r.nextInt(cuentas.size());
        } while (n1 == n2);

        origenDestino.add(cuentas.get(n1));
        origenDestino.add(cuentas.get(n2));

        return origenDestino;
    }
    
    public static int dineroATransferir() {
        Random r = new Random();
        return r.nextInt(20, 250);
    }
}
