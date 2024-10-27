/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.biblioteca;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.milaifontanals.biblioteca.BibliotecaException;
import org.milaifontanals.biblioteca.ComparaFitxaPerTitol;
import org.milaifontanals.biblioteca.ComparaFitxaReferenciaAmbString;
import org.milaifontanals.biblioteca.Fitxa;
import org.milaifontanals.biblioteca.IBiblioteca;
import org.milaifontanals.biblioteca.Biblioteca;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;

/**
 * Implementació de classe abstracta Bibliotca utilitzant un TreeSet com a
 * col·lecció per a les fitxes
 * 
 * @author Usuari
 */
@XmlRootElement(name="library")
public class BibliotecaTS extends Biblioteca implements IBiblioteca{

    protected BibliotecaTS() {
        
    }
    
    /**
     * Crea la biblioteca
     *
     * @param nom
     */
    public BibliotecaTS(String nom) {
        super(nom);
        fitxes = new TreeSet();
    }

    public void afegirFitxa(Fitxa f) {
        if (f == null) {
            throw new BibliotecaException("Intent d'afegir una fitxa null");
        }
        // Abans d'intentar afegir-la comprovarem que no hi ha un altra fitxa 
        // amb el mateix número d'ordre.
        // No tenim altra possibilitat que efectuar una recorregut seqüencial:
        for (Fitxa aux: fitxes ) {
            if (aux.getOrdre()==f.getOrdre()) {
                throw new BibliotecaException("Intent d'afegir fitxa amb ordre " + f.getOrdre() + "ja existent");
            }
        }
        
        if (fitxes.add(f) == false) {
            throw new BibliotecaException("Intent d'afegir fitxa amb referència " + f.getReferencia() + " ja existent");
        }
    }

    /**
     * Cerca una fitxa dins la biblioteca que tingui la referència indicada
     *
     * @param referencia
     * @return La fitxa en que hi sigui i null si no hi és
     */
    public Fitxa cercarFitxa(String referencia) {
//        ArrayList<Fitxa> a = new ArrayList(fitxes);
//        int pos = Collections.binarySearch(a, referencia, new ComparaFitxaReferenciaAmbString());
//        if (pos>=0) {
//            return a.get(pos);
//        } else {
//            return null;
//        }
        // Alternativament, utilitzant un iterator i cercant fitxa a fitxa...
        Iterator<Fitxa> it = fitxes.iterator();
        try {
            while (true) {
                Fitxa f = it.next();
                int compara = f.getReferencia().compareTo(referencia);
                if (compara == 0) {
                    return f;
                }
                if (compara > 0) {  // No cal continuar
                                    // Ja hem sobrepassat la referència cercada
                    return null;
                }
            }
        } catch (NoSuchElementException ex) {
            return null;        // No l'ha trobat
        }
    }

    /**
     * Cerca una fitxa dins la biblioteca que tingui la referència indicada i
     * l'extreu de la biblioteca
     *
     * @param referencia
     * @return La fitxa en cas que hi sigui i null si no hi és
     */
    public Fitxa extreureFitxa(String referencia) {
        ArrayList<Fitxa> a = new ArrayList(fitxes);
        int pos = Collections.binarySearch(a, referencia, new ComparaFitxaReferenciaAmbString());
        if (pos >= 0) {
            fitxes.remove(a.get(pos));
            return a.get(pos);
        } else {
            return null;
        }
    }

    /**
     * Buida la biblioteca
     */
    public void buidar() {
        fitxes.clear();
    }

    public String toString() {
        String result = nom + " - Qtat. actual de fitxes: " + fitxes.size();
        for (Fitxa f : fitxes) {
            result = result + "\n\t" + f;
        }
        return result;
    }

    public Iterator<Fitxa> recuperarFitxesOrdenadesPerReferencia() {
        return fitxes.iterator();
    }

    public Iterator<Fitxa> recuperarFitxesOrdenadesPerTitol() {
        // Generem una altra col·lecció de fitxes, còpia de "fitxes"
        // i ordenem aquesta nova col·lecció segons el títol
        // i retornem un "iterator" d'aquesta nova col·lecció
        ArrayList<Fitxa> aux = new ArrayList(fitxes);
        Collections.sort(aux, new ComparaFitxaPerTitol());
        return aux.iterator();
    }

}
