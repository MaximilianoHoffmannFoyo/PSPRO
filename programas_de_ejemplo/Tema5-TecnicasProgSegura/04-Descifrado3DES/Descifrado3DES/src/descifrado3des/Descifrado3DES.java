package descifrado3des;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Descifrado3DES {

  private static final String ALGORITMO_CLAVE_SIMETRICA = "DESede";  // 3DES
  private static final String NOM_FICH_CLAVE = "clave.raw";

  public static void main(String[] args) {

    if (args.length < 1) {
      System.out.println("ERROR: Indicar nombre de fichero para descifrar");
      return;
    }

    String nomFich = args[0];

    byte valorClave[];

    try (FileInputStream fisClave = new FileInputStream(NOM_FICH_CLAVE)) {
      valorClave = fisClave.readAllBytes();
    } catch (FileNotFoundException e) {
      System.out.printf("ERROR: no existe fichero de clave %s\n.", NOM_FICH_CLAVE);
      return;
    } catch (IOException e) {
      System.out.printf("ERROR: de E/S leyendo clave de fichero %s\n.", NOM_FICH_CLAVE);
      return;
    }

    try {

      SecretKeySpec keySpec = new SecretKeySpec(valorClave, ALGORITMO_CLAVE_SIMETRICA);
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITMO_CLAVE_SIMETRICA);
      SecretKey clave = keyFactory.generateSecret(keySpec);
      Cipher cifrado = Cipher.getInstance(ALGORITMO_CLAVE_SIMETRICA);
      cifrado.init(Cipher.DECRYPT_MODE, clave);

      try (FileInputStream fis = new FileInputStream(nomFich);
              FileOutputStream fos = new FileOutputStream(nomFich + ".desencript");
              BufferedInputStream is = new BufferedInputStream(fis);
              BufferedOutputStream os = new BufferedOutputStream(fos)) {
        byte[] buff = new byte[cifrado.getBlockSize()];
        while(is.read(buff) != -1) {
          os.write(cifrado.update(buff));
        }
        os.write(cifrado.doFinal());
      }

/*      
      try (FileInputStream fis = new FileInputStream(nomFich);
              FileOutputStream fos = new FileOutputStream(nomFich + ".desencript")) {        
        byte[] bytes = fis.readAllBytes();
          fos.write(cifrado.doFinal(bytes));
      }
*/

    } catch (NoSuchAlgorithmException e) {
      System.out.printf("No existe algoritmo de cifrado %s.\n", ALGORITMO_CLAVE_SIMETRICA);
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
    } catch (InvalidKeySpecException e) {
      System.out.println("Especificación de clave no válida.");
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      System.out.println("Clave no válida.");
      e.printStackTrace();
    } catch (IllegalBlockSizeException e) {
      System.out.println("Tamaño de bloque no válido.");
      e.printStackTrace();
    } catch (BadPaddingException e) {
      System.out.println("Excepción con relleno.");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("ERROR: de E/S encriptando fichero");
    }

  }

}
