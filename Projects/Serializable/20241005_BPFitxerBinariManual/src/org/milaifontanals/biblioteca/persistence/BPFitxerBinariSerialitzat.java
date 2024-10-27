/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.milaifontanals.biblioteca.persistence;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.milaifontanals.biblioteca.Biblioteca;
import org.milaifontanals.biblioteca.Fitxa;
import org.milaifontanals.biblioteca.FitxaLlibre;
import org.milaifontanals.biblioteca.FitxaRevista;
import org.milaifontanals.biblioteca.IBiblioteca;

/**
 * Capa de persistència de Biblioteca en fitxer binari.<BR>
 * Fitxer de configuració compatible amb els mètodes store i load de la classe
 * Properties de Java i amb continguts obligatoris: <BR>
 * - fitxer: Nom de fitxer binari on s'emmagatzema la biblioteca. <BR>
 * - classeBiblioteca: Nom de classe Biblioteca a generar en la càrrega des de
 * fitxer. <BR>
 * Nom per defecte del fitxer de propietats: BPFitxerBinariManual.properties
 *
 * @author Isidre Guixà
 */
public class BPFitxerBinariSerialitzat {

    private static String nomFitxer;
    /**
     * Constructor que cerca fitxer de configuració amb nom
     * BPFitxerBinariManual.properties
     */
    public BPFitxerBinariSerialitzat() {
        this("BPFitxerBinariManual.properties");
    }

    /**
     * Constructor que cerca fitxer de configuració indicat per paràmetre.
     *
     * @param nomFitxerConfiguracio Nom del fitxer de configuració a usar
     */
    public BPFitxerBinariSerialitzat(String nomFitxerConfiguracio) {
        try {
            if (nomFitxerConfiguracio == null || nomFitxerConfiguracio.length() == 0) {
                throw new BPFitxerBinariSerialitzatException("S'ha passat fitxer de configuració null o buit");
            }
            
            Properties p = new Properties();
            
            p.load(new FileReader(nomFitxerConfiguracio));
            
            nomFitxer = p.getProperty("fitxer");
           
            
            if(nomFitxer == null || nomFitxer.length() == 0){
                throw new BPFitxerBinariSerialitzatException("Error en el format de les propietats");
            }
                    
        } catch (Exception ex) {
            throw new BPFitxerBinariSerialitzatException("Error en carregar el fitxer de configuracio");
        }
        
    }

    public static void saveBiblioteca(IBiblioteca b) {
        if (b == null) {
            throw new BPFitxerBinariSerialitzatException("S'ha passat biblioteca null");
        }
        
        ObjectOutputStream oos = null;
        
        try {
            
            oos = new ObjectOutputStream(new FileOutputStream(nomFitxer));
            oos.writeObject(b);
            
        } catch (IOException ex) {
            throw new BPFitxerBinariSerialitzatException("Error en serialitzar la biblioteca en el fitxer " + nomFitxer, ex);
        } finally {
            try {
                oos.close();
            } catch (IOException ex) {
                throw new BPFitxerBinariSerialitzatException("Error en tancar el fitxer " + nomFitxer, ex);
            }
        }
    }

    public static IBiblioteca loadBiblioteca() {
        
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(nomFitxer));
        
            IBiblioteca b;
            try {
                b = (Biblioteca) ois.readObject();
            } catch (ClassNotFoundException ex) {
                throw new BPFitxerBinariSerialitzatException("No es troba la classe del objecte a recuperar ",ex);      
            }
            
            return b;
        } catch (IOException ex) {
            throw new BPFitxerBinariSerialitzatException("Error en recuperar del fitxer " + nomFitxer, ex);
        } finally {
            try {
                ois.close();
            } catch (IOException ex) {
                throw new BPFitxerBinariSerialitzatException("Error en tancar el fitxer " + nomFitxer, ex);
            }
        }
    }

}
