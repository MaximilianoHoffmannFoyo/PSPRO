package clientetelnetgenerico;

import java.io.IOException;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.examples.util.IOUtil;

public class ClienteTelnetGenerico {

  public static void main(String[] args) {

    if (args.length < 2) {
      System.out.println("ERROR: indicar como parámetros: servidor y puerto");
      System.exit(1);
    }
    String servidor = args[0];
    int puerto = Integer.parseInt(args[1]);

    TelnetClient telnet = new TelnetClient();

    try {
      telnet.connect(servidor, puerto);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(2);
    }

    IOUtil.readWrite(telnet.getInputStream(), telnet.getOutputStream(),
            System.in, System.out);

    try {
      telnet.disconnect();
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(3);
    }

  }
}
