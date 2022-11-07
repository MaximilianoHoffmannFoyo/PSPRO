package pspro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author maxih
 */
public class FiltraExpReg {

    /**
     * @param args the command line arguments
     */
//    static final private Pattern EXPRESIONREGULAR = Pattern.compile("a");//^a.*c.*b$
//
//    public static void main(String[] args) {
//
//        String linea = "";
//        Scanner teclado = new Scanner(System.in);
//
//        do {
//
//            System.out.print("Introducir línea:");
//            linea = teclado.nextLine();
//
//            //Crear proceso con {echo linea | grep EXPRESIONREGULAR}
//
//            if (linea.matches(EXPRESIONREGULAR.toString())) {
//                System.out.println("\"" + linea + "\" contiene la expreción regular \"" + EXPRESIONREGULAR + "\"");
//            }else if(linea.equals("")){
//                System.out.println("FIN");
//            }else{
//                System.out.println("\"" + linea + "\" no contiene la expreción regular \"" + EXPRESIONREGULAR + "\"");
//            }
//            
//        } while (!linea.equals(""));
//
//    }
//}
    static final private String EXPRESIONREGULAR = "a";//^a.*c.*b$

    public static void main(String[] args) throws IOException {

        String lineaa = "";
        Scanner teclado = new Scanner(System.in);

        do {

            System.out.print("Introducir línea:");
            lineaa = teclado.nextLine();
            String[] ss = {"echo", lineaa, "|", "grep", EXPRESIONREGULAR};
            ProcessBuilder pba = new ProcessBuilder(ss);
            Process p = pba.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String salida = br.readLine();

            System.out.println(salida);
            System.out.println(lineaa);
            if (lineaa.equals(salida)) {
                System.out.println("\"" + lineaa + "\" contiene la expreción regular \"" + EXPRESIONREGULAR + "\"");
            } else if (lineaa.equals("")) {
                System.out.println("FIN");
            } else {
                System.out.println("\"" + lineaa + "\" no contiene la expreción regular \"" + EXPRESIONREGULAR + "\"");
            }

        } while (!lineaa.equals(""));
    }
}
