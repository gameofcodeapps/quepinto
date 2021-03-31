package com.gameofcode.quepinto.presentadores;

import com.gameofcode.quepinto.DTO.UsuarioDTO;
import com.gameofcode.quepinto.interfaces.IMainPresenter;
import com.gameofcode.quepinto.models.UsuarioModel;

public class MainPresenter implements IMainPresenter {

    @Override
    public UsuarioDTO validarUsuario(String pUsuarrio, String pPassword) {
        UsuarioModel usuarioModel = new UsuarioModel();
        UsuarioDTO usuarioDTO = usuarioModel.obtenerDatosUsuario(pUsuarrio, pPassword);
        return usuarioDTO;
    }
}
