package com.gameofcode.quepinto.interfaces;

import android.app.Activity;
import android.content.Context;

import com.gameofcode.quepinto.DTO.UsuarioDTO;

public interface IMainPresenter {

    public boolean validarUsuario(String pUsuarrio, String pPassword,Context context);
    public boolean existeUsuarioGrabado(Context context);
}
