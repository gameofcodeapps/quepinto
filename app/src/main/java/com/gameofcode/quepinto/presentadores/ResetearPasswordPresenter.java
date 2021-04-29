package com.gameofcode.quepinto.presentadores;

import android.content.Intent;
import android.util.Log;

import com.gameofcode.quepinto.DTO.UsuarioDTO;
import com.gameofcode.quepinto.helpers.MailHelper;
import com.gameofcode.quepinto.interfaces.IResetearPasswordPresenter;
import com.gameofcode.quepinto.models.EventoModel;
import com.gameofcode.quepinto.models.UsuarioModel;

public class ResetearPasswordPresenter implements IResetearPasswordPresenter {

    private String codigoAleatorio;
    private UsuarioDTO usuario;
    private static ResetearPasswordPresenter instance = null;

    public static ResetearPasswordPresenter getInstance(){
        if (instance == null){
            instance = new ResetearPasswordPresenter();
        }
        return instance;
    }

    @Override
    public int enviarMailCodigoConfirmacion(String pMail) {
        UsuarioModel usuarioModel = UsuarioModel.getInstance();
        usuario = usuarioModel.obtenerDatosUsuarioPorMail(pMail);
        codigoAleatorio=String.valueOf(Math.random()*100000).substring(0,5);
        if (!usuario.getEmail().isEmpty()) {
            try {
                MailHelper.enviarMail(usuario.getEmail(), usuario.getUsername(), usuario.getFirstName(), codigoAleatorio);
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                //Si hubo algun error al enviar el correo
                return 1;
            }
        }else{
            //Si el email no existe
            return 2;
        }


    }

    @Override
    public boolean resetearPassword(String pPassword) {
        UsuarioModel usuarioModel = UsuarioModel.getInstance();
        boolean b = usuarioModel.actulizarPassword(usuario.getEmail(), pPassword);
        return b;
    }

    @Override
    public boolean validarCodigo(String pCodigo){
        if(this.codigoAleatorio.equals(pCodigo)){
            return true;
        }else{
            return false;
        }
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }
}
