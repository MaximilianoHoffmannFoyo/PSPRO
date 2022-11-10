package udp_echoserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDP_EchoServer {

  private final static int MAX_BYTES = 1400;
  private final static String COD_TEXTO = "UTF-8";

  public static void main(String[] args) {

    if (args.length < 1) {
      System.err.println("ERROR, indicar: puerto.");
      System.exit(1);
    }

    int numPuerto = Integer.parseInt(args[0]);

    try (DatagramSocket serverSocket = new DatagramSocket(numPuerto)) {
      System.out.printf("Creado socket de datagramas para puerto %s.\n", numPuerto);

      while (true) {

        System.out.println("Esperando datagramas.");

        byte[] datosRecibidos = new byte[MAX_BYTES];
        DatagramPacket paqueteRecibido
                = new DatagramPacket(datosRecibidos, datosRecibidos.length);

        serverSocket.receive(paqueteRecibido);

        String lineaRecibida = new String(paqueteRecibido.getData(),
                0, paqueteRecibido.getLength(), COD_TEXTO);

        InetAddress IPCliente = paqueteRecibido.getAddress();
        int puertoCliente = paqueteRecibido.getPort();
        System.out.printf("Recibido datagrama de %s:%d (%s)\n",
          IPCliente.getHostAddress(), puertoCliente, lineaRecibida);

        String respuesta = "#" + lineaRecibida + "#";

        byte[] b = respuesta.getBytes(COD_TEXTO);
        DatagramPacket paqueteEnviado = new DatagramPacket(b, b.length,
                IPCliente, puertoCliente);
        serverSocket.send(paqueteEnviado);

      }
    } catch (SocketException ex) {
      System.out.println("Excepción de sockets");
      ex.printStackTrace();
    } catch (IOException ex) {
      System.out.println("Excepción de E/S");
      ex.printStackTrace();
    }

  }

}
