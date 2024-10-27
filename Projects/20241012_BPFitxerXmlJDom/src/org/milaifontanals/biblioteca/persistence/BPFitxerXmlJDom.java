package org.milaifontanals.biblioteca.persistence;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import org.jdom2.Attribute;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.milaifontanals.biblioteca.Fitxa;
import org.milaifontanals.biblioteca.FitxaLlibre;
import org.milaifontanals.biblioteca.FitxaRevista;
import org.milaifontanals.biblioteca.IBiblioteca;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Juan Antonio
 */
public class BPFitxerXmlJDom {
  

    private String nomFitxer;
    private String nomClasseBiblioteca;

    /**
     * Constructor que cerca fitxer de configuració amb nom
     * BPFitxerBinariManual.properties
     */
    public BPFitxerXmlJDom() {
        this("BPFitxerBinariManual.properties");
    }

    /**
     * Constructor que cerca fitxer de configuració indicat per paràmetre.
     *
     * @param nomFitxerConfiguracio Nom del fitxer de configuració a usar
     */
    public BPFitxerXmlJDom(String nomFitxerConfiguracio) {
        try {
            if (nomFitxerConfiguracio == null || nomFitxerConfiguracio.length() == 0) {
                throw new BPFitxerXmlJDomException("S'ha passat fitxer de configuració null o buit");
            }
            
            Properties p = new Properties();
            
            p.load(new FileReader(nomFitxerConfiguracio));
            
            nomFitxer = p.getProperty("fitxer");
            nomClasseBiblioteca = p.getProperty("classeBiblioteca");
            
            if(nomFitxer == null || nomFitxer.length() == 0 || nomClasseBiblioteca == null || nomClasseBiblioteca.length() == 0){
                throw new BPFitxerXmlJDomException("Error en el format de les propietats");
            }
                    
        } catch (Exception ex) {
            throw new BPFitxerXmlJDomException("Error en carregar el fitxer de configuracio");
        }
        
    }

    public void saveBiblioteca(IBiblioteca b) {
        if (b == null) {
            throw new BPFitxerXmlJDomException("S'ha passat biblioteca null");
        }
        
        try {
            
            Element library = new Element("library");
            library.setAttribute(new Attribute("name", b.getNom()));
            
            Element counter = new Element("counter");
            counter.setText(Fitxa.getComptador()+"");
            library.addContent(counter);
            
            Element cards = new Element("cards");
            
            Iterator<Fitxa> it = b.recuperarFitxesOrdenadesPerReferencia();
            while (it.hasNext()) {
                Fitxa f = it.next();
                if (f instanceof FitxaLlibre) {
                    saveFitxaLlibre(f, cards);
                } else if (f instanceof FitxaRevista) {
                    saveFitxaRevista(f, cards);
                } else {
                    throw new BPFitxerXmlJDomException("Mètode saveBiblioteca no preparat per "
                            + "enregristrar objecte de la classe "
                            + f.getClass().getName());
                }
            }
            
            library.addContent(cards);
            
            Document doc = new Document(library);
            // Tenim el document en memòria...
            // En cas que calgui inserir etiqueta <!DOCTYPE> al document
            doc.setDocType(new DocType(doc.getRootElement().getName(), "../library.dtd"));
            // Per mostrar-lo per pantalla o enregistrar-lo a disc...
            XMLOutputter out = new XMLOutputter();
            out.setFormat(Format.getPrettyFormat());    // deixar l'XML "ben formatat"

            // Per enviar-lo cap pantalla
            out.output(doc, System.out);
            // Per enviar-lo a fitxer
            out.output(doc, new FileWriter(nomFitxer));
            
        } catch (IOException ex) {
            throw new BPFitxerXmlJDomException("Error en enregistrar en el fitxer " + nomFitxer, ex);
        }
    }

    private static void saveFitxaLlibre(Fitxa f, Element cards) throws IOException {
        FitxaLlibre fl = (FitxaLlibre) f;
        Element bookCard = new Element("bookCard");
        bookCard.setAttribute(new Attribute("isbn", fl.getIsbn()));
        saveFitxa(f,bookCard);
        
        if (fl.getEditorial() == null) {
            bookCard.addContent(new Element("editorial").setText("N"));
        } else {
            bookCard.addContent(new Element("editorial").setText(fl.getEditorial()));
        }
        
        cards.addContent(bookCard);
    }

    private static void saveFitxaRevista(Fitxa f, Element cards) throws IOException {
        FitxaRevista fr = (FitxaRevista) f;
        Element magazinCard = new Element("magazinCard");
        
        saveFitxa(f, magazinCard);
        
        magazinCard.setAttribute(new Attribute("year", fr.getAny()+""));
        magazinCard.setAttribute(new Attribute("num", fr.getNum()+""));

        cards.addContent(magazinCard);

    }

