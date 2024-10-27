/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.milaifontanals.data.Bus;
import org.milaifontanals.data.Fleet;
import org.milaifontanals.data.Taxi;
import org.milaifontanals.data.TransportVehicle;
import org.w3c.dom.NodeList;

/**
 *
 * @author Usuari
 */
public class FleetListPersistence {

    /**
     * Ha d'enregistrar una llista de flotes en format binari coherent amb el
     * format que intenti recuperar el mètode loadListFleetBin.<BR>
     * Qualsevol incidència ha de generar FleetListPersistenceException.
     *
     * @param ll Llista de Fleet a enregistrat
     * @param nomRutaFitxer Fitxer on enregistar la llista de rutes
     */
    public static void saveFleetListBin(List<Fleet> ll, String nomRutaFitxer) throws FleetListPersistenceException {
        // TO DO
        
        if(ll == null || nomRutaFitxer == null){
            throw new FleetListPersistenceException("S'ha passat un paràmetre NULL");
        }
        
        FileOutputStream fos;
        
        try {
            fos = new FileOutputStream(nomRutaFitxer, false); 
        } catch (FileNotFoundException ex) {
            throw new FleetListPersistenceException("Error", ex);
        }
        
        DataOutputStream dos = new DataOutputStream(fos);
        
        
        Iterator<Fleet> it = ll.iterator();
        
        try {
        
            while(it.hasNext()){
                Fleet f = it.next();
                //Gardar nom
                dos.writeUTF(f.getName());
                //Guardar vehicles
                Iterator<TransportVehicle> vit = f.iteVehicle();
                while(vit.hasNext()){
                    TransportVehicle v = vit.next();

                    if (v instanceof Bus) {
                        saveBus(v,dos);
                    } else if (v instanceof Taxi) {
                        saveTaxi(v,dos);
                    }else{
                        throw new FleetListPersistenceException("El metode no esta preparat per aquest tipus de clase");
                    }

                }
                dos.writeUTF("last");
            }
       
            dos.close();
        } catch (IOException ex) {
            throw new FleetListPersistenceException("Error en tancar el fitxer " + nomRutaFitxer, ex);
        }
            
        
    }

    /**
     * Ha d'enregistrar una llista de flotes en format xml validat pel fitxer
     * FleetListPersistence.dtd que es troba a l'arrel del projecte<BR>
     * Qualsevol incidència ha de generar FleetListPersistenceException.
     *
     * @param ll Llista de Fleet a enregistrat
     * @param nomRutaFitxer Fitxer on enregistar la llista de rutes
     */
    /**
     * Ha d'enregistrar una llista de flotes en format xml validat pel fitxer
     * FleetListPersistence.dtd que es troba a l'arrel del projecte<BR>
     * Qualsevol incidència ha de generar FleetListPersistenceException.
     *
     * @param ll Llista de Fleet a enregistrat
     * @param nomRutaFitxer Fitxer on enregistar la llista de rutes
     */
    public static void saveFleetListJDom(List<Fleet> ll, String nomRutaFitxer) throws FleetListPersistenceException {
        // TO DO
        
        if (ll == null || nomRutaFitxer == null) {
            throw new FleetListPersistenceException("S'ha passat un paràmetre NULL");
        }
        
        try {
            Element flotes = new Element("flotes");

            Iterator<Fleet> it = ll.iterator();
        
            while(it.hasNext()){
                Element flota = new Element("flota");
                
                Fleet f = it.next();
                //Gardar nom
                flota.setAttribute("nom", f.getName());
                
                //Guardar vehicles
                Iterator<TransportVehicle> vit = f.iteVehicle();
                while(vit.hasNext()){
                    TransportVehicle v = vit.next();

                    if (v instanceof Bus) {
                        saveBusJDOM((Bus)v, flota);
                    } else if (v instanceof Taxi) {
                        saveTaxiJDOM((Taxi)v, flota);
                    }else{
                        throw new FleetListPersistenceException("El metode no esta preparat per aquest tipus de clase");
                    }
                    
                }
            
                flotes.addContent(flota);
            }
            
            
            Document doc = new Document(flotes);
            
            doc.setDocType(new DocType(doc.getRootElement().getName(), "FleetListPersistence.dtd"));
            // Per mostrar-lo per pantalla o enregistrar-lo a disc...
            XMLOutputter out = new XMLOutputter();
            out.setFormat(Format.getPrettyFormat());    // deixar l'XML "ben formatat"
            // Per enviar-lo a fitxer
            out.output(doc, new FileWriter(nomRutaFitxer));
            
        } catch (IOException ex) {
            throw new FleetListPersistenceException("Error en enregistrar en el fitxer " + nomRutaFitxer, ex);
        }
    }

