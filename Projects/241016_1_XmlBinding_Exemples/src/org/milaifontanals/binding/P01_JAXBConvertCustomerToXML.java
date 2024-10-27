package org.milaifontanals.binding;

import java.io.File;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class P01_JAXBConvertCustomerToXML {

    public static void main(String[] args) {
        C1Customer customer = new C1Customer();
        customer.setId(100);
        customer.setAge(33);
        customer.setName("Pepe Gotera");
        try {
            File file = new File("_fileCustomer.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(C1Customer.class);
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
