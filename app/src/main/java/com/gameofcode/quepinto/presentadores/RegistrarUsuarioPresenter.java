package com.gameofcode.quepinto.presentadores;

import com.gameofcode.quepinto.DTO.UsuarioDTO;
import com.gameofcode.quepinto.interfaces.IRegistrarUsuarioPresenter;
import com.gameofcode.quepinto.models.UsuarioModel;

public class RegistrarUsuarioPresenter implements IRegistrarUsuarioPresenter {

    @Override
    public int crearUsuario(UsuarioDTO pUsuario) {

        UsuarioModel usuModel = UsuarioModel.getInstance();
        int restuladoValidacion = usuModel.verificarSiExisteUsuario(pUsuario);
        if(restuladoValidacion == 0){
            usuModel.crearUsuario(pUsuario);
        }
        return restuladoValidacion;
    }
}
