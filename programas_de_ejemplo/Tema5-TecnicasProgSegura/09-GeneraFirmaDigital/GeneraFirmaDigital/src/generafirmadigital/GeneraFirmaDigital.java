package generafirmadigital;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class GeneraFirmaDigital {

  private static final String ALGORITMO_FIRMA = "DSA";
  private static final String FICH_CLAVE_PRIV = "claveprivada.pkcs8";
  private static final int TAM_BUFFER = 16384;

  public static void main(String[] args) {

    if (args.length < 1) {
      System.out.println("ERROR: indicar nombre de fichero para calcular firma digital.");
      return;
    }

    String nomFich = args[0];

    KeyFactory keyFactory;
    byte clavePrivCodif[];
    try (FileInputStream fisClavePriv = new FileInputStream(FICH_CLAVE_PRIV)) {
      clavePrivCodif = fisClavePriv.readAllBytes();
    } catch (FileNotFoundException e) {
      System.out.printf("ERROR: no existe fichero de clave privada %s\n.", FICH_CLAVE_PRIV);
      return;
    } catch (IOException e) {
      System.out.printf("ERROR: de E/S leyendo clave de fichero %s\n.", FICH_CLAVE_PRIV);
      return;
    }

    Signature s;
    try (FileInputStream fis = new FileInputStream(nomFich);
            FileOutputStream fos = new FileOutputStream(nomFich + ".firma." + ALGORITMO_FIRMA);
            BufferedInputStream is = new BufferedInputStream(fis);
            BufferedOutputStream os = new BufferedOutputStream(fos)
            ) {

      keyFactory = KeyFactory.getInstance(ALGORITMO_FIRMA);
      PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(clavePrivCodif);
      PrivateKey clavePrivada = keyFactory.generatePrivate(privateKeySpec);

      s = Signature.getInstance(ALGORITMO_FIRMA);
      s.initSign(clavePrivada);

      byte[] buff = new byte[TAM_BUFFER];
      while (is.read(buff) != -1) {
        s.update(buff);
      }
      byte[] firma = s.sign();

      os.write(firma);

      System.out.printf("Firma digital en base 64 como texto:\n%s\n",
              Base64.getEncoder().encodeToString(firma).replaceAll("(.{76})", "$1\n"));

    } catch (NoSuchAlgorithmException e) {
      System.out.printf("No existe algoritmo de firma %s.\n", ALGORITMO_FIRMA);
    } catch (SignatureException e) {
      e.printStackTrace();
    } catch (InvalidKeySpecException e) {
      System.out.println("Especificación de clave no válida.");
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      System.out.println("Clave no válida.");
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      System.out.printf("ERROR: no existe fichero %s.\n", nomFich);
    }
    catch (IOException e) {
      System.out.println("ERROR: de E/S generando firma de fichero");
    }

  }

}
