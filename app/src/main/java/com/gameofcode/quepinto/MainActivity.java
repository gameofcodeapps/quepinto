package com.gameofcode.quepinto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.gameofcode.quepinto.helpers.ConnectDBHelper;

import java.sql.SQLException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this,"esto es solo un prueba, los stings van en el XML",Toast.LENGTH_LONG).show();





    }

}
