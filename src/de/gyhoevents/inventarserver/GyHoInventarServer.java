/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gyhoevents.inventarserver;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;

/**
 *
 * @author Programmieren
 */
public class GyHoInventarServer {
    
    //Datenbank
    private EbeanServer datenbank;
    private Config config;
    private static GyHoInventarServer instance;
    private UserManager usermanager;
    private InventarServer server;
    
    public GyHoInventarServer() {
        instance =this;
        config = new Config();
        verbindeDatenbank();
        usermanager = new UserManager();
        
        server = new InventarServer(12345);
    }
    
    private void verbindeDatenbank() {
        // Datenbank verbinden
        ServerConfig c = new ServerConfig();
        c.setName("TicTacToe");

        // Mysql einstellungen (Eigene Daten eintragen ) 
        DataSourceConfig mysql = new DataSourceConfig();
        mysql.setDriver("com.mysql.jdbc.Driver");
        
        mysql.setUsername(config.getProperty("mysql.user"));
        mysql.setPassword(config.getProperty("mysql.password"));
        mysql.setUrl("jdbc:mysql://127.0.0.1:3306/" + config.getProperty("mysql.dbname"));
        mysql.setHeartbeatSql("select count(*) from users");

        c.setDataSourceConfig(mysql);

        c.setDdlGenerate(true);
        //wenn tabbelen generiert werden sollen beim ersten start auf true setzen
        c.setDdlRun(false);

        c.setDefaultServer(false);
        c.setRegister(true);

        c.addPackage("de.gyhoevents.inventarserver.database.tables");

        datenbank = EbeanServerFactory.create(c);

    }

    public static GyHoInventarServer getInstance() {
        return instance;
    }

    public UserManager getUsermanager() {
        return usermanager;
    }

    public EbeanServer getDatenbank() {
        return datenbank;
    }

    public InventarServer getServer() {
        return server;
    }
}
