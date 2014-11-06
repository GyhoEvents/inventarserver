/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.Niklas.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Programmieren
 */
public class Serververbindung extends Thread {

    private Socket socket;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;
    private PublicKey clientKey;
    private SichererServer server;
    private Key aesKey;

    public Serververbindung(Socket socket, SichererServer server) throws IOException {
        this.server = server;
        this.socket = socket;
        objectOut = new ObjectOutputStream(socket.getOutputStream());
        objectIn = new ObjectInputStream(socket.getInputStream());
        this.erzeugeAES();

        this.start();
    }

    public void beende() {
        try {
            this.socket.close();
            this.stop();
        } catch (IOException ex) {
            Logger.getLogger(Serververbindung.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void erzeugeAES() {
        try {
            // zufaelligen Key erzeugen
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(128);
            aesKey = keygen.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Serververbindung.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {
        try {
            //objectOut.writeObject(this.serverKey.getPublic());
            this.clientKey = (PublicKey) objectIn.readObject();
            
            objectOut.writeObject(this.verschluesseleRSA(this.aesKey.getEncoded()));

            server.bearbeiteVerbindungsaufbau(this.socket.getInetAddress().getHostAddress(), this.socket.getPort());
            while (socket.isConnected()) {
                byte[] s = (byte[]) this.objectIn.readObject();
                this.server.bearbeiteNachricht(socket.getInetAddress().getHostAddress(), socket.getPort(), this.entschluessele(s));
            }
            socket.close();
            this.server.getClients().remove(socket.getInetAddress().getHostAddress() + socket.getPort());
            this.stop();

        } catch (ClassNotFoundException | IOException ex) {

            try {
                socket.close();
            } catch (IOException ex1) {
                Logger.getLogger(Serververbindung.class.getName()).log(Level.SEVERE, null, ex1);
            }
            this.server.bearbeiteVerbindungsende(socket.getInetAddress().getHostAddress(), socket.getPort());
            this.server.beendeVerbindung(socket.getInetAddress().getHostAddress(), socket.getPort());

            this.stop();

        }
    }

    public byte[] verschluessele(String text) {

        try {
            // Verschluesseln
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, this.aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());

            return encrypted;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Serververbindung.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Serververbindung.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Serververbindung.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Serververbindung.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Serververbindung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public byte[] verschluesseleRSA(byte[] text) {

        PublicKey key = this.clientKey;
        byte[] cipherText = null;
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance("RSA");
            // encrypt the plain text using the public key
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherText;
    }

    public String entschluessele(byte[] text) {

        try {
            // Entschluesseln
            Cipher cipher2 = Cipher.getInstance("AES");
            cipher2.init(Cipher.DECRYPT_MODE, this.aesKey);
            byte[] cipherData2 = cipher2.doFinal(text);
            return new String(cipherData2);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Serververbindung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ObjectOutputStream getObjectOut() {
        return objectOut;
    }

}
