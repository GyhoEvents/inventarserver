/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gyhoevents.inventarserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Programmieren
 */
public class Config {

    private Properties prop;
    private FileOutputStream out;
    private FileInputStream in;

    File f = new File("server.properties");

    public Config() {
        prop = new Properties();
        try {
            if (!f.exists()) {
                //f.createNewFile();
                out = new FileOutputStream(f);
                neu();
            } else {
                in = new FileInputStream(f);
               
                lese();
            }
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void lese() {
        try {
            prop.load(in);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void schreibe() {
        try {       
             out = new FileOutputStream(f);
            prop.store(out, null);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void neu() {
       try {
            prop.setProperty("mysql.user", "mysqluser");
            prop.setProperty("mysql.password", "password");
            prop.setProperty("mysql.dbname", "dbname");

           
            prop.store(out, null);
            System.out.println("Config wurde angelegt bitte anpassen und dann neu starten ...");
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    public String getProperty(String key){
        return prop.getProperty(key);
    }

}
