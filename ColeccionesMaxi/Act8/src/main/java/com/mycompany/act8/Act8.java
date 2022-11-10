package com.mycompany.act8;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author maxi
 */
public class Act8 {

    public static void main(String[] args) {

        Set<Integer> numeros = new HashSet<>();
        Random aleatorio = new Random();

        for (int i = 0; i < 50; i++) {
            int n = aleatorio.nextInt(1, 100);
            //int n = i;
            if (numeros.contains(n)) {
                System.out.println("numero repe "+n);
                i--;
            }else{
                numeros.add(n);
                System.out.println("Se inserto "+n);
                
                
            }
        }
    }
}
