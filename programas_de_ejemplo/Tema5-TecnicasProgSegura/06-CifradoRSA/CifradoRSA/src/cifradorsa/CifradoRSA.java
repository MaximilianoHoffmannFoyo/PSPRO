package cifradorsa;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;

public class CifradoRSA {

  private static final String ALGORITMO_CLAVE_PUBLICA = "RSA";
  private static final String FICH_CLAVE_PUB = "clavepublica.der";

  public static void main(String[] args) {

    if (args.length < 1) {
      System.out.println("ERROR: indicar texto a cifrar");
      return;
    }

    String cadenaEnClaro = args[0];
    for (int i = 1; i < args.length; i++) {
      cadenaEnClaro += " " + args[i];
    }

    byte clavePubCodif[];
    try (FileInputStream fisClavePub = new FileInputStream(FICH_CLAVE_PUB)) {
      clavePubCodif = fisClavePub.readAllBytes();
    } catch (FileNotFoundException e) {
      System.out.printf("ERROR: no existe fichero de clave pública %s\n.", FICH_CLAVE_PUB);
      return;
    } catch (IOException e) {
      System.out.printf("ERROR: de E/S leyendo clave de fichero %s\n.", FICH_CLAVE_PUB);
      return;
    }

//      RSAPublicKeySpec keySpec = new RSAPublicKeySpec(
//        new BigInteger(valorClavePub), ALGORITMO_CLAVE_PUBLICA);
    KeyFactory keyFactory;
    try {

      keyFactory = KeyFactory.getInstance(ALGORITMO_CLAVE_PUBLICA);
      X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(clavePubCodif);
      PublicKey clavePublica = keyFactory.generatePublic(publicKeySpec);

      byte[] mensajeEnClaro = cadenaEnClaro.getBytes("UTF-8");

      Cipher cifrado = Cipher.getInstance(ALGORITMO_CLAVE_PUBLICA);
      cifrado.init(Cipher.ENCRYPT_MODE, clavePublica);

      byte[] mensajeCifrado = cifrado.doFinal(mensajeEnClaro);
      
      System.out.printf("Texto cifrado codificado en base 64 como texto:\n%s\n",
              Base64.getEncoder().encodeToString(mensajeCifrado).replaceAll("(.{76})", "$1\n"));

    } catch (NoSuchAlgorithmException e) {
      System.out.printf("ERROR: no existe algoritmo de cifrado %s.\n.", ALGORITMO_CLAVE_PUBLICA);
    } catch (InvalidKeySpecException e) {
      System.out.println("ERROR: especificación de clave no válida.");
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      System.out.println("Clave no válida.");
      e.printStackTrace();
    } catch (IllegalBlockSizeException e) {
      System.out.println("Tamaño de bloque no válido.");
      e.printStackTrace();
    } catch (NoSuchPaddingException e) {
      System.out.println("Excepción con relleno.");
      e.printStackTrace();
    } catch (BadPaddingException e) {
      System.out.println("Excepción con relleno.");
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      System.out.println("ERROR: codificación de caracteres UTF-8 no soportada.");
    }

  }

}
