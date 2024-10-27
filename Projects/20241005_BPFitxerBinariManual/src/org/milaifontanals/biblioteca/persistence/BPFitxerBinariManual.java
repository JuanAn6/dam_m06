/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.milaifontanals.biblioteca.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.milaifontanals.biblioteca.Fitxa;
import org.milaifontanals.biblioteca.FitxaLlibre;
import org.milaifontanals.biblioteca.FitxaRevista;
import org.milaifontanals.biblioteca.IBiblioteca;


/**
 *
 * @author Juan Antonio
 */
public class BPFitxerBinariManual {
    public static void saveBiblioteca(IBiblioteca b, String fileName){
        
        if(b == null || fileName == null){
            throw new RuntimeException("S'ha passat un paràmetre NULL");
        }
        
        FileOutputStream fos;
        
        try {
            //Append: true,  per començar al final, false per a eliminar el contingut;    
            fos = new FileOutputStream(fileName, false); 
        } catch (FileNotFoundException ex) {
            System.out.println("No s'ha pogut crear el fitxer: "+fileName);
            System.out.println(ex.getMessage());
            return;
        }
        
        byte[] bytes = b.getNom().getBytes(StandardCharsets.UTF_8);
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
        byteBuffer.put(bytes);
        
        try {
            fos.write(byteBuffer.array());//Guarda el nom
            insertSeparator(fos);
        } catch (IOException ex) {
            System.out.println("Error en intentar guardar el nom");
            ex.printStackTrace();
        }
        
        ByteBuffer orderBytes = ByteBuffer.allocate(4);
        orderBytes.putInt(Fitxa.getComptador());
        
        try {
            fos.write(orderBytes.array());
            insertSeparator(fos);

        } catch (IOException ex) {
            System.out.println("Error en intentar guardar el ordre");
            ex.printStackTrace();
        }
        
        try{
            Iterator<Fitxa> fitxes = b.recuperarFitxesOrdenadesPerReferencia();
            
            while(fitxes.hasNext()){
                Fitxa f = fitxes.next();
                ByteBuffer charIdentifier = ByteBuffer.allocate(2);
                
                //Identificador L o F;
                if (f instanceof FitxaLlibre) {
                    charIdentifier.putChar('L');                   
                    fos.write(charIdentifier.array());
                } else if (f instanceof FitxaRevista) {
                    charIdentifier.putChar('F');                   
                    fos.write(charIdentifier.array());
                }
                insertSeparator(fos);
                
                //Referencia
                byte[] bytes_ref = f.getReferencia().getBytes(StandardCharsets.UTF_8);
                ByteBuffer ref_buff = ByteBuffer.allocate(bytes_ref.length);
                ref_buff.put(bytes_ref);
                
                fos.write(ref_buff.array());
                insertSeparator(fos);
                
                //Titol
                byte[] bytes_titol = f.getTitol().getBytes(StandardCharsets.UTF_8);
                ByteBuffer titol_buff = ByteBuffer.allocate(bytes_titol.length);
                titol_buff.put(bytes_titol);
                
                fos.write(titol_buff.array());
                insertSeparator(fos);
                
                
                //EsDeixa
                int es_deixa = -1;
                ByteBuffer esDeixaIdentifier = ByteBuffer.allocate(2);
                if(f.getEsDeixa() != null){
                    es_deixa = f.getEsDeixa() ? 1 : 0;
                    esDeixaIdentifier.putChar('*');
                }else{
                    esDeixaIdentifier.putChar('N');                   
                }    
                
                fos.write(esDeixaIdentifier.array());
                insertSeparator(fos);

                
                ByteBuffer bytes_es_deixa = ByteBuffer.allocate(4);
                bytes_es_deixa.putInt(es_deixa);
                fos.write(bytes_es_deixa.array());
                insertSeparator(fos);
                
                //Ordre
                ByteBuffer bytes_ordre = ByteBuffer.allocate(4);
                bytes_ordre.putInt(f.getOrdre());
                fos.write(bytes_ordre.array());
                insertSeparator(fos);
                
                //DataCreacio
                
               
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                byte[] bytes_date_create = formatter.format(f.getDataCreacio()).getBytes(StandardCharsets.UTF_8);
                ByteBuffer date_create_buff = ByteBuffer.allocate(bytes_date_create.length);
                date_create_buff.put(bytes_date_create);
                
                fos.write(date_create_buff.array());
                insertSeparator(fos);
                
                //Data Mod
                
                ByteBuffer dataModIdentifier = ByteBuffer.allocate(2);
                if(f.getDataModificacio() != null){
                    dataModIdentifier.putChar('*');
                    fos.write(dataModIdentifier.array());
                    insertSeparator(fos);
                    
                    byte[] bytes_date_mod = formatter.format(f.getDataModificacio()).getBytes(StandardCharsets.UTF_8);
                    ByteBuffer date_mod_buff = ByteBuffer.allocate(bytes_date_mod.length);
                    date_mod_buff.put(bytes_date_mod);

                    fos.write(date_mod_buff.array());
                    insertSeparator(fos);
                }else{
                    dataModIdentifier.putChar('N'); 
                    fos.write(dataModIdentifier.array());
                    insertSeparator(fos);
                }    
                
                
                
                
                
                
                //Dades Especifiques
                
                if (f instanceof FitxaLlibre) {
                    FitxaLlibre fitxaLlibre = (FitxaLlibre) f;
                    
                    //Editorial
                    ByteBuffer editorialIdentifier = ByteBuffer.allocate(2);
                    if(fitxaLlibre.getEditorial() != null){
                        editorialIdentifier.putChar('*');
                        fos.write(editorialIdentifier.array());
                        insertSeparator(fos);

                        byte[] bytes_editorial = fitxaLlibre.getEditorial().getBytes(StandardCharsets.UTF_8);
                        ByteBuffer editorial_buff = ByteBuffer.allocate(bytes_editorial.length);
                        editorial_buff.put(bytes_editorial);

                        fos.write(editorial_buff.array());
                        insertSeparator(fos);
                        
                    }else{
                        editorialIdentifier.putChar('N'); 
                        fos.write(editorialIdentifier.array());
                        insertSeparator(fos);
                    }    
                    
                    
                    //ISBN
                    byte[] bytes_isbn = fitxaLlibre.getIsbn().getBytes(StandardCharsets.UTF_8);
                    ByteBuffer isbn_buff = ByteBuffer.allocate(bytes_isbn.length);
                    isbn_buff.put(bytes_isbn);

                    fos.write(isbn_buff.array());
                    insertSeparator(fos);
                    
                    
                } else if (f instanceof FitxaRevista) {
                    FitxaRevista fitxaRevista = (FitxaRevista) f;
                    
                    //Any
                    ByteBuffer bytes_any = ByteBuffer.allocate(4);
                    bytes_any.putInt(fitxaRevista.getAny());
                    fos.write(bytes_any.array());
                    insertSeparator(fos);
                    
                    //Numero
                    ByteBuffer bytes_num = ByteBuffer.allocate(4);
                    bytes_num.putInt(fitxaRevista.getAny());
                    fos.write(bytes_num.array());
                    insertSeparator(fos);
                }
                
                
                
                
                
            }
        } catch (IOException ex) {
            System.out.println("Error en intentar guardar fitxa");
            ex.printStackTrace();
        }
        
        try {
            fos.close();
        } catch (IOException ex) {
            System.out.println("No s'ha pogut tancar el fitxer: "+fileName);
            System.out.println(ex.getMessage());
            return;
        }
    }
    
