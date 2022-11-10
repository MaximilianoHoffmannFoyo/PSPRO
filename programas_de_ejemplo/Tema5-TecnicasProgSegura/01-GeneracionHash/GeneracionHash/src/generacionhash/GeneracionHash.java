package generacionhash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GeneracionHash {

  public static void main(String[] args) {

    if (args.length < 1) {
      System.out.println("Introducir cadena de caracteres para calcular su hash");
      return;
    }
    
    String cadena = args[0];
    for(int i=1; i<args.length; i++) {
      cadena += " "+args[i];
    }
    
    MessageDigest md;

    try {
      
      byte[] bytes = cadena.getBytes();
      
      md = MessageDigest.getInstance("SHA-256");
      md.update(bytes);
      byte[] hash = md.digest();
      
      System.out.printf("Cadena: [%s]\nHash: [%s]\n.", cadena, valorHexadecimal(hash));

    } catch (NoSuchAlgorithmException e) {
      System.out.println("No disponible algoritmo de hash");
    }

  }

static String valorHexadecimal(byte[] bytes) {
    String result = "";
    for (byte b : bytes) {
      result += String.format(String.format("%x", b));
    }
    return result;
  }

}
