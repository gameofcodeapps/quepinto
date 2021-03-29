package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main3);

        final EditText UsuNom = findViewById(R.id.UsuNom);
        final EditText UsuApe = findViewById(R.id.UsuApe);
        final EditText UsuMail = findViewById(R.id.UsuMail);
        final EditText UsuPwd = findViewById(R.id.UsuPwd);
        final EditText UsuValPwd = findViewById(R.id.UsuValPwd);
        Button Enviar = (Button)findViewById(R.id.but_Env);

        Enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = UsuNom.getText().toString();
                String Apellido = UsuApe.getText().toString();
                String Mail = UsuMail.getText().toString();
                String Password = UsuPwd.getText().toString();
                String ConfPass = UsuValPwd.getText().toString();
               // registrar_usuario();
            }
    });
}
}

