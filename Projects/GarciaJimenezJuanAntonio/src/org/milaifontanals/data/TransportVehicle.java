package org.milaifontanals.data;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;



public abstract class TransportVehicle {

    private String vehicleIdentificationNumber; // (Bastidor) - Obligatori amb contingut i longitud 17
    private float fiscalPower;                  // (Potència fiscal) - Obligatori estrictament positiu
    private String registration;                // (Matrícula) - Optatiu i si té valor, no pot ser buit
    private Date registrationDate;              // (Data matrícula) - Optatiu i si en té, no pot ser futura
    // registration i registrationDate amb valor els dos o sense valor els dos
    private boolean isEco;                      // (Ecologic?) - Obligatori

    public TransportVehicle(String vehicleIdentificationNumber, float fiscalPower, boolean isEco) {
        setVehicleIdentificationNumber(vehicleIdentificationNumber);
        setFiscalPower(fiscalPower);
        setIsEco(isEco);
    }

    @XmlAttribute(name="identificadorBastidor")
    @XmlJavaTypeAdapter(ReferenceAdapter.class)
    public String getVehicleIdentificationNumber() {
        return vehicleIdentificationNumber;
    }

    public void setVehicleIdentificationNumber(String vehicleIdentificationNumber) {
        if (vehicleIdentificationNumber == null || vehicleIdentificationNumber.length() != 17) {
            throw new RuntimeException("Bastidor obligatori i de longitud 17 caràcters");
        }
        this.vehicleIdentificationNumber = vehicleIdentificationNumber.toUpperCase();
    }
    
    @XmlElement(name="potenciaFiscal")
    public float getFiscalPower() {
        return fiscalPower;
    }

    public void setFiscalPower(float fiscalPower) {
        if (fiscalPower < 0) {
            throw new RuntimeException("Potencia fiscal estrictament positiva");
        }
        this.fiscalPower = fiscalPower;
    }
    
    @XmlElement(name="matricula")
    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        if (registration != null && registration.length() == 0) {
            throw new RuntimeException("Matrícula optativa o amb contingut");
        }
        this.registration = registration;
        if (registrationDate == null) {
            registrationDate = new Date();
        }
    }

    @XmlElement(name="dataMatricula")
    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        if (registrationDate != null && registrationDate.after(new Date())) {
            throw new RuntimeException("Data matrícula optativa o no futura");
        }
        if (registrationDate == null) {
            this.registrationDate = null;
            this.registration = null;
        } else {
            if (registration == null) {
                throw new RuntimeException("No es pot assignar data de matrícula sense tenir matrícula");
            } else {
                this.registrationDate = (Date) registrationDate.clone();
            }
        }
    }

    
    @XmlTransient
    public boolean getIsEco() {
        return isEco;
    }

    public void setIsEco(boolean isEco) {
        this.isEco = isEco;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.vehicleIdentificationNumber);
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
        final TransportVehicle other = (TransportVehicle) obj;
        if (!Objects.equals(this.vehicleIdentificationNumber, other.vehicleIdentificationNumber)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bastidor=" + vehicleIdentificationNumber + ", potenciaFiscal=" + fiscalPower
                + ", matricula=" + registration + ", dataMatricula=" + registrationDate
                + ", ecològic?=" + isEco + '}';
    }

    // *************************************************************************
    // AFEGIR CODI SOTA AQUESTA LINIA
    
    protected TransportVehicle(){}
    
    
    @XmlElement(name="ecologic")
    private String getEcologic() {
        return this.getIsEco() ? "Si" : "No";
    }
    
    private void setEcologic(Boolean b) {
        this.setIsEco(b);
    }
}



class DateAdapter extends XmlAdapter<String,Date> {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public Date unmarshal(String v) throws Exception {
        return sdf.parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
        return sdf.format(v);
    }
}

class ReferenceAdapter extends XmlAdapter<String,String> {
   
    @Override
    public String unmarshal(String v) throws Exception {
        return v.substring(3);
    }

    @Override
    public String marshal(String bt) throws Exception {
        return "IB-"+bt;
    }
}