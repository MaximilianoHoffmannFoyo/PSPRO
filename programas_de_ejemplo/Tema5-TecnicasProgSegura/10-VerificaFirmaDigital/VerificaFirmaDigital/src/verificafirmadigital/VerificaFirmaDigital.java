package verificafirmadigital;

import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Signature;
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

public class VerificaFirmaDigital {
  private static final String ALGORITMO_FIRMA = "DSA";
  private static final String FICH_CERT = "certificadoX509.crt";
  private static final int TAM_BUFFER = 16384;

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

    if (args.length < 1) {
      System.out.println("ERROR: indicar nombre de fichero para verificar firma digital.");
      return;
    }

    String nomFich = args[0];
    String nomFichFirma = nomFich + ".firma." + ALGORITMO_FIRMA;

    Certificate certif = null;

    try (FileInputStream fisCertificado = new FileInputStream(FICH_CERT)) {
      CertificateFactory f = CertificateFactory.getInstance("X.509");
      certif = f.generateCertificate(fisCertificado);

      if (!certificadoCorrecto(certif)) {
        System.out.printf("Certificado %s no correcto.\n", FICH_CERT);
        System.exit(1);
      }

      PublicKey clavePublica = certif.getPublicKey();

      System.out.printf("Clave pública leída de certificado, formato: %s.\n", clavePublica.getFormat());

    } catch (FileNotFoundException e) {
      System.out.printf("ERROR: no existe fichero de certificado %s\n.", FICH_CERT);
      System.exit(1);
    } catch (IOException e) {
      System.out.printf("ERROR: de E/S leyendo fichero de certificado %s\n.", FICH_CERT);
      System.exit(1);
    } catch (CertificateException e) {
      System.out.println("ERROR: Excepción de certificado.");
      System.exit(1);
    }

    Signature s;
    try (FileInputStream fisFirma = new FileInputStream(nomFichFirma);
            FileInputStream fis = new FileInputStream(nomFich);
            BufferedInputStream is = new BufferedInputStream(fis)) {

      byte[] firma = fisFirma.readAllBytes();
      
      s = Signature.getInstance(ALGORITMO_FIRMA);
      s.initVerify(certif);

      byte[] buff = new byte[TAM_BUFFER];
      while (is.read(buff) != -1) {
        s.update(buff);
      }
      
      System.out.printf("Verificación de firma en fichero %s para fichero %s.\n", nomFichFirma, nomFich);
      
      System.out.println(s.verify(firma) ? "Firma correcta" : "ERROR: firma no correcta");

    } catch (NoSuchAlgorithmException e) {
      System.out.printf("No existe algoritmo de firma %s.\n", ALGORITMO_FIRMA);
    } catch (SignatureException e) {
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      System.out.println("Clave no válida.");
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      System.out.printf("ERROR: no existe fichero: %s.\n", e.getMessage());
    } catch (IOException e) {
      System.out.println("ERROR: de E/S");
      e.printStackTrace();
    }

  }

}
