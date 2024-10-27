package org.milaifontanals.binding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="Customer")
public class C7CustomerAmbConversionsEspecialsVer2 {
    // Conversions especials a aconseguir:
    // El camp "name" ha de quedar enregistrat dins l'XML com $ seguit del valor del camp
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

    @XmlTransient
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlAttribute(name="id")
    @XmlJavaTypeAdapter(IdCustomerAdapter.class)
    private Integer getIdAux() {
        return id;
    }

    private void setIdAux(Integer id) {
        this.id = id;
    }
    
    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date getDataAlta() {
        return dataAlta;
    }

    public void setDataAlta(Date dataAlta) {
        this.dataAlta = dataAlta;
    }

    public String toString() {
        return id + " - " + name + " - " + age + " - " + dataAlta;
    }
    
}

class IdCustomerAdapter extends XmlAdapter<String,Integer> {

    @Override
    public Integer unmarshal(String vt) throws Exception {
        return Integer.parseInt(vt.substring(1));
    }

    @Override
    public String marshal(Integer bt) throws Exception {
        return "$"+bt;
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

