package transferenciasinbloqueo;

import java.util.Random;

public class Cuenta {
  
  int saldo;
  final String numCuenta;

  public Cuenta() {
    Random r = new Random();
    this.saldo = 50000;
    this.numCuenta = "ES"+r.nextLong(10000000000L, 99999999999L)+r.nextLong(10000000000L, 99999999999L);
  }
  
  public synchronized int getSaldo() {
    return this.saldo;
  }
  
  public synchronized void ingresar(int cantidad) {
    this.saldo += cantidad;
  }
  
  public synchronized void sacar(int cantidad) {
    this.saldo -= cantidad;
  }
  
  public String getNumCuenta() {
    return this.numCuenta;
  }

    @Override
    public String toString() {
        return "Cuenta{" + "saldo=" + saldo + ", numCuenta=" + numCuenta + '}';
    }
  
}