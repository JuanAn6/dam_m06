package org.milaifontanals.binding;

import java.io.PrintStream;
import java.util.Vector;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class C5CustomerWithSeveralProductVer2 {

    private String name;
    private int age;
    private int id;
    private Vector<C2Product> products = new Vector<C2Product>();

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

    @XmlAttribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElementWrapper(name="products")
    @XmlElement(name="product")
    // L'anotació anterior fa que cada producte estigui en un
    // element <product>
    // I si... C2Product fos una classe amb derivades C2a i C2b
    // i pels objectes de la classe C2a volguessim element <classeA>
    // i pels objectes de la classe C2b volguessim element <classeB>???
    // Caldria escrire:
//    @XmlElements ({
//        @XmlElement(name = "classeA", type = nomPaquet.C2a.class),
//        @XmlElement(name = "classeB", type = nomPaquet.C2b.class)
//    })    
    public Vector<C2Product> getProducts() {
        return products;
    }

    public void setProducts(Vector<C2Product> p) {
        this.products = p;
    }

    public void addProduct(C2Product p) {
        products.add(p);
    }

    public String toString() {
        return id + " - " + name + " - " + age + " - " + products.size() + " produts";
    }

    public void visualitzar(PrintStream out) {
        out.println("Id: " + id);
        out.println("Name: " + name);
        out.println("Age: " + age);
        if (products.isEmpty()) {
            out.println("Zero products");
        } else {
            out.println("Products: ");
            for (int i = 0; i < products.size(); i++) {
                out.println("\t" + products.get(i));
            }
        }
    }
}
