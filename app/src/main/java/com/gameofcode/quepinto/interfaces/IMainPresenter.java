package com.gameofcode.quepinto.interfaces;

import com.gameofcode.quepinto.DTO.UsuarioDTO;

public interface IMainPresenter {

    public UsuarioDTO validarUsuario(String pUsuarrio, String pPassword);
}
