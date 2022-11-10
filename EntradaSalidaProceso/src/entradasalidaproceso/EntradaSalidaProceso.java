package entradasalidaproceso;

import java.io.File;

/**
 *
 * @author maxi
 */
public class EntradaSalidaProceso {

    public static void main(String[] args) {
        
        String[] ruta = {""};//lo quiere en args :| por eso es un array
        
        if (ruta.length < 1) {//Comprobar si hay comando 
            System.out.println("error");
            System.exit(1);
        }
        
        //crear el file a partir del comando
        File f = new File(ruta[0]);
        if (!f.isDirectory()){//Si no es un directorio
            System.out.println("no es un directorio");
            System.exit(2);
        }
        
        //creamos el proceso con la ruta
        ProcessBuilder pb = new ProcessBuilder("ls","-lF",ruta[0]);
        try {//Al crear el process se tiene que poner un tryCatch
            Process p = pb.start();
            //faltan cosas
        } catch (Exception e) {
            //mensaje error simple o ninguno
        }
    }
    
}
