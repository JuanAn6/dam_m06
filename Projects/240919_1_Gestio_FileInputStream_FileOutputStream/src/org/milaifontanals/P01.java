/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.milaifontanals;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Usuari
 */
public class P01 {
    public static void main(String[] args) {
        String nomFitxer ="enters100.bin";
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(nomFitxer);
        } catch (FileNotFoundException ex) {
            System.out.println("No es pot crear el fitxer "+nomFitxer);
            System.out.println("Avortem programa");
            return;
        }
        // Hem d'aconseguir enregistrar els 100 primers enters
        for (int i=1; i<=300; i++) {
            try {
                fos.write(i);
            } catch (IOException ex) {
                System.out.println("Error en enregistrar valor "+i);
                System.out.println("Avortem...");
                break;
            }
        }
        try {
            fos.close();
            System.out.println("Fitxer generat i tancat");
        } catch (IOException ex) {
            System.out.println("Error en tancar fitxer");
            System.out.println("Info: "+ex.getMessage());
        }
        
    }
}
