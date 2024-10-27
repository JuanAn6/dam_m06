package org.milaifontanals.biblioteca.persistence;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Iterator;
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
public class BPFitxerBinariManualDos {
    
    protected BPFitxerBinariManualDos() {
        //para que no se pueda crear objeto de la clase!!
    }
    
    
    public static void saveBiblioteca(IBiblioteca b, String fileName){
        
        if(b == null || fileName == null){
            throw new RuntimeException("S'ha passat un paràmetre NULL");
        }
        
        FileOutputStream fos;
        
        try {
            //Append: true,  per començar al final, false per a eliminar el contingut;    
            fos = new FileOutputStream(fileName, false); 
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("Error", ex);
        }
        
        DataOutputStream dos = new DataOutputStream(fos);
        
        try{
            dos.writeUTF(b.getClass().getName());
            dos.writeUTF(b.getNom());
            dos.writeInt(Fitxa.getComptador());
            Iterator<Fitxa> it = b.recuperarFitxesOrdenadesPerReferencia();
                        
            while(it.hasNext()){
                Fitxa f = it.next();
            
                if (f instanceof FitxaLlibre) {
                    saveFitxaLlibre(f,dos);
                } else if (f instanceof FitxaRevista) {
                    saveFitxaRevista(f,dos);
                }else{
                    throw new RuntimeException("El metode no esta preparat per aquest tipus de clase");
                }
                
            }
            
        }catch(IOException ex ){
            ex.getStackTrace();
        }
        
        
        try {
            dos.close();
        } catch (IOException ex) {
            throw new RuntimeException("Error", ex);
        }
    }
    
    public static IBiblioteca loadBiblioteca(String fileName){
        
        
        if(fileName == null){
            throw new RuntimeException("S'ha passat un paràmetre NULL");
        }
        
        FileInputStream fis;
        
        try {
            //Append: true,  per començar al final, false per a eliminar el contingut;    
            fis = new FileInputStream(fileName); 
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("Error", ex);
        }
        
        DataInputStream dis = new DataInputStream(fis);
        IBiblioteca b = null;
        
        try{
            String clase = dis.readUTF();
            String nom = dis.readUTF();
            
            try {
                Constructor c = Class.forName(clase).getConstructor(String.class);
                b = (IBiblioteca) c.newInstance(nom);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException("Error", ex);
        }
            
            
            Fitxa.setComptador(dis.readInt()); //cojerlo al final para que este actualizado
     
            try{
                while(true){

                    char typeFitxa = dis.readChar();

                    if (typeFitxa == 'L') {
                        b.afegirFitxa(loadFitxaLlibre(dis));
                    } else if (typeFitxa == 'R') {
                        b.afegirFitxa(loadFitxaRevista(dis));
                    }else{
                        throw new RuntimeException("El metode no esta preparat per aquest tipus de clase");
                    }

                }
            }catch(EOFException ex){}
            
            
        }catch(IOException ex ){
            ex.getStackTrace();
        }
        
        
        try {
            dis.close();
        } catch (IOException ex) {
            System.out.println("No s'ha pogut tancar el fitxer: "+fileName);
            System.out.println(ex.getMessage());
            return null;
        }
        
        return b;
    }
    
    
    private static void saveFitxa(Fitxa f , DataOutputStream dos) throws IOException{
     
        dos.writeUTF(f.getReferencia());
        dos.writeUTF(f.getTitol());
        
        if(f.getEsDeixa() == null){
            dos.writeChar('N');
        }else{
            dos.writeChar('*');
            dos.writeBoolean(f.getEsDeixa());
        }
        
        dos.writeInt(f.getOrdre());
        dos.writeLong(f.getDataCreacio().getTime());
        
        if(f.getDataModificacio() == null){
            dos.writeChar('N');
        }else{
            dos.writeChar('*');
            dos.writeLong(f.getDataModificacio().getTime());        
        }
    }
    
    private static void saveFitxaLlibre(Fitxa f , DataOutputStream dos) throws IOException{
        dos.writeChar('L');
        saveFitxa(f,dos);
        
        FitxaLlibre fl = (FitxaLlibre) f;
        if(fl.getEditorial() == null){
            dos.writeChar('N');
        }else{
            dos.writeChar('*');
            dos.writeUTF(fl.getEditorial());
        }
        dos.writeUTF(fl.getIsbn());
    }
    
    private static void saveFitxaRevista(Fitxa f , DataOutputStream dos) throws IOException{
        dos.writeChar('R');
        saveFitxa(f,dos);
        
        FitxaRevista fr = (FitxaRevista) f;
        dos.writeInt(fr.getAny());
        dos.writeInt(fr.getNum());
    }
    
    
    private static FitxaLlibre loadFitxaLlibre(DataInputStream dis) throws IOException{
        
        
        FitxaLlibre f = new FitxaLlibre(
            dis.readUTF(),
            dis.readUTF(),
            (dis.readChar() == '*' ? dis.readBoolean() : null),
            dis.readInt(),
            new Date(dis.readLong()),
            (dis.readChar() == '*' ? new Date(dis.readLong()) : null),
            (dis.readChar() == '*' ? dis.readUTF() : null),
            dis.readUTF()
        );
        return f;
    }
    
    private static FitxaRevista loadFitxaRevista(DataInputStream dis) throws IOException{
        
        FitxaRevista f = new FitxaRevista(
            dis.readUTF(),
            dis.readUTF(),
            (dis.readChar() == '*' ? dis.readBoolean() : null),
            dis.readInt(),
            new Date(dis.readLong()),
            (dis.readChar() == '*' ? new Date(dis.readLong()) : null),
            dis.readInt(),
            dis.readInt()
        );
        return f;
    }
}
