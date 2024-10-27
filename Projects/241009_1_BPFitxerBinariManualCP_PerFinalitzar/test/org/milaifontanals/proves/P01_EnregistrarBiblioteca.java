/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.milaifontanals.proves;

import org.milaifontanals.biblioteca.Biblioteca;
import org.milaifontanals.biblioteca.BibliotecaAL;
import org.milaifontanals.biblioteca.FitxaLlibre;
import org.milaifontanals.biblioteca.FitxaRevista;
import org.milaifontanals.biblioteca.persistence.BPFitxerBinariManual;

/**
 *
 * @author Usuari
 */
public class P01_EnregistrarBiblioteca {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Cal especificar 1 i només 1 paràmetre: nom de fitxer de configuració");
            System.out.println("Avortem");
            return;
        }

        try {
            Biblioteca b = new BibliotecaAL("M&F");
            b.afegirFitxa(new FitxaLlibre("2222222222", "El Quixot"));
            b.afegirFitxa(new FitxaRevista("1111111111", "Tiempo", 2000, 15));
            b.afegirFitxa(new FitxaLlibre("4444444444", "Programació avançada", "IOC", "0192837465"));
            b.afegirFitxa(new FitxaRevista("3333333333", "Mundo Científico", 2010, 3));

            BPFitxerBinariManual cp = new BPFitxerBinariManual(args[0]);
            cp.saveBiblioteca(b);
            System.out.println("Biblioteca enregistrada");
        } catch (Throwable ex) {
            System.out.println("Error en enregistrar biblioteca");
            System.out.println("Info: " + ex.getMessage());
            while (ex.getCause() != null) {
                System.out.println("      " + ex.getCause().getMessage());
                ex = ex.getCause();
            }
        }
    }

}
