/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.biblioteca;

import java.util.Comparator;

/**
 *
 * @author Usuari
 */
public class ComparaFitxaTitolAmbStringDescendent implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        if (o1 instanceof String && o2 instanceof Fitxa) {
            String s = (String) o1;
            Fitxa f = (Fitxa) o2;
            return -f.getTitol().compareTo(s);
        }
        if (o1 instanceof Fitxa && o2 instanceof String) {
            String s = (String) o2;
            Fitxa f = (Fitxa) o1;
            return -f.getTitol().compareTo(s);
        }
        throw new RuntimeException("Intent de comparar un títol de Fitxa amb un objecte no cadena");
    }

}