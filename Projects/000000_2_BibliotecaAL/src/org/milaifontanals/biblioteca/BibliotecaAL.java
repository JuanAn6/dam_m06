/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.biblioteca;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.milaifontanals.biblioteca.ComparaFitxaPerTitol;
import org.milaifontanals.biblioteca.ComparaFitxaReferenciaAmbString;
import org.milaifontanals.biblioteca.Fitxa;
import org.milaifontanals.biblioteca.BibliotecaException;
import org.milaifontanals.biblioteca.IBiblioteca;
import org.milaifontanals.biblioteca.Biblioteca;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Implementació de classe abstracta Bibliotca utilitzant una ArrayList com a
 * col·lecció per a les fitxes
 *
 * @author Usuari
 */
@XmlRootElement(name="library") //Si no li poses name agafa el nom de la clase
public class BibliotecaAL extends Biblioteca implements IBiblioteca {
    
    protected BibliotecaAL(){}
    
    // Implementació de classe abstracta Biblioteca
    // utilitzant una ArrayList per al camp "fitxes"
    /**
     * Crea la biblioteca amb una capacitat inicial de 10 fitxes
     *
     * @param nom
     */
    public BibliotecaAL(String nom){
        super(nom);
        fitxes = new ArrayList();
    }

    /**
     * Crea la biblioteca amb la capacitat inicial indicada
     *
     * @param nom
     */
    public BibliotecaAL(String nom, int capacitatInicial) {
        super(nom);
        if (capacitatInicial <= 0) {
            throw new BibliotecaException("La capacitat inicial d'una biblioteca ha de ser estrictament positiva");
        }
        fitxes = new ArrayList(capacitatInicial);
    }

    @Override
    public void afegirFitxa(Fitxa f) {
        if (f == null) {
            throw new BibliotecaException("Intent d'afegir una fitxa null");
        }
        // Cal comprovar que no existeixi cap fitxa amb idèntica referència
        // Com que fitxes està ordenada per referència, podem efectuar cerca dicotòmica
        int pos = Collections.binarySearch((List<Fitxa>) fitxes, f);  // Comparació via ORDRE NATURAL dins Fitxa (per Referència)
        // Alternativament:
//        int pos = Collections.binarySearch(fitxes, f.getReferencia(), new ComparaFitxaReferenciaAmbString());
        if (pos >= 0) {
            throw new BibliotecaException("Intent d'afegir fitxa amb referència " + f.getReferencia() + " ja existent");
        }
        // Estem en condicions d'afegir la fitxa f, que ha d'anar a la posició -pos-1

        // Abans, comprovarem que no hi ha un altra fitxa amb el mateix número d'ordre
        // No tenim altra possibilitat que efectuar una recorregut seqüencial:
        for (Fitxa aux : fitxes) {
            if (aux.getOrdre() == f.getOrdre()) {
                throw new BibliotecaException("Intent d'afegir fitxa amb ordre " + f.getOrdre() + "ja existent");
            }
        }

        // Afegim la fitxa a la posició -pos-1
        ((List<Fitxa>) fitxes).add(-pos - 1, f);
    }

    /**
     * Cerca una fitxa dins la biblioteca que tingui la referència indicada
     *
     * @param referencia
     * @return La fitxa en que hi sigui i null si no hi és
     */
    @Override
    public Fitxa cercarFitxa(String referencia) {
        int pos = Collections.binarySearch((List<Fitxa>) fitxes, referencia, new ComparaFitxaReferenciaAmbString());
        if (pos >= 0) {
            return ((List<Fitxa>) fitxes).get(pos);
        } else {
            return null;
        }
    }

    /**
     * Cerca una fitxa dins la biblioteca que tingui la referència indicada i
     * l'extreu de la biblioteca
     *
     * @param referencia
     * @return La fitxa en cas que hi sigui i null si no hi és
     */
    @Override
    public Fitxa extreureFitxa(String referencia) {
        int pos = Collections.binarySearch((List<Fitxa>) fitxes, referencia, new ComparaFitxaReferenciaAmbString());
        if (pos >= 0) {
            return ((List<Fitxa>) fitxes).remove(pos);
        } else {
            return null;
        }
    }

    /**
     * Buida la biblioteca
     */
    @Override
    public void buidar() {
        fitxes.clear();
    }

    @Override
    public String toString() {
        String result = nom + " - Qtat. actual de fitxes: " + fitxes.size();
        for (Fitxa f : fitxes) {
            result = result + "\n\t" + f;
        }
        return result;
    }

    @Override
    public Iterator<Fitxa> recuperarFitxesOrdenadesPerReferencia() {
        return fitxes.iterator();
    }

    @Override
    public Iterator<Fitxa> recuperarFitxesOrdenadesPerTitol() {
        // Generem una altra col·lecció de fitxes, còpia de "fitxes"
        // i ordenem aquesta nova col·lecció segons el títol
        // i retornem un "iterator" d'aquesta nova col·lecció
        ArrayList<Fitxa> aux = new ArrayList(fitxes);
        Collections.sort(aux, new ComparaFitxaPerTitol());
        return aux.iterator();
    }

}
