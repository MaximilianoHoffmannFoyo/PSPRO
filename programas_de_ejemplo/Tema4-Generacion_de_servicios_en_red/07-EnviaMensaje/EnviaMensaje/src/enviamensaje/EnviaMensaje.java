package enviamensaje;

import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.apache.commons.net.smtp.SimpleSMTPHeader;
import java.io.IOException;
import java.io.Writer;

public class EnviaMensaje {

  public static void main(String[] args) {
    if (args.length < 4) {
      System.out.println("ERROR: indicar como parámetros:");
      System.out.println("servidor remitente destinatario receptor_copia_1 ...");
      System.exit(1);
    }
    String servidorSMTP = args[0];
    String mensajeDe = args[1];
    String mensajeA = args[2];
    String copiaMensajeA[] = new String[args.length - 3];
    for (int i = 3; i < args.length; i++) {
      copiaMensajeA[i - 3] = args[i];
    }

    String asunto = "Mensaje de prueba con cabeceras";
    String cuerpo = "Este es un mensaje de prueba de " + mensajeDe
            + " a " + mensajeA + " con copia a " + copiaMensajeA.length + " destinatarios más";

    SMTPClient clienteSMTP = new SMTPClient();

    try {
      clienteSMTP.connect(servidorSMTP);  // IOException      
      int codResp = clienteSMTP.getReplyCode();
      if (!SMTPReply.isPositiveCompletion(codResp)) {
        System.out.printf("ERROR: Conexión rechazada con cód. respuesta %d.\n", codResp);
        return;
      }
      clienteSMTP.login();

      clienteSMTP.setSender(mensajeDe);
      clienteSMTP.addRecipient(mensajeA);

      SimpleSMTPHeader cab = new SimpleSMTPHeader(mensajeDe, mensajeA, asunto);
      for (String cc : copiaMensajeA) {
        clienteSMTP.addRecipient(cc);
        cab.addCC(cc);
      }

      try (Writer wr = clienteSMTP.sendMessageData()) {
        if (wr == null) {
          System.out.println("ERROR: al obtener Writer para enviar datos");
          return;
        }
        wr.write(cab.toString());
        wr.write(cuerpo);
      }

      if (!clienteSMTP.completePendingCommand()) {
        System.out.println("ERROR: Fallo al terminar la transacción.");
        return;
      }

    } catch (IOException e) {
      System.out.println("ERROR: de E/S.");
      e.printStackTrace();
      return;
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
