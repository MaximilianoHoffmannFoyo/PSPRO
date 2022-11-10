package com.mycompany.act1;

import java.util.Random;

/**
 *
 * @author maxi
 */
public class Act1 {

    public static void main(String[] args) {

        int[] arrayNumeros = new int[100];
        Random aleatorio = new Random();
        int aux = 1; 

        for (int n : arrayNumeros) {
            n = aleatorio.nextInt(1,200);
            System.out.println("numero "+aux+": "+n);
            aux++;
        }

    }
}
