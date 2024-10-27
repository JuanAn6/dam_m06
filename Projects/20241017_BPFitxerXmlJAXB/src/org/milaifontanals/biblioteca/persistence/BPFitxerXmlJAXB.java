/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.milaifontanals.biblioteca.persistence;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Properties;
import javax.xml.XMLConstants;
import org.apache.commons.io.FileUtils;
import org.milaifontanals.biblioteca.Biblioteca;
import org.milaifontanals.biblioteca.Fitxa;
import org.milaifontanals.biblioteca.FitxaLlibre;
import org.milaifontanals.biblioteca.FitxaRevista;
import org.milaifontanals.biblioteca.IBiblioteca;
import org.milaifontanals.biblioteca.*;

/**
 *
 * @author Juan Antonio
 */
public class BPFitxerXmlJAXB {
    
    
    private String nomFitxer;
    private String nomClasseBiblioteca;

    /**
     * Constructor que cerca fitxer de configuració amb nom
     * BPFitxerBinariManual.properties
     */
    public BPFitxerXmlJAXB() {
        this("BPFitxerBinariManual.properties");
    }

    /**
     * Constructor que cerca fitxer de configuració indicat per paràmetre.
     *
     * @param nomFitxerConfiguracio Nom del fitxer de configuració a usar
     */
    public BPFitxerXmlJAXB(String nomFitxerConfiguracio) {
        try {
            if (nomFitxerConfiguracio == null || nomFitxerConfiguracio.length() == 0) {
                throw new BPFitxerXmlJAXBException("S'ha passat fitxer de configuració null o buit");
            }
            
            Properties p = new Properties();
            
            p.load(new FileReader(nomFitxerConfiguracio));
            
            nomFitxer = p.getProperty("fitxer");
            nomClasseBiblioteca = p.getProperty("classeBiblioteca");
            
            if(nomFitxer == null || nomFitxer.length() == 0 || nomClasseBiblioteca == null || nomClasseBiblioteca.length() == 0){
                throw new BPFitxerXmlJAXBException("Error en el format de les propietats");
            }
                    
        } catch (Exception ex) {
            throw new BPFitxerXmlJAXBException("Error en carregar el fitxer de configuracio");
        }
        
    }

    public void saveBiblioteca(IBiblioteca b) {
        if (b == null) {
            throw new BPFitxerXmlJAXBException("S'ha passat biblioteca null");
        }
        
        try{
            
            FileWriter fileB = new FileWriter(nomFitxer);
            fileB.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            fileB.write("<!DOCTYPE library SYSTEM \"./library.dtd\">\n");
            
            JAXBContext jaxbContextB = JAXBContext.newInstance(
                    Class.forName(nomClasseBiblioteca));
            Marshaller jaxbMarshallerB = jaxbContextB.createMarshaller();

            // output pretty printed
            jaxbMarshallerB.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshallerB.setProperty(Marshaller.JAXB_FRAGMENT, true);
            
            jaxbMarshallerB.marshal(b, fileB);     // Envia a fitxer
            jaxbMarshallerB.marshal(b, System.out);// Envia a consola
            
            
        } catch (Exception e) {
            
            throw new BPFitxerXmlJAXBException("Error en intetnar enregistrar la biblioteca", e);
        }
    }
    
    
     public IBiblioteca loadBiblioteca() {
        
        try {
            
            IBiblioteca b = null;
            
            try {
                System.out.println(nomClasseBiblioteca);
                File file = new File(nomFitxer);
                JAXBContext jaxbContext = JAXBContext.newInstance(Class.forName(nomClasseBiblioteca),FitxaLlibre.class,FitxaRevista.class);

                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
               
                System.setProperty("javax.xml.accessExternalDTD", "all");
                
                
                b = (IBiblioteca) jaxbUnmarshaller.unmarshal(file);
                
                System.out.println(b);
                
                return b;
            } catch (JAXBException e) {
                e.printStackTrace();
            }
                
        } catch (ClassNotFoundException | IllegalArgumentException ex) {
            throw new BPFitxerXmlJAXBException("Error en crear l'objecte " + nomClasseBiblioteca, ex);
        }
            
            
            
        return null;
     }
    
}
