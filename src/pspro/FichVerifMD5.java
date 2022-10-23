package pspro;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;

/**
 *
 * @author maxih
 */
public class FichVerifMD5 {

    /**
     * @param args the command line arguments
     */
    static final private File file = new File(System.getProperty("user.dir") + File.separator + "nombreArchivo.txt");
    static final private File fileMd5 = new File(System.getProperty("user.dir") + File.separator + "nombreArchivo.md5.txt");
    static final private String opcion = "";

    public static void main(String[] args) throws IOException {

        String[] comandoL = {"md5sum", file.getPath()};
        String[] comandoC = {"md5sum", file.getPath(), ">", fileMd5.getPath()};
        String[] comandoT = {"md5sum", file.getPath()};
        Process process;

        if (file.exists() && !file.isDirectory()) {
            System.out.println(file + " si siste");

            switch (opcion) {
                case "-l":
                    process = new ProcessBuilder(comandoL).start();
                    break;
                case "-c":
                    try {
                        if (fileMd5.createNewFile()) {
                            System.out.println("El fichero se ha creado correctamente");
                        } 
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                    process = new ProcessBuilder(comandoC).start();
                    break;
                case "-t":
                    if (fileMd5.exists()){
                        Process process1 = new ProcessBuilder(comandoT).start();
                        FileReader fr = new FileReader(fileMd5);
                        BufferedReader br = new BufferedReader(fr);
                        
                        process1.getOutputStream().toString().equals(fr);
                        
                    }
                    break;
            }

        } else {
            System.out.println(file + " no siste");
        }

    }

}
