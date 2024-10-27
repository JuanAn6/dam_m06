package org.milaifontanals.binding;

import java.io.File;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class P14_JAXBConvertXMLToCustomerAmbConversionsEspecialsVer2 {

    public static void main(String[] args) {
        try {
            File file = new File("_fileCustomerAmbConversionsEspecialsVer2.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(C7CustomerAmbConversionsEspecialsVer2.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            C7CustomerAmbConversionsEspecialsVer2 customer = (C7CustomerAmbConversionsEspecialsVer2) jaxbUnmarshaller.unmarshal(file);
            System.out.println(customer);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
