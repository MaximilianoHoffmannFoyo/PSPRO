package infoficheros;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author maxi
 */
public class InfoFicheros {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        boolean bucle;

        do {
            bucle = true;
            System.out.print("$ infofich: ");
            String fileTexto = teclado.nextLine();
            fileTexto = fileTexto.replaceAll(" ", "");

            ProcessBuilder pb = new ProcessBuilder("wc");
            File file = new File(fileTexto);

            if (fileTexto.equals(".")) {
                bucle = false;
            } else if (fileTexto.equals("")) {

            } else if (!file.isFile()) {
                System.err.println(fileTexto + "");
            }
            try {
                pb.redirectInput(file);
                Process p = pb.start();
                BufferedReader br = p.inputReader();
                String salida = br.readLine();
                System.out.println(salida);
            } catch (IOException e) {
            }

        } while (bucle);

    }

}
