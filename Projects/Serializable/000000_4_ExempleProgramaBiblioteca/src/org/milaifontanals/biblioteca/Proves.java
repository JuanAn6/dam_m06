/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.biblioteca;

import org.milaifontanals.biblioteca.Fitxa;
import org.milaifontanals.biblioteca.FitxaLlibre;
import org.milaifontanals.biblioteca.FitxaRevista;
import org.milaifontanals.biblioteca.IBiblioteca;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuari
 */
public class Proves {

    public static void main(String[] args) throws SecurityException {
        if (args.length != 1) {
            System.out.println("Per executar, cal indicar el nom de la classe Biblioteca a utilitzar");
            System.exit(1);
        }
        System.out.println(args[0]);
        // args[0] conté el nom de la classe Biblioteca a emprar
        IBiblioteca b = null;

        // Creació d'un objecte de la classe args[0] invocant constructor sense paràmetres
//        try {
//            b = (IBiblioteca) Class.forName(args[0]).newInstance();
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
//            System.out.println("Error en intentar crear la Biblioteca:");
//            System.out.println(ex.getClass().getName() + ":" + ex.getMessage());
//            System.exit(1);
//        }
        // Si la classe no té constructor sense paràmetres o, encara que en tingui,
        // es vol emprar un constructor amb paràmetres, cal utilitzar "reflexio"
        // Donem per suposat que la classe Biblioteca a utilitzar te un contructor
        // amb paràmetre String que es correspon amb el nom de la biblioteca:
        try {
            Constructor c = Class.forName(args[0]).getConstructor(String.class);
            b = (IBiblioteca) c.newInstance("M&F");
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            System.out.println("Error en intentar crear la Biblioteca:");
            System.out.println(ex.getClass().getName() + ":" + ex.getMessage());
            System.exit(1);
        }

        System.out.println("Inici. Biblioteca: " + b);
        b.afegirFitxa(new FitxaLlibre("2222222222", "El Quixot"));
        b.afegirFitxa(new FitxaRevista("1111111111", "Tiempo", 2000, 15));
        b.afegirFitxa(new FitxaRevista("3333333333", "Mundo Científico", 2010, 3));
        System.out.println("Pas 1. Biblioteca: " + b);
        // Proves de cerca
        String referencia = "2222222222";
        System.out.println("Cerquem ref. " + referencia + ": " + b.cercarFitxa(referencia));
        referencia = "4444444444";
        System.out.println("Cerquem ref. " + referencia + ": " + b.cercarFitxa(referencia));
        referencia = "2222222222";
        System.out.println("Modifiquem títol de la fitxa " + referencia);
        b.cercarFitxa(referencia).setTitol("Pepito Grillo");
        System.out.println("Cerquem ref. " + referencia + ": " + b.cercarFitxa(referencia));
        // Proves d'extracció
        referencia = "2222222222";
        System.out.println("Extreure ref. " + referencia + ": " + b.extreureFitxa(referencia));
        referencia = "4444444444";
        System.out.println("Extreure ref. " + referencia + ": " + b.extreureFitxa(referencia));
        System.out.println("Pas 2. Biblioteca: " + b);
        // Proves de buidar
        b.buidar();
        System.out.println("Pas 3. Biblioteca: " + b);

        b.afegirFitxa(new FitxaLlibre("2222222222", "El Quixot"));
        b.afegirFitxa(new FitxaRevista("1111111111", "Tiempo", 2000, 15));
        b.afegirFitxa(new FitxaRevista("3333333333", "Mundo Científico", 2010, 3));

        // Prova de recuperarFitxes ordenades per referència
        // Opció 1 per fer-ne un recorregut
        Iterator<Fitxa> itf = b.recuperarFitxesOrdenadesPerReferencia();
        System.out.println("Recorregut per les fitxes via \"hasNext\"");
        while (itf.hasNext()) {
            System.out.println("\t" + itf.next());
        }

        // Opció 2 per fer-ne un recorregut
        itf = b.recuperarFitxesOrdenadesPerReferencia();
        System.out.println("Recorregut per les fitxes sense \"hasNext\"");
        try {
            while (true) {
                System.out.println("\t" + itf.next());
            }
        } catch (NoSuchElementException ex) {

        }

        // Prova recuperar fitxes ordenades per títol
        itf = b.recuperarFitxesOrdenadesPerTitol();
        System.out.println("Recorregut per les fitxes ordenades per títol:");
        try {
            while (true) {
                System.out.println("\t" + itf.next());
            }
        } catch (NoSuchElementException ex) {

        }

    }
}
