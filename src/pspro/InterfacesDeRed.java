package pspro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author maxih
 */
public class InterfacesDeRed {

    public static void main(String[] args) throws IOException {

        String[] ss = {"echo", "-a"};
        ProcessBuilder pb = new ProcessBuilder(ss);
        Process p = pb.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String comandOut = br.readLine();

        String[] arrayOfComand = comandOut.split("\n\n");
        String[] arrayOfComandLine = new String[arrayOfComand.length];
        ArrayList<String> arrayListOfComand = new ArrayList<>();
        String resultado = "";
        String aux = "";
        String aux2 = "";

        for (String a : arrayOfComand) {
            System.out.println(a);//Ya esta separado por interfaz
            arrayOfComandLine = a.split("\n");

            if (a.contains("inet")) {
                aux = a.substring(a.indexOf("inet") + 5, a.indexOf("netmask") - 2)
                        + "/";
                aux2 = a.substring(a.indexOf("netmask") + 8);
                if (aux2.contains("broadcast")) {
                    aux2 = aux2.substring(0, aux2.indexOf("broadcast") - 2);
                } else {
                    aux2 = aux2.substring(0, aux2.indexOf("\n"));
                }

            } else {
                aux = "no activa";
            }

            resultado += a.substring(0, a.indexOf(":")) + "[" + aux + aux2 + "]\n";
        }
        System.out.println(resultado);
    }

}
