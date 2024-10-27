/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.milaifontanals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuari
 */
public class P04 {

    public static void main(String[] args) {
        String nomFitxer = "enters100ok.bin";
        FileInputStream fis;
        try {
            fis = new FileInputStream(nomFitxer);
        } catch (FileNotFoundException ex) {
            System.out.println("Error en intentar obrir fitxer " + nomFitxer);
            System.out.println("Avortem programa...");
            return;
        }
        byte[] t = new byte[4];
        try {
            while (fis.read(t) != -1) {
                ByteBuffer bb = ByteBuffer.wrap(t);
                System.out.print(bb.getInt() + " ");
            }
        } catch (IOException ioe) {
            System.out.println("Error en intentar llegir de fitxer...");
            System.out.println("Info: " + ioe.getMessage());
        }
        try {
            fis.close();
        } catch (IOException ex) {
            System.out.println("Error en tancar fitxer");
            System.out.println("Info: " + ex.getMessage());
        }
    }
}
