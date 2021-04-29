package com.gameofcode.quepinto.models;

import android.util.Log;

import com.gameofcode.quepinto.DTO.EventoDTO;
import com.gameofcode.quepinto.DTO.UsuarioDTO;
import com.gameofcode.quepinto.helpers.ConnectDBHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public  class UsuarioModel {

    private static UsuarioModel instance = null;
    public  UsuarioDTO usuarioLogeado = null;

    public  UsuarioModel(){

    }

    public static UsuarioModel getInstance(){
        if(instance==null){
            instance = new UsuarioModel();
        }
        return instance;
    }

    public  UsuarioDTO obtenerDatosUsuario(String pNombre, String pPassword){

        String sql="SELECT * FROM auth_user where username=\""+pNombre+"\" and password=\""+encriptarMD5(pPassword)+"\" limit 1";
        ResultSet resultSet = null;
        UsuarioDTO usuarioDevuelto = new UsuarioDTO();
        try {
            ConnectDBHelper.establecerConexionBD();
            resultSet = ConnectDBHelper.ejecutarSQL(sql);
            while (resultSet.next()) {
                usuarioDevuelto.setId(resultSet.getString(1));
                usuarioDevuelto.setPassword(resultSet.getString(2));
                usuarioDevuelto.setLastLogin((resultSet.getString(3)));
                usuarioDevuelto.setIsSuperUser(resultSet.getString(4));
                usuarioDevuelto.setUsername(resultSet.getString(5));
                usuarioDevuelto.setFirstName((resultSet.getString(6)));
                usuarioDevuelto.setEmail(resultSet.getString(7));
                usuarioDevuelto.setIsStaff(resultSet.getString(8));
                usuarioDevuelto.setIsActive(resultSet.getString(9));
                usuarioDevuelto.setDateJoined(resultSet.getString(10));
                usuarioDevuelto.setLastName((resultSet.getString(11)));
            }
            ConnectDBHelper.desconectarBD();
        } catch (Exception e) {
            e.printStackTrace();
        }
        usuarioLogeado = usuarioDevuelto;
        return usuarioDevuelto;
    }

/*
    public  String encriptarMD5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
*/
    public String encriptarMD5(final String s) {
        try{
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for(int i = 0; i < messageDigest.length; i++){
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while(h.length() < 2){
                    h = "0" + h;
                }
                hexString.append(h);
            }
            return hexString.toString();
        }catch (NoSuchAlgorithmException e){
            Log.e("Error al encriptar MD5 ", " " + e.getMessage());
        }
        return "";
    }
    public UsuarioDTO getUsuarioLogeado() {
        return usuarioLogeado;
    }

    //Valores retorno
    /*
    0 = nombre de usuario y mail no registrado
    1 = email registrado
    2 = usuario registrado
    3 = nombre de usuario y mail registrado
     */
    public int verificarSiExisteUsuario(UsuarioDTO pUsuario){

        String sqlUsuario = "SELECT count(*) from auth_user where username=\""+pUsuario.getUsername()+"\"";
        String sqlEmail =  "SELECT count(*) from auth_user where email=\""+pUsuario.getEmail()+"\"";
        ResultSet resultSet = null;
        int resultadoEmail = 0;
        int resultadoFinal=0;
        try {
            Log.i("SQL",sqlUsuario);
            Log.i("SQL_email",sqlUsuario);
            ConnectDBHelper.establecerConexionBD();
            resultSet = ConnectDBHelper.ejecutarSQL(sqlUsuario);
            int resultadoUsuario = 0;
            while (resultSet.next()) {
                resultadoUsuario=resultSet.getInt(1);
            }
            resultSet = ConnectDBHelper.ejecutarSQL(sqlEmail);
            while (resultSet.next()) {
                resultadoEmail=resultSet.getInt(1);
            }
            //Si el usuario y el mail no estan registrados
            if (resultadoUsuario==0 && resultadoEmail==0){
                resultadoFinal=0;
            //Si el mail esta registrado
            }else if (resultadoEmail>0){
                resultadoFinal=1;
            //Si el nombre de usuario esta registrado
            }else if(resultadoUsuario>0){
                resultadoFinal=2;
            //Si el nombre de usuario y el mail estan registrados
            }else if(resultadoUsuario>0 && resultadoEmail>0){
                resultadoFinal=3;
            }
            ConnectDBHelper.desconectarBD();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultadoFinal;

    }

    public int crearUsuario(UsuarioDTO pUsuario){
        String sqlUltimoID =  "select  id from auth_user order by id desc limit 1";

        ResultSet resultSet = null;
        int ultimoID = 0;
        try {
            ConnectDBHelper.establecerConexionBD();
            resultSet = ConnectDBHelper.ejecutarSQL(sqlUltimoID);
            while(resultSet.next()){
                ultimoID = resultSet.getInt(1);

            }
            ultimoID = ultimoID+1;
            String sqlInsert="INSERT INTO auth_user (" +
                    "id," +
                    "password," +
                    "last_login," +
                    "is_superuser," +
                    "username," +
                    "first_name," +
                    "email," +
                    "is_staff," +
                    "is_active," +
                    "date_joined," +
                    "last_name)" +
                    "VALUES " +
                    "("+ultimoID+"," +
                    "\""+encriptarMD5(pUsuario.getPassword())+"\","+
                    "NOW(),"+
                    "0,"+
                    "\""+pUsuario.getUsername()+"\","+
                    "\""+pUsuario.getFirstName()+"\","+
                    "\""+pUsuario.getEmail()+"\","+
                    "0,"+
                    "1,"+
                    "NOW(),"+
                    "\""+pUsuario.getLastName()+"\""
                    +")";
            Log.i("SQL Insert",sqlInsert);
            Log.i("Password",pUsuario.getPassword());
            Log.i("MD5",encriptarMD5(pUsuario.getPassword()));
            int devuelveInsert = ConnectDBHelper.ejecutarSQLInsertUpdate(sqlInsert);
            Log.i("DevuelveInsert",String.valueOf(devuelveInsert));
            ConnectDBHelper.desconectarBD();
        }catch (Exception e){
            Log.i("error insert",e.getMessage());
            ConnectDBHelper.desconectarBD();
        }

        return ultimoID;
    }

    public boolean actulizarDatosUsuarioLogeado(){

        String sql= "UPDATE auth_user set first_name=\"" +
                usuarioLogeado.getFirstName()+"\", "+
                "email = \""+usuarioLogeado.getEmail()+"\", "+
                "last_name= \""+usuarioLogeado.getLastName()+"\" " +
                "WHERE " +
                "id="+usuarioLogeado.getId();

        ResultSet resultSet = null;
        try {
            ConnectDBHelper.establecerConexionBD();
            int devuelveInsert = ConnectDBHelper.ejecutarSQLInsertUpdate(sql);
            Log.i("DevuelveInsert",String.valueOf(devuelveInsert));
            ConnectDBHelper.desconectarBD();
        }catch (Exception e){
            Log.i("error insert",e.getMessage());
            ConnectDBHelper.desconectarBD();
            return false;
        }

        return true;

    }

    public  UsuarioDTO obtenerDatosUsuarioPorMail(String pMail){

        String sql="SELECT * FROM auth_user where email=\""+pMail+"\" limit 1";
        ResultSet resultSet = null;
        UsuarioDTO usuarioDevuelto = new UsuarioDTO();
        try {
            ConnectDBHelper.establecerConexionBD();
            resultSet = ConnectDBHelper.ejecutarSQL(sql);
            while (resultSet.next()) {
                usuarioDevuelto.setId(resultSet.getString(1));
                usuarioDevuelto.setPassword(resultSet.getString(2));
                usuarioDevuelto.setLastLogin((resultSet.getString(3)));
                usuarioDevuelto.setIsSuperUser(resultSet.getString(4));
                usuarioDevuelto.setUsername(resultSet.getString(5));
                usuarioDevuelto.setFirstName((resultSet.getString(6)));
                usuarioDevuelto.setEmail(resultSet.getString(7));
                usuarioDevuelto.setIsStaff(resultSet.getString(8));
                usuarioDevuelto.setIsActive(resultSet.getString(9));
                usuarioDevuelto.setDateJoined(resultSet.getString(10));
                usuarioDevuelto.setLastName((resultSet.getString(11)));
            }
            ConnectDBHelper.desconectarBD();
        } catch (Exception e) {
            ConnectDBHelper.desconectarBD();
            return usuarioDevuelto;
            //e.printStackTrace();
        }
        return usuarioDevuelto;
    }

    public boolean actulizarPassword(String pMail, String pPassword){

        String sql= "UPDATE auth_user set password=\"" +
                encriptarMD5(pPassword)+"\" "+
                "WHERE " +
                "email=\""+pMail+"\"";

        ResultSet resultSet = null;
        try {
            ConnectDBHelper.establecerConexionBD();
            int devuelveInsert = ConnectDBHelper.ejecutarSQLInsertUpdate(sql);
            Log.i("DevuelveInsert",String.valueOf(devuelveInsert));
            ConnectDBHelper.desconectarBD();
        }catch (Exception e){
            Log.i("error insert",e.getMessage());
            ConnectDBHelper.desconectarBD();
            return false;
        }

        return true;

    }


}
