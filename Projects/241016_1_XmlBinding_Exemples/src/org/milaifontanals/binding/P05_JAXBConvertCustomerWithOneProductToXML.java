package org.milaifontanals.binding;

import java.io.File;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class P05_JAXBConvertCustomerWithOneProductToXML {

    public static void main(String[] args) {
        C3CustomerWithOneProduct customer = new C3CustomerWithOneProduct();
        customer.setId(100);
        customer.setAge(33);
        customer.setName("Pepe Gotera");
        customer.setProduct(new C2Product("PIL001","Pilota Fubtol", 28F));
        try {
            File file = new File("_fileCustomerWithOneProduct.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(C3CustomerWithOneProduct.class);
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
