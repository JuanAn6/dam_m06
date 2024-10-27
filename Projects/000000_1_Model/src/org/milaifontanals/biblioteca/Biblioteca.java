/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.biblioteca;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Usuari
 */
//@XmlRootElement(name="library")
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    
    "counter",
    "fitxes"
})
public abstract class Biblioteca implements IBiblioteca { 
    
    protected String nom;               // Obligatori
    
    @XmlElementWrapper(name="cards")
    @XmlElements({
        @XmlElement(name="bookCard", type=FitxaLlibre.class),
        @XmlElement(name="magazinCard", type=FitxaRevista.class),
    })
    protected Collection<Fitxa> fitxes; // Referència ha de ser identificador
                                        // Ordenada per referència
    public Biblioteca(){}
    
    public Biblioteca(String nom) {
        setNom(nom);
    }
    
    @XmlAttribute(name="name")
    @Override
    public final String getNom() {
        return nom;
    }

    @Override
    public final void setNom(String nom) {
        if (nom == null || nom.length() == 0) {
            throw new BibliotecaException("El nom d'una biblioteca és obligatori");
        }
        this.nom = nom;
    }
    
    
    
    @XmlElement(name="counter")
    private int getCounter() {
        return Fitxa.getComptador();
    }
    
    private void setCounter(int comptador) {
        Fitxa.setComptador(comptador);
    }
    
}