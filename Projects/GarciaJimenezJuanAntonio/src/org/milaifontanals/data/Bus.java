package org.milaifontanals.data;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="autobus")
public class Bus extends TransportVehicle {

    private Integer seats;         // (places) - Obligatori i estrictament positiu

    public Bus(String vehicleIdentificationNumber, float fiscalPower, boolean isEco, Integer seats) {
        super(vehicleIdentificationNumber, fiscalPower, isEco);
        setSeats(seats);
    }

    @XmlElement(name="places")
    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        if (seats == null || seats <= 0) {
            throw new RuntimeException("Info places obligatòria i estrictament positiva");
        }
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Autobus{: " + super.toString() + ", places=" + seats + '}';
    }
    
    // *************************************************************************
    // AFEGIR CODI SOTA AQUESTA LINIA
    
    protected Bus (){
        super();
    }
}
