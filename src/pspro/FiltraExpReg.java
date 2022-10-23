package pspro;

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
    static final private Pattern EXPRESIONREGULAR = Pattern.compile("a");

    public static void main(String[] args) {

        String linea = "";
        Scanner teclado = new Scanner(System.in);

        do {

            System.out.print("Introducir línea:");
            linea = teclado.nextLine();
            if (linea.matches(EXPRESIONREGULAR.toString())) {
                System.out.println("\"" + linea + "\" contiene la expreción regular \"" + EXPRESIONREGULAR + "\"");
            }else if(linea.equals("")){
                System.out.println("FIN");
            }else{
                System.out.println("\"" + linea + "\" no contiene la expreción regular \"" + EXPRESIONREGULAR + "\"");
            }
            
        } while (!linea.equals(""));

    }
}
