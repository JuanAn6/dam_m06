/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.biblioteca;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 *
 * @author Usuari
 */
@XmlRootElement(name="magazineCard")
public class FitxaRevista extends Fitxa {
    private int any;
    private int num;
    
    protected FitxaRevista(){}

    public FitxaRevista(String referencia, String titol, int any, int num) {
        super(referencia, titol);
        setAny(any);
        setNum(num);
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
     * @param any L'any de la revista - Inmutables
     * @param num El número de la revista - Inmutables
     * @throws FitxaException en cas que la referència o el títol siguin nuls
     * o la referència tingui llargada inferior a 10 caràcters o el títol sigui
     * buït o l'ordre indicat és superior al comptador de fitxes creades
     */
    public FitxaRevista(String referencia, String titol, Boolean esDeixa, int ordre, Date dataCreacio, Date dataModificacio, int any, int num) {
        super(referencia, titol, esDeixa, ordre, dataCreacio, dataModificacio);
        setAny(any);
        setNum(num);
    }

    @XmlAttribute(name="year")
    public final int getAny() {
        return any;
    }

    private void setAny(int any) {
        if (any<=0) {
            throw new FitxaException("L'any de la revista ha de ser >= 0",'A',any);
        }
        this.any = any;
    }
    
    @XmlAttribute(name="num")
    public final int getNum() {
        return num;
    }

    private void setNum(int num) {
        if (num<=0) {
            throw new FitxaException("El número de la revista ha de ser >= 0",'N',num);
        }        
        this.num = num;
    }

    @Override
    public String toString() {
        return "Soc revista! Ref: " + getReferencia() + " - Títol: " + getTitol() + " - Any: " + any + " - Núm.: " + num;
    }
    
    @Override
    public void visualitzar() {
        System.out.println("Soc una revista!!!");
        super.visualitzar(); // No estem obligats!!!
        System.out.println("\tAny..................: " + any);
        System.out.println("\tNúmero...............: " + num);
    }
    
    
    
   
    
    
    
}
