package org.ccbsoft;

import java.io.File;
import java.util.Random;
import javafx.scene.image.Image;

class Raton implements Runnable {

  final int T_MOV_RATON = 10;  // Intervalo de tiempo para cambiar de posición en ms
  final int NUM_MOV_SIN_CAMBIO_VELOC = 20;

  Estado tablero;
//  int x, y;

  Raton(Estado tablero) {
    this.tablero = tablero;
  }

  @Override
  public void run() {
    System.out.printf("Posición inicial de ratón: (%f, %f).\n", tablero.getDatoncito().getPositionX(), tablero.getDatoncito().getPositionY());
    boolean fin = false;
    int numMovSinCambioVeloc = 0;
    for (int i = 0; /*i < 40 &&*/ !fin; i++) {
      try {
        Thread.sleep(this.T_MOV_RATON);
      } catch (InterruptedException ex) {
      }
      fin = tablero.terminado(); // En este rato, puede haber terminado el gato
      if (!fin) {
        tablero.moverRaton();
        System.out.printf("[%d] Ratón se mueve a (%f, %f).\n", i + 1, tablero.getDatoncito().getPositionX(), tablero.getDatoncito().getPositionY());
        if (tablero.gatoComeRaton()) {
          fin = true;
          System.out.printf("Ratón comido por gato.\n");
        } else {
          numMovSinCambioVeloc = (numMovSinCambioVeloc + 1) % NUM_MOV_SIN_CAMBIO_VELOC;
          if (numMovSinCambioVeloc == 0) {  // Cambiar velocidad
            tablero.cambiarDireccionRaton();
//            System.out.printf("[%d] Nueva velocidad ratón (%f, %f).\n", i + 1, tablero.getDatoncito().getVelocityX(), tablero.getDatoncito().getVelocityY());
          }
        }
      }
    }
    tablero.getDatoncito().setImage(new File("muerte.png").toURI().toString());
//    System.out.println("Ratón terminado.");
  }
}

class Gato implements Runnable {

  final int T_MOV_GATO = 10;  // Intervalo de tiempo para cambiar de posición en ms
  final int NUM_MOV_SIN_CAMBIO_VELOC = 20;

  Estado tablero;
//  int x, y;

  Gato(Estado tablero) {
    this.tablero = tablero;
  }

  @Override
  public void run() {
    System.out.printf("Posición inicial de gato: (%f, %f).\n", tablero.getGato().getPositionX(), tablero.getGato().getPositionY());
    boolean fin = false;
    int numMovSinCambioVeloc = 0;
    for (int i = 0; /*i < 40 &&*/ !fin; i++) {
      try {
        Thread.sleep(this.T_MOV_GATO);
      } catch (InterruptedException ex) {
      }
      fin = tablero.terminado(); // En este rato, puede haber terminado el ratón
      if (!fin) {
        tablero.moverGato();
        System.out.printf("[%d] Gato se mueve a (%f, %f).\n", i + 1, tablero.getGato().getPositionX(), tablero.getGato().getPositionY());
        if (tablero.gatoComeRaton()) {
          fin = true;
          System.out.printf("Gato come a ratón.\n");
        } else {
          numMovSinCambioVeloc = (numMovSinCambioVeloc + 1) % NUM_MOV_SIN_CAMBIO_VELOC;
          if (numMovSinCambioVeloc == 0) {  // Cambiar velocidad
            tablero.cambiarDireccionGato();
//            System.out.printf("[%d] Nueva velocidad ratón (%f, %f).\n", i + 1, tablero.getDatoncito().getVelocityX(), tablero.getDatoncito().getVelocityY());
          }
        }
      }
    }
    tablero.getDatoncito().setImage(new File("muerte.png").toURI().toString());
//    System.out.println("Gato terminado.");
  }
}

public class Estado {

  Random r;

  private final int maxX, maxY;

  private final Sprite datoncito;
  private final Sprite gato;
  
  private final Image[] imgsRaton = {
      new Image(new File("bicho.png").toURI().toString()),
      new Image(new File("bicho_rev.png").toURI().toString()),
    };
  private final Image[] imgsGato = {
      new Image(new File("chica.png").toURI().toString()),
      new Image(new File("chica_rev.png").toURI().toString()),
    };

  synchronized public Sprite getDatoncito() {
    return datoncito;
  }

  synchronized public Sprite getGato() {
    return gato;
  }

  private boolean fin = false;

  Estado(int maxX, int maxY) {

    this.maxX = maxX;
    this.maxY = maxY;

    r = new Random();

    datoncito = new Sprite();
    datoncito.setImage(imgsRaton[1]);
    datoncito.setPosition(r.nextInt(maxX), r.nextInt(maxY));

    gato = new Sprite();
    gato.setImage(imgsGato[1]);
    gato.setPosition(r.nextInt(maxX), r.nextInt(maxY));

  }

  /*
  static boolean contiguos(Raton r, Gato g) {
    return Math.abs(x1 - x2) <= 1 && Math.abs(y1 - y2) <= 1;
  }
   */
  synchronized public void cambiarDireccionRaton() {
    int vX = r.nextInt(11) - 5; //  [0,10]-5 = [-5,5]
    int vY = r.nextInt(11) - 5;
    if(Math.signum(vX) != Math.signum(datoncito.getVelocityX())) {
      datoncito.setImage(imgsRaton[vX>0 ? 1 : 0]);
    }
    datoncito.setVelocity(vX, vY);
  }

  synchronized public void moverRaton() {
    double nuevoX = datoncito.getPositionX() + datoncito.getVelocityX();
    if (nuevoX >= 0 && nuevoX <= this.maxX) {
      datoncito.setPositionX(nuevoX);
    }
    double nuevoY = datoncito.getPositionY() + datoncito.getVelocityY();
    if (nuevoY >= 0 && nuevoY <= this.maxY) {
      datoncito.setPositionY(nuevoY);
    }
  }

  synchronized public void cambiarDireccionGato() {
    int vX = r.nextInt(17) - 8; //  [0,16]-8 = [-8,8]
    int vY = r.nextInt(17) - 8;
    if(Math.signum(vX) != Math.signum(gato.getVelocityX())) {
      gato.setImage(imgsGato[vX>0 ? 1 : 0]);
    }
    gato.setVelocity(vX, vY);
  }

  synchronized public void moverGato() {
    double nuevoX = gato.getPositionX() + gato.getVelocityX();
    if (nuevoX >= 0 && nuevoX <= this.maxX) {
      gato.setPositionX(nuevoX);
    }
    double nuevoY = gato.getPositionY() + gato.getVelocityY();
    if (nuevoY >= 0 && nuevoY <= this.maxY) {
      gato.setPositionY(nuevoY);
    }
  }

  synchronized public boolean gatoComeRaton() {
    this.fin = this.getDatoncito().intersects(this.getGato());
    return this.fin;
  }

  synchronized public boolean terminado() {
    return this.fin;
  }

}