    public static IBiblioteca loadBiblioteca(String fileName){
        
        
        FileInputStream fis;
        
        try {
            
            fis = new FileInputStream(fileName);

            //Llegir el nom;
            StringBuilder stringBuilder = new StringBuilder();
            int byteLeido;
            while ((byteLeido = fis.read()) != -1) {
                char caracter = (char) byteLeido;
                if (caracter == ';') {
                    break;
                }
                stringBuilder.append(caracter);
            }

            String name = stringBuilder.toString();

            System.out.println("\nBiblioteca: "+name);
            
            //Llegir autonumeric
            int autonumeric;
            byte[] fileOutput = new byte[4];
            if(fis.read(fileOutput) != -1){
                ByteBuffer bufferExit = ByteBuffer.wrap(fileOutput);
                autonumeric = bufferExit.getInt();
                System.out.println("Autonumeric: "+autonumeric);
            }else{
                System.out.println("Error llegint el fitcher");
                return null;
            }
            
            
            fis.read(); //Saltem el separador per anar al seguent valor;
            
            
            for(int i = 0; i < autonumeric; i++){
                //Llegit fitxes
                
                
            }
            
            
            
            fis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    private static void insertSeparator(FileOutputStream fos){
        ByteBuffer orderBytes = ByteBuffer.allocate(2);
        orderBytes.putChar(';');
        try {
            fos.write(orderBytes.array());
        } catch (IOException ex) {
            System.out.println("Error en intentar guardar el separador");
            ex.printStackTrace();
        }
    }
}
