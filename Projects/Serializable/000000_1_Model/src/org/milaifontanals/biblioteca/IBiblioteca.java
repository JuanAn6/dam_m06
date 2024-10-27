/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.biblioteca;

import java.util.Iterator;

/**
 *
 * @author Usuari
 */
public interface IBiblioteca {

    /**
     * Afegeix una fitxa a la Biblioteca
     *
     * @param f Fitxa a afegir
     * @throws BibliotecaException si la Biblioteca ja conté una fitxa amb
     * idèntica referència u ordre
     */
    void afegirFitxa(Fitxa f);

    /**
     * Buida la biblioteca
     */
    void buidar();

    /**
     * Cerca una fitxa dins la biblioteca que tingui la referència indicada
     *
     * @param referencia
     * @return La fitxa en cas que hi sigui i null si no hi és
     */
    Fitxa cercarFitxa(String referencia);

    /**
     * Cerca una fitxa dins la biblioteca que tingui la referència indicada i
     * l'extreu de la biblioteca
     *
     * @param referencia
     * @return La fitxa en cas que hi sigui i null si no hi és
     */
    Fitxa extreureFitxa(String referencia);

    /**
     * Retorna el nom de la Biblioteca
     *
     * @return Nom de la biblioteca
     */
    String getNom();

    /**
     * Recupera les fitxes ordenades per referència
     *
     * @return Iterador de Fitxa ordenades per referència
     */
    Iterator<Fitxa> recuperarFitxesOrdenadesPerReferencia();

    /**
     * Recupera les fitxes ordenades per títol
     *
     * @return Iterador de Fitxa ordenades per títol
     */
    Iterator<Fitxa> recuperarFitxesOrdenadesPerTitol();

    /**
     * Permet canviar el nom de la Biblioteca
     *
     * @param nom Nou nom
     * @throws BibliotecaException si el nom és null o buit
     */
    void setNom(String nom);

    /**
     * Facilita una representació textual de la Biblioteca
     *
     * @return Representació textual de la Biblioteca
     */
    String toString();

}
