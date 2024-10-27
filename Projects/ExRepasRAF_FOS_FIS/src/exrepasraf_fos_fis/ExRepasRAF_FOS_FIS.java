/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exrepasraf_fos_fis;

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
public class ExRepasRAF_FOS_FIS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String fileName = "data.bin";
        FileOutputStream fos;
        
        try {
            //Append: true,  per començar al final, false per a eliminar el contingut;    
            fos = new FileOutputStream(fileName, false); 
        } catch (FileNotFoundException ex) {
            System.out.println("No s'ha pogut crear el fitxer: "+fileName);
            System.out.println(ex.getMessage());
            return;
        }
        
        ByteBuffer buffer_int = ByteBuffer.allocate(4);
        ByteBuffer buffer_short = ByteBuffer.allocate(2);
        
        for(int i = 1; i <= 100; i++){
            if( i % 2 == 0){
                buffer_short.clear();
                buffer_short.putShort((short) i);
                try {    
                    fos.write(buffer_short.array());
                } catch (IOException ex) {
                    System.out.println("Error en enregistrar en el fitxer: "+fileName);
                }
            }else{
                buffer_int.clear();
                buffer_int.putInt(i);
                try {    
                    fos.write(buffer_int.array());
                } catch (IOException ex) {
                    System.out.println("Error en enregistrar en el fitxer: "+fileName);
                }
            }
        }
        
        
        try {
            fos.close();
        } catch (IOException ex) {
            System.out.println("Error en tancar"+ ex.getMessage());
        }
        
        
        //Lectura FIS
        
        FileInputStream fis;
        
        try {
            try {
                fis = new FileInputStream(fileName);
                int size = fis.available();
                System.out.println("File size: "+size);
                
                int i = 1;
                int aux = 0;
                while ( aux != -1 ) {
                    if (i % 2 == 0){
                       
                        byte[] fileOutput = new byte[2];
                        if( ( aux = fis.read(fileOutput)) != -1){
                            ByteBuffer bufferExit = ByteBuffer.wrap(fileOutput);
                            short number = bufferExit.getShort();
                            System.out.println("Output Short: " + number);
                        }
                        
                        
                    }else{
                        
                        byte[] fileOutput = new byte[4];
                        if( (aux = fis.read(fileOutput)) != -1){
                            ByteBuffer bufferExit = ByteBuffer.wrap(fileOutput);
                            int number = bufferExit.getInt();
                            System.out.println("Output Int: " + number);
                        }
                        
                    }
                    
                    i++;
                    
                }
                
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                return;
            }
            
            fis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
        
        
        
        


        //Random acces file
        
        String fileNameRaf = "data_raf.bin";
        File f = new File(fileNameRaf);
        if(f.exists() && f.isFile() && !f.delete()){
            System.out.println("Error el fitxer existeis i no es pot eliminar, abroterm i sortim");
            return;
        }
        
        RandomAccessFile raf;
        
        try {
            //Append: true,  per començar al final, false per a eliminar el contingut;    
            raf = new RandomAccessFile(fileNameRaf, "rw"); 
        } catch (FileNotFoundException ex) {
            System.out.println("No s'ha pogut crear el fitxer: "+fileNameRaf);
            System.out.println(ex.getMessage());
            return;
        }
        
       
        
        for(int i = 1; i <= 100; i++){
            if( i % 2 == 0){
                try {
                    raf.writeShort( (short) i);
                } catch (IOException ex) {
                    System.out.println("Error en enregistrar en el fitxer: "+fileNameRaf);
                }
            }else{
                try {
                    raf.writeInt(i);
                } catch (IOException ex) {
                    System.out.println("Error en enregistrar en el fitxer: "+fileNameRaf);
                }
            }
            
        }
        
        try {
            raf.close();
        } catch (IOException ex) {
            System.out.println("No s'ha pogut tancar el fitxer: "+fileNameRaf);
            System.out.println(ex.getMessage());
            return;
        }
        
        
        
        RandomAccessFile fos2;
        
        try {
            try {
                
                fos2 = new RandomAccessFile(fileNameRaf, "r");
                
                try{
                    int i = 1;
                    while ( true ) {
                        if(i % 2 == 0){
                            System.out.println("Output short: " + fos2.readShort()); //Retorna exception quan acaba
                        }else{
                            System.out.println("Output int: " + fos2.readInt()); //Retorna exception quan acaba
                        }
                        i++;
                    }
                }catch(EOFException ex){} // final de bucle
                
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                return;
            }
            
            fos2.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
        
        
    }
    
}
