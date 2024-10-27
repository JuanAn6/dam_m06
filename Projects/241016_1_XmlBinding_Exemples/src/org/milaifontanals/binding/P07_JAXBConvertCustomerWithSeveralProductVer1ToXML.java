package org.milaifontanals.binding;

import java.io.File;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class P07_JAXBConvertCustomerWithSeveralProductVer1ToXML {

    public static void main(String[] args) {
        C4CustomerWithSeveralProductVer1 customer = new C4CustomerWithSeveralProductVer1();
        customer.setId(100);
        customer.setAge(33);
        customer.setName("Pepe Gotera");
        customer.addProduct(new C2Product("PIL001","Pilota Fubtol", 28F));
        customer.addProduct(new C2Product("PIL002","Pilota Bàsquet", 29F));
        customer.addProduct(new C2Product("PIL003","Pilota Volei", 30F));
        try {
            File file = new File("_fileCustomerWithSeveralProductVer1.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(C4CustomerWithSeveralProductVer1.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(customer, file);     // Envia a fitxer
            jaxbMarshaller.marshal(customer, System.out);// Envia a consola

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
