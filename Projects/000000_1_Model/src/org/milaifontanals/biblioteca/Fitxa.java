/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.biblioteca;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Classe que permet gestionar les dades bàsiques d'una fitxa de biblioteca.
 * <BR>
 * Permet gestionar els següents conceptes:<BR>
 * - Referència de la fitxa, que és obligatòria i amb 10<BR>
 * - Títol de la fitxa, que és obligatori i no buït<BR>
 * - Marca referent a si l'objecte corresponent a la fitxa es deixa en préstec o   si aquesta informació és desconeguda<BR>
 * - Número d'ordre de creació de la fitxa<BR>
 * - Nombre de fitxes creades durant l'execució d'un programa<BR>
 *
 * @author Usuari
 */
//public final class Fitxa  >>> No permetre derivar la class Fitxa
@XmlType(propOrder={"titol","esDeixa", "dataCreacio", "dataModificacio"})
public abstract class Fitxa implements Comparable<Fitxa> {

    /**
     * Camps: referència: obligatòria i de llargada mínima de 10 caràcters
     * titol: obligatori i no buït
     */
    private String referencia;
    private String titol;
    private Boolean esDeixa;
    private int ordre;   // Autonumèric
    private static int comptador;    
    private Date dataCreacio = new Date();  // Inicialitzada amb la data del sistema
    private Date dataModificacio;  // Inicialitzada a NULL
    // Només apuntarà una data a partir del moment en que
    // es modifiqui per primera vegada
    
    protected Fitxa(){
        
    }
    
    /**
     * Crea una nova fitxa a partir de la referència i títol indicats per paràmetre
     *
     * @param referencia La referència de la fitxa
     * @param titol El títol de la fitxa
     * @throws FitxaException en cas que la referència o el títol siguin nuls
     * o la referència tingui llargada inferior a 10 caràcters o el títol sigui
     * buït
     */
    public Fitxa(String referencia, String titol) {
        assignReferencia(referencia);
        assignTitol(titol);
        comptador++;
        ordre = comptador;
    }

