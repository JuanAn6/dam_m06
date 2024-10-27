package org.milaifontanals.data;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

@XmlRootElement(name="flota")
public class Fleet {
    private String name;     // Obligatori i amb contingut
    
    
    @XmlElements({
        @XmlElement(name="autobus", type=Bus.class),
        @XmlElement(name="taxi", type=Taxi.class),
    })
    private ArrayList<TransportVehicle> vehicles = new ArrayList();

    public Fleet(String name) {
        setName(name);
    }
    
    @XmlAttribute(name="nom")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name==null || name.length()==0) {
            throw new RuntimeException("Nom de flota obligatori i amb contingut");
        }
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.name.toUpperCase());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Fleet other = (Fleet) obj;
        return Objects.equals(this.name.toUpperCase(), other.name.toUpperCase());
    }

    @Override
    public String toString() {
        return "Flota: " + "nom=" + name + ", vehicles=" + vehicles + '}';
    }
    
    public void addVehicle (TransportVehicle v) {
        if (v==null) {
            throw new RuntimeException("No es pot afegir vehicle null");
        }
        if (vehicles.contains(v)) {
            throw new RuntimeException("La flota ja conté vehicle amb aquest bastidor");
        }
        vehicles.add(v);
    }
    
    public Iterator<TransportVehicle> iteVehicle() {
        return Collections.unmodifiableList(vehicles).iterator();
    }
    
    public int getQtyVehicle() {
        return vehicles.size();
    }
    
    // *************************************************************************
    // AFEGIR CODI SOTA AQUESTA LINIA
    
    protected Fleet () {}
}
