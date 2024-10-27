/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package activitatsfitxers;

import java.io.File;
import java.util.Date;

/**
 *
 * @author Juan Antonio
 */
public class Activitat3 {
 /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        File f = new File("\\A\\B\\carpeta");
        
        if(f.mkdir()){
            System.out.println("Creat correctament amb mkdir()");
        }else{
            System.out.println("Error amb mkdir()");
        }
        
        if(f.mkdirs()){
            System.out.println("Creat correctament amb mkdirs()");
        }else{
            System.out.println("Error amb mkdirs()");
        }
        
        
        System.out.println("get getName"+f.getName());
        System.out.println("get getParent"+f.getParent());
        System.out.println("get getAbsolutePath"+f.getAbsolutePath());
        System.out.println("get getPath"+f.getPath());
        System.out.println("get lastModified"+f.lastModified());
        
        Date d = new Date(f.lastModified());
        System.out.println("get lastModified"+d.toString());
        
        
    }   
    
}
