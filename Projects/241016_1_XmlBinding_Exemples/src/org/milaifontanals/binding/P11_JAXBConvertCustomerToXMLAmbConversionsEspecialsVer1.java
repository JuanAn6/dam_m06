package org.milaifontanals.binding;

import java.io.File;
import java.util.Date;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class P11_JAXBConvertCustomerToXMLAmbConversionsEspecialsVer1 {

    public static void main(String[] args) {
        C6CustomerAmbConversionsEspecialsVer1 customer = new C6CustomerAmbConversionsEspecialsVer1();
        customer.setId(100);
        customer.setAge(33);
        customer.setName("Pepe Gotera");
        customer.setDataAlta(new Date());
        try {
            File file = new File("_fileCustomerAmbConversionsEspecialsVer1.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(C6CustomerAmbConversionsEspecialsVer1.class);
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
