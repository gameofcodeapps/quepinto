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
    private UsuarioDTO usuarioLogeado = null;

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

    public int registrarUsuario(UsuarioDTO pUsuarioDTO) throws Exception {
        ConnectDBHelper.establecerConexionBD();

        return 0;
    }

    private  String encriptarMD5(String s) {
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
    public int verificarSiExisteUsuario(String pEmail, String pUsuario){

        String sqlUsuario = "SELECT count(*) from username=\""+pUsuario+"\"";
        String sqlEmail =  "SELECT count(*) from email=\""+pEmail+"\"";
        ResultSet resultSet = null;
        int resultadoEmail = 0;
        int resultadoFinal=0;
        try {
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




}
