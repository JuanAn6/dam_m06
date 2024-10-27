/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package activitatsfitxers;

import java.io.File;

/**
 *
 * @author Juan Antonio
 */
public class Activitat4 {
    
    public static void mostrarDir(File dir, int i){
        String separator = "  ".repeat(i);
        
        File[] files = dir.listFiles();
        
        if (files != null) {
            for(File f : files){
                if(f.exists() && f.isDirectory()){
                    System.out.println(separator+" - "+f.getName());
                    mostrarDir(f, i+1);
                }else{
                    System.out.println(separator+"   "+f.getName());
                }    
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        File f = new File(".\\");

        if(f.exists() && f.isDirectory()){
            System.out.println("-"+f.getName());
            mostrarDir(f, 0);
        }else{
            System.out.println("No es un directori o no existeix");
        }
        
    }
}
