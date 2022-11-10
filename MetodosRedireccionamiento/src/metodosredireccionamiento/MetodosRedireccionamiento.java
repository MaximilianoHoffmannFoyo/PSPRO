package metodosredireccionamiento;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.Arrays;
import java.util.Scanner;

public class MetodosRedireccionamiento {

    public static void main(String[] args) {

        String s = "grep a";
        String[] ss;
        ss = s.split(" ");
        ProcessBuilder pb = new ProcessBuilder(ss);
        pb.inheritIO();
        //comando < fichero
        pb.redirectInput(new File("entrada.txt"));

        Scanner teclado = new Scanner(System.in);
        System.out.println("""
                           Siempre se ejecutara:
                           pb.redirectInput(new File("entrada.txt"));""");

        System.out.println("""
                           Elije la opcion que prefieras:
                           1.- pb.redirectOutput(new File("salida.txt"));
                           2.- pb.redirectOutput(Redirect.appendTo(new File("salida.txt")));
                           3.- pb.redirectOutput(Redirect.DISCARD);
                           4.- pb.redirectError(new File("salida.txt"));
                           5.- pb.redirectError(Redirect.appendTo(new File("salida.txt")));
                           6.- pb.redirectError(Redirect.DISCARD);
                           """);
        int menu = teclado.nextInt();

        switch (menu) {
            case 1:
                //comando > fichero
                pb.redirectOutput(new File("salida.txt"));
                break;
            case 2:
                //comando >> fichero
                pb.redirectOutput(Redirect.appendTo(new File("salida.txt")));
                break;
            case 3:
                //comando > /deb/null
                pb.redirectOutput(Redirect.DISCARD);
                break;
            case 4:
                //comando 2> fichero
                pb.redirectError(new File("salida.txt"));
                break;
            case 5:
                //comando 2>> fichero
                pb.redirectError(Redirect.appendTo(new File("salida.txt")));
                break;
            case 6:
                //comando 2> /deb/null
                pb.redirectError(Redirect.DISCARD);
                break;
            default:
        }

        try {
            Process p = pb.start();
            int codRet = p.waitFor();
            System.out.println("La ejecución de " + Arrays.toString(ss)
                    + " devuelve " + codRet
                    + " " + (codRet == 0 ? "(ejecución correcta)" : "(ERROR)")
            );
        } catch (IOException e) {
            System.err.println("Error durante ejecución del proceso");
            System.err.println("Información detallada:");
            System.err.println("---------------------");
            e.printStackTrace();
            System.err.println("---------------------");
//                    System.exit(2);
        } catch (InterruptedException e) {
            System.err.println("Proceso interrumpido");
//                    System.exit(3);
        }

    }

}
