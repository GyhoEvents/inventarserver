/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gyhoevents.inventarserver;

import de.gyhoevents.inventarserver.listeners.LoginListener;
import de.gyhoevents.inventarserver.objects.Client;
import me.Niklas.Server.SichererServer;

/**
 *
 * @author Programmieren
 */
public class InventarServer extends SichererServer{
    
    private LoginListener login;

    public InventarServer(int pPort) {
        super(pPort, false);
        login  = new LoginListener();
    }

    @Override
    public void bearbeiteNachricht(String pIp, int pPort, String pNachricht) {
       
        String[] cmd = pNachricht.split(" ");
        if(cmd[0].equalsIgnoreCase("login")){
            login.bearbeiteNachricht(pIp, pPort, cmd);
        }
    }

    @Override
    public void bearbeiteVerbindungsende(String pIp, int pPort) {
        GyHoInventarServer.getInstance().getUsermanager().getClients().remove(pIp + "" + pPort);
    }

    @Override
    public void bearbeiteVerbindungsaufbau(String pIp, int pPort) {
        Client c = new Client(pIp,pPort);
        GyHoInventarServer.getInstance().getUsermanager().getClients().put(pIp + "" + pPort, c);
        
    }
    
    
}
