package enviamensajesencillo;

import java.io.IOException;
import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;

public class EnviaMensajeSencillo {

  public static void main(String[] args) {
    if (args.length < 3) {
      System.out.println("ERROR: indicar como parámetros:");
      System.out.println("servidor remitente destinatario");
      System.exit(1);
    }

    String servidorSMTP = args[0];
    String mensajeDe = args[1];
    String mensajeA = args[2];

    SMTPClient clienteSMTP = new SMTPClient();

    try {
      clienteSMTP.connect(servidorSMTP);  // IOException      
      int codResp = clienteSMTP.getReplyCode();
      if (!SMTPReply.isPositiveCompletion(codResp)) {
        System.out.printf("ERROR: Conexión rechazada con cód. respuesta %d.\n", codResp);
        return;
      }      
      clienteSMTP.login();
      
      if(!clienteSMTP.sendSimpleMessage(mensajeDe, mensajeA, "Hola ...")) {
        System.out.println("ERROR: No se pudo enviar mensaje.");
        return;
      }
      
    } catch (IOException e) {
      System.out.println("ERROR: de E/S.");
      e.printStackTrace();
      System.exit(2);
    } finally {
      if (clienteSMTP != null) {
        try {
          clienteSMTP.disconnect();
          System.out.println("INFO: conexión cerrada.");
        } catch (IOException e) {
          System.out.println("AVISO: no se pudo cerrar la conexión.");
        }
      }
    }
  }
}
