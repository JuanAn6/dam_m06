package org.milaifontanals.binding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="Customer")
public class C6CustomerAmbConversionsEspecialsVer1 {
    // Conversions especials a aconseguir:
    // El camp "id" ha de quedar enregistrat dins l'XML com $ seguit del valor del camp
    // El camp "dataAlta" ha de quedar enregistrat en format dd/mm/yyyy HH:MM:SS.mil·lèsimesDeSegon
    private String name;
    private int age;
    private int id;
    private Date dataAlta;

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @XmlTransient    // El fem "transient" per què no volem enregistrar el "id" tal qual
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    @XmlTransient   // El fem "transient" per què volem la data en un format específic
    public Date getDataAlta() {
        return dataAlta;
    }

    public void setDataAlta(Date dataAlta) {
        this.dataAlta = dataAlta;
    }

    public String toString() {
        return id + " - " + name + " - " + age + " - " + dataAlta;
    }
    
    // Propietats especials per aconseguir que enregistrat el "id" i la "dataAlta"
    // segons conversions específiques
    @XmlAttribute(name="id")
    private String getIdByJaxb() {
        return "$"+id;
    }
    
    private void setIdByJaxb (String id) {
        this.id = Integer.parseInt(id.substring(1));
    }

    @XmlElement (name="dataAlta")
    private String getDataAltaPerJAXB() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.S");
        return sdf.format(dataAlta);
    }

    private void setDataAltaPerJAXB(String dataAlta) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.S");
        try {
            this.dataAlta = sdf.parse(dataAlta);
        } catch (ParseException ex) {
        }
    }
}
