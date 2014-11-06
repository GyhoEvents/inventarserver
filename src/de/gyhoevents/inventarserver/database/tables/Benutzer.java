/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gyhoevents.inventarserver.database.tables;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author Programmieren
 */
@Entity
@Table(name = "users")
public class Benutzer  {

    @Id
    int id;
    String username;
    String password;
    String mail;
    int groupid;
    @Version
    Timestamp lastUpdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

  

    public String hashPassword(String pw) {
        try {
            byte[] bytesOfMessage = pw.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] result = md.digest(bytesOfMessage);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < result.length; i++) {
                hexString.append(Integer.toHexString(0xFF & result[i]));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Benutzer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

 

}
