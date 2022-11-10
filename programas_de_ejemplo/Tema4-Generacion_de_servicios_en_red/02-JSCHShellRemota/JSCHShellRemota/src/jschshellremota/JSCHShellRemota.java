package jschshellremota;

import com.jcraft.jsch.*;

import java.util.Scanner;

public class JSCHShellRemota {

  public static void main(String[] args) {

    Scanner s = new Scanner(System.in);
    System.out.print("Host: ");
    String host = s.nextLine();
    System.out.print("Usuario: ");
    String usuario = s.nextLine();
    System.out.print("Contraseña: ");
    String contraseña = s.nextLine();

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
      Channel channel = session.openChannel("shell");

      channel.setInputStream(System.in);
      channel.setOutputStream(System.out);
      channel.connect(3 * 1000);
//      channel.disconnect();
//      session.disconnect();
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
