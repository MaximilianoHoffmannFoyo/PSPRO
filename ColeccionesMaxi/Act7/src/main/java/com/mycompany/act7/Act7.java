package com.mycompany.act7;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author maxi
 */
public class Act7 {

    public static void main(String[] args) {
  
        Map<String, Integer> mapPaises = new HashMap<>();

        String[] paises = {"España", "Portugal", "Italia", "Francia"};
        int[] pobPaises = {47300000, 10134000, 60578000, 67440000};

        for (int i = 0; i < paises.length; i++) {
            mapPaises.put(paises[i], pobPaises[i]);
        }  
        
        Collection<Integer> cPaises = mapPaises.values();
        System.out.println("  ----  Lista de paises a partir del Map -----");
        cPaises.forEach(System.out::println);
        
    }
}
