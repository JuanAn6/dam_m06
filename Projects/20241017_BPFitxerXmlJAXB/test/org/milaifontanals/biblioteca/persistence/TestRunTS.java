package org.milaifontanals.biblioteca.persistence;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.milaifontanals.biblioteca.BibliotecaTS;
import org.milaifontanals.biblioteca.FitxaLlibre;
import org.milaifontanals.biblioteca.FitxaRevista;
import org.milaifontanals.biblioteca.IBiblioteca;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Juan Antonio
 */
public class TestRunTS {
    public static void main(String []args){
        System.out.println("Start test!\n");
        BibliotecaTS b = new BibliotecaTS("Juan");
        
        System.out.println("Inici. Biblioteca: " + b);
        b.afegirFitxa(new FitxaLlibre("2222222222", "El Quixot"));
        b.afegirFitxa(new FitxaRevista("1111111111", "Tiempo", 2000, 15));
        b.afegirFitxa(new FitxaRevista("3333333333", "Mundo Científico", 2010, 3));
        
        System.out.println(b);
        
        
        System.out.println("\nIntent de guardar el nom i el ordre en el fitxer");
        
        BPFitxerXmlJAXB bpf = new BPFitxerXmlJAXB(args[0]);
        
        bpf.saveBiblioteca(b);
        
        System.out.println("\nGuardat correctament!");
        
        
        BibliotecaTS b2;
        
        b2 =(BibliotecaTS) bpf.loadBiblioteca();

        System.out.println("Recuperació de biblioteca: ");
        System.out.println(b2);
        
    }
}
