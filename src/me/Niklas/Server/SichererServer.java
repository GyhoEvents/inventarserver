/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.Niklas.Server;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Programmieren
 */
public class SichererServer {
    
    private HashMap<String, Serververbindung> clientSends;
 
    private boolean testmodus;
    
    public SichererServer(int pPort, boolean pTestmode){
       testmodus = pTestmode;
        clientSends = new HashMap<>();
      
        
        try {
            NetworkMainLoop nml = new NetworkMainLoop(pPort, this);
          
        } catch (IOException ex) {
            Logger.getLogger(SichererServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    protected void bearbeiteVerbindungsaufbau(String pIp, int pPort){
        
    }
    protected void bearbeiteVerbindungsende(String pClientIP, int pPartnerPort) {}
    protected void bearbeiteNachricht(String pIp, int pPort, String pNachricht){
        
    }
    public void beendeVerbindung(String pClientIP, int pPartnerPort){
        if(this.clientSends.get(pClientIP + pPartnerPort) != null){
            this.clientSends.get(pClientIP + pPartnerPort).beende();
            this.clientSends.remove(pClientIP + pPartnerPort);
        }
        
    }
    public void sendeAnEinen(String pIp, int pPort, String pNachricht){
        if(this.clientSends.get(pIp+ pPort) != null){
            try {
                this.clientSends.get(pIp+ pPort).getObjectOut().writeObject(this.clientSends.get(pIp+ pPort).verschluessele(pNachricht));
            } catch (IOException ex) {
                this.bearbeiteVerbindungsende(pIp, pPort);
                //Logger.getLogger(SichererServer.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }

    public boolean Testmodus() {
        return testmodus;
    }

    public HashMap<String, Serververbindung> getClients() {
        return clientSends;
    }

}
