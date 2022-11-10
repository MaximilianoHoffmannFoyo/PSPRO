package infotexto;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

/**
 *
 * @author maxi
 */
public class InfoTexto {

    public static void main(String[] args) {

       

            ProcessBuilder pb = new ProcessBuilder("wc");

            try ( InputStreamReader isstdin = new InputStreamReader(System.in, "UTF-8");  BufferedReader brstdin = new BufferedReader(isstdin)) {
                String linea;
                System.out.print("$ infotexto: ");
                linea = brstdin.readLine();

                Process p = pb.start();
                try ( OutputStream osp = p.getOutputStream();  OutputStreamWriter oswp = new OutputStreamWriter(osp, "UTF-8")) {
                    oswp.write(linea);
                }
                try {
                    p.waitFor();
                    try ( InputStream isp = p.getInputStream();  InputStreamReader isrp = new InputStreamReader(isp);  BufferedReader brp = new BufferedReader(isrp)) {
                        String salidaProc = brp.readLine();
//                        System.out.println(salidaProc);
                        String[] ss = salidaProc.split(" ");
                        int numAux = ss.length - 8;
                        if (ss[ss.length - 7].equals(" ")) {
                            numAux = ss.length - 7;
                        }
                        System.out.println(ss[numAux] + " palabras, " + ss[ss.length - 1] + " bytes.");
                    }
                } catch (InterruptedException e) {
                }
            } catch (IOException e) {
                System.out.println("ERROR: de E/S");

            }


    }

}
