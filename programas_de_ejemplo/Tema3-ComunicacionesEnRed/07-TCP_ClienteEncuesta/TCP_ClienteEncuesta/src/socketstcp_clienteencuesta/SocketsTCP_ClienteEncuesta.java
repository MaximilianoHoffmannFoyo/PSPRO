package socketstcp_clienteencuesta;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class SocketsTCP_ClienteEncuesta {

  private final static String COD_TEXTO = "UTF-8";

  public static void main(String[] args) {

    if (args.length < 3) {
      System.err.println("ERROR, indicar: id_zona host_servidor puerto");
      System.exit(1);
    }
    String idZona = args[0];
    String nomHost = args[1];
    int numPuerto = Integer.parseInt(args[2]);

    try (Socket socketComunicacion = new Socket(nomHost, numPuerto)) {
      System.out.println("Conectado a servidor.");

      try (OutputStream osAServidor = socketComunicacion.getOutputStream();
              OutputStreamWriter oswAServidor = new OutputStreamWriter(osAServidor, COD_TEXTO);
              BufferedWriter bwAServidor = new BufferedWriter(oswAServidor)) {

        System.out.printf(">>Encuestador para zona %s comienza.\n", idZona);

        bwAServidor.write("@zona#" + idZona + "@");
        bwAServidor.newLine();

        final Random r = new Random();
        int numRespuestas = 200 + r.nextInt(200 - 100) + 1;
        for (int i = 0; i < numRespuestas; i++) {
          int numRespuesta = r.nextInt(10);   // Respuesta de 0 a 9, 0 es NS/NC
          String respuesta = "";  // NS/NC
          if (numRespuesta > 0) {
            respuesta = "respuesta_" + numRespuesta;
          }
          bwAServidor.write(respuesta);
          bwAServidor.newLine();
        }
        bwAServidor.newLine();  // Línea vacía indica el fin de las respuestas enviadas
        bwAServidor.flush();
        System.out.printf("%d respuestas enviadas para registro para zona: %s.\n",
                numRespuestas, idZona);
      }
    } catch (UnknownHostException e) {
      System.err.println("Host desconocido: " + nomHost);
      System.exit(1);
    } catch (IOException ex) {
      System.err.println("Excepción de E/S");
      ex.printStackTrace();
      System.exit(1);
    }

    System.out.printf(">>Encuestador para zona %s termina.\n", idZona);

  }

}
