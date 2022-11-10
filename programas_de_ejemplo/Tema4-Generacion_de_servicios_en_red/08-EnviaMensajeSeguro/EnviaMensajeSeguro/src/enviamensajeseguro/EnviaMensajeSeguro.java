package enviamensajeseguro;

import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.apache.commons.net.smtp.SimpleSMTPHeader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.io.IOException;
import java.io.Writer;

public class EnviaMensajeSeguro {

  public static void main(String[] args) {
    if (args.length < 4) {
      System.out.println("ERROR: indicar como parámetros:");
      System.out.println("servidor remitente contraseña destinatario");
      System.exit(1);
    }
    String servidorSMTP = args[0];
    String mensajeDe = args[1];  // Lo que antes solo era emisor (mensajeDe)
    String password = args[2];
    String mensajeA = args[3];
   String copiaMensajeA[] = new String[args.length - 4];
    for (int i = 4; i < args.length; i++) {
      copiaMensajeA[i - 4] = args[i];
    }

    String asunto = "Mensaje de prueba de SMTP seguro con autenticación";
    String cuerpo = "Mensaje enviado desde " + mensajeDe
            + " con SMTP sobre SSL y con autenticación";

    AuthenticatingSMTPClient clienteSMTP = new AuthenticatingSMTPClient("TLS", true);

    try {
      clienteSMTP.connect(servidorSMTP, 465);

      int codResp = clienteSMTP.getReplyCode();
      if (!SMTPReply.isPositiveCompletion(codResp)) {
        System.out.printf("ERROR: Conexión rechazada con cód. respuesta %d.\n", codResp);
        return;
      }

      clienteSMTP.elogin();  // EHLO nombre_de_host_cliente

      if (!clienteSMTP.auth(AuthenticatingSMTPClient.AUTH_METHOD.PLAIN,
              mensajeDe, password)) {
        System.out.println("ERROR de autenticación");
        return;
      }

      clienteSMTP.setSender(mensajeDe);
      clienteSMTP.addRecipient(mensajeA);

      SimpleSMTPHeader cab = new SimpleSMTPHeader(mensajeDe, mensajeA, asunto);
      for (String cc : copiaMensajeA) {
        cab.addCC(cc);
        clienteSMTP.addRecipient(cc);
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

    } catch (NoSuchAlgorithmException e) {
      System.out.println("ERROR: de SSL.");
      e.printStackTrace();
      return;
    } catch (InvalidKeyException | InvalidKeySpecException e) {
      System.out.println("ERROR: con claves.");
      e.printStackTrace();
      return;
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
