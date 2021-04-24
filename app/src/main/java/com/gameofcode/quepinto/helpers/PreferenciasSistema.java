package com.gameofcode.quepinto.helpers;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PreferenciasSistema  {

    public static SharedPreferences preferenciasSistema;


    public static SharedPreferences obtener() {
        return preferenciasSistema;
    }

    public static void agregarPreferencia(Context context,String clave,String valor){
        preferenciasSistema = context.getSharedPreferences(context.getPackageName() + "_preferences", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferenciasSistema.edit();
        editor.putString(clave,valor);
        editor.commit();
    }

    public static String leerPreferencia(Context context,String clave){
        preferenciasSistema = context.getSharedPreferences(context.getPackageName() + "_preferences", context.MODE_PRIVATE);
        return preferenciasSistema.getString(clave,"");
    }



}
