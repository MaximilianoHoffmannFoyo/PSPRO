package metodosredireccionamiento;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class EntradaManual {
    
    public static void main(String[] args) {
        
       Scanner teclado = new Scanner(System.in);
        String s;
        String[] ss;
        boolean seguir = true;

        do {

            
            System.out.println("Indica una palabra:");
            s = teclado.nextLine();
            seguir = true;

            if (s == "") {
                System.out.println("No lo dejes en blanco.");
                seguir = false;
            }
            if(s.equals("quit")||s.equals("exit")){
                seguir = false;
            }

            if (seguir) {
                ss = s.split(" ");

                ProcessBuilder pb = new ProcessBuilder(ss);
                pb.inheritIO();

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

        } while (!(s.equalsIgnoreCase("exit") || s.equalsIgnoreCase("quit")));
        
    }
}

