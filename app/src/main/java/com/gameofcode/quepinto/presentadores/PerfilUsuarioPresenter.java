package com.gameofcode.quepinto.presentadores;

import com.gameofcode.quepinto.interfaces.IPerfilUsuarioPresenter;
import com.gameofcode.quepinto.models.UsuarioModel;

public class PerfilUsuarioPresenter implements IPerfilUsuarioPresenter {


    @Override
    public boolean actulizarDatosUsuarioLogeado() {
        return UsuarioModel.getInstance().actulizarDatosUsuarioLogeado();

    }
}
