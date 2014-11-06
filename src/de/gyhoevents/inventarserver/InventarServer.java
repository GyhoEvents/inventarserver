/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gyhoevents.inventarserver;

import me.Niklas.Server.SichererServer;

/**
 *
 * @author Programmieren
 */
public class InventarServer extends SichererServer{

    public InventarServer(int pPort) {
        super(pPort, false);
    }

    @Override
    public void bearbeiteNachricht(String pIp, int pPort, String pNachricht) {
        
    }

    @Override
    public void bearbeiteVerbindungsende(String pClientIP, int pPartnerPort) {
        
    }

    @Override
    public void bearbeiteVerbindungsaufbau(String pIp, int pPort) {
        
    }
    
    
}
