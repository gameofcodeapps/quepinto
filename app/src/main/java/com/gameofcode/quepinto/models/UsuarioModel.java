package com.gameofcode.quepinto.models;

import com.gameofcode.quepinto.DTO.UsuarioDTO;
import com.gameofcode.quepinto.helpers.ConnectDBHelper;

import java.sql.ResultSet;

public class UsuarioModel {



    public UsuarioModel(){

    }

    public UsuarioDTO ObtenerDatosUsuario(String pNombre, String pPassword){
        String sql="SELECT * FROM auth_user where username=\""+pNombre+"\" limit 1";
        ResultSet resultSet = null;
        UsuarioDTO usuarioDevuelto = new UsuarioDTO();
        System.out.println("hola");

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
        return usuarioDevuelto;
    }
}
