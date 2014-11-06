/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gyhoevents.inventarserver.listeners;

import de.gyhoevents.inventarserver.GyHoInventarServer;
import de.gyhoevents.inventarserver.database.tables.Benutzer;

/**
 *
 * @author Programmieren
 */
public class LoginListener {

    public void bearbeiteNachricht(String pIp, int pPort, String[] cmd) {
        String user = cmd[1];
        String password = cmd[2];
        
        Benutzer b = GyHoInventarServer.getInstance().getDatenbank().find(Benutzer.class).where().like("username", user).findUnique();
        if(b == null){
            System.out.println("Benutzer nicht bekannt: " + user);
            GyHoInventarServer.getInstance().getServer().sendeAnEinen(pIp, pPort, "login failed");
        }else if (b.getPassword().equalsIgnoreCase( b.hashPassword(password))){
            System.out.println("Login: " + user);
            GyHoInventarServer.getInstance().getUsermanager().login(pIp + pPort, b);
            GyHoInventarServer.getInstance().getServer().sendeAnEinen(pIp, pPort, "login ok");
        }else{
            System.out.println("Passwort falsch: " + user + " gegeben " + b.hashPassword(password) + " sollte " + b.getPassword());
           GyHoInventarServer.getInstance().getServer().sendeAnEinen(pIp, pPort, "login failed"); 
        }
    }

}
