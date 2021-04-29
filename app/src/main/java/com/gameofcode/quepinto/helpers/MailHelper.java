package com.gameofcode.quepinto.helpers;

import android.util.Log;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MailHelper {


    public static boolean enviarMail(String pEmail,String pUsuario,String pNombre, String pCodigo) throws  Exception{
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("email", pEmail)
                .addFormDataPart("codigo", pCodigo)
                .addFormDataPart("usuario", pUsuario)
                .addFormDataPart("nombre", pNombre)
                .build();

        Request request = new Request.Builder()
                .url("https://quepinto.pythonanywhere.com/home/enviarMail")
                .post(requestBody)
                .build();

            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                Log.i("error mail",response.toString());
                return false;
            }
            /*
            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }*/

            //Log.i("resultado POST",response.body().string());
            return true;

        }
    }


