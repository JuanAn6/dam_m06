/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.biblioteca;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.Date;

/**
 *
 * @author Usuari
 */
@XmlRootElement(name="bookCard")
//@XmlType(propOrder={"titol","esDeixa", "dataCreacio", "dataModificacio", "editorial"})
public class FitxaLlibre extends Fitxa {

    private String editorial;   // Ha de permetre null
    private String isbn;        // Ha de ser O buit o ISBN-10 o ISBN-13

    protected FitxaLlibre() {
        super();
    }
    
    public FitxaLlibre(String referencia, String titol) {
        super(referencia, titol);
        isbn = "";
    }

    public FitxaLlibre(String referencia, String titol,
            String editorial, String isbn) {
        super(referencia, titol);
        // Alternativament: this(referencia,titol)
        this.editorial = editorial;
        assignIsbn(isbn);
    }

    /**
     * Carrega una fitxa existent (ja té ordre) a partir de la referència i títol
     * i marca de préstec i ordre-dataCreacio-dataModificacio indicats per paràmetre
     *
     * @param referencia La referència de la fitxa
     * @param titol El títol de la fitxa
     * @param esDeixa Marca per indicar si l'objecte es deixa o no
     * @param ordre L'ordre que correspon a la fitxa
     * @param dataCreacio La data de creació de la fitxa
     * @param dataModificacio La data de darrera modificació de la fitxa
     * @param editorial Editorial
     * @param isbn ISBN
     * @throws FitxaException en cas que la referència o el títol siguin nuls
     * o la referència tingui llargada inferior a 10 caràcters o el títol sigui
     * buït o l'ordre indicat és superior al comptador de fitxes creades
     */    
    public FitxaLlibre(String referencia, String titol, Boolean esDeixa, int ordre, Date dataCreacio, Date dataModificacio, String editorial, String isbn) {
        super(referencia, titol, esDeixa, ordre, dataCreacio, dataModificacio);
        this.editorial = editorial;
        assignIsbn(isbn);
    }
    
    @XmlElement(name="editorial")
    public final String getEditorial() {
        return editorial;
    }
    
    public final void setEditorial(String editorial) {
        this.editorial = editorial;
        setDataModificacio();
    }
    
    @XmlAttribute(name="isbn")
    public final String getIsbn() {
        return isbn;
    }

    private void assignIsbn(String isbn) {
        if (isbn == null) {
            throw new FitxaException("L'ISBN ha de ser buit o ISBN-10 o ISBN-13",'I',isbn);
        }
        if (!isbn.equals("") && isbn.length() != 10 && isbn.length() != 13) {
            throw new FitxaException("L'ISBN ha de ser buit o ISBN-10 o ISBN-13",'I',isbn);
        }
        // Comprovar la correctesa de ISBN-10 o ISBN-13...
        this.isbn = isbn;
    }

    public final void setIsbn(String isbn) {
        assignIsbn(isbn);
        setDataModificacio();
    }

    @Override
    public void visualitzar() {
        System.out.println("Soc un llibre!!!");
        super.visualitzar(); // No estem obligats!!!
        System.out.println("\tEditorial............: " + editorial);
        System.out.println("\tISBN.................: " + isbn);
    }

    @Override
    public String toString() {
        return "Soc llibre! Ref: " + getReferencia() + " - Títol: " + getTitol() + " - ISBN: " + isbn;
    }

}
