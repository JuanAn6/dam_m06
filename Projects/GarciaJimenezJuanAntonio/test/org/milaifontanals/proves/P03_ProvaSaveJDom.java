/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.proves;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.milaifontanals.FleetListPersistence;
import org.milaifontanals.FleetListPersistenceException;
import org.milaifontanals.data.Bus;
import org.milaifontanals.data.Fleet;
import org.milaifontanals.data.Taxi;

/**
 *
 * @author Usuari
 */
public class P03_ProvaSaveJDom {
    public static void main(String[] args) {
        List<Fleet> ll = new ArrayList();

        Fleet fleet = new Fleet("Autobusos Gotera");
        Bus b = new Bus("12345678901234561", 20.5F, false, 55);
        b.setRegistration("1111BBB");
        b.setRegistrationDate(new Date(2010 - 1900, 12 - 1, 25));
        fleet.addVehicle(b);
        Taxi t = new Taxi("12345678901234562", 13.5F, true, "ARG123");
        t.setRegistration("2222CCC");
        t.setRegistrationDate(new Date(2012 - 1900, 11 - 1, 24));
        fleet.addVehicle(t);
        b = new Bus("12345678901234563", 22.5F, true, 70);
        fleet.addVehicle(b);
        t = new Taxi("12345678901234564", 15.9F, false, "QTE123");
        fleet.addVehicle(t);
        ll.add(fleet);

        ll.add(new Fleet("Busos Averiats"));

        fleet = new Fleet("Mortadelo Busos");
        b = new Bus("32345678901234561", 20.5F, false, 55);
        b.setRegistration("1111ZZZ");
        b.setRegistrationDate(new Date(2010 - 1900, 12 - 1, 25));
        fleet.addVehicle(b);
        t = new Taxi("32345678901234562", 13.5F, true, "ARG123");
        t.setRegistration("2222YYY");
        t.setRegistrationDate(new Date(2012 - 1900, 11 - 1, 24));
        fleet.addVehicle(t);
        b = new Bus("32345678901234563", 22.5F, true, 70);
        fleet.addVehicle(b);
        t = new Taxi("32345678901234564", 15.9F, false, "QTE123");
        fleet.addVehicle(t);
        ll.add(fleet);

        System.out.println("ProvaSaveJDom");
        System.out.println("Llista de flotes a enregistrar:");
        for (Fleet f : ll) {
            System.out.println("\t"+f);
        }
        try{
            FleetListPersistence.saveFleetListJDom(ll,"llistaFlotes.xml");
        } catch (FleetListPersistenceException ex) {
            System.out.println("Error en enregistrar el fitxer xml");
            ex.printStackTrace();
        }
        System.out.println("Enregistrament efectuat!");
    }
}