    private static void saveFitxa(Fitxa f, Element card) throws IOException {
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        
        card.setAttribute(new Attribute("reference", "r"+f.getReferencia()));
        card.addContent(new Element("title").setText(f.getTitol()));
        card.addContent(new Element("isLent").setText(f.getEsDeixa() != null?"Y":"N"));
        card.setAttribute(new Attribute("order", ""+f.getOrdre()));
        
        card.addContent(new Element("creationDate").setText(format.format(f.getDataCreacio())));
        
        if (f.getDataModificacio() == null) {
            card.addContent(new Element("modificationDate").setText("N"));
        } else {
            card.addContent(new Element("modificationDate").setText(format.format(f.getDataModificacio())));
        }
    }

    
    public IBiblioteca loadBiblioteca() {
        SAXBuilder builder = null;
        // Es suposa que tenim el fitxer books.xml en el nivell immediatament superior 
        // a la carpeta del projecte

        File xmlFile = new File(nomFitxer);
        try {
            // Per validar un fitxer en el procés de càrrega, cal utilitzar un SAXBuilder amb instruccions
            // de validació, però si el fitxer a carregar no conté DOCTYPE, es produirà una excepció en la
            // validació. Per això creem el SAXBuilder amb validació o sense, segons el fitxer XML contingui
            // o no la cadena DOCTYPE i per saber-ho ens ajudem de FileUtils d'Apache Commons
            if (FileUtils.readFileToString(xmlFile, (String) null).contains("<!DOCTYPE")) {
                builder = new SAXBuilder(XMLReaders.DTDVALIDATING);
            } else {
                builder = new SAXBuilder();
            }
        } catch (IOException ex) {
            System.out.println("Error en comprovar si el fitxer XML conté etiqueta DOCTYPE");
            System.out.println("Excepció: " + ex.getClass().getName());
            System.out.println(ex.getMessage());
            System.exit(1);
        }
        try {
            Document document = builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            
            
            IBiblioteca b = null;
            try {
                Constructor c = Class.forName(nomClasseBiblioteca).getConstructor(String.class);
                b = (IBiblioteca) c.newInstance(rootNode.getAttributeValue("name")); // Segona dada: nom de la biblioteca
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                throw new BPFitxerXmlJDomException("Error en crear l'objecte " + nomClasseBiblioteca, ex);
            }
            
            
            List<Element> list = rootNode.getChildren();
            Fitxa.setComptador(Integer.parseInt(list.get(0).getText()));
            List<Element> fitxes = list.get(1).getChildren();
            
            
            for(int i = 0; i < fitxes.size(); i++){
                if(fitxes.get(i).getName() == "magazinCard"){
                    b.afegirFitxa(loadFitxaRevista(fitxes.get(i)));
                }else if(fitxes.get(i).getName() == "bookCard"){
                    b.afegirFitxa(loadFitxaLlibre(fitxes.get(i)));
                }else{
                    throw new BPFitxerXmlJDomException("El contingut del xml es erroni");
                }
            }
            
            return b;
        } catch (IOException | JDOMException io) {
            System.out.println("Excepció: " + io.getClass().getName());
            System.out.println(io.getMessage());
        }
        return null;
    }
      
    private static FitxaRevista loadFitxaRevista(Element fitxa) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        
        String referencia = fitxa.getAttributeValue("reference").substring(1);
        String titol = fitxa.getChildText("title");
        Boolean esDeixa = fitxa.getChildText("isLent").equals("Y");
        
        int ordre = Integer.parseInt(fitxa.getAttributeValue("order"));
        Date dataCreacio;
        Date dataModificacio;

        try{
            dataCreacio = format.parse(fitxa.getChildText("creationDate"));
            
            if (!fitxa.getChildText("modificationDate").equals("N")) {
                dataModificacio = format.parse(fitxa.getChildText("modificationDate"));

            } else {
                dataModificacio = null;
            }
            
        }catch(ParseException ex){
            throw new BPFitxerXmlJDomException("Error en el format de les dates en el xml");
        }
        int any = Integer.parseInt(fitxa.getAttributeValue("year"));
        int num = Integer.parseInt(fitxa.getAttributeValue("num"));
        
        return new FitxaRevista(referencia, titol, esDeixa, ordre, dataCreacio, dataModificacio, any, num);
    }

    private static FitxaLlibre loadFitxaLlibre(Element fitxa) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        
        String referencia = fitxa.getAttributeValue("reference").substring(1);
        String titol = fitxa.getChildText("title");
        Boolean esDeixa = fitxa.getChildText("isLent").equals("Y");
        
        int ordre = Integer.parseInt(fitxa.getAttributeValue("order"));
        Date dataCreacio;
        Date dataModificacio;

        try{
            dataCreacio = format.parse(fitxa.getChildText("creationDate"));
            
            if (!fitxa.getChildText("modificationDate").equals("N")) {
                dataModificacio = format.parse(fitxa.getChildText("modificationDate"));

            } else {
                dataModificacio = null;
            }
            
        }catch(ParseException ex){
            throw new BPFitxerXmlJDomException("Error en el format de les dates en el xml");
        }
        String editorial = fitxa.getChildText("editorial").equals("N") ? null : fitxa.getChildText("editorial");
        String isbn = fitxa.getAttributeValue("isbn");
        
        return new FitxaLlibre(referencia, titol, esDeixa, ordre, dataCreacio, dataModificacio, editorial, isbn);
    }
   
}