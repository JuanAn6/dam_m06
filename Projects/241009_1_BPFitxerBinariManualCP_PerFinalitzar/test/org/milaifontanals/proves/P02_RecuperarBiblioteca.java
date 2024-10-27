/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.proves;

import org.milaifontanals.biblioteca.Fitxa;
import org.milaifontanals.biblioteca.IBiblioteca;
import org.milaifontanals.biblioteca.persistence.BPFitxerBinariManual;
import java.util.Iterator;

/**
 *
 * @author Usuari
 */
public class P02_RecuperarBiblioteca {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Cal especificar 1 i només 1 paràmetre: nom de fitxer de configuració");
            System.out.println("Avortem");
            return;
        }
        try {
            BPFitxerBinariManual cp = new BPFitxerBinariManual(args[0]);
            IBiblioteca b = cp.loadBiblioteca();
            System.out.print("\tRecuperada:");
            System.out.println("\tBiblioteca de tipus " + b.getClass().getName());
            System.out.println("\tNom:  " + b.getNom());
            Iterator<Fitxa> itf = b.recuperarFitxesOrdenadesPerReferencia();
            while (itf.hasNext()) {
                itf.next().visualitzar();
            }
        } catch (Throwable ex) {
            System.out.println("Error en recuperar la biblioteca");
            System.out.println("Info: " + ex.getMessage());
            while (ex.getCause() != null) {
                System.out.println("      " + ex.getCause().getMessage());
                ex = ex.getCause();
            }
        }
    }
}
