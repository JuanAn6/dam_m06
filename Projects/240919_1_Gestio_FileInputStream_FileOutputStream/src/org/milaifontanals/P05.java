/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.milaifontanals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

/**
 *
 * @author Usuari
 */
public class P05 {
    public static void main(String[] args) {
        String nomFitxer ="enters100ok.bin";
        File f = new File(nomFitxer);
        if (f.exists() && f.isFile() && !f.delete()) {
            System.out.println("El fitxer existeix i no es pot eliminar");
            System.out.println("Avortem programa...");
            return;
        }
        RandomAccessFile raf;
        try {
            raf = new RandomAccessFile(nomFitxer,"rw");
        } catch (FileNotFoundException ex) {
            System.out.println("No es pot crear el fitxer "+nomFitxer);
            System.out.println("Avortem programa");
            return;
        }
        // Hem d'aconseguir enregistrar els 100 primers enters
        for (int i=1; i<=300; i++) {
            try {
                raf.writeInt(i);
            } catch (IOException ex) {
                System.out.println("Error en enregistrar valor "+i);
                System.out.println("Avortem...");
                break;
            }
        }
        try {
            raf.close();
            System.out.println("Fitxer generat i tancat");
        } catch (IOException ex) {
            System.out.println("Error en tancar fitxer");
            System.out.println("Info: "+ex.getMessage());
        }
        
    }
}
