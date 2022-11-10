package pruebas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author maxi
 */
public class Pruebas {

    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("wc");
        try ( InputStreamReader isstdin = new InputStreamReader(System.in, "UTF-8"); // Lee línea a línea de entrada estándar
                  BufferedReader brstdin = new BufferedReader(isstdin)) {
            String linea;
            System.out.print("Introduce línea:");
            linea = brstdin.readLine();
                Process p = pb.start();// Lanza el proceso
                try ( OutputStream osp = p.getOutputStream();  
                    OutputStreamWriter oswp = new OutputStreamWriter(osp, "UTF-8")) {
                    oswp.write(linea); // Envía línea leida al proceso
                }
                try {
                    p.waitFor();// Espera a que termine antes de leer salida
                    try ( InputStream isp = p.getInputStream();  InputStreamReader isrp = new InputStreamReader(isp);  
                        BufferedReader brp = new BufferedReader(isrp)) {
                        String salidaProc = brp.readLine();// Salida proceso es una sola línea
                        System.out.println(salidaProc);
                    }
                } catch (InterruptedException e) {
                }
                System.out.print("Introduce línea:");
            
        } catch (IOException e) {
            System.out.println("ERROR: de E/S");
//            e.printStackTrace();
        }
    }
}


/*
    public static void main(String[] args) throws IOException {
        
        String[] ss = { "grep" , "a"};
        Scanner teclado = new Scanner(System.in);
        
        ProcessBuilder pb = new ProcessBuilder(ss);
        //pb.inheritIO();
        
        
        Process p = pb.start();
        BufferedReader br = p.inputReader();
        String salida = br.readLine();
        
        
        System.out.println(salida);
        
        
        
//        System.out.println("loco mundo");  
//        JOptionPane.showMessageDialog(null, "loco mundo");
//        JOptionPane.showInputDialog("f");

//        Runtime r = Runtime.getRuntime();

//        System.out.printf("memoria: %d\n Procesos: %d", r.freeMemory(), r.availableProcessors());
//
//        try {
//            Process p = r.exec("nano k.txt");
//        } catch (IOException ex) {
//            Logger.getLogger(Pruebas.class.getName()).log(Level.SEVERE, null, ex);
//        }
       
    }

     }



ProcessBuilder pb = new ProcessBuilder("md5sum");
        try ( InputStreamReader isstdin = new InputStreamReader(System.in, "UTF-8"); // Lee línea a línea de entrada estándar
                  BufferedReader brstdin = new BufferedReader(isstdin)) {
            String linea;
            System.out.print("Introduce línea:");
            while ((linea = brstdin.readLine()) != null && linea.length() != 0) {
                Process p = pb.start();// Lanza el proceso
                try ( OutputStream osp = p.getOutputStream();  
                    OutputStreamWriter oswp = new OutputStreamWriter(osp, "UTF-8")) {
                    oswp.write(linea); // Envía línea leida al proceso
                }
                try {
                    p.waitFor();// Espera a que termine antes de leer salida
                    try ( InputStream isp = p.getInputStream();  InputStreamReader isrp = new InputStreamReader(isp);  BufferedReader brp = new BufferedReader(isrp)) {
                        String salidaProc = brp.readLine();// Salida proceso es una sola línea
                        String[] cadenas = salidaProc.split(" ");
                        System.out.printf("%s\n", cadenas[0]);
                    }
                } catch (InterruptedException e) {
                }
                System.out.print("Introduce línea:");
            }
        } catch (IOException e) {
            System.out.println("ERROR: de E/S");
            e.printStackTrace();
        }
 */
