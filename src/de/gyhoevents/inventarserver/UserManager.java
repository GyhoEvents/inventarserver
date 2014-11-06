/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gyhoevents.inventarserver;

import de.gyhoevents.inventarserver.database.tables.Benutzer;
import de.gyhoevents.inventarserver.objects.Client;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Programmieren
 */
public class UserManager {
    private HashMap<String,Client> clients;


    public UserManager() {
        clients = new HashMap<>();
       
    }

    public HashMap<String, Client> getClients() {
        return clients;
    }
    public void login(String pip, Benutzer b){
        clients.get(pip).setEingeloggt(true);
        clients.get(pip).setBenutzer(b);        
    }
    
    
    
}
