/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.proves;

import java.util.List;
import org.milaifontanals.FleetListPersistence;
import org.milaifontanals.FleetListPersistenceException;
import org.milaifontanals.data.Fleet;

/**
 *
 * @author Usuari
 */
public class P02_ProvaLoadBin {

    public static void main(String[] args) {
        System.out.println("ProvaLoadBIN");  
        List<Fleet> ll = null;
        try{
            ll = FleetListPersistence.loadFleetBin("llistaFlotes.bin");
        } catch (FleetListPersistenceException ex) {
            System.out.println("Error en carregar el fitxer binari");
            ex.printStackTrace();
        }
        System.out.println("Llista recuperada:");
        for (Fleet f : ll) {
            System.out.println("\t" + f);
        }
    }
}
