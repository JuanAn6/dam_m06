package org.milaifontanals.binding;

import java.io.File;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class Main {

    public static void main(String[] args) {
        Person p = new Person();
        p.setId(100);
        p.setAge(33);
        p.setName("Pepe Gotera");
        try {
            File file = new File("_fileCustomer.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(p, file);     // Envia a fitxer
            jaxbMarshaller.marshal(p, System.out);// Envia a consola

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
