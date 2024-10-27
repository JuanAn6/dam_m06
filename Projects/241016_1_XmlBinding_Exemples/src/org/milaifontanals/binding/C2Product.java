/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.binding;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

/**
 *
 * @author iguixa
 */
@XmlRootElement
public class C2Product {

    
    private String code;
    private String name;
    private Float price;

    private C2Product() {
    }

    public C2Product(String code, String name, Float price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    @XmlAttribute(name="codigo")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

//    @XmlElement  // Pot ser-hi o no... Equivalent
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name==null) {
            throw new RuntimeException("Nom de producte obligatori");
        }
        this.name = name;
    }

    @XmlTransient   // Preu no estarà a l'XML
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" + "code=" + code + ", name=" + name + ", price=" + price + '}';
    }

}
