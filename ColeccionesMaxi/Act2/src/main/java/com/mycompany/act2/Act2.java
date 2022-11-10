package com.mycompany.act2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author maxi
 */
public class Act2 {

    public static void main(String[] args) {
        
        final int TAMANIO = 100;
        
        List<Integer> listaNumeros = new ArrayList<>(Arrays.asList(new Integer[TAMANIO]));
        Random aleatorio = new Random();
        int aux = 1; 

        for (Integer n : listaNumeros) {
            n = aleatorio.nextInt(1,200);
            System.out.println("numero "+aux+": "+n);
            aux++;
        }
       
 
        
    }
}
