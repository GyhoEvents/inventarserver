/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gyhoevents.inventarserver.objects;

import de.gyhoevents.inventarserver.database.tables.Benutzer;

/**
 *
 * @author Programmieren
 */
public class Client {
   private final String ip;
   private final int port;
   
   private boolean eingeloggt;
   private Benutzer benutzer;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public boolean isEingeloggt() {
        return eingeloggt;
    }

    public void setEingeloggt(boolean eingeloggt) {
        this.eingeloggt = eingeloggt;
    }

    public Benutzer getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
    }
   
   
    
}
