package com.gameofcode.quepinto.interfaces;

import com.gameofcode.quepinto.DTO.UsuarioDTO;

public interface IResetearPasswordPresenter {

    public int enviarMailCodigoConfirmacion(String pMail);
    public boolean resetearPassword(String pPassword);
    public boolean validarCodigo(String pCodigo);
    public UsuarioDTO getUsuario();

}