    /**
     * Crea una nova fitxa a partir de la referència, títol i marca de préstec
     * indicats per paràmetre
     *
     * @param referencia La referència de la fitxa
     * @param titol El títol de la fitxa
     * @param esDeixa Marca per indicar si l'objecte es deixa o no
     * @throws FitxaException en cas que la referència o el títol siguin nuls
     * o la referència tingui llargada inferior a 10 caràcters o el títol sigui
     * buït
     */
    public Fitxa(String referencia, String titol, Boolean esDeixa) {
        assignReferencia(referencia);
        assignTitol(titol);
        assignEsDeixa(esDeixa);
        comptador++;
        ordre = comptador;
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
     * @throws FitxaException en cas que la referència o el títol siguin nuls
     * o la referència tingui llargada inferior a 10 caràcters o el títol sigui
     * buït o l'ordre indicat és superior al comptador de fitxes creades
     */
    public Fitxa(String referencia, String titol, Boolean esDeixa, int ordre, Date dataCreacio, Date dataModificacio) {
        if (ordre>comptador) {
            throw new FitxaException("Intent de creació de fitxa amb ordre superior al comptador.",'O',ordre);
        }
        if (ordre<0) {
            throw new FitxaException("Intent de creació de fitxa amb ordre no estrictament positiu.",'O',ordre);
        }
        if (dataCreacio==null) {
            throw new FitxaException("Intent de creació de fitxa amb data de creació nul·la",'C');
        }
        assignReferencia(referencia);
        assignTitol(titol);
        assignEsDeixa(esDeixa);
        this.ordre = ordre;
        this.dataCreacio = (Date)dataCreacio.clone();
        if (dataModificacio!=null) {
            if (dataModificacio.compareTo(dataCreacio)<=0) {
                throw new FitxaException("Intent de creació de fitxa amb data de modificació anterior a data de creació",'M');
            }
            this.dataModificacio = (Date)dataModificacio.clone();
        }
    }

    // Mètodes "getter" per consultar el contingut de les dades membre
    /**
     * Permet obtenir la referència de la fitxa
     *
     * @return La referència
     */
    @XmlAttribute(name="reference", required=true)
    @XmlJavaTypeAdapter(ReferenceAdapter.class)
    @XmlID //Camps que siguin unics (identificadors)
    public final String getReferencia() {
        return referencia;
    }

    /**
     * Permet obtenir el títol de la fitxa
     *
     * @return El títol
     */
    @XmlElement(name="title")
    public final String getTitol() {
        return titol;
    }

    /**
     * Permet obtenir la marca de préstec de la fitxa
     *
     * @return true si es pot prestar, false si no es pot prestar, null si es
     * desconeix.
     */
    @XmlElement(name="isLent")
    public final Boolean getEsDeixa() {
        return esDeixa;
    }

    /**
     * Permet obtenir el número d'ordre de creació de la fitxa
     *
     * @return El número d'ordre de creació
     */
    @XmlAttribute(name="order")
    public final int getOrdre() {
        return ordre;
    }

    /**
     * Permet obtenir el número de fitxes creades durant l'execucií d'un
     * programa
     *
     * @return El número de fitxes creades
     */
    public final static int getComptador() {
        return comptador;
    }

    public static void setComptador(int comptador) {
        Fitxa.comptador = comptador;
    }

    /**
     * Permet consultar la data de creació de la fitxa
     *
     * @return
     */
    @XmlElement(name="creationDate")
    @XmlJavaTypeAdapter(DateAdapter.class)
    public final Date getDataCreacio() {
        return dataCreacio;
    }

    /**
     * Permet consultar la data de la darrera modificació de la fitxa
     *
     * @return
     */
    @XmlElement(name="modificationDate")
    @XmlJavaTypeAdapter(DateAdapter.class)
    public final Date getDataModificacio() {
        return dataModificacio;
    }

    // Mètodes "setter" 
    /**
     * Permet assignar la referéncia a una fitxa
     *
     * @param referencia Referència a assignar
     * @throws FitxaException si la referència és nul·la o si té menys de 10
     * caràcters
     */
    private final void setReferencia(String referencia) {
        assignReferencia(referencia);
        setDataModificacio();
    }

    private void assignReferencia(String referencia) {
        if (referencia == null) {
            throw new FitxaException("La referència és obligatòria.",'R',referencia);
        }
        if (referencia.length() != 10) {
            throw new FitxaException("La referència ha de tenir 10 caràcters de llargada",'R',referencia);
        }
        this.referencia = referencia;
    }

    /**
     * Permet assignar el títol a una fitxa
     *
     * @param titol Títol a assignar
     * @throws FitxaException si el títol és null o buït
     */
    public final void setTitol(String titol) {
        assignTitol(titol);
        setDataModificacio();
    }

    private void assignTitol(String titol) {
        if (titol == null || titol.length() == 0) {
            throw new FitxaException("Títol obligatori i no buït",'T',titol);
        }
        this.titol = titol;
    }

    /**
     * Permet assignar la marca de préstec a una fitxa
     *
     * @param esDeixa true si és deixa, false si no es deixa o null si es
     * desconeix
     */
    public final void setEsDeixa(Boolean esDeixa) {
        assignEsDeixa(esDeixa);
        setDataModificacio();
    }

    private void assignEsDeixa(Boolean esDeixa) {
        this.esDeixa = esDeixa;
    }

    @Override
    public abstract String toString();

    @Override
    /**
    /*
     * Dues fitxes són iguals si pertanyen a la mateixa classe i
     * tenen la mateixa referència
    */
    public final boolean equals(Object o) {
        if (this == o) {
            return true;      
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Fitxa f = (Fitxa) o;
        return referencia.equals(f.referencia);
    }

    @Override
    public final int hashCode() {
        return 7 * referencia.hashCode();
    }

    public void visualitzar() {
        System.out.println("\tReferència...........: " + referencia);
        System.out.println("\tTítol................: " + titol);
        System.out.println("\tEs Deixa?............: " + esDeixa);
        System.out.println("\tNúm. Ordre...........: " + ordre);
        System.out.println("\tData Creació.........: " + dataCreacio);
        System.out.println("\tData Last Modificació: " + dataModificacio);
    }

    protected final void setDataModificacio() {
        if (dataModificacio == null) {
            dataModificacio = new Date();
        } else {
            dataModificacio.setTime(System.currentTimeMillis());
        }
    }

    @Override
    public int compareTo(Fitxa o) {
            return referencia.compareTo(o.referencia);
    }
    
};


class ReferenceAdapter extends XmlAdapter<String,String> {
   
    @Override
    public String unmarshal(String v) throws Exception {
        return v.substring(1);
    }

    @Override
    public String marshal(String bt) throws Exception {
        return "r"+bt;
    }
}

class DateAdapter extends XmlAdapter<String,Date> {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.S");

    @Override
    public Date unmarshal(String v) throws Exception {
        return sdf.parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
        return sdf.format(v);
    }
}