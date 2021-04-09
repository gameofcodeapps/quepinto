package com.gameofcode.quepinto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class activity_main_googlesign extends AppCompatActivity {
    ImageView imageView2;
    TextView txtGNombre,txtGMail,txtGID;
    Button signOut;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_googlesign);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        imageView2 = findViewById(R.id.imageView2);
        txtGNombre = findViewById(R.id.txtGNom);
        txtGMail   = findViewById(R.id.txtGMail);
        txtGID     = findViewById(R.id.txtGID);
        signOut     = findViewById(R.id.btnSiO);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    // ...
                    case R.id.btnSiO:
                        signOut();
                        break;

                }
            }
        });


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            txtGNombre.setText(personName);
            txtGMail.setText(personEmail);
            txtGID.setText(personId);
            Glide.with(this).load(String.valueOf(personPhoto)).into(imageView2);
        }

    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(activity_main_googlesign.this, "Signed Out Succesfully", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }
}