    /**
     * Ha de recuperar una llista de flotes en format binari coherent amb el
     * format que intenti enregistrar el mètode saveFleetListBin.<BR>
     * Qualsevol incidència ha de generar FleetListPersistenceException.
     *
     * @param nomRutaFitxer Fitxer on enregistar la llista de rutes
     * @return la flota recuperada
     */
    /**
     * Ha de recuperar una llista de flotes en format binari coherent amb el
     * format que intenti enregistrar el mètode saveFleetListBin.<BR>
     * Qualsevol incidència ha de generar FleetListPersistenceException.
     *
     * @param nomRutaFitxer Fitxer on enregistar la llista de rutes
     * @return la flota recuperada
     */
    public static List<Fleet> loadFleetBin(String nomRutaFitxer) throws FleetListPersistenceException {
        
        if(nomRutaFitxer == null){
            throw new FleetListPersistenceException("S'ha passat un paràmetre NULL");
        }
        
        FileInputStream fis;
        
        try {
            fis = new FileInputStream(nomRutaFitxer); 
        } catch (FileNotFoundException ex) {
            throw new FleetListPersistenceException("Error", ex);
        }
        
        DataInputStream dis = new DataInputStream(fis);
        
        List<Fleet> ll = new ArrayList<Fleet>();
        
        try{
            
             try{
                
                while(true){
                    Fleet f = new Fleet(dis.readUTF());
                    
                    boolean i = true;   
                    while(i){
                        String classe = dis.readUTF();
                        
                        if (classe.equals("bus")) {
                            f.addVehicle(loadBus(dis));
                        } else if (classe.equals("taxi")) {
                            f.addVehicle(loadTaxi(dis));
                        } else if(classe.equals("last")){
                            i = false;    
                        }else{
                            
                            throw new FleetListPersistenceException("No esta preparat per aquest tipus de clase");
                        }
                    }
                    
                    ll.add(f);
                }
            }catch(EOFException ex){}
             
             
        } catch (IOException ex) {
            throw new FleetListPersistenceException("Error en llegir del fitxer: "+nomRutaFitxer, ex);
        }
        
        
        
        return ll;    // Ha de retornar la llista de flotes recuperada
    }

    /**
     * Ha de recuperar una llista de flotes en format xml validat pel fitxer
     * FleetListPersistence.dtd que es troba a l'arrel del projecte<BR>
     * Qualsevol incidència ha de generar FleetListPersistenceException.
     *
     * @param nomRutaFitxer Fitxer on enregistar la llista de rutes
     * @return la flota recuperada
     */
    public static List<Fleet> loadFleetJAXB(String nomRutaFitxer) throws FleetListPersistenceException {
        List<Fleet> ll = new ArrayList<Fleet>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            // En cas que el fitxer XML contingui informació de validació i es vulgui
            // comprovar en efectuar el parse, cal haver avisat la factoria:
            dbFactory.setValidating(true);
            // En aquest cas, com que el document XML no conté informació de validació
            // apareix un missatge d'advertència (no genera excepció)
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            //dBuilder.setErrorHandler(null);  // Per a omitir cap avís de la validació
                                             //doncs en aquest cas, el document XML no disposa de <!DOCTYPE>
            /* Si no es desactiva, cal invocar setErrorHandler assignant un objecte d'una classe que implementi 
             la interfície ErrorHandler, en la qual cal haver indicat què fer davant qualsevol dels tipus 
             d'error possibles */

            File file = new File(nomRutaFitxer);

            org.w3c.dom.Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();     // Eliminar nodes buits

            JAXBContext jaxbContext = JAXBContext.newInstance(Fleet.class, Bus.class, Taxi.class, TransportVehicle.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            NodeList llista = doc.getElementsByTagName("flota");
            int q = llista.getLength();
            int i;
            for (i = 0; i < q; i++) {
                Fleet f = (Fleet) jaxbUnmarshaller.unmarshal(llista.item(i));
                ll.add(f);
            }
        } catch (Exception e) {
            throw new FleetListPersistenceException("Error en carregar el xml", e);
        }
        
        return ll;     // Ha de retornar la llista de flotes recuperada
    }

   
    
    
    private static void saveBus(TransportVehicle v, DataOutputStream dos) throws FleetListPersistenceException{
        Bus b = (Bus)v;
        try{
            dos.writeUTF("bus");
            dos.writeInt(b.getSeats());
            
            saveVehicle(v, dos);
            
        }catch(IOException ex){
            throw new FleetListPersistenceException("Error en enregistrar Bus");
        }
        
        
    }
    
