package com.gameofcode.quepinto.helpers;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CompletableFuture;


public class ConnectDBHelper {

    private static int locaPort=5656;
    private static int remotePort=3306;
    private static String remoteHost="quepinto.mysql.pythonanywhere-services.com";
    private static String sshHost="ssh.pythonanywhere.com";
    private static String user ="quepinto";
    private static String password ="proyecto2020";
    private static String dbUserName ="quepinto";
    private static String dbPassword ="proyecto2020";
    private static String url = "jdbc:mysql://localhost:"+locaPort+"/quepinto$quepinto";
    private static Connection conn = null;
    private static Session session= null;
    private static String driverName="com.mysql.jdbc.Driver";

    public ConnectDBHelper(){

    }


    private static void establecerConexionBD() throws Exception {
        try{
            //Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            session=jsch.getSession(user, sshHost, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            System.out.println("Connected");
            int assinged_port=session.setPortForwardingL(locaPort, remoteHost, remotePort);
            System.out.println("localhost:"+assinged_port+" -> "+remoteHost+":"+remotePort);
            System.out.println("Port Forwarded");
        }catch(Exception e){
            //Capturo error si no se puedo crear tunel SSH
            throw e;
        }

        try{
            Class.forName(driverName).newInstance();
            conn = DriverManager.getConnection (url, dbUserName, dbPassword);
        }catch (Exception e){
            //En caso de error al conectar al BD
            throw e;
        }
    }

    private void desconectarBD() throws Exception {

        try {
            if(conn != null && !conn.isClosed()){
                System.out.println("Closing Database Connection");
                conn.close();
            }
            if(session !=null && session.isConnected()){
                System.out.println("Closing SSH Connection");
                session.disconnect();

            }
        }catch(Exception e) {
            throw e;
        }

    }

    public static ResultSet  ejecutarSQL(String pQuery) throws Exception {

        establecerConexionBD();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(pQuery);
        return rs;

    }


}
