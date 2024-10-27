package org.milaifontanals.data;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="taxi")
public class Taxi extends TransportVehicle {

    private String license;       // (Llicència) - Obligatòria i amb contingut

    public Taxi(String vehicleIdentificationNumber, float fiscalPower, boolean isEco, String license) {
        super(vehicleIdentificationNumber, fiscalPower, isEco);
        setLicense(license);
    }
    
    @XmlElement(name="llicencia")
    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        if (license==null || license.length()==0 ) {
            throw new RuntimeException("Llicència obligatòria i amb contingut");
        }
        this.license = license;
    }

    @Override
    public String toString() {
        return "Taxi:" + super.toString()+ ", llicència=" + license + '}';
    }

    // *************************************************************************
    // AFEGIR CODI SOTA AQUESTA LINIA
    
    protected Taxi (){
        super();
    }
}
