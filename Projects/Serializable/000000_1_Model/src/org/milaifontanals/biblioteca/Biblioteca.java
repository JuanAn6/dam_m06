/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.biblioteca;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Usuari
 */
@XmlRootElement(name="library")
public abstract class Biblioteca implements IBiblioteca, Serializable {
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
    
    
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(nom);
        out.writeInt(Fitxa.getComptador());
        out.writeObject(fitxes);
    }
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        nom = in.readUTF();
        Fitxa.setComptador(in.readInt());
        fitxes = (Collection<Fitxa>) in.readObject(); 
    }
    
}
