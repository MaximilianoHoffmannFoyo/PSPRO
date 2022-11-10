package listamensajespop3;

import org.apache.commons.net.pop3.POP3Client;
import org.apache.commons.net.pop3.POP3MessageInfo;
import java.io.IOException;
import java.io.Reader;

public class ListaMensajesPOP3 {

//  private final int PUERTO_POP3 = 110;

  public static void main(String[] args) {

    if (args.length < 3) {
      System.out.println("ERROR: indicar como parámetros:");
      System.out.println("servidor usuario contraseña");
      System.exit(1);
    }
    String servidorPOP3 = args[0];
    String usuario = args[1];
    String password = args[2];

    POP3Client clientePOP3 = new POP3Client();

    try {
      clientePOP3.connect(servidorPOP3);  // IOException
      if (!clientePOP3.login(usuario, password)) {  // IOException
        System.out.print("ERROR: no se pudo hacer login en el servidor POP3.");
        System.exit(2);
      }

      POP3MessageInfo[] infoMensajes = clientePOP3.listMessages(); // IOException

      if (infoMensajes == null) {
        System.out.println("ERROR: al obtener lista de mensajes de servidor.");
        System.exit(3);
      }

      System.out.printf("%s mensajes en el servidor.\n", infoMensajes.length);

      for (POP3MessageInfo infoMsg : infoMensajes) {        
        System.out.printf("======== Inicio de mensaje %d ========\n", infoMsg.number);
        System.out.printf("%4d-%d.\n", infoMsg.number, infoMsg.size);
        System.out.println("------------------");
        Reader rMsg = clientePOP3.retrieveMessageTop(infoMsg.number, 1);  // Lee cabeceras y 1 línea 
        char[] textoMsg = new char[200];
        System.out.print("[");
        while (rMsg.read(textoMsg) != -1) {
          System.out.printf("%s", new String(textoMsg));
        }
        System.out.print("]\n");
        System.out.printf("======== Fin de mensaje %d ========\n", infoMsg.number);
      }

    } catch (IOException e) {
      System.out.println("ERROR: de E/S");  // connect, login o listMessages
      e.printStackTrace();
      System.exit(4);
    } finally {
      if (clientePOP3 != null) {
        try {
          clientePOP3.logout();
          clientePOP3.disconnect();
          System.out.println("INFO: conexión cerrada.");
        } catch (IOException e) {
          System.out.println("AVISO: no se pudo cerrar la conexión.");
        }
      }
    }
  }
}
