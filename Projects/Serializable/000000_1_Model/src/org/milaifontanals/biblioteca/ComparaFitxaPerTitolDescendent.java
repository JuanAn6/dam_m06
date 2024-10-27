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
public class ComparaFitxaPerTitolDescendent implements Comparator<Fitxa> {

    @Override
    public int compare(Fitxa o1, Fitxa o2) {
        return -o1.getTitol().compareTo(o2.getTitol());
    }

}
