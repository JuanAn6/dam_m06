/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exrepasraf_fos_fis;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan Antonio
 */
public class ExRepasRAF_FOS_FIS2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String fileName = "data_DOS_DIS.bin";
        DataOutputStream dos;
        
        try {
            //Append: true,  per començar al final, false per a eliminar el contingut;    
            dos = new DataOutputStream( new FileOutputStream(fileName)); 
            
        } catch (FileNotFoundException ex) {
            System.out.println("No s'ha pogut crear el fitxer: "+fileName);
            System.out.println(ex.getMessage());
            return;
        }
        
        
        
        for(int i = 1; i <= 300; i++){
            try {    
                dos.writeInt(i);
            } catch (IOException ex) {
                System.out.println("Error en enregistrar en el fitxer: "+fileName);
            }
        }
        
        
        try {
            dos.close();
        } catch (IOException ex) {
            System.out.println("Error en tancar"+ ex.getMessage());
        }
        
        
        //Lectura FIS
        
        DataInputStream dis;
        
        try {
            dis = new DataInputStream (new FileInputStream(fileName));
            try {
                while (true) {
                    System.out.println("Output DOS_DIS: " + dis.readInt());                    
                }
            }catch(EOFException ex){ //Final del bucle, retorna la exception quan no troba més elements per llegir
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                return;
            }
            
            dis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
}
