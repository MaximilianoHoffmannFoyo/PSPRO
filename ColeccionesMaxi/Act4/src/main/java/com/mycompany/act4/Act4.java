package com.mycompany.act4;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author maxi
 */
public class Act4 {

    public static void main(String[] args) {
        
        Scanner teclado = new Scanner(System.in);
        Map<String, Integer> mapPaises = new HashMap<>();

        String[] paises = {"España", "Portugal", "Italia", "Francia"};
        int[] pobPaises = {47300000, 10134000, 60578000, 67440000};

        for (int i = 0; i < paises.length; i++) {
            mapPaises.put(paises[i], pobPaises[i]);
        }        
        System.out.println("Introduce el país a buscar");
        String pais = teclado.nextLine();
        
        if(mapPaises.containsKey(pais)){
            System.out.printf("El pais %s tiene una población de %s %n", pais, mapPaises.get(pais));
        }else{
            System.out.printf("NO se tiene poblacion para el país %s %n", pais);
        }
        
    }
}
