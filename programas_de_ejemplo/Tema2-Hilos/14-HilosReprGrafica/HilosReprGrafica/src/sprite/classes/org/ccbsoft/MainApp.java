package org.ccbsoft;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class MainApp extends Application {

  private final String TITULO_VENTANA = "Gato y Ratón";
  private final int MAX_X = 512;
  private final int MAX_Y = 512;

  @Override
  public void start(Stage stage) throws Exception {

    // Inicia interfaz de usuario
    Group root = new Group();
    Scene theScene = new Scene(root);
    stage.setScene(theScene);

    stage.setTitle(TITULO_VENTANA);

    Canvas canvas = new Canvas(MAX_X, MAX_Y);
    root.getChildren().add(canvas);

    GraphicsContext gc = canvas.getGraphicsContext2D();

    // Crea estado compartido
    Estado estado = new Estado(MAX_X, MAX_Y);

    // Obtén sprites del estado
    Sprite datoncito = estado.getDatoncito();
    Sprite gato = estado.getGato();
    stage.show();

    // Clase para bucle de presentación.
    new AnimationTimer() {

      // Período para representar estado dado por DELTA_NS en nanosegundos
      private final long DELTA_NS = 10000; // 10^4 ns = 10 ms = 0.01 s, 100fps
      private long nextNanoTime = 0;

      public void handle(long currentNanoTime) {

        if (nextNanoTime != 0 && currentNanoTime < nextNanoTime) {
          return;
        }

        gc.clearRect(0, 0, MAX_X, MAX_Y);

        datoncito.render(gc);
        gato.render(gc);

        nextNanoTime = currentNanoTime + DELTA_NS;

      }
    }.start();

    // Crea y lanza hilos para cada actor
    Thread hR = new Thread(new Raton(estado));
    Thread hG = new Thread(new Gato(estado));

    hR.start();
    hG.start();

  }

  public static void main(String[] args) {
    launch(args);
  }

}
