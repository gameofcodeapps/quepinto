package com.gameofcode.quepinto.presentadores;

import com.gameofcode.quepinto.DTO.UsuarioDTO;
import com.gameofcode.quepinto.interfaces.IMainPresenter;
import com.gameofcode.quepinto.models.UsuarioModel;

public class MainPresenter implements IMainPresenter {

    @Override
    public boolean validarUsuario(String pUsuarrio, String pPassword) {

        UsuarioModel instance = UsuarioModel.getInstance();
        UsuarioDTO usuarioDTO=instance.obtenerDatosUsuario(pUsuarrio,pPassword);
        if (usuarioDTO.getUsername()==null){
            return false;
        }else{
            return true;
        }
    }
}
