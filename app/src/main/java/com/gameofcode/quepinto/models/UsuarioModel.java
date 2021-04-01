package com.gameofcode.quepinto.models;

import com.gameofcode.quepinto.DTO.UsuarioDTO;
import com.gameofcode.quepinto.helpers.ConnectDBHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;

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
}
