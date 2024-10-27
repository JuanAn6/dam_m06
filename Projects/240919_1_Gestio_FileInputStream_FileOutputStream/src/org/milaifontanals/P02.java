/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.milaifontanals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuari
 */
public class P02 {

    public static void main(String[] args) {
        String nomFitxer = "enters100.bin";
        FileInputStream fis;
        try {
            fis = new FileInputStream(nomFitxer);
        } catch (FileNotFoundException ex) {
            System.out.println("Error en intentar obrir fitxer " + nomFitxer);
            System.out.println("Avortem programa...");
            return;
        }
        try {
            int aux;
            while ((aux = fis.read()) != -1) {
                System.out.print(aux + " ");
            }
        } catch (IOException ioe) {
            System.out.println("Error en intentar llegir de fitxer...");
            System.out.println("Info: " + ioe.getMessage());
        }
        try {
            fis.close();
        } catch (IOException ex) {
            System.out.println("Error en tancar fitxer");
            System.out.println("Info: "+ex.getMessage());
        }
    }
}
