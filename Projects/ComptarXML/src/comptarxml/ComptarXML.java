/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package comptarxml;


import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;

/**
 *
 * @author Juan Antonio
 */
public class ComptarXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SAXBuilder builder = null;
        // Es suposa que tenim el fitxer books.xml en el nivell immediatament superior 
        // a la carpeta del projecte

        File xmlFile = new File(".." + File.separator + "mondial.xml");
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
            System.out.println("Document carregat. Nombre de fills: " + document.getContentSize());
            Element rootNode = document.getRootElement();
            long habitants_total = 0;
            List<Element> list = rootNode.getChildren("country"); 
            
            for (int i = 0; i < list.size(); i++) {
                Element country = list.get(i);
                Element name = country.getChild("name");
                
                Element encompassed = country.getChild("encompassed");
                
                if(encompassed.getAttributeValue("continent").equals("europe")){
                    float percent =  Float.parseFloat(encompassed.getAttributeValue("percentage"));

                    System.out.println(encompassed.getAttributeValue("continent")+" - "+name.getText()+" - "+percent);
                    List<Element> population = country.getChildren("population");
                    
                    int index = 0;
                    int year = -1;
                    int qt = 0;
                    for(Element popu:population){
                        if(year == -1){
                            year = Integer.parseInt(popu.getAttributeValue("year"));
                            index = qt;
                        }
                        
                        if(Integer.parseInt(popu.getAttributeValue("year")) > year){
                            index = qt;
                        }
                        
                        if(
                            popu.getAttributeValue("measured") != null &&
                            popu.getAttributeValue("measured").equals("census") && 
                            Integer.parseInt(popu.getAttributeValue("year")) == year
                        ){
                            index = qt;
                        }
                        
                        qt++;
                    }
                    
                    System.out.println(population.get(index).getAttributeValue("measured")+" - "+population.get(index).getAttributeValue("year")+" - "+population.get(index).getValue());
                    
                    float total_popu = (Float.parseFloat(population.get(index).getText()) * percent) / 100;
                    
                    System.out.println(String.format("%.0f", total_popu));
                    habitants_total += total_popu;
                }
            }
            
            
            System.out.println("Total europa: "+habitants_total);
            
        } catch (IOException | JDOMException io) {
            System.out.println("Excepció: " + io.getClass().getName());
            System.out.println(io.getMessage());
        }
    }
    
}
