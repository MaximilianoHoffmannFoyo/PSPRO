package com.mycompany.act6;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author maxi
 */
public class Act6 {

    public static void main(String[] args) {
        
        Map<String, Integer> mapPaises = new HashMap<>();

        String[] paises = {"España", "Portugal", "Italia", "Francia"};
        int[] pobPaises = {47300000, 10134000, 60578000, 67440000};

        for (int i = 0; i < paises.length; i++) {
            mapPaises.put(paises[i], pobPaises[i]);
        }  
        
        for (String key : mapPaises.keySet()) {
            System.out.printf("%s -- Población: %s %n", key, mapPaises.get(key));
        }
       
        
    }
}