    private static void saveTaxi(TransportVehicle v, DataOutputStream dos) throws FleetListPersistenceException{
        Taxi t = (Taxi)v;
        try{
            dos.writeUTF("taxi");
            dos.writeUTF(t.getLicense());
            
            saveVehicle(v, dos);
            
        }catch(IOException ex){
            throw new FleetListPersistenceException("Error en enregistrar Bus");
        }
    }
    
    private static void saveVehicle(TransportVehicle v, DataOutputStream dos) throws FleetListPersistenceException{
         try{
        
            dos.writeUTF(v.getVehicleIdentificationNumber());
            dos.writeFloat(v.getFiscalPower());
            
            if(v.getRegistration() != null && v.getRegistration().trim().length() > 0 ){
                dos.writeUTF("*");
                dos.writeUTF(v.getRegistration());
            }else{
                dos.writeUTF("NULL");
            }
            
            if(v.getRegistrationDate()!= null){
                dos.writeUTF("*");
                dos.writeLong(v.getRegistrationDate().getTime());
            }else{
                dos.writeUTF("NULL");
            }
            
            dos.writeBoolean(v.getIsEco());
            
        }catch(IOException ex){
            throw new FleetListPersistenceException("Error en enregistrar Bus");
        }
    }
    
    
    
    private static Bus loadBus(DataInputStream dis) throws FleetListPersistenceException{
        
        try{
            int seats = dis.readInt();
            
            String vehicleIdentificationNumber = dis.readUTF();
            float fiscalPower = dis.readFloat();
            
            String registration = null;
            if(dis.readUTF().equals("*")){
                registration = dis.readUTF();
            }
            
            Date time = null;
            if(dis.readUTF().equals("*")){
                time = new Date(dis.readLong());
            }
            
            boolean isEco = dis.readBoolean();
            Bus b = new Bus(
                    vehicleIdentificationNumber,
                    fiscalPower,
                    isEco,
                    seats
            ); 
            
            b.setRegistration(registration);
            b.setRegistrationDate(time);
            
            return b;
        }catch(IOException ex){
            throw new FleetListPersistenceException("Error en enregistrar Bus");
        }
        
    }
    
    
    private static Taxi loadTaxi(DataInputStream dis) throws FleetListPersistenceException{
        try{
            String license = dis.readUTF();
            
            
            String vehicleIdentificationNumber = dis.readUTF();
            float fiscalPower = dis.readFloat();
            
            String registration = null;
            if(dis.readUTF().equals("*")){
                registration = dis.readUTF();
            }
            
            Date time = null;
            if(dis.readUTF().equals("*")){
                time = new Date(dis.readLong());
            }
            
            boolean isEco = dis.readBoolean();
            Taxi t = new Taxi(
                    vehicleIdentificationNumber,
                    fiscalPower,
                    isEco,
                    license
            ); 
            
            t.setRegistration(registration);
            t.setRegistrationDate(time);
            
            return t;
        }catch(IOException ex){
            throw new FleetListPersistenceException("Error en enregistrar Bus");
        }
    }
    
    
    
    
    private static void saveBusJDOM(Bus v, Element flota){
        Element bus = new Element("autobus");
        
        saveVehicleJDOM(v, bus);
        
        bus.addContent(new Element("places").setText(""+v.getSeats()));
        flota.addContent(bus);
    }
    
    private static void saveTaxiJDOM(Taxi v, Element flota){
        Element taxi = new Element("taxi");
        
        saveVehicleJDOM(v, taxi);
        
        taxi.addContent(new Element("llicencia").setText(v.getLicense()));
        flota.addContent(taxi);
        
    }
    
    private static void saveVehicleJDOM(TransportVehicle v, Element vehicle){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        
        vehicle.setAttribute("identificadorBastidor", "IB-"+v.getVehicleIdentificationNumber());
        vehicle.setAttribute("ecologic", (v.getIsEco() ? "Si": "No"));
        
        vehicle.addContent(new Element("potenciaFiscal").setText(""+v.getFiscalPower()));
        
        if(v.getRegistration() != null){
            vehicle.addContent(new Element("matricula").setText(v.getRegistration()));            
        }
        
        if(v.getRegistrationDate() != null){
            vehicle.addContent(new Element("dataMatricula").setText(format.format(v.getRegistrationDate())));
        }
        
        
        
    }
  
}
