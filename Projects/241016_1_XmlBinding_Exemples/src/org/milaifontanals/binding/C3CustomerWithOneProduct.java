package org.milaifontanals.binding;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class C3CustomerWithOneProduct {

    private String name;
    private int age;
    private int id;
    private C2Product product;

    @XmlElement         // No caldria
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement         // No caldria
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @XmlAttribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    // L'agafarà per l'AccessorType per defecte
    // L'anomenarà "product" (si no posem "name") i el contingut serà segons
    // anotacions a la classe C2Product
    public C2Product getProduct() {
        return product;
    }

    public void setProduct(C2Product p) {
        this.product = p;
    }


    public String toString() {
        return id + " - " + name + " - " + age + " - " + product;
    }
}
