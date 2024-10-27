/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.milaifontanals;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuari
 */
public class P06 {

    public static void main(String[] args) {
        String nomFitxer = "enters100ok.bin";
        RandomAccessFile raf;
        try {
            raf = new RandomAccessFile(nomFitxer, "r");
        } catch (FileNotFoundException ex) {
            System.out.println("Error en intentar obrir fitxer " + nomFitxer);
            System.out.println("Avortem programa...");
            return;
        }
        try {
            while (true) {
                System.out.print(raf.readInt() + " ");
            }
        } catch (EOFException eof) {

        } catch (IOException ioe) {
            System.out.println("Error en intentar llegir de fitxer...");
            System.out.println("Info: " + ioe.getMessage());
        }
        try {
            raf.close();
        } catch (IOException ex) {
            System.out.println("Error en tancar fitxer");
            System.out.println("Info: " + ex.getMessage());
        }
    }
}
