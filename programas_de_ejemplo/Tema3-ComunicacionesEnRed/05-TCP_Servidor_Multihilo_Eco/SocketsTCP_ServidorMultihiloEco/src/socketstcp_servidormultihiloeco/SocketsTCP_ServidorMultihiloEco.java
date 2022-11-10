package socketstcp_servidormultihiloeco;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;

class HiloServidor implements Runnable {

  private final static String COD_TEXTO = "UTF-8";
  private final Socket socketComunicacion;

  HiloServidor(Socket socketComunicacion) {
    this.socketComunicacion = socketComunicacion;
  }

  @Override
  public void run() {
    try (InputStream isDeCliente = this.socketComunicacion.getInputStream();
            OutputStream osACliente = this.socketComunicacion.getOutputStream();
            InputStreamReader isrDeCliente = new InputStreamReader(isDeCliente, COD_TEXTO);
            BufferedReader brDeCliente = new BufferedReader(isrDeCliente);
            OutputStreamWriter oswACliente = new OutputStreamWriter(osACliente, COD_TEXTO);
            BufferedWriter bwACliente = new BufferedWriter(oswACliente)) {
      String lineaRecibida;
      while ((lineaRecibida = brDeCliente.readLine()) != null && lineaRecibida.length() > 0) {
        bwACliente.write("#" + lineaRecibida + "#");
        bwACliente.newLine();
        bwACliente.flush();
      }
    } catch (IOException ex) {
      System.out.println("Excepción de E/S");
      ex.printStackTrace();
      System.exit(1);
    } finally {
      if (this.socketComunicacion != null) {
        try {
          this.socketComunicacion.close();
          System.out.println("Cliente desconectado.");
        } catch (IOException ex) {
        }
      }
    }
  }

}

class SocketsTCP_ServidorMultihiloEco {

  public static void main(String[] args) {

    if (args.length < 1) {
      System.err.println("ERROR, indicar: puerto.");
      System.exit(1);
    }

    int numPuerto = Integer.parseInt(args[0]);

    try (ServerSocket socketServidor = new ServerSocket(numPuerto)) {
      System.out.printf("Creado socket de servidor en puerto %d. Esperando conexiones de clientes.\n", numPuerto);

      while (true) {    // Acepta una conexión de cliente tras otra
        Socket socketComNuevoCliente = socketServidor.accept();
        System.out.printf("Cliente conectado desde %s:%d.\n",
                socketComNuevoCliente.getInetAddress().getHostAddress(),
                socketComNuevoCliente.getPort());

        Thread hiloSesion = new Thread(new HiloServidor(socketComNuevoCliente));
        hiloSesion.start();
      }

    } catch (IOException ex) {
      System.out.println("Excepción de E/S");
      ex.printStackTrace();
      System.exit(1);
    }
  }

}
