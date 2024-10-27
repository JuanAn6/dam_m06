package org.milaifontanals.binding;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name="customer") //Si no li poses name agafa el nom de la clase
@XmlType(propOrder={"name","age"})
// Si no hi és, ordre camps alfabètics
// Els noms han de ser els que tenen els noms dels camps o de les properties
// Si es posa, ha de contenir TOTS els elements que tindrà l'XML
public class C1Customer {
    private String name;
    private int age;
    private int id;
   
    @XmlElement       // No cal posar-lo per què estem en XmlAccessType.PUBLIC_MEMBER
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name="edat")        // Cal posar @XmlElement si volem canviar el nom 
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    
    @XmlAttribute(name="codi")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return id + " - " + name + " - " + age;
    }
    
}

/*
    <customer codi="">
        <name></name>
        <edat></edat>
    </customer>



*/