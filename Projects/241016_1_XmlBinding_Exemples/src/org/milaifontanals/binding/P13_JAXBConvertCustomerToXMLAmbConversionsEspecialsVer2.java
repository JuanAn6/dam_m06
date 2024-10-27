package org.milaifontanals.binding;

import java.io.File;
import java.util.Date;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class P13_JAXBConvertCustomerToXMLAmbConversionsEspecialsVer2 {

    public static void main(String[] args) {
        C7CustomerAmbConversionsEspecialsVer2 customer = new C7CustomerAmbConversionsEspecialsVer2();
        customer.setId(100);
        customer.setAge(33);
        customer.setName("Pepe Gotera");
        customer.setDataAlta(new Date());
        try {
            File file = new File("_fileCustomerAmbConversionsEspecialsVer2.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(C7CustomerAmbConversionsEspecialsVer2.class);
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
