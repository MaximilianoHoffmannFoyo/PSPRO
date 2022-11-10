package consultacertificado;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.PublicKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateFactory;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;

public class ConsultaCertificado {

  private static final String FICH_CERT = "certificadoX509.crt";

  private static boolean certificadoCorrecto(Certificate certif) {
    boolean certifOK = true;
    try {
      PublicKey clavePublica = certif.getPublicKey();
      certif.verify(clavePublica);
      System.out.println("Certificado autofirmado, y firma correcta.");
      if (certif.getType().equals("X.509")) {
        X509Certificate certX509 = (X509Certificate) certif;
        certX509.checkValidity();
      }
    } catch (NoSuchProviderException | SignatureException e) {
      certifOK = false;
      System.out.println("ERROR: Excepción de certificado.");
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      certifOK = false;
      System.out.println("ERROR: Excepción con certificado");
      e.printStackTrace();
    } catch (CertificateExpiredException e) {
      certifOK = false;
      System.out.println("ERROR: Certificado caducado.");
      e.printStackTrace();
    } catch (CertificateNotYetValidException e) {
      certifOK = false;
      System.out.println("ERROR: Certificado aún no válido.");
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      certifOK = false;
      System.out.println("AVISO: Certificado autofirmado y firma no correcta, o no autofirmado, en cuyo caso no sé verificar firma");
      certifOK = false;
    } catch (CertificateException e) {
      certifOK = false;
      System.out.println("ERROR: Excepción de certificado.");
    }    
    return certifOK;
  }

  public static void main(String[] args) {

    try (FileInputStream fisCertificado = new FileInputStream(FICH_CERT)) {

      CertificateFactory f = CertificateFactory.getInstance("X.509");
      Certificate certif = f.generateCertificate(fisCertificado);

      if (!certificadoCorrecto(certif)) {
        System.out.printf("Certificado %s no correcto.\n", FICH_CERT);
        System.exit(1);
      }

      PublicKey clavePublica = certif.getPublicKey();

      System.out.printf("Clave pública leída de certificado, formato: %s.\n", clavePublica.getFormat());

    } catch (FileNotFoundException e) {
      System.out.printf("ERROR: no existe fichero de certificado %s\n.", FICH_CERT);
    } catch (IOException e) {
      System.out.printf("ERROR: de E/S leyendo clave de fichero %s\n.", FICH_CERT);
    } catch (CertificateException e) {
      System.out.println("ERROR: Excepción de certificado.");
    }

  }

}
