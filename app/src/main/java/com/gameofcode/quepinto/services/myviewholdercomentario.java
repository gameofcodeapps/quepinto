package com.gameofcode.quepinto.services;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gameofcode.quepinto.R;

public class myviewholdercomentario extends RecyclerView.ViewHolder
{

    TextView t1,t2,t3;
    public myviewholdercomentario(@NonNull View itemView)
    {
        super(itemView);
        t1=(TextView)itemView.findViewById(R.id.txtpersona);
        t2=(TextView)itemView.findViewById(R.id.comcomentario);
        t3=(TextView)itemView.findViewById(R.id.comfecha);

    }
}