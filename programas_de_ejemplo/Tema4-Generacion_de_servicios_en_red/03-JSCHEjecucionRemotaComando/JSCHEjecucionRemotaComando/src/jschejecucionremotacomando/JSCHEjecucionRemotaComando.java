package jschejecucionremotacomando;

import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class JSCHEjecucionRemotaComando {

  public static void main(String[] args) {

    Scanner s = new Scanner(System.in);
    System.out.print("Host: ");
    String host = s.nextLine();
    System.out.print("Usuario: ");
    String usuario = s.nextLine();
    System.out.print("Contraseña: ");
    String contraseña = s.nextLine();
    System.out.print("Comando: ");
    String comando = s.nextLine();

    try {
      JSch jsch = new JSch();
      Session session = jsch.getSession(usuario, host, 22);
      session.setPassword(contraseña);

      UserInfo ui = new MiUserInfo() {
        public void showMessage(String message) {
          System.out.println(message);
        }

        public boolean promptYesNo(String message) {
          return true;
        }
      };

      session.setUserInfo(ui);
      session.connect(30000);   // making a connection with timeout.
      Channel channel = session.openChannel("exec");
      ChannelExec channelExec = (ChannelExec) channel;
      channelExec.setCommand(comando);

      channelExec.setInputStream(null);
      channelExec.setErrStream(System.err);

      try (InputStream is = channel.getInputStream();
              InputStreamReader isr = new InputStreamReader(is);
              BufferedReader br = new BufferedReader(isr)) {

        channelExec.connect();

        String lineaSalida = null;
        while ((lineaSalida = br.readLine()) != null) {
          System.out.println(lineaSalida);
        }

      } catch (IOException e) {
        e.printStackTrace();
      }

      channel.disconnect();
      session.disconnect();
    } catch (JSchException e) {
      e.printStackTrace();
    }
  }

  public static abstract class MiUserInfo
          implements UserInfo, UIKeyboardInteractive {

    public String getPassword() {
      return null;
    }

    public boolean promptYesNo(String str) {
      return false;
    }

    public String getPassphrase() {
      return null;
    }

    public boolean promptPassphrase(String message) {
      return false;
    }

    public boolean promptPassword(String message) {
      return false;
    }

    public void showMessage(String message) {
    }

    public String[] promptKeyboardInteractive(String destination,
            String name,
            String instruction,
            String[] prompt,
            boolean[] echo) {
      return null;
    }

  }

}
