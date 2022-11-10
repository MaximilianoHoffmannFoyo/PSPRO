package generacionparclavesrsa;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class GeneracionParClavesRSA {

  private static final String ALGORITMO_CLAVE_PUBLICA = "RSA";
  private static final int TAM_CLAVE = 1024;
  private static SecureRandom algSeguroGenNumAleat;
  private static final String NOM_FICH_CLAVE_PUBLICA = "clavepublica.der";
  private static final String NOM_FICH_CLAVE_PRIVADA = "claveprivada.pkcs8";

  public static void main(String[] args) {

    try {

      algSeguroGenNumAleat = SecureRandom.getInstanceStrong();  // Explicar que es seguro, referirir a fich. config java.security, securerandom.strongAlgorithms.
      KeyPairGenerator genParClaves = KeyPairGenerator.getInstance(ALGORITMO_CLAVE_PUBLICA);
      genParClaves.initialize(TAM_CLAVE, algSeguroGenNumAleat);

      KeyPair parClaves = genParClaves.generateKeyPair();
      PublicKey clavePublica = parClaves.getPublic();
      PrivateKey clavePrivada = parClaves.getPrivate();
      
      //      RSAKeySpec keySpec = (DESedeKeySpec) keySpecFactory.getKeySpec(parClaves, );
      /*
      KeyFactory keySpecFactory = KeyFactory.getInstance(ALGORITMO_CLAVE_ASIMETRICA);
      
      RSAPublicKeySpec publicKeySpec = (RSAPublicKeySpec) keySpecFactory.getKeySpec(clavePublica, RSAPublicKeySpec.class);      
      RSAPrivateKeySpec privateKeySpec = (RSAPrivateKeySpec) keySpecFactory.getKeySpec(clavePrivada, RSAPrivateKeySpec.class);
      
      publicKeySpec.getModulus();
      publicKeySpec.getPublicExponent();
      publicKeySpec.getParams();
      
      privateKeySpec.getModulus();
      privateKeySpec.getPrivateExponent();
      privateKeySpec.getParams();
       */
      
      try (FileOutputStream fosClavePublica = new FileOutputStream(NOM_FICH_CLAVE_PUBLICA)) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
                clavePublica.getEncoded(), ALGORITMO_CLAVE_PUBLICA);
        fosClavePublica.write(x509EncodedKeySpec.getEncoded());
        System.out.printf("Clave pública guardada en formato %s en fichero %s:\n%s\n",                
                x509EncodedKeySpec.getFormat(), NOM_FICH_CLAVE_PUBLICA,
                Base64.getEncoder().encodeToString(x509EncodedKeySpec.getEncoded()).replaceAll("(.{76})", "$1\n"));  // clavePublica.getEncoded() tiene lo mismo);
      } catch (IOException e) {
        System.out.println("Error de E/S escribiendo clave pública en fichero");
        throw (e);
      }

      try (FileOutputStream fosClavePrivada = new FileOutputStream(NOM_FICH_CLAVE_PRIVADA)) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                clavePrivada.getEncoded(), ALGORITMO_CLAVE_PUBLICA);
        fosClavePrivada.write(pkcs8EncodedKeySpec.getEncoded());
        System.out.printf("Clave privada guardada en formato %s en fichero %s:\n%s\n",  // clavePrivada.getEncoded() tiene lo mismo
                pkcs8EncodedKeySpec.getFormat(), NOM_FICH_CLAVE_PRIVADA,
                Base64.getEncoder().encodeToString(pkcs8EncodedKeySpec.getEncoded()).replaceAll("(.{76})", "$1\n"));
      } catch (IOException e) {
        System.out.println("Error de E/S escribiendo clave privada en fichero");
        throw (e);
      }

    } catch (NoSuchAlgorithmException e) {
      System.out.println("Algoritmo de generación de claves no soportado");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
