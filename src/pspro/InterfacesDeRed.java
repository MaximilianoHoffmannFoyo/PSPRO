package pspro;

import java.util.ArrayList;

/**
 *
 * @author maxih
 */
public class InterfacesDeRed {

    /**
     * @param args the command line arguments
     */
    static final private String comandOut
            = "enp4s0: flags=4099<UP,BROADCAST,MULTICAST>  mtu 1500\n"
            + "        ether fc:34:97:68:65:11  txqueuelen 1000  (Ethernet)\n"
            + "        RX packets 83157  bytes 27294732 (27.2 MB)\n"
            + "        RX errors 0  dropped 20546  overruns 0  frame 0\n"
            + "        TX packets 8471  bytes 912381 (912.3 KB)\n"
            + "        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0\n"
            + "\n"
            + "lo: flags=73<UP,LOOPBACK,RUNNING>  mtu 65536\n"
            + "        inet 127.0.0.1  netmask 255.0.0.0\n"
            + "        inet6 ::1  prefixlen 128  scopeid 0x10<host>\n"
            + "        loop  txqueuelen 1000  (Bucle local)\n"
            + "        RX packets 902  bytes 100199 (100.1 KB)\n"
            + "        RX errors 0  dropped 0  overruns 0  frame 0\n"
            + "        TX packets 902  bytes 100199 (100.1 KB)\n"
            + "        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0\n"
            + "\n"
            + "wlp3s0: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500\n"
            + "        inet 192.168.102.143  netmask 255.255.240.0  broadcast 192.168.111.255\n"
            + "        inet6 fe80::23fa:6f34:c9bb:77e3  prefixlen 64  scopeid 0x20<link>\n"
            + "        ether d0:37:45:e2:c7:c8  txqueuelen 1000  (Ethernet)\n"
            + "        RX packets 30071  bytes 7697713 (7.6 MB)\n"
            + "        RX errors 0  dropped 0  overruns 0  frame 0\n"
            + "        TX packets 209  bytes 98382 (98.3 KB)\n"
            + "        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0";

    public static void main(String[] args) {

        String[] arrayOfComand = comandOut.split("\n\n");
        String[] arrayOfComandLine = new String[arrayOfComand.length];
        ArrayList<String> arrayListOfComand = new ArrayList<>();
        String resultado = "";
        String aux = "";
        String aux2 = "";


        for (String a : arrayOfComand) {
            System.out.println(a);//Ya esta separado por interfaz
            arrayOfComandLine = a.split("\n");

            
            if(a.contains("inet")){
                aux = a.substring(a.indexOf("inet")+5,a.indexOf("netmask")-2)
                        +"/";
                aux2 = a.substring(a.indexOf("netmask")+8);
                if(aux2.contains("broadcast")){
                    aux2 = aux2.substring(0, aux2.indexOf("broadcast")-2);
                }else{
                    aux2 = aux2.substring(0, aux2.indexOf("\n"));
                }
                
            }else{
                aux = "no activa";
            }
            
            resultado += a.substring(0, a.indexOf(":"))+"["+aux+aux2+"]\n";
//            System.out.println(aux2);
            
            
            
            
            

//            for (String b : arrayOfComandLine) {
//                arrayListOfComand.add(b);
////                System.out.println(b + "\n ---------------------- \n \n \n");
//            }
        }System.out.println(resultado);
//        for (String a : arrayListOfComand) {
//            System.out.println(a + "\n ---------------------- \n \n \n");
//        }
    }

}
