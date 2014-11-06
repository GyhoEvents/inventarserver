/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.Niklas.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Programmieren
 */
public class NetworkMainLoop extends Thread{

    private ServerSocket server;
    private SichererServer sise;
    public NetworkMainLoop(int pPort, SichererServer sise) throws IOException{
            this.sise =sise;
            server = new ServerSocket(pPort);
            this.start();
            
    }
    @Override
    public void run() {
        Socket socket;
        System.out.println("Starte Serverdienst");
        while (true) {
            try {
                socket = server.accept();
                sise.getClients().put(socket.getInetAddress().getHostAddress()+socket.getPort(), new Serververbindung(socket, sise));
            } catch (IOException ex) {
                Logger.getLogger(NetworkMainLoop.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            }
    }
    
}
