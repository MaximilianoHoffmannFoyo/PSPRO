package transferenciasinbloqueo;

public class GestorTransferencias {

  public static boolean transferencia(Cuenta c1, Cuenta c2, int cantidad) {
    Cuenta cuentaMenor, cuentaMayor;
    if(c1.getNumCuenta().compareTo(c2.getNumCuenta()) < 0) {
      cuentaMenor = c1;
      cuentaMayor = c2;
    }
    else {
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

}
