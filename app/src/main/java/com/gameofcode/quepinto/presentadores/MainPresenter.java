package com.gameofcode.quepinto.presentadores;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.gameofcode.quepinto.DTO.UsuarioDTO;
import com.gameofcode.quepinto.helpers.PreferenciasSistema;
import com.gameofcode.quepinto.interfaces.IMainPresenter;
import com.gameofcode.quepinto.models.UsuarioModel;

public class MainPresenter  implements IMainPresenter  {

    @Override
    public boolean validarUsuario(String pUsuarrio, String pPassword,Context context) {

        String usuarioGrabado = PreferenciasSistema.leerPreferencia(context,"usuario");
        String passwordGrabado= PreferenciasSistema.leerPreferencia(context,"password");;
        UsuarioModel instance = UsuarioModel.getInstance();
        UsuarioDTO usuarioDTO;
        Log.i("usuario",usuarioGrabado);
        if(usuarioGrabado.isEmpty() || !pUsuarrio.isEmpty()){
            usuarioDTO=instance.obtenerDatosUsuario(pUsuarrio,pPassword);
            //Si el usuario se logea, se guarda el usuario y la clave
            PreferenciasSistema.agregarPreferencia(context,"usuario",pUsuarrio);
            PreferenciasSistema.agregarPreferencia(context,"password",pPassword);
        }else{
            usuarioDTO=instance.obtenerDatosUsuario(usuarioGrabado,passwordGrabado);
        }

        if (usuarioDTO.getUsername()==null){
            return false;
        }else{
            return true;
        }
    }

    public boolean existeUsuarioGrabado(Context context){

        //SharedPreferences miPreferencia  = getSharedPreferences("DatosPersonalesQuePinto", Context.MODE_PRIVATE);


        String usuario = PreferenciasSistema.leerPreferencia(context,"usuario");
        if(usuario.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
}
